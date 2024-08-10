package com.traffic.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(

): ViewModel() {

    fun setUpIsFirstLogin() = viewModelScope.launch {
        //DataStore().setUpIsFirstLogin()
    }

    suspend fun checkIsFirstLogin(): Boolean {
        delay(1000)
        //return DataStore().checkIsFirstLogin()
        return false
    }
}