package com.budianto.mytourismapp.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val userData = MutableLiveData<String>()


    fun setUserName(username: String){
        userData.value = username
    }
}