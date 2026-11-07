package sa.factory.narjisspabeautysal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import sa.factory.narjisspabeautysal.domain.usecase.*
import sa.factory.narjisspabeautysal.model.NarjisSpaItem
import sa.factory.narjisspabeautysal.model.NarjisSpaOrder
import sa.factory.narjisspabeautysal.model.NarjisSpaTelemetry
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class NarjisSpaUiState(
    val catalog: List<NarjisSpaItem> = emptyList(),
    val filteredCatalog: List<NarjisSpaItem> = emptyList(),
    val selectedCategory: String = "الكل",
    val cart: List<NarjisSpaItem> = emptyList(),
    val telemetry: NarjisSpaTelemetry? = null,
    val lastOrder: NarjisSpaOrder? = null,
    val isCheckingOut: Boolean = false,
    val activeTab: String = "home"
)

class NarjisSpaViewModel(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val manageCartUseCase: ManageCartUseCase,
    private val processCheckoutUseCase: ProcessCheckoutUseCase,
    private val observeHardwareTelemetryUseCase: ObserveHardwareTelemetryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NarjisSpaUiState())
    val uiState: StateFlow<NarjisSpaUiState> = _uiState.asStateFlow()

    init {
        // Observe Catalog
        getCatalogUseCase().onEach { list ->
            _uiState.update { it.copy(catalog = list, filteredCatalog = list) }
        }.launchIn(viewModelScope)

        // Observe Cart
        manageCartUseCase.cart.onEach { cartItems ->
            _uiState.update { it.copy(cart = cartItems) }
        }.launchIn(viewModelScope)

        // Observe Telemetry
        observeHardwareTelemetryUseCase().onEach { telem ->
            _uiState.update { it.copy(telemetry = telem) }
        }.launchIn(viewModelScope)
    }

    fun selectCategory(category: String) {
        _uiState.update { state ->
            val filtered = if (category == "الكل") state.catalog else state.catalog.filter { it.category == category }
            state.copy(selectedCategory = category, filteredCatalog = filtered)
        }
    }

    fun switchTab(tab: String) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    fun addToCart(item: NarjisSpaItem) = manageCartUseCase.add(item)
    fun removeFromCart(item: NarjisSpaItem) = manageCartUseCase.remove(item)

    fun checkout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isCheckingOut = true) }
            val order = processCheckoutUseCase()
            _uiState.update { it.copy(isCheckingOut = false, lastOrder = order, activeTab = "checkout") }
        }
    }
}
