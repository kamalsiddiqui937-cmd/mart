package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ProductItem
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.BrandPink
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

/**
 * Highlights & Information expandable accordion cards matching reference screenshot:
 * - Highlights card with "Brand", "Product Type", and "View more v" pill button
 * - Information card with smooth expand/collapse chevron
 */
@Composable
fun PdpAccordionCards(
  product: ProductItem,
  isHighlightsExpanded: Boolean,
  isInformationExpanded: Boolean,
  onToggleHighlights: () -> Unit,
  onToggleInformation: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // 1. HIGHLIGHTS CARD (matches screenshot)
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .animateContentSize(animationSpec = spring(stiffness = 500f)),
      shape = RoundedCornerShape(18.dp),
      color = SurfaceCard,
      border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
      shadowElevation = 1.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        // Highlights Header with chevron up/down
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple(bounded = true),
              onClick = onToggleHighlights
            ),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Highlights",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )

          Icon(
            imageVector = if (isHighlightsExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = "Toggle highlights",
            tint = TextPrimary,
            modifier = Modifier.size(24.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Basic Highlights Rows (matches screenshot)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          HighlightRow(label = "Brand", value = product.highlights["Brand"] ?: "MOM")
          HighlightRow(label = "Product Type", value = product.highlights["Product Type"] ?: "California Almond")

          // Extended Highlights when expanded
          AnimatedVisibility(
            visible = isHighlightsExpanded,
            enter = expandVertically(tween(260)) + fadeIn(tween(200)),
            exit = shrinkVertically(tween(200)) + fadeOut(tween(150))
          ) {
            Column(
              verticalArrangement = Arrangement.spacedBy(12.dp),
              modifier = Modifier.padding(top = 12.dp)
            ) {
              product.extendedHighlights.forEach { (key, value) ->
                HighlightRow(label = key, value = value)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // "View more v" / "View less ^" Pill Button (matches screenshot)
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(Color(0xFFFFF0F5))
              .border(1.dp, Color(0xFFFFD6E5), RoundedCornerShape(20.dp))
              .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = onToggleHighlights
              )
              .padding(horizontal = 14.dp, vertical = 6.dp)
              .testTag("highlights_view_more_pill")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text(
                text = if (isHighlightsExpanded) "View less" else "View more",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BrandPink
              )
              Icon(
                imageVector = if (isHighlightsExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = BrandPink,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }

    // 2. INFORMATION CARD (matches screenshot)
    val arrowRotation by animateFloatAsState(
      targetValue = if (isInformationExpanded) 180f else 0f,
      animationSpec = spring(stiffness = 400f),
      label = "infoArrowRotation"
    )

    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .animateContentSize(animationSpec = spring(stiffness = 500f)),
      shape = RoundedCornerShape(18.dp),
      color = SurfaceCard,
      border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
      shadowElevation = 1.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        // Information Header with animated rotating chevron
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple(bounded = true),
              onClick = onToggleInformation
            ),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Information",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )

          Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Toggle Information",
            tint = TextPrimary,
            modifier = Modifier
              .size(24.dp)
              .rotate(arrowRotation)
          )
        }

        // Expanded Information Content
        AnimatedVisibility(
          visible = isInformationExpanded,
          enter = expandVertically(tween(280)) + fadeIn(tween(220)),
          exit = shrinkVertically(tween(220)) + fadeOut(tween(180))
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            HorizontalDivider(color = BorderSubtle)

            // Information Details
            product.information.forEach { (title, desc) ->
              Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                  text = title,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = TextSecondary
                )
                Text(
                  text = desc,
                  fontSize = 13.sp,
                  color = TextPrimary,
                  lineHeight = 18.sp
                )
              }
            }

            HorizontalDivider(color = BorderSubtle)

            // Nutritional Information Table
            Text(
              text = "Nutritional Values (Approx. per 100g)",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )

            Column(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF9FAFB))
                .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(10.dp))
                .padding(12.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              product.nutritionalInfo.forEach { nut ->
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(
                    text = nut.label,
                    fontSize = 12.sp,
                    color = TextSecondary
                  )
                  Text(
                    text = nut.valuePer100g,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun HighlightRow(label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      fontSize = 14.sp,
      color = TextSecondary,
      modifier = Modifier.width(130.dp)
    )
    Text(
      text = value,
      fontSize = 14.sp,
      fontWeight = FontWeight.Medium,
      color = TextPrimary,
      modifier = Modifier.weight(1f)
    )
  }
}
