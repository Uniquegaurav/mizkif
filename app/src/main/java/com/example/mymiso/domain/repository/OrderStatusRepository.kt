package com.example.mymiso.domain.repository

import com.example.mymiso.domain.model.OrderStatus
import kotlinx.coroutines.flow.Flow

interface OrderStatusRepository {
    fun trackOrderStatus(orderId: String): Flow<OrderStatus>
}