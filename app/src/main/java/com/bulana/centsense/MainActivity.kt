package com.bulana.centsense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bulana.centsense.ui.accounts_list.AccountListScreen
import com.bulana.centsense.ui.add_edit_account.AddEditAccountScreen
import com.bulana.centsense.ui.theme.CentsenseTheme
import com.bulana.centsense.util.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CentsenseTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.ACCOUNTS_LIST
                ) {

                    composable(Routes.ACCOUNTS_LIST) {

                        AccountListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }

                    composable(
                        route = Routes.ADD_EDIT_ACCOUNT + "?accountId={accountId}",
                        arguments = listOf(
                            navArgument(name = "accountId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditAccountScreen(onPopBackStack = {
                            navController.popBackStack()
                        }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CentsenseTheme {
        AccountListScreen(
            onNavigate = {}
        )
    }
}