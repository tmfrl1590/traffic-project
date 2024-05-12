package com.system.traffic.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.data.local.dataSource.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(

): ViewModel() {

    fun setUpIsFirstLogin() = viewModelScope.launch {
        DataStore().setUpIsFirstLogin()
    }

    suspend fun checkIsFirstLogin(): Boolean {
        delay(3000)
        return DataStore().checkIsFirstLogin()
    }

    // 버스 도착 정보 이동
    /*fun goBusArriveScreen(navHostController: NavHostController, ars_id: String){
        NavigationUtils.navigate(navHostController, BusArriveNav.navigateWithArg(ars_id))
    }*/
}