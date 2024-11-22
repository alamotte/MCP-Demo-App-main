package com.mcp.demomcp1.routes.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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

object CatalogRoute : NavRoute<CatalogViewModel> {

    override val route = "catalog/"

    @Composable
    override fun viewModel(): CatalogViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: CatalogViewModel) = CatalogPage(viewModel, viewModel::onViewItemClicked, viewModel::onPurchaseClicked, viewModel::onTagClicked)
}

@Composable
private fun CatalogPage(
    viewModel: CatalogViewModel,
    onViewItemClicked: () -> Unit,
    onPurchaseClicked: () -> Unit,
    onTagClicked: () -> Unit,
) {
    val viewState = viewModel.viewState
    val context = MCPApplication.instance.applicationContext

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = context.getString(R.string.catalog_screen_title),
            style = MaterialTheme.typography.titleSmall,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = onViewItemClicked) {
            Text(text = context.getString(R.string.catalog_viewitem))
        }

        Button(onClick = onPurchaseClicked) {
            Text(text = context.getString(R.string.catalog_purchase))
        }

        Button(onClick = onTagClicked) {
            Text(text = context.getString(R.string.catalog_tag))
        }
    }
}

@Preview
@Composable
fun CatalogPreview()
{
    var viewModel = CatalogViewModel(MyRouteNavigator())
    CatalogPage(viewModel, onViewItemClicked = {}, onPurchaseClicked = {}, onTagClicked = {})
}