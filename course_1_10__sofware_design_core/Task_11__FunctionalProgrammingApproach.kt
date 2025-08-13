// An attempt to adapt some system with business logic into a functional style
// As an advantage, you can point out how succinctly the code for calculating metrics looks.
// However, unfortunately, no implementation has been achieved in which a new object will be created rather
// than modifying an existing one.
import java.util.UUID


sealed interface Team {
    val name: String;
    val key: String get() = "${this::class.simpleName}:${name}"
}

data class AgileTeam(override val name: String) : Team
data class FeatureTeam(override val name: String) : Team


data class Client(
    val id: UUID,
    val name: String,
    val team: Team
)


data class Survey(
    val id: UUID,
    val title: String,
    val questions: List<String>,
    val answers: Map<UUID, List<Int?>>
)

fun submitResponse(s: Survey, client: Client, response: List<Int?>): Survey {
    require(response.size == s.questions.size) { "The condition must be met: answer count = question count" }
    val updated = s.answers.toMutableMap()
    updated[client.id] = response.toList()

    return s.copy(answers = updated.toMap())
}


private fun isComplete(totalQuestions: Int, ans: List<Int?>?): Boolean =
    ans != null && ans.size == totalQuestions && ans.all { it != null }


fun completionRateByTeam(s: Survey, clients: List<Client>): Map<String, Double> {
    val n = s.questions.size
    return clients.groupBy { it.team.key }.mapValues { (_, teamClients) ->
        val completed = teamClients.count { u -> isComplete(n, s.answers[u.id]) }
        if (teamClients.isEmpty()) 0.0 else completed.toDouble() / teamClients.size
    }
}


fun answerCountsPerQuestionByTeam(s: Survey, clients: List<Client>): Map<String, List<Long>> {
    val n = s.questions.size
    return clients.groupBy { it.team.key }.mapValues { (_, teamClients) ->
        (0 until n).map { qi ->
            teamClients.asSequence()
                .mapNotNull { s.answers[it.id] }
                .count { it.getOrNull(qi) != null }
                .toLong()
        }
    }
}
