package sa.factory.narjisspabeautysal.data.remote

import sa.factory.narjisspabeautysal.model.NarjisSpaItem
import sa.factory.narjisspabeautysal.model.NarjisSpaOrder

interface NarjisSpaApiService {
    suspend fun fetchCatalog(): List<NarjisSpaItem>
    suspend fun submitOrder(order: NarjisSpaOrder): Boolean
}
