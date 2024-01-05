package com.bulana.centsense.ui.add_edit_account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bulana.centsense.util.UiEvent

@Composable
fun AddEditAccountScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditAccountViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true, block = {

        viewModel.uiEvent.collect { event ->

            when (event) {

                is UiEvent.PopBackStack -> onPopBackStack()

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }

                else -> Unit
            }
        }
    })

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditAccountEvent.OnSaveAccountClick)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            // Account Name
            TextField(
                value = viewModel.account?.accountName ?: "",
                onValueChange = { viewModel.onEvent(AddEditAccountEvent.onAccountNameChange(it)) },
                placeholder = { Text(text = "Account Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))


            // Account Number
            TextField(
                value = viewModel.account?.accountNumber ?: "",
                onValueChange = { newNumber ->
                    viewModel.onEvent(AddEditAccountEvent.onAccountNumberChange(newNumber))
                },
                placeholder = { Text(text = "Account number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Account Type
            TextField(
                value = viewModel.account?.accountType ?: "",
                onValueChange = { newType ->
                    viewModel.onEvent(AddEditAccountEvent.onAccountTypeChange(newType))
                },
                placeholder = { Text(text = "Account type") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
//
//            // Opening Balance
//
//            TextField(
//                value = viewModel.account?.openingBalance?.toString() ?: "",
//                onValueChange = { newValueString ->
//                    // Convert the new string value to Double
//                    val newDoubleValue = newValueString.toDoubleOrNull() // Handle null if conversion fails
//                    if (newDoubleValue != null) {
//                        // Only update if conversion is successful
//                        viewModel.onEvent(AddEditAccountEvent.onOpeningBalanceChange(newDoubleValue))
//                    }
//                },
//                placeholder = { Text(text = "Opening Balance") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//
//            // Closing Balance
//            TextField(
//                value = viewModel.account?.closingBalance?.toString() ?: "",
//                onValueChange = { newValue: String ->
//                    viewModel.onEvent(
//                        AddEditAccountEvent.onClosingBalanceChange(
//                            newValue.toDoubleOrNull() ?: 0.0
//                        )
//                    )
//                },
//                placeholder = {
//                    Text(text = "Closing Balance")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Annual Interest Rate
//            TextField(
//                value = viewModel.account?.annualInterestRate?.toString() ?: "",
//                onValueChange = {
//                    viewModel.onEvent(
//                        AddEditAccountEvent.onAnnualInterestRateChange(
//                            it.toDoubleOrNull() ?: 0.0
//                        )
//                    )
//                },
//                placeholder = { Text(text = "Annual Interest Rate") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Monthly Installment
//            TextField(
//                value = viewModel.account?.monthlyInstallment?.toString() ?: "",
//                onValueChange = {
//                    viewModel.onEvent(
//                        AddEditAccountEvent.onMonthlyInstallmentChange(
//                            it.toDoubleOrNull() ?: 0.0
//                        )
//                    )
//                },
//                placeholder = { Text(text = "Monthly Installment") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//
//            // First Payment Due Date
//            var showFirstPaymentDatePicker by remember { mutableStateOf(false) }
//            val firstPaymentDate = viewModel.account?.firstPaymentDueDate ?: Date()
//            TextField(
//                value = SimpleDateFormat(
//                    "yyyy-MM-dd",
//                    Locale.getDefault()
//                ).format(firstPaymentDate),
//                onValueChange = { /* Read Only Field */ },
//                readOnly = true,
//                placeholder = { Text(text = "First Payment Due Date") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clickable { showFirstPaymentDatePicker = true }
//            )
//
//            if (showFirstPaymentDatePicker) {
//                DatePickerDialog(
//                    date = firstPaymentDate,
//                    onDateSelected = { selectedDate ->
//                        viewModel.onEvent(
//                            AddEditAccountEvent.onFirstPaymentDueDaterChange(
//                                selectedDate
//                            )
//                        )
//                        showFirstPaymentDatePicker = false
//                    },
//                    onDismissRequest = { showFirstPaymentDatePicker = false }
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Last Payment Due Date
//            var showLastPaymentDatePicker by remember { mutableStateOf(false) }
//
//            val lastPaymentDate = viewModel.account?.lastPaymentDueDate ?: Date()
//
//            TextField(
//                value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(lastPaymentDate),
//                onValueChange = {
//                    /* Read Only Field */
//                },
//                readOnly = true,
//                placeholder = { Text(text = "Last Payment Due Date") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clickable { showLastPaymentDatePicker = true }
//            )
//            if (showLastPaymentDatePicker) {
//
//                DatePickerDialog(
//                    date = lastPaymentDate,
//                    onDateSelected = { selectedDate ->
//                        viewModel.onEvent(
//                            AddEditAccountEvent.onLastPaymentDueDateChange(
//                                selectedDate
//                            )
//                        )
//                        showLastPaymentDatePicker = false
//                    },
//                    onDismissRequest = { showLastPaymentDatePicker = false }
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//
//
//            // Number of Installments Remaining
//            TextField(
//                value = viewModel.account?.numberOfInstallmentsRemaining?.toString() ?: "",
//                onValueChange = { newValue: String ->
//                    viewModel.onEvent(
//                        AddEditAccountEvent.onNumberOfInstallmentsRemainingChange(
//                            newValue.toIntOrNull() ?: 0
//                        )
//                    )
//                },
//                placeholder = { Text(text = "Number of Installments Remaining") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Original Term Months
//            TextField(
//                value = viewModel.account?.originalTermMonths?.toString() ?: "",
//                onValueChange = { newValue: String ->
//                    viewModel.onEvent(
//                        AddEditAccountEvent.onOriginalTermMonthsChange(
//                            newValue.toIntOrNull() ?: 0
//                        )
//                    )
//                },
//                placeholder = { Text(text = "Original Term Months") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Total Arrears
//            TextField(
//                value = viewModel.account?.totalArrears?.toString() ?: "",
//                onValueChange = { newValue: String ->
//                    viewModel.onEvent(
//                        AddEditAccountEvent.onTotalArrearsChange(
//                            newValue.toDoubleOrNull() ?: 0.0
//                        )
//                    )
//                },
//                placeholder = { Text(text = "Total Arrears") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Current Due
//            TextField(
//                value = viewModel.account?.currentDue?.toString() ?: "",
//                onValueChange = {
//                    viewModel.onEvent(
//                        AddEditAccountEvent.onCurrentDueChange(
//                            it.toDoubleOrNull() ?: 0.0
//                        )
//                    )
//                },
//                placeholder = { Text(text = "Current Due") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Settlement
//            TextField(
//                value = viewModel.account?.settlement?.toString() ?: "",
//                onValueChange = {
//                    viewModel.onEvent(
//                        AddEditAccountEvent.onSettlementChange(
//                            it.toDoubleOrNull() ?: 0.0
//                        )
//                    )
//                },
//                placeholder = { Text(text = "Settlement") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//            Spacer(modifier = Modifier.height(8.dp))


        }
    }
}