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
            SearchResult(
                "1",
                "Title 1",
                "Description 1",
                "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
            SearchResult(
                "2",
                "Title 2",
                "Description 2",
                "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
            SearchResult(
                "3",
                "Title 3",
                "Description 3",
                "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
            SearchResult(
                "4",
                "Title 4",
                "Description 4",
                "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
            SearchResult(
                "5",
                "Title 5",
                "Description 5",
                "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
            SearchResult(
                "6",
                "Title 6",
                "Description 6",
                "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
            SearchResult(
                "7",
                "Title 7",
                "Description 7",
                "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
            SearchResult(
                "8",
                "Title 8",
                "Description 8",
                "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
            SearchResult(
                "9",
                "Title 9",
                "Description 9",
                "Ihttps://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
            SearchResult(
                "10",
                "Title 10",
                "Description 10",
                "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"
            ),
        )
        emit(result)
    }
}