package pstens.de.rquiz.database

import android.arch.persistence.room.*
import pstens.de.rquiz.data.Subreddit

@Dao
interface SubredditDao {
    @Query("SELECT * FROM Subreddit")
    fun getAll(): List<Subreddit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg subreddit: Subreddit)

    @Delete
    fun delete(subreddit: Subreddit)
}