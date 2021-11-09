package com.ashok.kanigiri.expensemanager.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.models.UserLoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    var userNameTextField = MutableLiveData<String>()
    var passwordTextField = MutableLiveData<String>()
    var loginButtonEnabledStatus = MutableLiveData<Boolean>()

    var userLogin = UserLoginModel(username = "", password = "")

    fun onUsernameFieldTextChangedListener(s: CharSequence?){
        s?.let {
            userLogin = userLogin.copy(username = it.toString())
        }
        handleLoginButtonEnableStatus(userLogin)
    }

    fun onPasswordFieldTextChangedListener(s: CharSequence?){
        s?.let {
            userLogin = userLogin.copy(password = it.toString())
        }
        handleLoginButtonEnableStatus(userLogin)
    }

    fun handleLoginButtonEnableStatus(userUserLoginModelDetails: UserLoginModel){
        if(userUserLoginModelDetails.username.trim() != "" && userUserLoginModelDetails.password.trim() != ""){
            loginButtonEnabledStatus.postValue(true)
        }else{
            loginButtonEnabledStatus.postValue(false)
        }
    }

    fun login(){

    }

    fun onCreateNewAccountClicked(){

    }
}