package pstens.de.rquiz

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import pstens.de.rquiz.extensions.start

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            lparams(matchParent, matchParent)
            imageView(R.drawable.ic_launcher_foreground).lparams {
                gravity = Gravity.CENTER
            }
            backgroundColorResource = R.color.colorPrimaryDark
        }
        delegateToMainActivity()
    }

    private fun delegateToMainActivity() {
        GlobalScope.launch {
            delay(1250L)
            intentFor<MainActivity>()
                .singleTop()
                .start(act)
            finish()
        }
    }
}
