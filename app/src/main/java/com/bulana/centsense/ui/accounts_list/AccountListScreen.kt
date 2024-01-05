package com.bulana.centsense.ui.accounts_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulana.centsense.util.UiEvent

@Composable
fun AccountListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AccountsListViewModel = hiltViewModel()
) {

    val accounts = viewModel.accountsList.collectAsState(initial = emptyList())

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
            FloatingActionButton(onClick = {
                viewModel.onEvent(AccountEvent.OnAddAccountClick)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            items(accounts.value) { account ->
                AccountItem(
                    account = account,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(AccountEvent.OnAccountClick(account))
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AccountListScreenPreview() {
//    val mockViewModel = AccountsListViewModel()
//    val mockOnNavigate: (UiEvent.Navigate) -> Unit = {}
//
//    AccountListScreen(
//        onNavigate = mockOnNavigate,
//        viewModel = mockViewModel
//    )
//}
