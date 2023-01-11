package com.example.zaanzainmerchant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zaanzainmerchant.viewmodels.LogoutViewModel

@Composable
fun LogoutScreen(
    logoutViewModel: LogoutViewModel
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
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}