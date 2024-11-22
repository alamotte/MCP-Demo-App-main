package com.mcp.demomcp1.routes.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mcp.demomcp1.navigation.RouteNavigator
import com.mcp.demomcp1.routes.catalog.CatalogRoute
import com.mcp.demomcp1.routes.experience.ExperienceRoute
import com.mcp.demomcp1.routes.experience.PromotionRoute
import com.mcp.demomcp1.routes.serverside.ServerSideRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val routeNavigator: RouteNavigator,
) : ViewModel(), RouteNavigator by routeNavigator {
    fun onExperienceClicked() {
        Log.d("Demo", "Experience clicked")
        navigateToRoute(ExperienceRoute.route)
    }

    fun onPromotionClicked()
    {
        Log.d("Demo", "Promotion clicked")
        navigateToRoute(PromotionRoute.route)
    }

    fun onServerSideClicked()
    {
        Log.d("Demo", "Server Side clicked")
        navigateToRoute(ServerSideRoute.route)
    }

    fun onCatalogClicked()
    {
        Log.d("Demo", "Catalog clicked")
        navigateToRoute(CatalogRoute.route)
    }
}