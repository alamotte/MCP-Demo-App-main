package com.mcp.demomcp1.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mcp.demomcp1.routes.catalog.CatalogRoute
import com.mcp.demomcp1.routes.experience.ExperienceRoute
import com.mcp.demomcp1.routes.experience.PromotionRoute
import com.mcp.demomcp1.routes.home.HomeRoute
import com.mcp.demomcp1.routes.serverside.ServerSideRoute

@Composable
fun NavigationComponent(navHostController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navHostController,
        startDestination = HomeRoute.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        HomeRoute.composable(this, navHostController)
        ExperienceRoute.composable(this, navHostController)
        PromotionRoute.composable(this, navHostController)
        ServerSideRoute.composable(this, navHostController)
        CatalogRoute.composable(this, navHostController)
    }
}