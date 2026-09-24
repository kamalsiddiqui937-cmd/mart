package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ProductItem
import com.example.model.ProductVariant
import com.example.ui.theme.BrandPink
import com.example.ui.theme.PriceGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WishlistRed

/**
 * Top bar matching the reference screenshot:
 * - Circular back button
 * - Centered product thumbnail, title, and current variant price
 * - Circular search button and wishlist/cart indicators
 */
@Composable
fun PdpTopBar(
  product: ProductItem,
  selectedVariant: ProductVariant,
  isWishlisted: Boolean,
  cartCount: Int,
  onBackClick: () -> Unit,
  onSearchClick: () -> Unit,
  onWishlistClick: () -> Unit,
  onCartClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth(),
    color = Color.White.copy(alpha = 0.96f),
    shadowElevation = 2.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // 1. Back button in circular pill/card (matches screenshot)
      Box(
        modifier = Modifier
          .size(42.dp)
          .shadow(elevation = 3.dp, shape = CircleShape, clip = false)
          .clip(CircleShape)
          .background(Color.White)
          .border(1.dp, Color(0xFFECEEF1), CircleShape)
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(bounded = true),
            onClick = onBackClick
          )
          .testTag("top_bar_back_button"),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Go back",
          tint = Color(0xFF1E232A),
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      // 2. Middle Section: Thumbnail + Title + Price preview (matches screenshot)
      Row(
        modifier = Modifier
          .weight(1f)
          .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Thumbnail
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(8.dp))
            .background(Color(0xFFF9FAFB)),
          contentAlignment = Alignment.Center
        ) {
          Image(
            painter = painterResource(id = R.drawable.mom_almonds_pack),
            contentDescription = product.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(36.dp)
          )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Product text & price preview
        Column(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = product.name,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "₹${selectedVariant.price}",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "₹${selectedVariant.mrp}",
              fontSize = 12.sp,
              color = TextSecondary,
              textDecoration = TextDecoration.LineThrough
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(6.dp))

      // 3. Right Action Buttons: Wishlist & Search (matches screenshot)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Wishlist quick indicator
        Box(
          modifier = Modifier
            .size(42.dp)
            .shadow(elevation = 2.dp, shape = CircleShape, clip = false)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, Color(0xFFECEEF1), CircleShape)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple(bounded = true, color = WishlistRed.copy(alpha = 0.2f)),
              onClick = onWishlistClick
            )
            .testTag("top_bar_wishlist_button"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "View Wishlist",
            tint = if (isWishlisted) WishlistRed else Color(0xFF4B5563),
            modifier = Modifier.size(20.dp)
          )
        }

        // Search circular button (matches screenshot)
        Box(
          modifier = Modifier
            .size(42.dp)
            .shadow(elevation = 2.dp, shape = CircleShape, clip = false)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, Color(0xFFECEEF1), CircleShape)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple(bounded = true),
              onClick = onSearchClick
            )
            .testTag("top_bar_search_button"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search catalog",
            tint = Color(0xFF1E232A),
            modifier = Modifier.size(20.dp)
          )
        }

        // Cart counter if items added
        AnimatedVisibility(
          visible = cartCount > 0,
          enter = scaleIn() + fadeIn(),
          exit = scaleOut() + fadeOut()
        ) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .shadow(elevation = 2.dp, shape = CircleShape, clip = false)
              .clip(CircleShape)
              .background(BrandPink)
              .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = Color.White),
                onClick = onCartClick
              )
              .testTag("top_bar_cart_button"),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Filled.ShoppingCart,
              contentDescription = "Cart",
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}
