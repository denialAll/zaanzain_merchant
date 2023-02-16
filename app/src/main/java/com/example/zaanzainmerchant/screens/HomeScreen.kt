package com.example.zaanzainmerchant.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zaanzainmerchant.R
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.TokenManager
import com.example.zaanzainmerchant.viewmodels.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector? = null, val popUpTo: String? = null) {
    object Login : Screen("login", R.string.login_screen)
    object Registration : Screen("registration", R.string.registration_screen)
    object Home : Screen("home", R.string.home_screen, Icons.Filled.Home, R.string.home_screen.toString())
    object RestaurantDetails : Screen("restaurant_details", R.string.restaurant_details)
    object Settings : Screen("settings", R.string.settings, Icons.Filled.Settings)
    object Profile : Screen("profile", R.string.profile, Icons.Filled.Person)
    object Product : Screen("add_product", R.string.add_product, Icons.Filled.Add)
    object ProductList : Screen("product_list", R.string.product_list, Icons.Filled.List)
    object ProductEdit : Screen("product_edit", R.string.edit_product)
    object UpdateProductImage : Screen("update_product_image", R.string.update_product_image)
    object Logout : Screen("logout", R.string.logout, Icons.Filled.Logout)
    object AddProductImage : Screen("add_product_image", R.string.add_product_image)
    object PreviousOrders : Screen("previous_orders", R.string.previous_orders, Icons.Filled.History)
    object OpenCloseRestaurant : Screen("open_close_restaurant", R.string.open_close_restaurant, Icons.Filled.Lock)
    object MyAddresses : Screen("my_address", R.string.my_address, Icons.Filled.MyLocation)
    object NewAddress: Screen("new_address", R.string.new_address)
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MerchantApp(
    registrationViewModel: RegistrationViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    restaurantDetailViewModel: RestaurantDetailViewModel = viewModel(),
    addProductViewModel: AddProductViewModel = viewModel(),
    productListViewModel: ProductListViewModel = viewModel(),
    productEditViewModel: ProductEditViewModel = viewModel(),
    newOrdersViewModel: NewOrdersViewModel = viewModel(),
    logoutViewModel: LogoutViewModel = viewModel(),
    previousOrdersViewModel: PreviousOrdersViewModel = viewModel(),
    openCloseRestaurantViewModel: OpenCloseRestaurantViewModel = viewModel(),
    addressViewModel: AddressViewModel = viewModel(),
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
                navController = navController,
                restaurantDetailViewModel = restaurantDetailViewModel
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                newOrdersViewModel = newOrdersViewModel
            )
        }
    ) { innerPadding ->
        var startDest = Screen.Login.route
        if (tokenManager.getToken() != null) {
            startDest = Screen.Home.route
            Log.d(TAG, "token is: ${tokenManager.getToken()}")
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
                    newOrdersViewModel = newOrdersViewModel,
                    navigateToRegistration = {
                        navController.navigate(Screen.Registration.route){
                            popUpTo(Screen.Login.route)
                        }

                    }
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
                NewOrdersScreen(
                    newOrdersViewModel = newOrdersViewModel
                )
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
            composable(route = Screen.Product.route) {
                AddProductScreen(
                    addProductViewModel = addProductViewModel,
                    navController = navController
                )
            }
            composable(route = Screen.AddProductImage.route){
                ImageSelectScreen(
                    addProductViewModel = addProductViewModel,
                    navController = navController
                )
            }
            composable(route = Screen.ProductList.route) {
                ProductListScreen(
                    productListViewModel = productListViewModel,
                    productEditViewModel = productEditViewModel,
                    navController = navController
                )
            }
            composable(route = Screen.ProductEdit.route) {
                ProductEditScreen(
                    productEditViewModel = productEditViewModel, 
                    productListViewModel = productListViewModel,
                    navController = navController
                )
            }
            composable(route = Screen.UpdateProductImage.route) {
                ImageUpdateScreen(productEditViewModel = productEditViewModel)
            }
            composable(route = Screen.Logout.route) {
                LogoutScreen(
                    logoutViewModel = logoutViewModel,
                    navController = navController
                )
            }
            composable(route = Screen.PreviousOrders.route){
                PreviousOrdersScreen(
                    previousOrdersViewModel = previousOrdersViewModel
                )
            }
            composable(route = Screen.OpenCloseRestaurant.route){
                OpenCloseRestaurantScreen(
                    openCloseRestaurantViewModel = openCloseRestaurantViewModel
                )
            }
            composable(route = Screen.MyAddresses.route){
                MyAddressesScreen(addressViewModel = addressViewModel, navController = navController)
            }

            composable(route = Screen.NewAddress.route){
                NewAddressScreen(
                    addressViewModel = addressViewModel,
                    navController = navController
                )
            }

        }

    }
}


@Composable
fun BottomBar(
    navController: NavController,
    newOrdersViewModel: NewOrdersViewModel
){
    val screens = listOf(
        Screen.Home
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val cartList by newOrdersViewModel.newOrdersCartList.collectAsState()
        screens.forEach { screen ->
            Log.d(TAG, "the bottom navigation item is ${screen.route}")
            BottomNavigationItem(
                alwaysShowLabel = true,
                icon = {
                    val badgeNumber: Int = cartList.size
                    if (badgeNumber > 0){
                        BadgedBox(badge = {
                            Badge(
                                backgroundColor = MaterialTheme.colorScheme.onPrimary
                            ) {
                                Text(
                                    text = badgeNumber.toString(),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }) {
                            Icon(
                                screen.icon!!,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    } else {
                        Icon(
                            screen.icon!!,
                            null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                },
                selected = currentDestination?.hierarchy?.any {it.route == screen.route} == true,
                selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(screen.popUpTo!!) { inclusive = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
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
                Icon(
                    Icons.Filled.Menu,
                    ""
                )
            }
        },
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController,
    restaurantDetailViewModel: RestaurantDetailViewModel
){
    LaunchedEffect(Unit){
        restaurantDetailViewModel.getMerchantInfo()
    }

    val userDetails by restaurantDetailViewModel.merchantInfo.collectAsState()

    val items = listOf(
        Screen.Settings,
        Screen.Profile,
        Screen.Product,
        Screen.ProductList,
        Screen.PreviousOrders,
        Screen.OpenCloseRestaurant,
        Screen.MyAddresses,
        Screen.Logout
    )
    Column {

        if (userDetails != null){
            androidx.compose.material3.Text(
                text = "${userDetails!!.firstName} ${userDetails!!.lastName}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 12.dp, top = 24.dp, bottom = 12.dp)
            )
            androidx.compose.material3.Text(
                text = "${userDetails!!.name}",
                fontSize = 13.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(start = 12.dp, bottom = 24.dp)
            )
        } else {
            androidx.compose.material3.Text(
                text = "Not logged in!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 12.dp, top = 24.dp, bottom = 12.dp)
            )
        }

        androidx.compose.material3.Divider(color = Color.Black)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
        )



        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            DrawerItem(
                item = item ,
                selected = currentRoute == item.route,
                onItemClick = {
                    navController.navigate(item.route){
                        popUpTo(Screen.Home.route)
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
            .height(45.dp)
            .padding(start = 10.dp)

    ){
        Image(imageVector = item.icon!!,
            contentDescription = "icon is ${item.route}",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = stringResource(id = item.resourceId),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }

}

