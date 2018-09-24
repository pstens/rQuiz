package pstens.de.rquiz.data

import com.google.gson.annotations.SerializedName

interface Mappable<out T : Any> {
    fun mapToResult(): T?
}

class ListingResponse<T : Any>(
        @SerializedName("kind") val kind: String,
        @SerializedName("data") val data: ListingData<T>) : Mappable<List<T>> {

    override fun mapToResult(): List<T> {
        return data.children.map { it.data }
    }
}

class ListingData<T>(
        @SerializedName("modhash") val hash: String,
        @SerializedName("dist") val amount: Int,
        @SerializedName("children") val children: List<ListingChild<T>>)

class ListingChild<T>(
        @SerializedName("kind") val kind: String,
        @SerializedName("data") val data: T)

