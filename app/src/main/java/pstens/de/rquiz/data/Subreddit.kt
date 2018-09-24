package pstens.de.rquiz.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Subreddit(
        @SerializedName("display_name") @PrimaryKey val name: String,
        @SerializedName("icon_img") val icon: String)
