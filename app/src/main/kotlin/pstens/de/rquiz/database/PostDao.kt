package pstens.de.rquiz.database

import androidx.room.*
import pstens.de.rquiz.data.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM Post")
    fun getAll(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg posts: Post)

    @Delete
    fun delete(post: Post)
}
