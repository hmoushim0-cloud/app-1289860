package sa.factory.narjisspabeautysal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NarjisSpaDao {
    @Query("SELECT * FROM beauty_salon_items_cache ORDER BY id ASC")
    fun observeAllItems(): Flow<List<NarjisSpaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<NarjisSpaEntity>)

    @Query("DELETE FROM beauty_salon_items_cache")
    suspend fun clearAll()
}
