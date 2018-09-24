package pstens.de.rquiz.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import pstens.de.rquiz.data.Post
import pstens.de.rquiz.data.Subreddit

@Database(entities = [Subreddit::class, Post::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract val subredditDao: SubredditDao
    abstract val postDao: PostDao
}