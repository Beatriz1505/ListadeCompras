package com.mcr.listacompras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mcr.listacompras.ui.ListaComprasScreen
import com.mcr.listacompras.ui.LojaScreen
import com.mcr.listacompras.ui.theme.MCRTheme
import com.mcr.listacompras.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MCRTheme {
                AppNavigation(viewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "lojas") {
        composable("lojas") {
            LojaScreen(viewModel) { lojaId, nomeLoja ->
                navController.navigate("lista/$lojaId/$nomeLoja")
            }
        }
        composable(
            "lista/{lojaId}/{nomeLoja}",
            arguments = listOf(
                navArgument("lojaId") { type = NavType.IntType },
                navArgument("nomeLoja") { type = NavType.StringType }
            )
        ) { back ->
            ListaComprasScreen(
                lojaId = back.arguments!!.getInt("lojaId"),
                nomeLoja = back.arguments!!.getString("nomeLoja") ?: "",
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}