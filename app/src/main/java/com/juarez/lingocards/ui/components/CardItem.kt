package com.juarez.lingocards.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.juarez.lingocards.ui.theme.Purple40
import com.juarez.lingocards.ui.theme.PurpleGrey80

@Composable
fun CardItem(
    // The card has a front and back text along with an image
    frontText: String,
    backText: String,
    imageResId: Int?,
    frontColor: Color = Purple40,
    backColor: Color = PurpleGrey80,
    modifier: Modifier = Modifier
) {
    var isFront by remember { mutableStateOf(true) }

    // Rotation animation (controls visual flip)
    val rotation by animateFloatAsState(
        targetValue = if (isFront) 0f else 180f,
        animationSpec = tween(durationMillis = 400)
    )

    // Animate color based on the logical side (isFront) so color animation
    // starts together with the rotation flip and stays consistent with the side.
    val cardColor by animateColorAsState(
        targetValue = if (isFront) frontColor else backColor,
        animationSpec = tween(durationMillis = 400)
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = modifier
            .fillMaxWidth()
            .clickable { isFront = !isFront }
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                },
            contentAlignment = Alignment.Center
        ) {
            // Mostrar la cara correspondiente según la rotación para evitar
            // que el texto cambie antes de que la cara sea visible.
            if (rotation <= 90f) {
                FrontFace(
                    text = frontText,
                    imageResId = imageResId
                )
            } else {
                Box(
                    modifier = Modifier.graphicsLayer {
                        rotationY = 180f
                    }
                ) {
                    BackFace(
                        text = backText,
                        imageResId = imageResId
                    )
                }
            }
        }
    }
}

@Composable
private fun FrontFace(text: String, imageResId: Int?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (imageResId != null && imageResId != 0) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier.size(90.dp)
            )
        }
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun BackFace(text: String, imageResId: Int?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (imageResId != null && imageResId != 0) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier.size(90.dp)
            )
        }
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}
