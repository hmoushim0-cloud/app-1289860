package sa.factory.narjisspabeautysal.domain.repository

import sa.factory.narjisspabeautysal.model.NarjisSpaItem
import sa.factory.narjisspabeautysal.model.NarjisSpaOrder
import sa.factory.narjisspabeautysal.model.NarjisSpaTelemetry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NarjisSpaRepository {
    val catalogFlow: Flow<List<NarjisSpaItem>>
    val activeCart: StateFlow<List<NarjisSpaItem>>
    val telemetryFlow: Flow<NarjisSpaTelemetry>
    
    fun addToCart(item: NarjisSpaItem)
    fun removeFromCart(item: NarjisSpaItem)
    fun clearCart()
    suspend fun checkout(paymentMethod: String): NarjisSpaOrder
    suspend fun refreshCatalog()
}
