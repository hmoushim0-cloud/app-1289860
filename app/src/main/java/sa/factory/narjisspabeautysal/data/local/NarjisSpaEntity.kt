package sa.factory.narjisspabeautysal.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import sa.factory.narjisspabeautysal.model.NarjisSpaItem

@Entity(tableName = "beauty_salon_items_cache")
data class NarjisSpaEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val priceSar: Double,
    val badge: String,
    val category: String,
    val status: String
) {
    fun toDomain(): NarjisSpaItem = NarjisSpaItem(
        id = id,
        title = title,
        description = description,
        priceSar = priceSar,
        badge = badge,
        category = category,
        status = status
    )
}
