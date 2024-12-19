package com.example.bytecity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bytecity.model.DbConn
import com.example.bytecity.model.ScreenWithModalNavigationDrawer
import com.example.bytecity.ui.theme.ByteCityTheme
import com.example.bytecity.view.Navigation.MainNavDrawer
import com.example.bytecity.view.Navigation.Navigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ByteCityTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route


                //Check navigationStack in Log
                navController.addOnDestinationChangedListener { controller, _, _ ->
                    val routes = controller
                        .currentBackStack.value
                        .map { it.destination.route }
                        .joinToString(", ")

                    Log.d("BackStackLog", "BackStack: $routes")
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        DbConn.connect(this@MainActivity)
                    }
//
                    if(currentRoute == null || currentRoute in ScreenWithModalNavigationDrawer.AllScreens.list)
                    {
                        MainNavDrawer(drawerState, navController){
                            navController, drawerState -> Navigation(navController = navController, drawerState)
                        }
                    }
                    else{
                        Navigation(navController = navController, drawerState = drawerState)
                    }


//                    MakeReviewPage(idProduct = 1, navHostController=navController)

                }
            }
        }
    }

    override fun onDestroy() {
        DbConn.connection.close()
        super.onDestroy()
    }

}


