package com.system.traffic.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OtherViewModel : ViewModel(){

    private val _intentSuggestActivity = MutableLiveData<Unit>()
    val intentSuggestActivity: LiveData<Unit>
        get() = _intentSuggestActivity

    private val _setArriveColor = MutableLiveData<Unit>()
    val setArriveColor: LiveData<Unit>
        get() = _setArriveColor


    fun intentSuggestActivity(){
        _intentSuggestActivity.value = Unit
    }

    fun setArriveColor(){
        _setArriveColor.value = Unit
    }

}