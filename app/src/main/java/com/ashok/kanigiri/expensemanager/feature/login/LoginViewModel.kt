package com.ashok.kanigiri.expensemanager.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.models.UserLoginModel
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    var userNameTextField = MutableLiveData<String>()
    var passwordTextField = MutableLiveData<String>()
    var loginButtonEnabledStatus = MutableLiveData<Boolean>()
    val event = SingleLiveEvent<LoginViewModelEvent>()

    var userLogin = UserLoginModel(name = "", dateOfBirth = 0, salary = "")

    fun onUsernameFieldTextChangedListener(s: CharSequence?){
        s?.let {
            userLogin = userLogin.copy(name = it.toString())
        }
        handleLoginButtonEnableStatus(userLogin)
    }
    fun handleLoginButtonEnableStatus(userUserLoginModelDetails: UserLoginModel){
        if(userUserLoginModelDetails.name.trim() != ""){
            loginButtonEnabledStatus.postValue(true)
        }else{
            loginButtonEnabledStatus.postValue(false)
        }
    }

    fun login(){

    }

    fun onCreateNewAccountClicked(){
        event.postValue(LoginViewModelEvent.NavigateToCreateAccountPage)
    }
}