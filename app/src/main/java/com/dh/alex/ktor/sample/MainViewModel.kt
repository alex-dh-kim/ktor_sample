package com.dh.alex.ktor.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.alex.ktor.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.getRepositories()
                .fold(
                    { error ->
                        // handle error
                    },
                    { response ->
                        // handle success
                    }
                )
        }
    }

    fun test() {
        // Do Nothing
    }
}
