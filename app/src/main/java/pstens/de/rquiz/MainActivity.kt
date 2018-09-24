package pstens.de.rquiz

import android.arch.persistence.room.Room
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import org.jetbrains.anko.button
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import pstens.de.rquiz.api.RestApi
import pstens.de.rquiz.data.Subreddit
import pstens.de.rquiz.database.AppDatabase
import pstens.de.rquiz.extensions.i
import pstens.de.rquiz.extensions.pickRandom
import ru.gildor.coroutines.retrofit.await

class MainActivity : AppCompatActivity() {
    private val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "db")
                .fallbackToDestructiveMigration()
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.enableDefaults()
        verticalLayout {
            val response = textView("Waiting for request")
            button("Download Subreddits") {
                onClick {
                    launch(UI) {
                        val subreddits = RestApi.getSubreddits().await()
                        awaitInsert(subreddits.mapToResult())
                        response.text = "Inserted ${subreddits.mapToResult().size} subreddits"
                        i("$subreddits")
                    }
                }
            }
            button("Download Posts for random subreddit") {
                onClick {
                    launch(UI) {
                        val subreddits = withContext(DefaultDispatcher) { db.subredditDao.getAll() }
                        val randomSubs = subreddits.pickRandom(4)
                        val post = RestApi.getPostForSubreddit(randomSubs.first())
                        selector(post.title, randomSubs.map { it.name }) { _, index ->
                            val selectedSub = randomSubs[index]
                        }
                    }
                }
            }
        }
    }

    private suspend fun awaitInsert(subs: List<Subreddit>) = withContext(DefaultDispatcher) {
        db.subredditDao.insert(*subs.toTypedArray())
    }
}
