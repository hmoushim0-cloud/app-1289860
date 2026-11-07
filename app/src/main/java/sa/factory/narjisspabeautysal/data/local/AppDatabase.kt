package sa.factory.narjisspabeautysal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NarjisSpaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beauty_salonDao(): NarjisSpaDao
}
