package pstens.de.rquiz.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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