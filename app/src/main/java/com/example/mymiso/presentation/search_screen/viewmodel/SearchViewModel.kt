package com.example.mymiso.presentation.search_screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymiso.domain.model.SearchResult
import com.example.mymiso.domain.use_cases.GetSearchResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
        observeQuery2()
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }


    //In this approach, we use flatMapLatest to ensure only the latest API call is processed,
    //and previous API calls are automatically canceled.


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
    // In this approach, we allow parallel API calls to process without canceling them.
    // We ensure only the result of the latest query updates the UI, even if earlier queries finish after the latest one.


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeQuery2() {
        _query
            .debounce(300)
            .map { it.trim().lowercase() }
            .filter { it.isNotEmpty() }
            .distinctUntilChanged() // Only proceed if the query has changed
            .onEach { query ->
                val cachedResult = queryCache[query]
                if (cachedResult != null) {
                    // Emit cached result if it exists
                    _searchResults.value = cachedResult
                } else {
                    // Store the current query to check later if it's the latest one
                    val currentQuery = query

                    // Perform parallel API call without canceling previous ones
                    viewModelScope.launch {
                        try {
                            // Collect the Flow to get the results
                            GetSearchResult()(query)
                                .timeout(5.seconds)
                                .retry(2) { it is IOException }
                                .collect { results ->
                                    // Only update the UI if this is the most recent query
                                    if (currentQuery == _query.value) {
                                        lastSuccessfulResult = results
                                        queryCache[query] = results
                                        _searchResults.value = results
                                    }
                                }
                        } catch (e: Exception) {
                            // On failure, emit the last successful result if the query is still the most recent one
                            if (currentQuery == _query.value) {
                                _searchResults.value = lastSuccessfulResult
                            }
                        }
                    }
                }
            }
            .launchIn(viewModelScope) // This is the main collector for the StateFlow
    }


}
