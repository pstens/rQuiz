package pstens.de.rquiz.data

data class Game(
        val subreddits: List<Subreddit> = emptyList(),
        val post: Post? = null,
        val playersToAnswers: Map<String, String> = emptyMap())

data class Player(
        val id: String = "",
        val name: String = "",
        val games: List<String> = emptyList())
