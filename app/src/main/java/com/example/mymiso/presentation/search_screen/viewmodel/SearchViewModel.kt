package com.example.mymiso.presentation.search_screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymiso.domain.SearchResult
import com.example.mymiso.domain.use_cases.GetSearchResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.io.IOException
import kotlin.time.Duration.Companion.seconds

class SearchViewModel : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> get() = _searchResults

    private val queryCache = mutableMapOf<String, List<SearchResult>>()
    private var lastSuccessfulResult: List<SearchResult> = emptyList()

    init {
        observeQuery()
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeQuery() {
        _query
            .debounce(300)
            .map { it.trim().lowercase() }
            .filter { it.isNotEmpty() }
            .distinctUntilChanged() // Only proceed if the query has changed
            .flatMapLatest { query ->
                val cachedResult = queryCache[query]
                if (cachedResult != null) {
                    flowOf(cachedResult)
                } else {
                    Log.d("SearchViewModel", "Fetching new results for: $query")
                    // Perform the API call in a background thread
                    GetSearchResult()(query)
                        .timeout(5.seconds)
                        .retry(2) { it is IOException }
                        .onEach { results ->
                            Log.d("SearchViewModel", "Results fetched : $results")
                            lastSuccessfulResult = results
                            queryCache[query] = results
                        }
                        .catch { e ->
                            emit(lastSuccessfulResult)
                        }
                }
            }
            .onEach { results ->
                Log.d("SearchViewModel", "Emitting results: $results")
                _searchResults.value = results
            }
            .launchIn(viewModelScope)
    }
}
