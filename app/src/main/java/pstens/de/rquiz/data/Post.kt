package pstens.de.rquiz.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
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
