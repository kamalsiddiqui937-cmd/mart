package com.example.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.ui.theme.WishlistRed
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * Animated Wishlist Button with:
 * - Spring scale bounce animation on tap
 * - Expanding radial burst particle hearts when added
 * - Glowing radial ring transition
 * - Smooth color crossfade from subtle grey to vibrant ruby red
 */
@Composable
fun WishlistButtonWithAnimation(
  isWishlisted: Boolean,
  onToggleWishlist: () -> Unit,
  modifier: Modifier = Modifier,
  buttonSize: Dp = 44.dp,
  iconSize: Dp = 22.dp,
  elevated: Boolean = true,
  backgroundColor: Color = Color.White
) {
  val scope = rememberCoroutineScope()
  val scaleAnim = remember { Animatable(1f) }
  val particleProgress = remember { Animatable(0f) }
  val ringRadiusAnim = remember { Animatable(0f) }
  val ringAlphaAnim = remember { Animatable(0f) }

  // Heart color transition
  val heartColor by animateColorAsState(
    targetValue = if (isWishlisted) WishlistRed else Color(0xFF6B7280),
    animationSpec = tween(durationMillis = 260, easing = FastOutSlowInEasing),
    label = "heartColorAnim"
  )

  fun triggerAddAnimation() {
    scope.launch {
      // Bouncy spring scale
      scaleAnim.snapTo(0.75f)
      scaleAnim.animateTo(
        targetValue = 1.35f,
        animationSpec = spring(
          dampingRatio = Spring.DampingRatioMediumBouncy,
          stiffness = Spring.StiffnessLow
        )
      )
      scaleAnim.animateTo(
        targetValue = 1.0f,
        animationSpec = spring(
          dampingRatio = Spring.DampingRatioNoBouncy,
          stiffness = Spring.StiffnessMedium
        )
      )
    }

    scope.launch {
      // Glow ring expansion
      ringAlphaAnim.snapTo(0.6f)
      ringRadiusAnim.snapTo(0f)
      launch {
        ringRadiusAnim.animateTo(1.4f, tween(380, easing = FastOutSlowInEasing))
      }
      launch {
        ringAlphaAnim.animateTo(0f, tween(380, easing = LinearEasing))
      }
    }

    scope.launch {
      // Radial particle burst
      particleProgress.snapTo(0f)
      particleProgress.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 480, easing = FastOutSlowInEasing)
      )
    }
  }

  fun triggerRemoveAnimation() {
    scope.launch {
      scaleAnim.snapTo(0.85f)
      scaleAnim.animateTo(
        targetValue = 1.0f,
        animationSpec = spring(
          dampingRatio = Spring.DampingRatioLowBouncy,
          stiffness = Spring.StiffnessMedium
        )
      )
    }
  }

  Box(
    modifier = modifier.size(buttonSize + 28.dp),
    contentAlignment = Alignment.Center
  ) {
    // 1. Particle hearts burst on adding to wishlist
    if (particleProgress.value in 0.01f..0.99f) {
      val progress = particleProgress.value
      val particleCount = 6
      val maxDistance = 34.dp.value
      val distance = progress * maxDistance
      val alpha = (1f - progress).coerceIn(0f, 1f)

      for (i in 0 until particleCount) {
        val angleDeg = (i * (360.0 / particleCount)) + 15.0
        val angleRad = Math.toRadians(angleDeg)
        val xOffset = (cos(angleRad) * distance).roundToInt()
        val yOffset = (sin(angleRad) * distance).roundToInt()
        val particleScale = (1f - (progress * 0.4f)).coerceIn(0f, 1f)

        Box(
          modifier = Modifier
            .offset { IntOffset(xOffset, yOffset) }
            .graphicsLayer {
              this.alpha = alpha
              this.scaleX = particleScale
              this.scaleY = particleScale
            }
        ) {
          Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = if (i % 2 == 0) WishlistRed else Color(0xFFFF85A1),
            modifier = Modifier.size(10.dp)
          )
        }
      }
    }

    // 2. Pulse ring canvas
    if (ringAlphaAnim.value > 0.01f) {
      Canvas(modifier = Modifier.size(buttonSize * 1.8f)) {
        val radius = (size.minDimension / 2f) * ringRadiusAnim.value
        drawCircle(
          color = WishlistRed.copy(alpha = ringAlphaAnim.value),
          radius = radius
        )
      }
    }

    // 3. The circular button container
    Box(
      modifier = Modifier
        .size(buttonSize)
        .scale(scaleAnim.value)
        .then(
          if (elevated) {
            Modifier.shadow(
              elevation = 4.dp,
              shape = CircleShape,
              clip = false
            )
          } else {
            Modifier
          }
        )
        .clip(CircleShape)
        .background(backgroundColor)
        .border(1.dp, Color(0xFFECEEF1), CircleShape)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = ripple(bounded = true, color = WishlistRed.copy(alpha = 0.2f)),
          onClick = {
            val willBeWishlisted = !isWishlisted
            if (willBeWishlisted) {
              triggerAddAnimation()
            } else {
              triggerRemoveAnimation()
            }
            onToggleWishlist()
          }
        )
        .testTag("wishlist_toggle_button"),
      contentAlignment = Alignment.Center
    ) {
      Crossfade(
        targetState = isWishlisted,
        animationSpec = tween(durationMillis = 200),
        label = "heartCrossfade"
      ) { wishlisted ->
        if (wishlisted) {
          Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Remove from Wishlist",
            tint = heartColor,
            modifier = Modifier.size(iconSize)
          )
        } else {
          Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "Add to Wishlist",
            tint = heartColor,
            modifier = Modifier.size(iconSize)
          )
        }
      }
    }
  }
}
