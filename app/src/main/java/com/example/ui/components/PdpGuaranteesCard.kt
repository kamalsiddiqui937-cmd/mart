package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PublishedWithChanges
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.ElectricBolt
import androidx.compose.material.icons.outlined.SyncProblem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextPrimary

/**
 * Guarantees row matching the reference screenshot:
 * - Card 1: Circular icon with no-return symbol + "No return or replacement"
 * - Card 2: Circular icon with fast delivery symbol + "Fast Delivery"
 */
@Composable
fun PdpGuaranteesCard(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Left Box: No return or replacement
    Surface(
      modifier = Modifier
        .weight(1f)
        .height(118.dp),
      shape = RoundedCornerShape(16.dp),
      color = SurfaceCard,
      border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
      shadowElevation = 1.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        // Custom Circular Icon with No-Return graphic
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(0xFFF3F4F6))
            .border(1.dp, Color(0xFFE5E7EB), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Canvas(modifier = Modifier.size(24.dp)) {
            val strokeWidth = 2.dp.toPx()
            // Rounded square / box
            drawRoundRect(
              color = Color(0xFF374151),
              topLeft = Offset(size.width * 0.2f, size.height * 0.2f),
              size = androidx.compose.ui.geometry.Size(size.width * 0.6f, size.height * 0.6f),
              cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx()),
              style = Stroke(width = strokeWidth)
            )
            // Slash line (prohibited)
            drawLine(
              color = Color(0xFF374151),
              start = Offset(size.width * 0.15f, size.height * 0.15f),
              end = Offset(size.width * 0.85f, size.height * 0.85f),
              strokeWidth = strokeWidth,
              cap = StrokeCap.Round
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "No return or replacement",
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
          color = TextPrimary,
          textAlign = TextAlign.Center,
          lineHeight = 16.sp
        )
      }
    }

    // Right Box: Fast Delivery
    Surface(
      modifier = Modifier
        .weight(1f)
        .height(118.dp),
      shape = RoundedCornerShape(16.dp),
      color = SurfaceCard,
      border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
      shadowElevation = 1.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        // Custom Circular Icon with Lightning / Speed delivery
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(0xFFF3F4F6))
            .border(1.dp, Color(0xFFE5E7EB), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Filled.Bolt,
            contentDescription = "Fast Delivery",
            tint = Color(0xFF1E232A),
            modifier = Modifier.size(26.dp)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Fast Delivery",
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
          color = TextPrimary,
          textAlign = TextAlign.Center,
          lineHeight = 16.sp
        )
      }
    }
  }
}
