package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ProductItem
import com.example.model.ProductVariant
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.BrandPink
import com.example.ui.theme.DiscountGreen
import com.example.ui.theme.OutOfStockAmber
import com.example.ui.theme.PriceGreen
import com.example.ui.theme.PriceGreenLight
import com.example.ui.theme.RatingGreen
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

/**
 * Main Product Details Card from reference screenshot:
 * - Star rating badge: ★ 4.5 (1334) | 10 mins
 * - Product Title
 * - Net quantity
 * - Pack size chips
 * - Large green rounded badge: ₹69
 * - MRP ₹99 (incl. of all taxes) ₹30 OFF
 * - "View all MOM products >" card
 * - In-card Restock Notification test bar if out of stock
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PdpDetailsCard(
  product: ProductItem,
  selectedVariant: ProductVariant,
  isNotified: Boolean,
  onSelectVariant: (ProductVariant) -> Unit,
  onNotifyClick: () -> Unit,
  onBrandClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
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
      // 1. Rating Pill + Delivery Time (matches screenshot)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFFF0FDF4))
            .border(1.dp, Color(0xFFDCFCE7), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = RatingGreen,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "${product.rating}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = RatingGreen
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "(${product.reviewCount})",
            fontSize = 11.sp,
            color = TextSecondary
          )
        }

        Text(
          text = "|",
          fontSize = 12.sp,
          color = TextMuted
        )

        Text(
          text = "${product.deliveryMinutes} mins",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = TextSecondary
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 2. Main Title (matches screenshot)
      Text(
        text = product.name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        lineHeight = 24.sp
      )

      Spacer(modifier = Modifier.height(6.dp))

      // 3. Net quantity (matches screenshot)
      Text(
        text = "Net quantity: 1 pack (${selectedVariant.weight} or 40 g)",
        fontSize = 13.sp,
        color = TextSecondary,
        fontWeight = FontWeight.Normal
      )

      Spacer(modifier = Modifier.height(14.dp))

      // 4. Pack Size Selector Chips
      Text(
        text = "Select Pack Size:",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextSecondary
      )
      Spacer(modifier = Modifier.height(8.dp))

      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        product.variants.forEach { variant ->
          val isSelected = variant.id == selectedVariant.id
          val chipBorderColor by animateColorAsState(
            targetValue = if (isSelected) PriceGreen else Color(0xFFE5E7EB),
            animationSpec = tween(200),
            label = "chipBorder"
          )
          val chipBgColor by animateColorAsState(
            targetValue = if (isSelected) Color(0xFFF0FDF4) else Color(0xFFFAFAFA),
            animationSpec = tween(200),
            label = "chipBg"
          )

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(chipBgColor)
              .border(if (isSelected) 1.5.dp else 1.dp, chipBorderColor, RoundedCornerShape(10.dp))
              .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = { onSelectVariant(variant) }
              )
              .padding(horizontal = 12.dp, vertical = 8.dp)
              .testTag("variant_chip_${variant.weight.replace(" ", "_")}")
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = variant.weight,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) PriceGreen else TextPrimary
              )
              Text(
                text = "₹${variant.price}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) PriceGreen else TextSecondary
              )
              if (!variant.inStock) {
                Text(
                  text = "Out of Stock",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = OutOfStockAmber
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 5. Price Section (exact look from screenshot)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        // Green rounded badge with bold price (matches screenshot)
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(PriceGreen)
            .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
          Text(
            text = "₹${selectedVariant.price}",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
          )
        }

        // MRP strikethrough & discount tag (matches screenshot)
        Column(verticalArrangement = Arrangement.Center) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "MRP ₹${selectedVariant.mrp}",
              fontSize = 14.sp,
              color = TextSecondary,
              textDecoration = TextDecoration.LineThrough
            )
            Text(
              text = "₹${selectedVariant.discount} OFF",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = DiscountGreen
            )
          }
          Text(
            text = "(incl. of all taxes)",
            fontSize = 11.sp,
            color = TextMuted
          )
        }
      }

      // 6. Out of Stock / Notify Section inside card if variant is out of stock
      if (!selectedVariant.inStock) {
        Spacer(modifier = Modifier.height(14.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFFBEB))
            .border(1.dp, Color(0xFFFDE68A), RoundedCornerShape(12.dp))
            .padding(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Currently Out of Stock",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = OutOfStockAmber
              )
              Text(
                text = if (isNotified) "✓ You'll be alerted as soon as it arrives" else "Get notified immediately when restocked",
                fontSize = 11.sp,
                color = TextSecondary
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Inline Notify Button following user requirements:
            // "notify ko fix karo or uspe ek bar tap kar ke baad disable ho jaye hai aur click karne ke baad button ko transparent kar dena"
            OutlinedButton(
              onClick = onNotifyClick,
              enabled = !isNotified,
              colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = if (isNotified) Color(0xFF059669) else BrandPink,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color(0xFF059669)
              ),
              border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = if (isNotified) Color(0x44059669) else BrandPink
              ),
              shape = RoundedCornerShape(20.dp),
              modifier = Modifier.testTag("inline_notify_button")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(
                  imageVector = if (isNotified) Icons.Filled.Check else Icons.Outlined.Notifications,
                  contentDescription = null,
                  modifier = Modifier.size(14.dp)
                )
                Text(
                  text = if (isNotified) "Notified" else "Notify Me",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 7. "View all MOM products >" Inner Card (exact match from screenshot)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(bounded = true),
            onClick = onBrandClick
          )
          .padding(horizontal = 14.dp, vertical = 12.dp)
          .testTag("brand_store_banner")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // MOM brand pill logo
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(6.dp))
                .background(Color(0xFFFAFAFA))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "M♥M",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = BrandPink
              )
            }

            Text(
              text = "View all MOM products",
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary
            )
          }

          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Navigate to MOM products",
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }
  }
}
