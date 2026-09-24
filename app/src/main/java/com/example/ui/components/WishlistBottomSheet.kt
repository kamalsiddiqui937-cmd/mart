package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ProductItem
import com.example.model.ProductVariant
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.BrandPink
import com.example.ui.theme.PriceGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WishlistRed

/**
 * Bottom Sheet displaying saved wishlist items
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistBottomSheet(
  isWishlisted: Boolean,
  product: ProductItem,
  selectedVariant: ProductVariant,
  onDismiss: () -> Unit,
  onRemoveFromWishlist: () -> Unit,
  onAddToCartFromWishlist: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    dragHandle = {
      Box(
        modifier = Modifier
          .padding(vertical = 10.dp)
          .size(width = 42.dp, height = 4.dp)
          .clip(CircleShape)
          .background(Color(0xFFD1D5DB))
      )
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = WishlistRed,
            modifier = Modifier.size(22.dp)
          )
          Text(
            text = "Your Wishlist",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }

        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color(0xFFF3F4F6))
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple(bounded = true),
              onClick = onDismiss
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
            tint = TextSecondary,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      if (!isWishlisted) {
        // Empty State
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(64.dp)
              .clip(CircleShape)
              .background(Color(0xFFFEE2E2)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Filled.Favorite,
              contentDescription = null,
              tint = Color(0xFFF87171),
              modifier = Modifier.size(32.dp)
            )
          }
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Your wishlist is empty",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Tap the heart icon on any product to save it for later.",
            fontSize = 13.sp,
            color = TextSecondary
          )
        }
      } else {
        // Saved Item Card
        Surface(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          color = Color(0xFFF9FAFB),
          border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Thumbnail
            Box(
              modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(8.dp)),
              contentAlignment = Alignment.Center
            ) {
              Image(
                painter = painterResource(id = R.drawable.mom_almonds_pack),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(54.dp)
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = product.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              Text(
                text = selectedVariant.weight,
                fontSize = 12.sp,
                color = TextSecondary
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "₹${selectedVariant.price}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = PriceGreen
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Actions
            Column(
              horizontalAlignment = Alignment.End,
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true),
                    onClick = onRemoveFromWishlist
                  ),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Filled.Delete,
                  contentDescription = "Remove",
                  tint = Color(0xFFEF4444),
                  modifier = Modifier.size(18.dp)
                )
              }

              Button(
                onClick = {
                  onAddToCartFromWishlist()
                  onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = BrandPink),
                shape = RoundedCornerShape(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                  horizontal = 10.dp,
                  vertical = 4.dp
                ),
                modifier = Modifier.height(30.dp)
              ) {
                Text("Add", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))
      }
    }
  }
}
