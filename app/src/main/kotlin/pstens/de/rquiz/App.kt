package pstens.de.rquiz

import android.app.Application
import android.os.StrictMode
import androidx.room.Room
import com.amitshekhar.DebugDB
import pstens.de.rquiz.database.AppDatabase

class App : Application() {

    internal val database by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, DatabaseName)
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        StrictMode.enableDefaults()
        DebugDB.initialize(this)
    }

    private companion object Constants {
        const val DatabaseName = "rquiz_db"
    }
}
