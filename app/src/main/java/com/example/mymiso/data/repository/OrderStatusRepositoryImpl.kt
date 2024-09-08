package com.example.mymiso.data.repository

import com.example.mymiso.domain.model.OrderStatus
import com.example.mymiso.domain.repository.OrderStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader

class OrderStatusRepositoryImpl(private val client: OkHttpClient) : OrderStatusRepository {
    override fun trackOrderStatus(orderId: String): Flow<OrderStatus> = flow {
        val request = Request.Builder()
            .url("https://api.example.com/orders/$orderId/status")
            .build()

        // Use OkHttp to make a server-sent events (SSE) request
        // and emit the order status updates
        // This is a simplified example, you may need to handle connection errors, timeouts, etc.
        //

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IllegalStateException("Failed to fetch order status")
            }

            val reader = BufferedReader(InputStreamReader(response.body?.byteStream()))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line!!.startsWith("data:")) {
                    // Parse the data (assuming JSON format) and emit it
                    val statusJson = line!!.substring(5).trim()
                    val orderStatus = parseOrderStatusFromJson(statusJson)
                    emit(orderStatus)
                }
            }

        }
    }

    private fun parseOrderStatusFromJson(json: String): OrderStatus {
        // Parse JSON and convert to OrderStatus object
        // You can use Gson, Moshi, or any JSON parser here
        return OrderStatus(status = json)
    }
}