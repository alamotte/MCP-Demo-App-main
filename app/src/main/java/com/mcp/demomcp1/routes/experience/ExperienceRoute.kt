package com.mcp.demomcp1.routes.experience

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

/**
 * Every screen has a route, so that we don't have to add the route setup of all screens to the NavigationComponent.
 *
 * Inherits NavRoute, to be able to navigate away from this screen. All navigation logic is in there.
 */
object ExperienceRoute : NavRoute<ExperienceViewModel> {

    override val route = "experience/"

    @Composable
    override fun viewModel(): ExperienceViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: ExperienceViewModel) = ExperiencePage(viewModel)
}

/**
 * Just your average Composable, nothing special here.
 */
@Composable
private fun ExperiencePage(
    viewModel: ExperienceViewModel
) {
    val viewState = viewModel.viewState
    val context = MCPApplication.instance.applicationContext

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = context.getString(R.string.experience_screen_title),
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
fun ExperiencePreview()
{
     var viewModel = ExperienceViewModel(MyRouteNavigator())
    ExperiencePage(viewModel)
}