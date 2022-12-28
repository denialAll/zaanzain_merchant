package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zaanzainmerchant.R
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.TokenManager
import com.example.zaanzainmerchant.viewmodels.LoginViewModel
import com.example.zaanzainmerchant.viewmodels.RegistrationViewModel
import com.example.zaanzainmerchant.viewmodels.RestaurantDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector? = null) {
    object Login : Screen("login", R.string.login_screen)
    object Registration : Screen("registration", R.string.registration_screen)
    object Home : Screen("home", R.string.home_screen)
    object RestaurantDetails : Screen("restaurant_details", R.string.restaurant_details)
    object Test : Screen("test", R.string.test_screen)
    object Settings : Screen("settings", R.string.settings, Icons.Filled.Settings)
    object Profile : Screen("profile", R.string.profile, Icons.Filled.Person)
    object Product : Screen("add_product", R.string.add_product, Icons.Filled.Add)
}


@Composable
fun MerchantApp(
    registrationViewModel: RegistrationViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    restaurantDetailViewModel: RestaurantDetailViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    tokenManager: TokenManager = TokenManager(LocalContext.current),
    scope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(DrawerState(initialValue = DrawerValue.Closed))
){
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerContent = {
                        Drawer(
                            scope = scope,
                            scaffoldState = scaffoldState,
                            navController = navController
                        )
        },
        bottomBar = {}
    ) { innerPadding ->
        var startDest = Screen.Login.route
        if (tokenManager.getToken() != null) {
            startDest = Screen.Test.route
        }
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
            composable(route = Screen.Profile.route ) {
                RestaurantDetailScreen(
                    restaurantDetailViewModel = restaurantDetailViewModel
                )
            }
            composable(route = Screen.Product.route) {
                Text(text = "Add product screen")
            }
            composable(route = Screen.Settings.route ){
                Text(text = "Settings screen")
            }
            composable(route = Screen.Test.route) {
                TestScreen(
                    viewModel = restaurantDetailViewModel
                )
            }

        }

    }
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
){
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name))},
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        }
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
){
    val items = listOf(
        Screen.Settings,
        Screen.Profile,
        Screen.Product
    )
    Column {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            DrawerItem(
                item = item ,
                selected = currentRoute == item.route,
                onItemClick = {
                    navController.navigate(item.route){

                    }
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        }
    }
}

@Composable
fun DrawerItem(
    item: Screen,
    selected: Boolean,
    onItemClick: (Screen) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })

    ){
        Image(imageVector = item.icon!!,
            contentDescription = "icon is ${item.route}"
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = stringResource(id = item.resourceId)
        )
    }

}


























