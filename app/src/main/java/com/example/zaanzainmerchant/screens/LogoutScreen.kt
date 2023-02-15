package com.example.zaanzainmerchant.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.zaanzainmerchant.viewmodels.LogoutViewModel

@Composable
fun LogoutScreen(
    logoutViewModel: LogoutViewModel,
    navController: NavController
){
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Logout")
            },
            text = {
                Text(
                    "Are you sure you want to logout?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        logoutViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route)
                        }

                    }
                ) {
                    Text("Confirm", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        navController.navigate(Screen.Home.route){
                            popUpTo(Screen.Home.route)
                        }
                    }
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.primary)
                }
            }
        )
    }
}