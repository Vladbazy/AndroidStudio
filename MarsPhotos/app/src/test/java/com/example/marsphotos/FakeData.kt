package com.example.marsphotos

import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApiService

// Тестовые данные вместо ответа сервера
object FakeDataSource {
    private const val idOne = "img1"
    private const val idTwo = "img2"
    private const val imgOne = "url.1"
    private const val imgTwo = "url.2"

    val photosList = listOf(
        MarsPhoto(id = idOne, imgSrc = imgOne),
        MarsPhoto(id = idTwo, imgSrc = imgTwo)
    )
}

// Фиктивный сервис: не ходит в сеть, а возвращает тестовые данные
class FakeMarsApiService : MarsApiService {
    override suspend fun getPhotos(): List<MarsPhoto> = FakeDataSource.photosList
}

// Фиктивный репозиторий для теста ViewModel
class FakeNetworkMarsPhotosRepository : MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> = FakeDataSource.photosList
}