package pstens.de.rquiz.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import pstens.de.rquiz.data.Post
import pstens.de.rquiz.data.Subreddit
import pstens.de.rquiz.extensions.pickRandom
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestApi {

    private val redditApi: RedditApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        redditApi = retrofit.create(RedditApi::class.java)
    }

    fun getSubreddits() = redditApi.getSubreddits()

    suspend fun getPostForSubreddit(subreddit: Subreddit): Post {
        val apiResponse = redditApi.getPostsForSubreddit(subreddit.name).await()
        return apiResponse.mapToResult().pickRandom().first()
    }
}