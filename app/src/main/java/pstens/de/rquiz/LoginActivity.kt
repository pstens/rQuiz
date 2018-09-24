package pstens.de.rquiz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.button
import org.jetbrains.anko.editText
import org.jetbrains.anko.verticalLayout

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            val name = editText("Name")
            button("Log-in") {
               onClick {
                   val ref = FirebaseDatabase.getInstance().getReference("players")
                   ref.push().key?.let {
                       ref.child(it).setValue(name.text.toString())
                   }
               }
            }
        }
    }
}