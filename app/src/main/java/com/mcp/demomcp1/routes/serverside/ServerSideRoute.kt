package com.mcp.demomcp1.routes.serverside

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcp.demomcp1.MCPApplication
import com.mcp.demomcp1.R
import com.mcp.demomcp1.navigation.MyRouteNavigator
import com.mcp.demomcp1.navigation.NavRoute
import com.mcp.demomcp1.templates.ShowBanner

object ServerSideRoute : NavRoute<ServerSideViewModel> {

    override val route = "serverside/"

    @Composable
    override fun viewModel(): ServerSideViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: ServerSideViewModel) = ServerSidePage(viewModel)
}

@Composable
private fun ServerSidePage(
    viewModel: ServerSideViewModel
) {
    val viewState = viewModel.viewState
    val context = MCPApplication.instance.applicationContext

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = context.getString(R.string.serverside_screen_title),
            style = MaterialTheme.typography.titleSmall,
        )

        if(!viewState.bannerType.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(30.dp))
            ShowBanner(data = viewState, ctaAction = viewModel::onCtaClicked)
        }
    }
}

@Preview
@Composable
fun ServerSidePreview()
{
    var viewModel = ServerSideViewModel(MyRouteNavigator())
    com.mcp.demomcp1.routes.serverside.ServerSidePage(viewModel)
}