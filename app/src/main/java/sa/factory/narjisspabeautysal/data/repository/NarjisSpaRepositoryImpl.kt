package sa.factory.narjisspabeautysal.data.repository

import sa.factory.narjisspabeautysal.domain.repository.NarjisSpaRepository
import sa.factory.narjisspabeautysal.hardware.HardwareSensorDriver
import sa.factory.narjisspabeautysal.model.NarjisSpaItem
import sa.factory.narjisspabeautysal.model.NarjisSpaOrder
import sa.factory.narjisspabeautysal.model.NarjisSpaTelemetry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NarjisSpaRepositoryImpl(
    private val sensorDriver: HardwareSensorDriver
) : NarjisSpaRepository {

    private val _catalog = MutableStateFlow<List<NarjisSpaItem>>(listOf(
        NarjisSpaItem("1", "الباقة التنفيذية المعتمدة", "تكامل مباشر مع UV-C Chamber Sanitation IoT Sensor", 180.0, "معتمد", "الخدمات الرئيسية"),
        NarjisSpaItem("2", "خدمة المعالجة الفورية", "أداء أصيل بسرعة 60 إطار في الثانية", 95.0, "فوري", "المراقبة الحية"),
        NarjisSpaItem("3", "باقة الصيانة والدعم السحابي", "تشفير كامل للبيانات المحلية ومزامنة آمنة", 250.0, "شامل", "التقارير الضريبية")
    ))
    override val catalogFlow: Flow<List<NarjisSpaItem>> = _catalog.asStateFlow()

    private val _cart = MutableStateFlow<List<NarjisSpaItem>>(emptyList())
    override val activeCart: StateFlow<List<NarjisSpaItem>> = _cart.asStateFlow()

    override val telemetryFlow: Flow<NarjisSpaTelemetry> = sensorDriver.observePeripheralStream()
        .map { frame ->
            NarjisSpaTelemetry(
                peripheralName = frame.peripheralName,
                status = frame.connectionState,
                liveReading = frame.livePayload,
                batteryPct = 96,
                signalDbm = -54
            )
        }

    override fun addToCart(item: NarjisSpaItem) {
        val current = _cart.value.toMutableList()
        current.add(item)
        _cart.value = current
    }

    override fun removeFromCart(item: NarjisSpaItem) {
        val current = _cart.value.toMutableList()
        current.remove(item)
        _cart.value = current
    }

    override fun clearCart() {
        _cart.value = emptyList()
    }

    override suspend fun checkout(paymentMethod: String): NarjisSpaOrder {
        val currentItems = _cart.value.toList()
        val subtotal = currentItems.sumOf { it.priceSar }
        val vat = subtotal * 0.15
        val total = subtotal + vat
        val order = NarjisSpaOrder(
            orderId = "ORD-${System.currentTimeMillis() % 100000}",
            items = currentItems,
            subtotalSar = subtotal,
            vatSar = vat,
            totalSar = total,
            paymentMethod = paymentMethod
        )
        clearCart()
        return order
    }

    override suspend fun refreshCatalog() {
        // Offline-first sync logic
    }
}
