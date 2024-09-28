package com.example.mymiso.domain.repository

import com.example.mymiso.domain.model.Location
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import org.json.JSONObject

interface DeliveryPartnerLocationRepository {
    fun getDeliveryPartnerLocation(): Flow<Location>
}