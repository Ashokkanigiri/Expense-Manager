package com.ashok.kanigiri.expensemanager.feature.createnewaccount

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.models.UserLoginModel
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import com.google.gson.Gson
import javax.inject.Inject

class CreateAccountViewmodel @Inject constructor(): ViewModel(){
    var userLoginModel = UserLoginModel("", "","", "")
    val dateOfBirthObserver = ObservableField<String>()
    val event = SingleLiveEvent<CreateAccountViewmodelEvent>()

    fun createAccount(){
        event.postValue(CreateAccountViewmodelEvent.NavigateToChooseCategoryScreen)
    }

    fun saveDateOfBirthFromPicker(dob: String){
        userLoginModel.dateOfBirth = dob
        dateOfBirthObserver.set(dob)
    }

    fun navigateBackToLoginScreen(){
        event.postValue(CreateAccountViewmodelEvent.NavigateToLoginScreen)
    }



}