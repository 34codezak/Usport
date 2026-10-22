package com.example.usport.data.remote.api

// data/remote/api/SportsApi.kt
interface SportsApi {

    // 1. Cursor-based pagination for Fixtures
    @GET("api/v1/institutions/{institutionId}/fixtures")
    suspend fun getFixtures(
        @Path("institutionId") institutionId: String,
        @Query("sport") sport: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("cursor") cursor: String? = null
    ): CursorResponse<MatchDto>

    // 2. Image Upload (Multipart)
    @Multipart
    @POST("api/v1/institutions/{institutionId}/teams/{teamId}/logo")
    suspend fun uploadTeamLogo(
        @Path("institutionId") institutionId: String,
        @Path("teamId") teamId: String,
        @Part logo: MultipartBody.Part
    ): ImageUploadResponseDto // Returns the direct URL

    // 3. Submitting a Result (Requires Auth & Role check on backend)
    @POST("api/v1/matches/{matchId}/result")
    suspend fun submitMatchResult(
        @Path("matchId") matchId: String,
        @Body resultDto: SubmitResultDto
    ): MatchDto
}