package com.bulana.centsense.util

sealed class UiEvent {
    data object PopBackStack : UiEvent()

    data class Navigate(val route: String) : UiEvent()

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ) : UiEvent()

}
