package pstens.de.rquiz.api

import pstens.de.rquiz.data.ListingResponse
import pstens.de.rquiz.data.Post
import pstens.de.rquiz.data.Subreddit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditApi {
    @GET("subreddits.json?sort=hot")
    fun getSubreddits(): Call<ListingResponse<Subreddit>>

    @GET("r/{subreddit}?limit=10&sort=hot")
    fun getPostsForSubreddit(@Path("subreddit") subreddit: String): Call<ListingResponse<Post>>
}