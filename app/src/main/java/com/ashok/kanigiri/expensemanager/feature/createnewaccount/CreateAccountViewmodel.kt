package com.ashok.kanigiri.expensemanager.feature.createnewaccount

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.models.UserLoginModel
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewmodel @Inject constructor(@ApplicationContext val context: Context) :
    ViewModel() {
    var userLoginModel = UserLoginModel("", "", "")
    val dateOfBirthObserver = ObservableField<String>()
    val event = SingleLiveEvent<CreateAccountViewmodelEvent>()

    init {
        SharedPreferenceService.putBoolean(SharedPreferenceService.IS_USER_FILLED_CREATE_ACCOUNT_DETAILS, false, context)
    }

    fun createAccount() {
        if (userLoginModel.dateOfBirth.trim() != ""  && userLoginModel.salary.trim() != "" && userLoginModel.name.trim() != "") {
            SharedPreferenceService.putUserLoginModel(context, userLoginModel)
            event.postValue(CreateAccountViewmodelEvent.NavigateToChooseCategoryScreen)
        }else{
            event.postValue(CreateAccountViewmodelEvent.ShowErrorShackBar)
        }
    }

    fun saveDateOfBirthFromPicker(dob: String) {
        userLoginModel.dateOfBirth = dob
        dateOfBirthObserver.set(dob)
    }

    fun onCancelButtonClicked(){
        event.postValue(CreateAccountViewmodelEvent.HandleCancelButtonClicked)
    }
}