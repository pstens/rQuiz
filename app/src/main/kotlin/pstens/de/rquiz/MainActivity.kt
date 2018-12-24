package pstens.de.rquiz

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.find
import pstens.de.rquiz.api.RestApi
import pstens.de.rquiz.data.Subreddit
import pstens.de.rquiz.extensions.loadUrl
import pstens.de.rquiz.extensions.pickRandom

data class SubredditRow(val root: View) {
    private val icon by lazy { root.find<ImageView>(R.id.icon) }
    private val name by lazy { root.find<TextView>(R.id.name) }

    fun bindSubreddit(subreddit: Subreddit, onClick: (Subreddit) -> Unit) {
        icon.loadUrl(subreddit.icon)
        name.text = subreddit.name
        name.setOnClickListener {
            onClick(subreddit)
        }
    }
}

class MainActivity : AppCompatActivity() {

    private val subViews: MutableList<SubredditRow> by lazy {
        mutableListOf(
            SubredditRow(sub1),
            SubredditRow(sub2),
            SubredditRow(sub3),
            SubredditRow(sub4)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initGame()
    }

    private fun initGame() {
        GlobalScope.launch(Dispatchers.IO) {
            val subreddits = RestApi.getSubreddits().await().mapToResult()
            val randomSubs = subreddits.pickRandom(amount = 4)
            val chosenSub = randomSubs.first()
            val post = RestApi.getPostForSubreddit(chosenSub)
            withContext(Dispatchers.Main) {
                postTitle.text = post.title
                randomSubs.forEachIndexed { index, subreddit ->
                    subViews[index].bindSubreddit(subreddit) { selected ->
                        if (selected == chosenSub) {
                            initGame()
                        }
                    }
                }
            }
        }
    }
}
