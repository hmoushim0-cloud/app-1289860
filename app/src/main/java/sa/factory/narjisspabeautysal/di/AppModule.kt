package sa.factory.narjisspabeautysal.di

import sa.factory.narjisspabeautysal.data.repository.NarjisSpaRepositoryImpl
import sa.factory.narjisspabeautysal.domain.repository.NarjisSpaRepository
import sa.factory.narjisspabeautysal.domain.usecase.*
import sa.factory.narjisspabeautysal.hardware.HardwareSensorDriver
import sa.factory.narjisspabeautysal.ui.viewmodel.NarjisSpaViewModel

class DiContainer {
    val sensorDriver = HardwareSensorDriver()
    val repository: NarjisSpaRepository = NarjisSpaRepositoryImpl(sensorDriver)

    val getCatalogUseCase = GetCatalogUseCase(repository)
    val manageCartUseCase = ManageCartUseCase(repository)
    val processCheckoutUseCase = ProcessCheckoutUseCase(repository)
    val observeHardwareTelemetryUseCase = ObserveHardwareTelemetryUseCase(repository)

    fun provideViewModel(): NarjisSpaViewModel = NarjisSpaViewModel(
        getCatalogUseCase,
        manageCartUseCase,
        processCheckoutUseCase,
        observeHardwareTelemetryUseCase
    )
}
