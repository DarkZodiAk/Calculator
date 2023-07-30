package com.exampl.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exampl.myapplication.ui.theme.MyApplicationTheme
import java.lang.IllegalArgumentException

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                var text by remember { mutableStateOf("0") }

                val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
                val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                            Text(
                                text = text,
                                fontSize = 48.sp,
                                lineHeight = 52.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                            Keyboard(text = text, onClick = { text = it })
                        }
                    },
                    sheetContainerColor = Color.Transparent,
                    sheetShadowElevation = 0.dp,
                    sheetShape = RectangleShape,
                    sheetPeekHeight = 96.dp,
                    sheetDragHandle = {},
                    content = {}
                )
            }
        }
    }
}


@Composable
fun ClickableIcon(
    icon: Any,
    color: Color,
    background: Color = Color.Transparent,
    onClick: () -> Unit
) {
    val modifier = Modifier
        .clip(CircleShape)
        .background(background)
        .clickable { onClick() }
        .padding(12.dp)
        .size(28.dp)
    if (icon is Int) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = color,
            modifier = modifier
        )
    } else if (icon is ImageVector) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = modifier
        )
    } else if (icon is String) {
        Text(
            text = icon,
            fontSize = 24.sp,
            color = color,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
    } else throw IllegalArgumentException("The taken icon isn't an ID, String or ImageVector")
}


@Composable
fun Keyboard(
    text: String,
    onClick: (String) -> Unit
) {
    val orange = Color(0xFFFE7038)
    val black = Color.Black
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        ClickableIcon(icon = "C", color = orange) {
            onClick("0")
        }
        ClickableIcon(icon = R.drawable.delete, color = orange) {
            onClick(backspace(text))
        }
        ClickableIcon(icon = R.drawable.percent, color = orange) {
            onClick(percent(text))
        }
        ClickableIcon(icon = R.drawable.divide, color = orange) {
            onClick(putOperator(text, '÷'))
        }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        ClickableIcon(icon = "7", color = black) {
            onClick(putDigit(text, 7))
        }
        ClickableIcon(icon = "8", color = black) {
            onClick(putDigit(text, 8))
        }
        ClickableIcon(icon = "9", color = black) {
            onClick(putDigit(text, 9))
        }
        ClickableIcon(icon = Icons.Filled.Clear, color = orange) {
            onClick(putOperator(text, '×'))
        }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        ClickableIcon(icon = "4", color = black) {
            onClick(putDigit(text, 4))
        }
        ClickableIcon(icon = "5", color = black) {
            onClick(putDigit(text, 5))
        }
        ClickableIcon(icon = "6", color = black) {
            onClick(putDigit(text, 6))
        }
        ClickableIcon(icon = R.drawable.remove, color = orange) {
            onClick(putOperator(text, '-'))
        }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        ClickableIcon(icon = "1", color = black) {
            onClick(putDigit(text, 1))
        }
        ClickableIcon(icon = "2", color = black) {
            onClick(putDigit(text, 2))
        }
        ClickableIcon(icon = "3", color = black) {
            onClick(putDigit(text, 3))
        }
        ClickableIcon(icon = Icons.Filled.Add, color = orange) {
            onClick(putOperator(text, '+'))
        }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        ClickableIcon(icon = "?", color = orange) { }
        ClickableIcon(icon = "0", color = black) {
            onClick(putDigit(text, 0))
        }
        ClickableIcon(icon = ".", color = black) {
            onClick(putDot(text))
        }
        ClickableIcon(icon = R.drawable.equal, color = Color.White, background = orange) {
            onClick(calculate(text))
        }
    }
}