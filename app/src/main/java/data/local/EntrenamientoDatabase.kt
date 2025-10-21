package data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.EjercicioEntity
import model.EntrenamientoDao
import model.EntrenamientoEntity

@Database(
    entities = [EntrenamientoEntity::class, EjercicioEntity::class],
    version = 1
)
abstract class EntrenamientoDatabase : RoomDatabase() {
    abstract fun entrenamientoDao(): EntrenamientoDao

    companion object {
        @Volatile
        private var INSTANCE: EntrenamientoDatabase? = null

        fun getInstance(context: Context): EntrenamientoDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    EntrenamientoDatabase::class.java
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
