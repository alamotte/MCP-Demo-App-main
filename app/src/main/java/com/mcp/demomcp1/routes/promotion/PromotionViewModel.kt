package com.mcp.demomcp1.routes.experience

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.evergage.android.Campaign
import com.evergage.android.CampaignHandler
import com.evergage.android.Evergage
import com.evergage.android.Screen
import com.mcp.demomcp1.MCPApplication
import com.mcp.demomcp1.MCPSendEvent
import com.mcp.demomcp1.R
import com.mcp.demomcp1.buildBannerData
import com.mcp.demomcp1.navigation.RouteNavigator
import com.mcp.demomcp1.templates.BannerViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PromotionViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
) : ViewModel(), RouteNavigator by routeNavigator { // prefer delegation over inheritance

    private val TAG: String = "MCP Demo"
    val context = MCPApplication.instance.applicationContext
    private val trackingAction = context.getString(R.string.promotion_screen_action)
    private val campaignTarget: String = context.getString(R.string.promotion_campaign_action)
    private val screen: Screen? = Evergage.getInstance().getScreenForActivity(MCPApplication.instance.currentActivity)
    private var activeCampaign: Campaign? = null

    //ViewState should be constructed from Campaign Data
    var viewState by mutableStateOf(
        BannerViewState(bannerType = "",
            promotionId = "",
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
           val handler = object : CampaignHandler {
               override fun handleCampaign(campaign: Campaign) {
                   val bannerType = campaign.data.optString("bannerType")
                   if (bannerType.isEmpty()) {
                       Log.d(TAG, "No bannerType")
                       return
                   }

                   if (activeCampaign != null && activeCampaign == campaign) {
                       Log.d(TAG, "Ignoring campaign name " + campaign.campaignName + " since equivalent content is already active")
                       return
                   }

                   // Track the impression for statistics even if the user is in the control group.
                   screen.trackImpression(campaign)

                   // Only display the campaign if the user is not in the control group.
                   if (!campaign.isControlGroup) {
                       activeCampaign = campaign
                       // Keep active campaign as long as needed for (re)display and comparison
                       Log.d(TAG, "New active campaign name " + campaign.campaignName + " for target " + campaign.target + " with data " + campaign.data)

                       //Custom campaign tracking

                       // Display campaign content
                       viewState = buildBannerData(campaign.data)
                       viewState.campaignId = campaign.campaignId
                       viewState.experienceId = campaign.experienceId
                   }
               }
           }

           screen.setCampaignHandler(handler, campaignTarget)
           //Tracking action
           MCPSendEvent(screen, trackingAction)
       }
    }

    fun onCtaClicked(p : Int) {
        Log.d(TAG, "Navigating to ${viewState.imageUrl}")
        activeCampaign?.let { it1 -> screen?.trackClickthrough(it1) }

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(viewState.url))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContextCompat.startActivity(
            MCPApplication.instance.currentActivity.applicationContext,
            browserIntent,
            null
        )
    }
}