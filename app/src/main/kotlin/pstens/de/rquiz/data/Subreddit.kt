package pstens.de.rquiz.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Subreddit(
        @SerializedName("display_name") @PrimaryKey val name: String,
        @SerializedName("icon_img") val icon: String)
