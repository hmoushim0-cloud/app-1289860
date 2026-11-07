package sa.factory.narjisspabeautysal.domain.usecase

import sa.factory.narjisspabeautysal.domain.repository.NarjisSpaRepository
import sa.factory.narjisspabeautysal.model.NarjisSpaItem
import sa.factory.narjisspabeautysal.model.NarjisSpaOrder
import sa.factory.narjisspabeautysal.model.NarjisSpaTelemetry
import kotlinx.coroutines.flow.Flow

class GetCatalogUseCase(private val repository: NarjisSpaRepository) {
    operator fun invoke(): Flow<List<NarjisSpaItem>> = repository.catalogFlow
}

class ManageCartUseCase(private val repository: NarjisSpaRepository) {
    val cart = repository.activeCart
    fun add(item: NarjisSpaItem) = repository.addToCart(item)
    fun remove(item: NarjisSpaItem) = repository.removeFromCart(item)
    fun clear() = repository.clearCart()
}

class ProcessCheckoutUseCase(private val repository: NarjisSpaRepository) {
    suspend operator fun invoke(paymentMethod: String = "Mada Contactless EMV"): NarjisSpaOrder =
        repository.checkout(paymentMethod)
}

class ObserveHardwareTelemetryUseCase(private val repository: NarjisSpaRepository) {
    operator fun invoke(): Flow<NarjisSpaTelemetry> = repository.telemetryFlow
}
