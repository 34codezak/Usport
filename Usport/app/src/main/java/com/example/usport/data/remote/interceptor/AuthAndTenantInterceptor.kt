package com.example.usport.data.remote.interceptor

// data/remote/interceptor/AuthAndTenantInterceptor.kt
class AuthAndTenantInterceptor @Inject constructor(
    private val tokenManager: TokenManager // Wraps DataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val builder = originalRequest.newBuilder()
            // 1. Inject Multi-tenant Header
            .addHeader("X-Institution-ID", tokenManager.getInstitutionIdSync())
            .addHeader("Accept", "application/json")

        // 2. Inject JWT if available
        val token = tokenManager.getJwtTokenSync()
        if (!token.isNullOrEmpty()) {
            builder.addHeader("Authorization", "Bearer $token")
        }

        val response = chain.proceed(builder.build())

        // 3. Handle 401 Unauthorized globally (e.g., token expired)
        if (response.code == 401) {
            // Trigger token refresh or force logout
            tokenManager.clearSession()
        }

        return response
    }
}