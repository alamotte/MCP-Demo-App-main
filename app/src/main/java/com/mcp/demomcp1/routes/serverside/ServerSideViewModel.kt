package com.mcp.demomcp1.routes.serverside

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.evergage.android.Evergage
import com.evergage.android.Screen
import com.mcp.demomcp1.MCPApplication
import com.mcp.demomcp1.MCPSendEvent
import com.mcp.demomcp1.R
import com.mcp.demomcp1.api.ServerSideCampaign
import com.mcp.demomcp1.navigation.RouteNavigator
import com.mcp.demomcp1.templates.BannerViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServerSideViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
) : ViewModel(), RouteNavigator by routeNavigator { // prefer delegation over inheritance

    private val context = MCPApplication.instance.applicationContext
    private val TAG: String = "MCP Demo"
    private val trackingAction = context.getString(R.string.serverside_screen_action)
    private val campaignAction: String = context.getString(R.string.serverside_campaign_action)
    private val applicationName: String = context.getString(R.string.serverside_app_name)
    private val screen: Screen? = Evergage.getInstance().getScreenForActivity(MCPApplication.instance.currentActivity)

    //ViewState should be constructed from Campaign Data
    var viewState by mutableStateOf(
        BannerViewState(bannerType = "",
            promotionId = "",
            promotionObjectId = "",
            backgroundColor = "",
            headerColor = "",
            bodyColor = "",
            imageUrl = "",
            header = "",
            body = "",
            textCTA = "",
            url = "")
    )

    init {
        screen?.let {
            //Tracking action
            MCPSendEvent(screen, trackingAction)
        }

        val campaignData = ServerSideCampaign().getCampaign(campaignAction, applicationName, responseCallback =  {
            if(it.has("payload"))
            {
                viewState.experienceId = it.getString("experienceId")
                viewState.campaignId = it.getString("campaignId")

                val payload = it.getJSONObject("payload")
                viewState.bannerType = "serverside"
                viewState.body = payload.optString("body")
                viewState.url = payload.optString("ctaURL")
                viewState.promotionObjectId = payload.optString("promoId")
                viewState.header = payload.optString("headerText")
                viewState.textCTA = payload.optString("ctaText")
                viewState.bodyColor = "FF" + payload.getJSONObject("bodyColor").optString("hex").uppercase().drop(1)
                viewState.headerColor = "FF" + payload.getJSONObject("headerColor").optString("hex").uppercase().drop(1)
                viewState.imageUrl = payload.optString("imageUrl")
            }
        })
    }

    fun onCtaClicked(p : Int) {
        Log.d(TAG, "Navigating to ${viewState.url}")

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(viewState.url))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContextCompat.startActivity(
            MCPApplication.instance.currentActivity.applicationContext,
            browserIntent,
            null
        )
    }
}