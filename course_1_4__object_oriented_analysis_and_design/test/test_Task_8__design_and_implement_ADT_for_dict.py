import unittest
from course_1_4__object_oriented_analysis_and_design.Task_8__design_and_implement_ADT_for_dict import Dict


class TestHashTablePutOperation(unittest.TestCase):
    def test_put_operation__1(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        result = native_dict.is_key('txt1')
        self.assertEqual(result, True)

    def test_put_operation__2(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        result = native_dict.get('txt1')
        self.assertEqual(result, 19632)

    def test_put_operation__3(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.put('txt1', 19632)
        result = native_dict.get('txt1')
        self.assertEqual(result, 19632)

    def test_put_operation__4(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.put('txt2', 43513)
        result = native_dict.get('txt1')
        self.assertEqual(result, 19632)

    def test_put_operation__5(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.put('txt1', 43513)
        result = native_dict.get('txt1')
        self.assertEqual(result, 43513)

    def test_put_operation__6(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.put('txt2', 43513)
        native_dict.put('txt1', 43513)
        result = native_dict.get('txt1')
        self.assertEqual(result, 43513)

    def test_put_operation__7(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.delete('txt1')
        result = native_dict.get('txt1')
        self.assertEqual(result, None)

    def test_put_operation__8(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.delete('txt1')
        result = native_dict.get_put_status()
        self.assertEqual(result, native_dict.GET_OK)

    def test_put_operation__9(self):
        native_dict = Dict()
        result = native_dict.get_put_status()
        self.assertEqual(result, native_dict.GET_NIL)

    def test_put_operation__10(self):
        native_dict = Dict()
        for i in range(256):
            native_dict.put(str(i), i*100)
        result = native_dict.get_put_status()
        self.assertEqual(result, native_dict.GET_OK)

    def test_put_operation__11(self):
        native_dict = Dict()
        for i in range(256):
            native_dict.put(str(i), i*100)
        native_dict.put('overflow_err', 999)
        result = native_dict.get_put_status()
        self.assertEqual(result, native_dict.GET_ERR)


class TestHashTableGetOperation(unittest.TestCase):
    def test_get_operation__1(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        result = native_dict.get('txt1')
        self.assertEqual(result, 19632)

    def test_get_operation__2(self):
        native_dict = Dict()
        native_dict.put('txt1', 999)
        native_dict.put('txt1', 19632)
        result = native_dict.get('txt1')
        self.assertEqual(result, 19632)

    def test_get_operation__3(self):
        native_dict = Dict()
        native_dict.put('txt1', 999)
        native_dict.put('txt1', 19632)
        native_dict.get('txt1')
        native_dict.get('txt1')
        result = native_dict.get('txt1')
        self.assertEqual(result, 19632)

    def test_get_operation__4(self):
        native_dict = Dict()
        native_dict.put('txt1', 999)
        native_dict.put('txt1', 19632)
        native_dict.get('txt1')
        native_dict.get('txt1')
        result = native_dict.get('txt1')
        self.assertEqual(result, 19632)

    def test_get_operation__5(self):
        native_dict = Dict()
        native_dict.put('txt1', 999)
        native_dict.put('txt1', 19632)
        native_dict.put('frg', 542)
        result = native_dict.get('frg')
        self.assertEqual(result, 542)

    def test_get_operation__6(self):
        native_dict = Dict()
        native_dict.put('txt1', 999)
        native_dict.put('txt145', 19632)
        native_dict.put('frg', 542)
        native_dict.get('frg')
        result = native_dict.get_get_status()
        self.assertEqual(result, native_dict.GET_OK)

    def test_get_operation__7(self):
        native_dict = Dict()
        native_dict.put('txt1', 999)
        native_dict.put('txt145', 19632)
        native_dict.put('frg', 542)
        result = native_dict.get_get_status()
        self.assertEqual(result, native_dict.GET_NIL)

    def test_get_operation__8(self):
        native_dict = Dict()
        native_dict.put('txt1', 999)
        native_dict.put('txt145', 19632)
        native_dict.put('frg', 542)
        native_dict.get('noway')
        result = native_dict.get_get_status()
        self.assertEqual(result, native_dict.GET_ERR)



class TestHashTableDeleteOperation(unittest.TestCase):
    def test_delet_operation__1(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.delete('txt1')
        result = native_dict.get('txt1')
        self.assertEqual(result, None)

    def test_delet_operation__2(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.delete('txt1')
        native_dict.delete('txt1')
        result = native_dict.get('txt1')
        self.assertEqual(result, None)

    def test_delet_operation__3(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.put('txt2', 9632)
        native_dict.put('txt3', 632)
        native_dict.delete('txt1')
        native_dict.delete('txt2')
        result = native_dict.get('txt3')
        self.assertEqual(result, 632)

    def test_delet_operation__4(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.put('txt2', 9632)
        native_dict.put('txt3', 632)
        native_dict.delete('txt1')
        native_dict.delete('txt2')
        native_dict.put('txt1', ('fd', 'rt'))
        result = native_dict.get('txt1')
        self.assertEqual(result, ('fd', 'rt'))

    def test_delet_operation__5(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.put('txt2', 9632)
        native_dict.put('txt3', 632)
        native_dict.delete('txt1')
        native_dict.delete('txt2')
        native_dict.put('txt1', ('fd', 'rt'))
        result = native_dict.get_delete_status()
        self.assertEqual(result, native_dict.DELETE_OK)

    def test_delet_operation__6(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.put('txt2', 9632)
        native_dict.put('txt3', 632)
        result = native_dict.get_delete_status()
        self.assertEqual(result, native_dict.DELETE_NIL)

    def test_delet_operation__7(self):
        native_dict = Dict()
        native_dict.put('txt1', 19632)
        native_dict.put('txt2', 9632)
        native_dict.put('txt3', 632)
        native_dict.delete('noway')
        result = native_dict.get_delete_status()
        self.assertEqual(result, native_dict.DELETE_ERR)

