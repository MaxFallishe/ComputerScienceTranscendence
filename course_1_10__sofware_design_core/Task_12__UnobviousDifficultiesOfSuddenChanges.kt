data class Message(val role: Role, val text: String, val ts: Long)
enum class Role { CLIENT, MANAGER }

data class Conversation(
    val id: String,
    val messages: List<Message>
)

data class AnalysisResult(
    val riskScore: Double,
    val flags: List<String>,
    val summary: String
)


interface LlmClient {
    suspend fun complete(prompt: String): String
}


interface PromptBuilder {
    fun build(conversation: Conversation): String
}


interface ConversationAnalyzer {
    suspend fun analyze(conversation: Conversation): AnalysisResult
}