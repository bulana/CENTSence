package com.bulana.centsense.ui.accounts_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulana.centsense.util.UiEvent
import java.text.NumberFormat

@Composable
fun AccountListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AccountsListViewModel = hiltViewModel()
) {

    val accounts = viewModel.accountsList.collectAsState(initial = emptyList())

    val totalInstallment = accounts.value.sumOf { it.monthlyInstallment }

    val grey = Color(0xFF757575)

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true, block = {

        viewModel.uiEvent.collect { event ->

            when (event) {

                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.ShowSnackbar -> {

                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )

                    when (result) {

                        SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(AccountEvent.OnUndoDeleteClick)
                        }

                        SnackbarResult.Dismissed -> {}

                        else -> Unit
                    }
                }

                else -> Unit
            }

        }
    })

    Scaffold(

        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AccountEvent.OnAddAccountClick)
                },
                backgroundColor = Color(0xFF757575)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (accounts.value.isEmpty()) {
                // Display a message when there are no accounts
                Text(
                    text = "No accounts added. Please add an account.",
                    fontSize = 24.sp,
                    color = grey,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // Display account count and total installment value
                Text(
                    text = "${accounts.value.size} Accounts",
                    fontSize = 24.sp,
                    color = grey,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total Installment Value",
                        color = grey,
                        style = MaterialTheme.typography.subtitle1
                    )

                    Text(
                        totalInstallment.formatAsCurrency(),
                        color = Color.Red,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                }

                // List of accounts
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(
                        accounts.value
                    ) { account ->
                        AccountItem(
                            account = account,
                            onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.onEvent(AccountEvent.OnAccountClick(account)) }
                                .padding(16.dp)
                                .padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


// Utility function to format numbers as currency
fun Double.formatAsCurrency(): String {
    val format = NumberFormat.getCurrencyInstance()
    return format.format(this)
}

