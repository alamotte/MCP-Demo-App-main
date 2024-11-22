package com.mcp.demomcp1.routes.catalog

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.evergage.android.Evergage
import com.evergage.android.Screen
import com.evergage.android.promote.LineItem
import com.evergage.android.promote.Order
import com.evergage.android.promote.Product
import com.evergage.android.promote.Tag
import com.mcp.demomcp1.MCPApplication
import com.mcp.demomcp1.MCPSendEvent
import com.mcp.demomcp1.R
import com.mcp.demomcp1.navigation.RouteNavigator
import com.mcp.demomcp1.templates.BannerViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
) : ViewModel(), RouteNavigator by routeNavigator { // prefer delegation over inheritance

    private val context = MCPApplication.instance.applicationContext
    private val TAG: String = "MCP Demo"
    private val trackingAction = context.getString(R.string.catalog_screen_action)
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
    }

    fun onViewItemClicked()
    {
        Log.d("Demo", "ViewItem clicked")
        screen?.viewItem(Product(context.getString(R.string.catalog_viewItem_product_id)))
        Toast.makeText(context, "View item", Toast.LENGTH_SHORT).show()
    }

    fun onPurchaseClicked()
    {
        Log.d("Demo", "Purchase clicked")
        val items = arrayListOf(LineItem(Product(context.getString(R.string.catalog_viewItem_product_id)),1))
        val order = Order(UUID.randomUUID().toString(),items, 1.0)
        screen?.purchase(order)
        Toast.makeText(context, "Purchase", Toast.LENGTH_SHORT).show()
    }

    fun onTagClicked()
    {
        Log.d("Demo", "Tag clicked")
        val product = Product(context.getString(R.string.catalog_viewItem_product_id))
        val tag = Tag(context.getString(R.string.catalog_viewItem_tag), Tag.Type.ItemClass)
        product.tags?.add(tag)

        //Solo Tag, sin producto
        screen?.viewTag(Tag(context.getString(R.string.catalog_viewItem_tag), Tag.Type.ItemClass));

        //Producto + tag
        screen?.viewItem(product)
        Toast.makeText(context, "Tag", Toast.LENGTH_SHORT).show()
    }
}