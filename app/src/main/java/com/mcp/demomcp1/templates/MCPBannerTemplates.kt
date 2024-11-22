package com.mcp.demomcp1.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mcp.demomcp1.ui.theme.Demomcp1Theme

data class BannerViewState(
    var bannerType: String?,
    var promotionId: String? = "",
    var promotionObjectId: String? = "",
    var backgroundColor: String? = "",
    var headerColor: String? = "",
    var bodyColor: String? = "",
    var imageUrl: String? = "",
    var header: String? = "",
    var body: String? = "",
    var textCTA: String? = "",
    var url: String? = "",
    var campaignId: String = "",
    var experienceId: String = ""
)

@Composable
fun ShowBanner(data: BannerViewState,
               ctaAction : (Int) -> Unit) {

    //We can select different templates based on the bannerType attribute
    when (data.bannerType) {
        "experience" -> ExperienceBanner(data, ctaAction)
        "promotion" -> PromotionBanner(data, ctaAction)
        "serverside" -> ServerSideBanner(data, ctaAction)
    }
}
@Composable
fun ServerSideBanner(data: BannerViewState, ctaAction: (Int) -> Unit)
{
    var backgroundColor : Color = Color(java.lang.Long.parseLong("FF3E50B4",16))
    var headerColor : Color = Color(java.lang.Long.parseLong("FFFFFFFF",16))
    var bodyColor : Color = Color(java.lang.Long.parseLong("FFFFFFFF",16))

    if(!data.backgroundColor.isNullOrEmpty())
    {
        backgroundColor = Color(java.lang.Long.parseLong(data.backgroundColor,16))
    }

    if(!data.headerColor.isNullOrEmpty())
    {
        headerColor = Color(java.lang.Long.parseLong(data.headerColor,16))
    }

    if(!data.bodyColor.isNullOrEmpty())
    {
        bodyColor = Color(java.lang.Long.parseLong(data.bodyColor,16))
    }

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(defaultElevation = 5.dp))
    {
        Box(Modifier.height(100.dp))
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.imageUrl)
                        .build(),
                    contentDescription = null,
                )
                Column(Modifier.background(backgroundColor).fillMaxHeight()) {
                    Text(
                        // on below line specifying text for heading.
                        text = data.header.toString(),
                        fontSize = 12.sp,
                        // adding text alignment,
                        textAlign = TextAlign.Center,
                        // on below line adding text color.
                        color = headerColor,
                        // on below line adding font weight.
                        fontWeight = FontWeight.Bold,
                        // on below line adding padding from all sides.
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        // on below line specifying text for heading.
                        text = data.body.toString(),
                        fontSize = 8.sp,
                        // adding text alignment,
                        textAlign = TextAlign.Center,
                        // on below line adding text color.
                        color = bodyColor,
                        // on below line adding padding from all sides.
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun PromotionBanner(data: BannerViewState, ctaAction: (Int) -> Unit)
{
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(defaultElevation = 5.dp))
    {
        Box(Modifier.height(200.dp))
        {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                .data(data.imageUrl)
                .build(),
                contentDescription = null,
                Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), startY = 50f
                        )
                    ))
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(12.dp), contentAlignment = Alignment.BottomStart) {
                Text(
                    text = data.header.toString(),
                    style = TextStyle(color = Color.White, fontSize = 16.sp)
                )
            }
        }
    }
}

@Composable
fun ExperienceBanner(data : BannerViewState,
ctaAction : (Int) -> Unit)
{
    var backgroundColor : Color = Color(java.lang.Long.parseLong("FF3E50B4",16))
    var headerColor : Color = Color(java.lang.Long.parseLong("FFFFFFFF",16))
    var bodyColor : Color = Color(java.lang.Long.parseLong("FFFFFFFF",16))

    if(!data.backgroundColor.isNullOrEmpty())
    {
        backgroundColor = Color(java.lang.Long.parseLong(data.backgroundColor,16))
    }

    if(!data.headerColor.isNullOrEmpty())
    {
        headerColor = Color(java.lang.Long.parseLong(data.headerColor,16))
    }

    if(!data.bodyColor.isNullOrEmpty())
    {
        bodyColor = Color(java.lang.Long.parseLong(data.bodyColor,16))
    }

    Row(modifier = Modifier.padding(all = 8.dp)) {
        Column(modifier = Modifier.background(backgroundColor)){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.imageUrl)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(55.dp)
            )
        }
        Spacer(modifier = Modifier.width(18.dp))
        Column {
            Text(text = data.header ?: "", color = headerColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = data.body ?: "", color = bodyColor)
        }
        Spacer(modifier = Modifier.width(18.dp))
        Column {
            ClickableText(
                text = AnnotatedString(data.textCTA ?: ""),
                style = MaterialTheme.typography.titleSmall,
                onClick = ctaAction
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ExperienceBannerPreview()
{
    val campaignData = BannerViewState(
        bannerType = "experience",
        promotionId = null,
        promotionObjectId = null,
        imageUrl= "https://toppng.com/uploads/preview/banco-estado-logo-vector-free-1157417441645zqbo15eo.png",
        header = "Este es el título del banner",
        headerColor = "FFFF9100",
        body = "Este es el subtítulo del banner",
        bodyColor = "FF000000",
        backgroundColor = "FF3E50B4",
        textCTA = "Click aquí",
        url = "https://www.bancoestado.cl"
    )

    Demomcp1Theme {
        ExperienceBanner(campaignData) {}
    }
}
