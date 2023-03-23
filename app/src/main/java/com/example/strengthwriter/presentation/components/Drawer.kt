package com.example.strengthwriter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import com.example.strengthwriter.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.strengthwriter.ui.theme.PADDING_DRAWER
import com.example.strengthwriter.ui.theme.SPACER_LARGE_HEIGHT
import com.example.strengthwriter.ui.theme.SPACER_MEDIUM_HEIGHT
import com.example.strengthwriter.ui.theme.SPACER_WIDTH

// drawer - https://semicolonspace.com/jetpack-compose-navigation-drawer/

@Preview
@Composable
private fun DrawerContentPreview() {
    DrawerContent(
        itemClickListener = {}
    ) {

    }
}

@Composable
fun DrawerContent(
    gradientColor: List<Color> = listOf(Color(0xFF382F2F), Color(0xFF747474)),
    itemClickListener: (String) -> Unit,
    closeDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColor))
            .padding(all = PADDING_DRAWER),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val itemList = navDrawerItemList()
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            repeat(itemList.size) {i ->
                NavigationListItem(
                    item = itemList[i],
                    itemClickListener = itemClickListener,
                    closeDrawer = closeDrawer
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .size(SPACER_LARGE_HEIGHT))
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
fun NavigationListItem(
    item: DrawerItem,
    itemClickListener: (String) -> Unit,
    closeDrawer: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                closeDrawer()
                itemClickListener(item.route)
            }
    ) {
        Icon(
            painter = item.icon,
            contentDescription = item.icon.toString(),
            tint = Color.White
        )
        Spacer(modifier = Modifier.size(SPACER_WIDTH))
        Text(
            text = item.name,
            color = Color.White
        )
    }
}

@Composable
fun navDrawerItemList(): List<DrawerItem> {
    return listOf(
        DrawerItem(
            name = "List",
            route = "list",
            icon = painterResource(R.drawable.ic_list_24)
        ),
        DrawerItem(
            name = "Calculator",
            route = "calculator",
            icon = painterResource(R.drawable.ic_calculate_24)
        )
    )
}

data class DrawerItem(
    val name: String,
    val route: String,
    val icon: Painter
)