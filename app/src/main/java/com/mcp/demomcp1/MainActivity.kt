package com.mcp.demomcp1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.evergage.android.Screen
import com.google.gson.Gson
import com.mcp.demomcp1.navigation.NavigationComponent
import com.mcp.demomcp1.templates.BannerViewState
import com.mcp.demomcp1.ui.theme.Demomcp1Theme
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject

fun MCPSendEvent(screen: Screen, route : String, type: String = "tracking")
{
    screen.trackAction(route)
    Log.d("Evergage", "Event sent -> $route")
}

fun buildBannerData(json: JSONObject): BannerViewState {
    val jsonString: String = json.toString()

    if(json["bannerType"] == "promotion"){
        val jobj = json["promotion"];
        val assets = (jobj as JSONArray).getJSONObject(0)["assets"];
        val attributes = (jobj as JSONArray).getJSONObject(0)["attributes"];

        val state : BannerViewState = BannerViewState(
            bannerType = json.getString("bannerType"),
            imageUrl = (assets as JSONArray).getJSONObject(0)["imageUrl"].toString(),
            header = ((attributes as JSONObject)["description"] as JSONObject).getString("value").toString()
        );

        return state;
    }


    return Gson().fromJson(jsonString, BannerViewState::class.java)
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private var activeCampaign: Campaign? = null
    lateinit var navController : NavHostController

    // [START ask_post_notifications]
    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    // [END ask_post_notifications]

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            navController = rememberNavController()
            Demomcp1Theme {
                Scaffold {
                    NavigationComponent(navController, it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

}
