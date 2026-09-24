package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ProductVariant
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.BrandPink
import com.example.ui.theme.BrandPinkDark
import com.example.ui.theme.PriceGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

/**
 * Sticky Bottom Bar matching reference screenshot:
 * - When in stock: Full-width vibrant pink "Add to Cart" button (or quantity counter [-] 1 [+])
 * - When out of stock: "Notify Me" button.
 *   REQUIREMENT:
 *   - After 1 tap, disables completely.
 *   - After click, becomes transparent.
 */
@Composable
fun PdpBottomBar(
  selectedVariant: ProductVariant,
  cartQuantity: Int,
  isNotified: Boolean,
  onAddToCart: () -> Unit,
  onIncrementCart: () -> Unit,
  onDecrementCart: () -> Unit,
  onNotifyClick: () -> Unit,
  onToggleStockSim: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth(),
    color = Color.White,
    shadowElevation = 8.dp,
    border = BorderStroke(1.dp, BorderSubtle)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
      // Stock simulation toggle pill for easy testing
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(if (selectedVariant.inStock) PriceGreen else Color(0xFFF59E0B))
          )
          Text(
            text = if (selectedVariant.inStock) "In Stock (Ready in 10 mins)" else "Out of Stock (Testing Notify)",
            fontSize = 11.sp,
            color = if (selectedVariant.inStock) PriceGreen else Color(0xFFD97706),
            fontWeight = FontWeight.SemiBold
          )
        }

        Text(
          text = if (selectedVariant.inStock) "Simulate Out of Stock" else "Simulate In Stock",
          fontSize = 11.sp,
          color = BrandPink,
          fontWeight = FontWeight.Bold,
          modifier = Modifier
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null,
              onClick = onToggleStockSim
            )
            .testTag("toggle_stock_simulation_button")
        )
      }

      AnimatedContent(
        targetState = Pair(selectedVariant.inStock, cartQuantity),
        transitionSpec = {
          (fadeIn(tween(220)) + scaleIn(initialScale = 0.95f)) togetherWith
            (fadeOut(tween(180)) + scaleOut(targetScale = 0.95f))
        },
        label = "bottomBarCtaTransition"
      ) { (inStock, qty) ->
        if (!inStock) {
          // OUT OF STOCK: Notify Button
          // Requirement:
          // "notify ko fix karo or uspe ek bar tap kar ke baad disable ho jaye hai aur click karne ke baad button ko transparent kar dena"
          val notifyBgColor by animateColorAsState(
            targetValue = if (isNotified) Color.Transparent else BrandPink,
            animationSpec = tween(300, easing = FastOutSlowInEasing),
            label = "notifyBgColor"
          )
          val notifyBorderColor by animateColorAsState(
            targetValue = if (isNotified) Color(0x5510B981) else Color.Transparent,
            animationSpec = tween(300),
            label = "notifyBorderColor"
          )
          val notifyTextColor by animateColorAsState(
            targetValue = if (isNotified) Color(0xFF059669) else Color.White,
            animationSpec = tween(300),
            label = "notifyTextColor"
          )

          Button(
            onClick = onNotifyClick,
            enabled = !isNotified, // Disabled after 1 tap!
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("notify_me_button"),
            colors = ButtonDefaults.buttonColors(
              containerColor = notifyBgColor,
              contentColor = notifyTextColor,
              disabledContainerColor = Color.Transparent, // Transparent when disabled!
              disabledContentColor = Color(0xFF059669)
            ),
            shape = RoundedCornerShape(12.dp),
            border = if (isNotified) BorderStroke(1.dp, notifyBorderColor) else null,
            elevation = ButtonDefaults.buttonElevation(
              defaultElevation = if (isNotified) 0.dp else 2.dp,
              pressedElevation = 0.dp,
              disabledElevation = 0.dp
            )
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = if (isNotified) Icons.Filled.Check else Icons.Outlined.Notifications,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = notifyTextColor
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = if (isNotified) "✓ Restock Notification Set" else "Notify Me When Available",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = notifyTextColor
              )
            }
          }
        } else {
          // IN STOCK: Add to Cart or Quantity Selector
          if (qty == 0) {
            // Full Width Vibrant Pink "Add to Cart" Button (exact match from screenshot)
            Button(
              onClick = onAddToCart,
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("add_to_cart_button"),
              colors = ButtonDefaults.buttonColors(
                containerColor = BrandPink,
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(12.dp),
              elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
              Text(
                text = "Add to Cart",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.2.sp
              )
            }
          } else {
            // Active Cart Quantity Selector [-] Qty [+] with subtotal
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text(
                  text = "$qty item${if (qty > 1) "s" else ""} in cart",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Medium,
                  color = TextSecondary
                )
                Text(
                  text = "₹${selectedVariant.price * qty}",
                  fontSize = 17.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = TextPrimary
                )
              }

              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                  .clip(RoundedCornerShape(10.dp))
                  .background(BrandPink)
                  .padding(horizontal = 4.dp, vertical = 2.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                      interactionSource = remember { MutableInteractionSource() },
                      indication = ripple(bounded = true, color = Color.White),
                      onClick = onDecrementCart
                    )
                    .testTag("decrement_cart_button"),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Decrease quantity",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                  )
                }

                Text(
                  text = "$qty",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  modifier = Modifier.padding(horizontal = 14.dp)
                )

                Box(
                  modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                      interactionSource = remember { MutableInteractionSource() },
                      indication = ripple(bounded = true, color = Color.White),
                      onClick = onIncrementCart
                    )
                    .testTag("increment_cart_button"),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Increase quantity",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
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
