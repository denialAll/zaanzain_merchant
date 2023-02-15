package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.zaanzainmerchant.network.LoginScreenState
import com.example.zaanzainmerchant.network.NetworkResult
import com.example.zaanzainmerchant.utils.Constants
import com.example.zaanzainmerchant.utils.UserResponse
import com.example.zaanzainmerchant.viewmodels.LoginViewModel
import com.example.zaanzainmerchant.viewmodels.RegistrationViewModel


@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel,
    screenState: LoginScreenState,
    isWrongCred: Boolean = false,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = ScrollState(0)),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create account",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 18.dp)
        )
        TextField(
            value = registrationViewModel.username,
            onValueChange = { registrationViewModel.updateUserName(it) },
            label = { Text("Email") },
            placeholder = { Text("example@gmail.com") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = registrationViewModel.firstName,
            onValueChange = { registrationViewModel.updateFirstName(it) },
            label = { Text("First Name") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = registrationViewModel.lastName,
            onValueChange = { registrationViewModel.updateLastName(it) },
            label = { Text("Last Name") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        val passwordVisible = rememberSaveable{ mutableStateOf(false) }
        TextField(
            value = registrationViewModel.password,
            onValueChange = { registrationViewModel.updateUserPassword(it) },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                    Icon(imageVector  = image, description)
                }
            }
        )
        val confirmPasswordVisible = rememberSaveable{ mutableStateOf(false) }
        TextField(
            value = registrationViewModel.confirmPassword,
            onValueChange = { registrationViewModel.updateUserConfirmPassword(it) },
            label = { Text("Confirm Password ") },
            visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                val image = if (confirmPasswordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (confirmPasswordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = {confirmPasswordVisible.value = !confirmPasswordVisible.value}){
                    Icon(imageVector  = image, description)
                }
            }
        )
        Button(onClick = { registrationViewModel.postUserRegistrationData() }) {
            Text("Register")
        }

        when(screenState) {
            LoginScreenState.Landing -> {

            }
            LoginScreenState.Loading -> {
                CircularProgressIndicator()
            }
            LoginScreenState.Error -> {
                Spacer(modifier = Modifier.height(25.dp))
                androidx.compose.material3.Text("Please enter valid credentials!")
            }
            LoginScreenState.Success -> {
                Spacer(modifier = Modifier.height(25.dp))
                androidx.compose.material3.Text("Successfully logged in!")
            }
        }

    }
}

@Composable
fun FinalRegistrationScreen(
    registrationViewModel: RegistrationViewModel,
    userResponse: NetworkResult<UserResponse>?,
    navController: NavController,
){
    when (userResponse) {
        is NetworkResult.Error -> {
            RegistrationScreen(
                registrationViewModel = registrationViewModel,
                screenState = LoginScreenState.Error,
                isWrongCred = true,
            )
            registrationViewModel.resetInputFields()
        }
        is NetworkResult.Success -> {
            RegistrationScreen(
                registrationViewModel = registrationViewModel,
                screenState = LoginScreenState.Success,
            )
            LaunchedEffect(Unit) {
                navController.navigate(Screen.Login.route){
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
                Log.d(Constants.TAG, "Successfully navigated to login screen")
            }
        }
        is NetworkResult.Loading -> {
            RegistrationScreen(
                registrationViewModel = registrationViewModel,
                screenState = LoginScreenState.Loading,
            )
        }
        else -> {
            RegistrationScreen(
                registrationViewModel = registrationViewModel,
                screenState = LoginScreenState.Landing,
            )
        }
    }
}