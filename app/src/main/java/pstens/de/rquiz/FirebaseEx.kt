package pstens.de.rquiz

import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.database.*
import kotlinx.coroutines.experimental.suspendCancellableCoroutine

private suspend fun <T : Any> readReference(
        reference: DatabaseReference,
        type: Class<T>
): T = suspendCancellableCoroutine { continuation ->
    val listener = object : ValueEventListener {

        override fun onCancelled(error: DatabaseError) {
            val exception = when (error.toException()) {
                is FirebaseException -> error.toException()
                else -> Exception("The Firebase call was canceled")
            }

            continuation.resumeWithException(exception)
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            try {
                val data: T? = snapshot.getValue(type)

                continuation.resume(data!!)
            } catch (exception: Exception) {
                continuation.resumeWithException(exception)
            }
        }
    }

    continuation.invokeOnCancellation { reference.removeEventListener(listener) }
    reference.addListenerForSingleValueEvent(listener)
}

suspend fun <T : Any> DatabaseReference.readValue(type: Class<T>): T = readReference(this, type)

suspend inline fun <reified T : Any> DatabaseReference.readValue(): T = readValue(T::class.java)

private suspend fun <T : Any> readReferences(
        reference: DatabaseReference,
        type: Class<T>
): List<T> = suspendCancellableCoroutine { continuation ->
    val listener = object : ValueEventListener {

        override fun onCancelled(error: DatabaseError) {
            val exception = when (error.toException()) {
                is FirebaseException -> error.toException()
                else -> Exception("The Firebase call was canceled")
            }

            continuation.resumeWithException(exception)
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            try {
                val data: List<T> = snapshot.children.toHashSet().map { it.getValue(type)!! }

                continuation.resume(data)
            } catch (exception: Exception) {
                continuation.resumeWithException(exception)
            }
        }
    }

    continuation.invokeOnCancellation { reference.removeEventListener(listener) }
    reference.addListenerForSingleValueEvent(listener)
}

suspend fun <T : Any> DatabaseReference.readList(type: Class<T>): List<T> = readReferences(this, type)

suspend inline fun <reified T : Any> DatabaseReference.readList(): List<T> = readList(T::class.java)

suspend fun <T : Any> DatabaseReference.saveValue(value: T): Unit =
        setValue(value).await().let { Unit }

suspend fun <T : Any> DatabaseReference.pushValue(value: T): Unit = push().saveValue(value)

private suspend fun <T : Any> awaitQuerySingleValue(query: Query, type: Class<T>): T =
        suspendCancellableCoroutine { continuation ->
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) = try {
                    continuation.resume(snapshot.getValue(type)!!)
                } catch (exception: Exception) {
                    continuation.resumeWithException(exception)
                }

                override fun onCancelled(error: DatabaseError) =
                        continuation.resumeWithException(error.toException())
            }

            query.addListenerForSingleValueEvent(listener)
            continuation.invokeOnCancellation { query.removeEventListener(listener) }
        }

suspend fun <T : Any> Query.readValue(type: Class<T>): T = awaitQuerySingleValue(this, type)

suspend inline fun <reified T : Any> Query.readValue(): T = readValue(T::class.java)

private suspend fun <T : Any> awaitQueryListValue(query: Query, type: Class<T>): List<T> =
        suspendCancellableCoroutine { continuation ->
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) = try {
                    val data: List<T> = snapshot.children.toHashSet().map { it.getValue(type)!! }

                    continuation.resume(data)
                } catch (exception: Exception) {
                    continuation.resumeWithException(exception)
                }

                override fun onCancelled(error: DatabaseError) =
                        continuation.resumeWithException(error.toException())
            }

            query.addListenerForSingleValueEvent(listener)
            continuation.invokeOnCompletion { query.removeEventListener(listener) }
        }

suspend fun <T : Any> Query.readList(type: Class<T>): List<T> = awaitQueryListValue(this, type)

suspend inline fun <reified T : Any> Query.readList(): List<T> = readList(T::class.java)

private suspend fun <T> awaitTask(task: Task<T>): T = suspendCancellableCoroutine { continuation ->
    task
            .addOnSuccessListener(continuation::resume)
            .addOnFailureListener(continuation::resumeWithException)
            .addOnCanceledListener { continuation.cancel() }
}

suspend fun <T> Task<T>.await(): T = awaitTask(this)