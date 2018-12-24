package pstens.de.rquiz.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(indices = [Index("subreddit")],
        foreignKeys = [ForeignKey(entity = Subreddit::class,
                parentColumns = arrayOf("name"),
                childColumns = arrayOf("subreddit"),
                onDelete = CASCADE)])
data class Post(
        @SerializedName("id") @PrimaryKey val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("subreddit") val subreddit: String)
