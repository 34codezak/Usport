package com.example.usport.data.paging

// data/paging/FixturesPagingSource.kt
class FixturesPagingSource(
    private val api: SportsApi,
    private val institutionId: String,
    private val sportFilter: String?
) : PagingSource<String, Match>() { // String is the Cursor type

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Match> {
        return try {
            val cursor = params.key // null on first load
            val response = api.getFixtures(institutionId, sportFilter, params.loadSize, cursor)

            LoadResult.Page(
                data = response.results.map { it.toDomain() },
                prevKey = response.previousCursor,
                nextKey = response.nextCursor // Paging 3 uses this to fetch the next page
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Match>): String? = null
}