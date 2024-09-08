package com.example.mymiso.domain.use_cases

import com.example.mymiso.domain.model.OrderStatus
import com.example.mymiso.domain.repository.OrderStatusRepository
import kotlinx.coroutines.flow.Flow

class TrackOrderStatusUseCase(private val orderStatusRepository: OrderStatusRepository) {
    operator fun invoke(orderId: String): Flow<OrderStatus> {
        return orderStatusRepository.trackOrderStatus(orderId)
    }
}