package com.example.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ProductItem
import com.example.ui.theme.PriceGreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PdpImageGallery(
  product: ProductItem,
  isWishlisted: Boolean,
  onToggleWishlist: () -> Unit,
  modifier: Modifier = Modifier
) {
  val pagerState = rememberPagerState(pageCount = { 3 })

  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            Color(0xFFEAF5FF),
            Color(0xFFF1F7FC),
            Color(0xFFFFFFFF)
          )
        )
      )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Pager Carousel
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(280.dp),
        contentAlignment = Alignment.Center
      ) {
        HorizontalPager(
          state = pagerState,
          modifier = Modifier.fillMaxSize()
        ) { page ->
          Box(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
          ) {
            when (page) {
              0 -> {
                Image(
                  painter = painterResource(id = R.drawable.mom_almonds_pack),
                  contentDescription = "MOM Almonds Pack View",
                  contentScale = ContentScale.Fit,
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                )
              }
              1 -> {
                Image(
                  painter = painterResource(id = R.drawable.mom_almonds_bowl),
                  contentDescription = "Roasted Almonds Bowl View",
                  contentScale = ContentScale.Fit,
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                )
              }
              else -> {
                Box(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF3F4F6))
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp)),
                  contentAlignment = Alignment.Center
                ) {
                  Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                  ) {
                    Image(
                      painter = painterResource(id = R.drawable.mom_almonds_bowl),
                      contentDescription = "Nutritional Closeup",
                      contentScale = ContentScale.Crop,
                      modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                      text = "100% California Grown",
                      fontSize = 15.sp,
                      fontWeight = FontWeight.Bold,
                      color = Color(0xFF1E232A)
                    )
                    Text(
                      text = "Drum Roasted with Rock Salt • Zero Oil",
                      fontSize = 12.sp,
                      color = PriceGreen,
                      fontWeight = FontWeight.Medium
                    )
                  }
                }
              }
            }
          }
        }

        // Top Left "NOT FRIED" badge (visible in screenshot)
        Box(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(start = 18.dp, top = 8.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF11998E))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Filled.CheckCircle,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(12.dp)
            )
            Text(
              text = "NOT FRIED",
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color.White,
              letterSpacing = 0.5.sp
            )
          }
        }

        // Top Right Animated Wishlist Button (matches user request)
        Box(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(end = 12.dp, top = 4.dp)
        ) {
          WishlistButtonWithAnimation(
            isWishlisted = isWishlisted,
            onToggleWishlist = onToggleWishlist,
            buttonSize = 44.dp,
            iconSize = 22.dp,
            elevated = true
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Dots Indicator (active dot is an expanded pill, matches screenshot)
      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        repeat(3) { index ->
          val isSelected = pagerState.currentPage == index
          val width by animateDpAsState(
            targetValue = if (isSelected) 18.dp else 6.dp,
            label = "dotWidth"
          )
          Box(
            modifier = Modifier
              .padding(horizontal = 3.dp)
              .height(6.dp)
              .width(width)
              .clip(CircleShape)
              .background(
                if (isSelected) Color(0xFF2C3E50) else Color(0xFFD1D5DB)
              )
          )
        }
      }
    }
  }
}
