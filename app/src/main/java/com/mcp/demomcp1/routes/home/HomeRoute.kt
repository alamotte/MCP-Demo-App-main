package com.mcp.demomcp1.routes.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mcp.demomcp1.MCPApplication
import com.mcp.demomcp1.R
import com.mcp.demomcp1.navigation.NavRoute

object HomeRoute : NavRoute<HomeViewModel> {

    override val route = "home/"

    @Composable
    override fun viewModel(): HomeViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: HomeViewModel) = Home(viewModel::onExperienceClicked, viewModel::onPromotionClicked, viewModel::onServerSideClicked, viewModel::onCatalogClicked)
}

@Composable
private fun Home(
    onBannerExperienceClicked: () -> Unit,
    onBannerPromotionClicked : () -> Unit,
    onBannerServerSideClicked: () -> Unit,
    onCatalogClicked: () -> Unit
) {
    val context = MCPApplication.instance.applicationContext

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(context.getString(R.string.home_screen_logo))
                .build(),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = context.getString(R.string.home_screen_title),
            style = MaterialTheme.typography.displayMedium,
        )
        Button(onClick = onBannerExperienceClicked) {
            Text(text = context.getString(R.string.experience_menu_item))
        }
        Button(onClick = onBannerPromotionClicked) {
            Text(text = context.getString(R.string.promotion_menu_item))
        }
        Button(onClick = onBannerServerSideClicked) {
            Text(text = context.getString(R.string.serverside_menu_item))
        }
        Button(onClick = onCatalogClicked) {
            Text(text = context.getString(R.string.catalog_menu_item))
        }
    }
}

@Preview
@Composable
fun HomePreview()
{
    Home(onBannerExperienceClicked = {}, onBannerPromotionClicked = {}, onBannerServerSideClicked = {}, onCatalogClicked = {})
}