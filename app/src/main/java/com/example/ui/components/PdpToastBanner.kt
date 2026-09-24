package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PdpToastBanner(
  message: String?,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  LaunchedEffect(message) {
    if (message != null) {
      delay(3200)
      onDismiss()
    }
  }

  AnimatedVisibility(
    visible = message != null,
    enter = slideInVertically(initialOffsetY = { -it }, animationSpec = spring(stiffness = 500f)) + fadeIn(),
    exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = spring(stiffness = 500f)) + fadeOut(),
    modifier = modifier
  ) {
    if (message != null) {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .shadow(elevation = 6.dp, shape = RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFF1E232A),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF374151))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = message,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.weight(1f)
          )

          Spacer(modifier = Modifier.width(8.dp))

          Box(
            modifier = Modifier
              .size(24.dp)
              .clip(CircleShape)
              .clickable { onDismiss() },
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Filled.Close,
              contentDescription = "Dismiss",
              tint = Color(0xFF9CA3AF),
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }
    }
  }
}
