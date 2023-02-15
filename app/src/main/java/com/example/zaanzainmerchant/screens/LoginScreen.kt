package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zaanzainmerchant.network.LoginScreenState
import com.example.zaanzainmerchant.network.NetworkResult
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.UserResponse
import com.example.zaanzainmerchant.viewmodels.LoginViewModel
import com.example.zaanzainmerchant.viewmodels.NewOrdersViewModel


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    screenState: LoginScreenState,
    loginViewModel: LoginViewModel,
    newOrdersViewModel: NewOrdersViewModel? = null,
    isWrongCred: Boolean = false,
    navigateToRegistration: () -> Unit

){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state = ScrollState(0)),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){

        Text(
            text = "Welcome to ZaanZain!",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 18.dp)
        )

        TextField(
            value = loginViewModel.username,
            onValueChange = { loginViewModel.updateUserName(it) },
            label = { Text("Email") },
            placeholder = { Text("example@gmail.com") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = isWrongCred
        )
        val passwordVisible = rememberSaveable{ mutableStateOf(false) }
        TextField(
            value = loginViewModel.password,
            onValueChange = { loginViewModel.updateUserPassword(it) },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isError = isWrongCred,
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible.value) "Hide password" else "Show password"

                androidx.compose.material.IconButton(onClick = {
                    passwordVisible.value = !passwordVisible.value
                }) {
                    androidx.compose.material.Icon(imageVector = image, description)
                }
            }
        )

        Button(
            modifier = modifier
                .padding(horizontal = 4.dp, vertical = 4.dp),
            onClick = {loginViewModel.postLoginData()},
        ){
            Text(text = "Login")
        }
        Row {
            Text(text = "Don't have an account? ")
            Text(
                text = "Register Now",
                modifier.clickable {
                    navigateToRegistration()
                },
                color = MaterialTheme.colorScheme.scrim
            )
        }

        when(screenState) {
            LoginScreenState.Landing -> {

            }
            LoginScreenState.Loading -> {
                CircularProgressIndicator()
            }
            LoginScreenState.Error -> {
                Spacer(modifier = Modifier.height(25.dp))
                Text("Please enter valid credentials!")
            }
            LoginScreenState.Success -> {
                Spacer(modifier = Modifier.height(25.dp))
                Text("Successfully logged in!")
            }
        }

    }
}


@Composable
fun LoginFinalScreen(
    loginViewModel: LoginViewModel,
    userResponse: NetworkResult<UserResponse>?,
    navController: NavController,
    newOrdersViewModel: NewOrdersViewModel,
    navigateToRegistration: () -> Unit
){
    when (userResponse) {
        is NetworkResult.Error -> {
            LoginScreen(
                loginViewModel = loginViewModel,
                screenState = LoginScreenState.Error,
                isWrongCred = true,
                navigateToRegistration = { navigateToRegistration() }
            )
            loginViewModel.resetInputFields()
        }
        is NetworkResult.Success -> {
            LoginScreen(
                loginViewModel = loginViewModel,
                screenState = LoginScreenState.Success,
                newOrdersViewModel = newOrdersViewModel,
                navigateToRegistration = { navigateToRegistration() }
            )
            LaunchedEffect(Unit) {
                navController.navigate(Screen.Home.route)
                newOrdersViewModel.refreshCartItems()
                Log.d(TAG, "Successfully navigated to home screen")
            }
        }
        is NetworkResult.Loading -> {
            LoginScreen(
                loginViewModel = loginViewModel,
                screenState = LoginScreenState.Loading,
                navigateToRegistration = { navigateToRegistration() }
            )
        }
        else -> {
            LoginScreen(
                loginViewModel = loginViewModel,
                screenState = LoginScreenState.Landing,
                navigateToRegistration = { navigateToRegistration() }
            )
        }
    }

}
