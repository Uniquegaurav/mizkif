package com.example.mymiso.features.design_patterns.singelton

class UserSession private constructor() {
    private var userId: String? = null
    private var authToken: String? = null
    private var userPreferences: UserPreferences? = null

    companion object {

        @Volatile
        private var instance: UserSession? = null
        fun getInstance(): UserSession {
            return instance ?: synchronized(this) {
                instance ?: UserSession().also { instance = it }
            }
        }

    }

    // Thread-safe methods to access and modify session data
    @Synchronized
    fun setUserId(id: String) {
        userId = id
    }

    @Synchronized
    fun getUserId(): String? {
        return userId
    }

    @Synchronized
    fun setAuthToken(token: String) {
        authToken = token
    }

    @Synchronized
    fun getAuthToken(): String? {
        return authToken
    }

    @Synchronized
    fun setUserPreferences(preferences: UserPreferences) {
        userPreferences = preferences
    }

    @Synchronized
    fun getUserPreferences(): UserPreferences? {
        return userPreferences
    }

    @Synchronized
    fun clearSession() {
        userId = null
        authToken = null
        userPreferences = null
    }
}

data class UserPreferences(
    val preferredCuisine: String?,
    val deliveryAddress: String?,
)