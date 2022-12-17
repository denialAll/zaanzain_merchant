package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zaanzainmerchant.R
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.TokenManager
import com.example.zaanzainmerchant.viewmodels.LoginViewModel
import com.example.zaanzainmerchant.viewmodels.RegistrationViewModel
import com.example.zaanzainmerchant.viewmodels.RestaurantDetailViewModel


sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Login : Screen("login", R.string.login_screen)
    object Registration : Screen("registration", R.string.registration_screen)
    object Home : Screen("home", R.string.home_screen)
    object RestaurantDetails : Screen("restaurant_details", R.string.restaurant_details)
}


@Composable
fun MerchantApp(
    registrationViewModel: RegistrationViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    restaurantDetailViewModel: RestaurantDetailViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    tokenManager: TokenManager = TokenManager(LocalContext.current)
){
    Scaffold(
        topBar = {},
        bottomBar = {}
    ) { innerPadding ->
        var startDest = Screen.Login.route
        if (tokenManager.getToken() != null) {
            startDest = Screen.RestaurantDetails.route
        }
        Log.d(TAG, "Start")
        NavHost(
            navController = navController,
            startDestination = startDest,
            Modifier.padding(innerPadding)
        ){
            composable(route = Screen.Login.route){
                val userResponse by loginViewModel.uiUserResponse.collectAsState()
                LoginFinalScreen(
                    loginViewModel = loginViewModel,
                    userResponse = userResponse,
                    navController = navController,
                    navigateToRegistration = { navController.navigate(Screen.Registration.route) }
                )
            }
            composable(route = Screen.Registration.route) {
                val userResponse by registrationViewModel.uiUserResponse.collectAsState()
                FinalRegistrationScreen(
                    registrationViewModel = registrationViewModel,
                    userResponse = userResponse,
                    navController = navController
                )
            }
            composable(route = Screen.Home.route) {
                NewOrdersScreen()
            }
            composable(route = Screen.RestaurantDetails.route) {
                RestaurantDetailScreen(
                    restaurantDetailViewModel = restaurantDetailViewModel
                )
            }

        }

    }

}
