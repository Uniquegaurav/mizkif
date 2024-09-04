package com.example.mymiso.domain.use_cases

import androidx.room.Query
import com.example.mymiso.domain.SearchResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSearchResult {
    suspend operator fun invoke(searchQuery: String): Flow<List<SearchResult>> = flow {
        delay(200)
        val result = listOf<SearchResult>(
            SearchResult("1", "title1", "description1"),
            SearchResult("2", "title2", "description2"),
            SearchResult("3", "title3", "description3"),
            SearchResult("4", "title4", "description4"),
            SearchResult("5", "title5", "description5"),
            SearchResult("6", "title6", "description6"),
            SearchResult("7", "title7", "description7"),
            SearchResult("8", "title8", "description8"),
            SearchResult("9", "title9", "description9"),
        )
        emit(result)
    }
}