# Мышление на уровне дизайна и чем в этом может помочь TDD
## Введение
В данной небольшой заметке я бы хотел рассмотреть то, как наиболее эффективно думать о
программе. Как лучше всего поместить себе в сознание данную абстракцию и получить доступ
к самым наилучшим инструментам для работы с ней. Действительно важный, и возможно даже революционый концепт для многих
это то, что о программе можно думать на трех уровнях - уровне исполнения, уровне кода и на уровне дизайна. И именно 
уровень дизайна - это самый сильный способ мышления о программе. Пока на уровне исполнения сложно сместиться с акцента 
на состояниях программы и их изменениях, а на уровне кода отдалиться от конкретной реализации, на уровне дизайна возможно 
добиться получить представление на уровне не только текущих зависимостей и ограничений, но и будущих, осознать предвещающиеся
упущенные возможности и допущенные провалы которые непременно о себе напомнят.

## Посмотрим на практике
Главной целью и одновременно инструменом достижения мышления на уровне дизайна является язые логики. Если же 
присутствует возможность выразить будущую спецификацию с помощью формул (например благодаря Higher-order logic) это само 
по себе позволит по другому взглянуть на зависимости между элементами и возможными сценариями.
Однако чтобы начать движение к правильныму формату измышлений, возможно начать с небольшого шага, а именно - использование 
TDD.

Для примера ДО будет использоваться код для коннектора на базе Airbyte Python CDK который был написан по базовым принципам TDD,
даже точнее будет сказать - по тем основам которые понял автор при первичном его освоении. Далее будет представлена модифицированная 
версия данного отрезка кода, который будет более полно соответствовать концепции на уровне размышления в рамках языка логики и на уровне дизайна, 
нежели уровне кода или исполнения. Оснообразующей логикой является то, что **"Код не должен следовать тестам, ни тесты не должны следовать коду. И тесты, и код должны следовать дизайну, логической архитектуре."**

### Вариант "ДО"
Набор некоторых юниттестов и дальнейшая реализация:
```python
import pytest
import requests
from unittest.mock import MagicMock, patch
from source import AbstractGGGAPITlmStream, SCcMetrics

class TestAbstractGGGAPITlmStream:
    @pytest.fixture
    def config(self):
        return {
            "ggg_api_token": "",
            "tlm_type": "",
            "encryption_key": ""
        }

    def test_init(self, config):
        stream = AbstractGGGAPITlmStream(config)
        assert stream.config == config
        assert stream.tlm_type == config["tlm_type"]
        assert stream.encryption_key == bytes(config["encryption_key"], encoding='utf8')

    def test_path(self):
        stream = AbstractGGGAPITlmStream({})
        assert stream.path() == "tttt/lst/"

    def test_get_updated_state(self):
        stream = AbstractGGGAPITlmStream({})
        current_state = {"tedl_id": 1}
        latest_record = {"id": 5}
        updated_state = stream.get_updated_state(current_state, latest_record)
        assert updated_state == {"tedl_id": 5}

    def test_next_page_token(self):
        stream = AbstractGGGAPITlmStream({})
        response_mock = MagicMock()
        response_mock.json.return_value = [{"id": 1}, {"id": 2}, {"id": 3}]
        token = stream.next_page_token(response_mock)
        assert token == {"tedl_id": 4}

class TestSCcMetrics:
    @pytest.fixture
    def config(self):
        return {
            "ggg_api_token": "",
            "encryption_key": ""
        }

    def test_parse_response(self, config):
        stream = SCcMetrics(config)
        response_mock = MagicMock()
        response_mock.json.return_value = [{"id": 1, "key": "key1"}, {"id": 2, "key": "key2"}]
        with patch("requests.get") as mock_get:
            mock_get.return_value.content = b"dummy_content"
            parsed_records = list(stream.parse_response(response_mock))

        assert len(parsed_records) == 2
        assert parsed_records[0]["period_from"] == "period_from_value"
        assert parsed_records[0]["period_to"] == "period_to_value"
        assert parsed_records[0]["dataVersion"] == "data_version_value"

    def test_enr_reco(self, config):
        stream = SCcMetrics(config)
        record = {"id": 1, "key": "key1"}

        with patch("requests.get") as mock_get:
            mock_get.return_value.content = b"dummy_content"
            enriched_records = list(stream.enr_reco(record))

        assert len(enriched_records) == 1
        assert enriched_records[0]["name"] == "metric_name"
        assert enriched_records[0]["objectId"] == "object_id"
```

Итоговый код:
```python
class AbstractGGGAPITlmStream(HttpStream, ABC):
    # TODO make methods from vars or not, think about it
    state_checkpoint_interval = 1
    url_base = URL_BASE  # Base URL for the API
    primary_key = "id"  # Primary key of the stream

    def __init__(self, config: Mapping[str, Any]):
        super().__init__(authenticator=CustomGGGAPITokenAuthenticator(token=config["ggg_api_token"]))
        self.config = config
        self.tlm_type = self.config["tlm_type"]
        self.encryption_key = bytes(self.config['encryption_key'], encoding='utf8')

    def path(self, **kwargs) -> str:
        return "tttt/lst/"

    def get_updated_state(self, current_stream_state: MutableMapping[str, Any], latest_record: Mapping[str, Any]) -> Mapping[str, Any]:
        current_state_id = current_stream_state.get("tedl_id", 0)
        latest_record_id = latest_record.get("id", 0)
        next_state_id = max(current_state_id, latest_record_id)
        return {"tedl_id": next_state_id}

    def next_page_token(self, response: requests.Response) -> Optional[Mapping[str, Any]]:
        records = response.json()
        if not records:
            return None 

        last_record_id = records[-1].get("id")
        return {"tedl_id": last_record_id + 1}

    def request_params(
            self, stream_state: Mapping[str, Any] = None, stream_slice: Mapping[str, Any] = None, next_page_token: Mapping[str, Any] = None
    ) -> MutableMapping[str, Any]:
        tedl_id = next_page_token["tedl_id"] if next_page_token else stream_state.get("tedl_id", 0)
        return {"type": self.tlm_type, "tedl_id": tedl_id, "count": 1}

    @property
    def cursor_field(self) -> Union[str, List[str]]:
        return "id"

    @property
    def source_defined_cursor(self) -> bool:
        return True

    @property
    def supported_sync_modes(self) -> List[str]:
        return ["incremental"]


class SCcMetrics(AbstractGGGAPITlmStream):
    def parse_response(self, response: requests.Response, **kwargs) -> Iterable[Mapping]:
        records = response.json()
        for record in records:
            enriched_records = self.enr_reco(record)
            for e_record in enriched_records:
                yield e_record | record

    def enr_reco(self, record) -> Sequence[dict]:
        params = {"tedl_id": record['id']}
        response = requests.get(
            url=f'{self.url_base}tld/pull/',
            headers={"Authorization": f"Token {self.config['ggg_api_token']}"},
            params=params)
        decrypted_zip_file = decrypt_tdm_file(response.content, self.encryption_key, record['key'])
        io_body = io.BytesIO(decrypted_zip_file)

        met_file_name = None
        meta_file_name = None
        archiv = zipfile.ZipFile(io_body)
        for name in archiv.namelist():
            if '_Patte_' in name:
                met_file_name = name
                continue
            if 'jood_Info_' in name:
                meta_file_name = name
                continue

        if not meta_file_name or not met_file_name:
            return []

        metrs_file = archiv.read(met_file_name)
        meta_file = archiv.read(meta_file_name)
        metrs_file = json.loads(metrs_file)
        meta_file = json.loads(meta_file)

        result_dict = dict()
        result_dict['period_from'] = meta_file.get('MMhookl', {})[0].get('period', {}).get('from')
        result_dict['period_to'] = meta_file.get('MMhookl', {})[0].get('period', {}).get('to')
        result_dict['dataVersion'] = meta_file.get('dataVersion')

        for metric in metrs_file.get('MMhookl'):
            result_dict['name'] = metric.get('name')
            result_dict['objectId'] = metric.get('objectId')
            result_dict['compilationError'] = metric.get('compilationError')
            result_dict['compilationErrorReason'] = metric.get('compilationErrorReason')
            result_dict['count'] = metric.get('count')
            yield result_dict

```

Тесты для нового коннектора:
### Вариант "ПОСЛЕ"
```python
import pytest
from unittest.mock import MagicMock, patch
from source import CustomConnector

class TestCustomConnector:
    @pytest.fixture
    def config(self):
        return {
            "api_token": "",
            "encryption_key": ""
        }

    def test_data_retrieval(self, config):
        connector = CustomConnector(config)
        expected_url = "https://******.com/api/data"
        expected_response = [{"id": 1, "name": "Record 1"}, {"id": 2, "name": "Record 2"}]

        with patch("requests.get") as mock_get:
            mock_get.return_value.json.return_value = expected_response
            data = connector.retrieve_data()

        assert data == expected_response
        mock_get.assert_called_once_with(expected_url, headers={"Authorization": f"Token {config['api_token']}"})

    def test_data_enrichment(self, config):
        connector = CustomConnector(config)
        record = {"id": 1, "name": "Record 1"}

        with patch("requests.get") as mock_get:
            mock_get.return_value.content = b"dummy_content"
            enriched_records = list(connector.enrich_data([record]))

        assert len(enriched_records) == 1
        assert "enriched_field" in enriched_records[0]

    def test_error_handling(self, config):
        connector = CustomConnector(config)
        with patch("requests.get") as mock_get:
            mock_get.side_effect = Exception("API error")
            with pytest.raises(Exception):
                connector.retrieve_data()

        with patch("requests.get") as mock_get:
            mock_get.return_value.content = b"dummy_content"
            with patch("source.decrypt_tdm_file") as mock_decrypt:
                mock_decrypt.side_effect = Exception("Decryption error")
                with pytest.raises(Exception):
                    connector.enrich_data([{"id": 1, "name": "Record 1"}])

    def test_incremental_sync(self, config):
        connector = CustomConnector(config)
        stream_state = {"last_processed_id": 5}

        with patch("requests.get") as mock_get:
            mock_get.return_value.json.return_value = [{"id": 6, "name": "Record 6"}, {"id": 7, "name": "Record 7"}]
            data = connector.retrieve_incremental_data(stream_state)
```

Код для нового коннектора:
```python
import requests
from typing import Mapping, Any, Optional, Iterable, Sequence, Union
from source import decrypt_tdm_file

class CustomConnector:
    def __init__(self, config: Mapping[str, Any]):
        self.config = config
        self.api_token = config["api_token"]
        self.encryption_key = bytes(config['encryption_key'], encoding='utf8')
        self.base_url = "https://****.com/api/"

    def retrieve_data(self) -> Iterable[Mapping[str, Any]]:
        url = f"{self.base_url}data"
        headers = {"Authorization": f"Token {self.api_token}"}
        response = requests.get(url, headers=headers)
        response.raise_for_status()
        return response.json()

    def enrich_data(self, records: Iterable[Mapping[str, Any]]) -> Iterable[Mapping[str, Any]]:
        for record in records:
            enriched_record = self._enrich_record(record)
            yield enriched_record

    def _enrich_record(self, record: Mapping[str, Any]) -> Mapping[str, Any]:
        params = {"id": record['id']}
        url = f"{self.base_url}enrich"
        headers = {"Authorization": f"Token {self.api_token}"}
        response = requests.get(url, headers=headers, params=params)
        response.raise_for_status()
        decrypted_content = decrypt_tdm_file(response.content, self.encryption_key, record['key'])
        enriched_record = {"enriched_field": "value"}
        return {**record, **enriched_record}

    def retrieve_incremental_data(self, stream_state: Mapping[str, Any]) -> Iterable[Mapping[str, Any]]:
        last_processed_id = stream_state.get("last_processed_id", 0)
        url = f"{self.base_url}data?last_processed_id={last_processed_id}"
        headers = {"Authorization": f"Token {self.api_token}"}
        response = requests.get(url, headers=headers)
        response.raise_for_status()
        return response.json()

```

## Вывод
Не смотря на то, что вариант "После" получился гораздо скромнее по размерам одна из главных концепций которую можно увидеть в
тестах для этой части это то насколько много внимания уделяется потенциально не положительным/не запланированным сценариям. Это
позволяет более глубоко и в более общем виде представить логику программу. Также стоит помнить, что даже в рамках достаточно ясной и понятной задачи,
которая в достаточной мере обрамлена фреймворком с регламентирующими абстрактными классами - есть возможность задуматься на уровень выше относительно будушего вашего кода и что, возможно,
в какой-то момент логика внутри модуля понадобиться в не его пределах. В примере после можно видеть как код следует единому дизайну - дизайну обработки
данных в рамках ETL процесса.
