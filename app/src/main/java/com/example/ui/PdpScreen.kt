package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.PdpAccordionCards
import com.example.ui.components.PdpBottomBar
import com.example.ui.components.PdpDetailsCard
import com.example.ui.components.PdpGuaranteesCard
import com.example.ui.components.PdpImageGallery
import com.example.ui.components.PdpToastBanner
import com.example.ui.components.PdpTopBar
import com.example.ui.components.WishlistBottomSheet
import com.example.ui.theme.BackgroundLight
import com.example.viewmodel.PdpViewModel

@Composable
fun PdpScreen(
  viewModel: PdpViewModel = viewModel(),
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val listState = rememberLazyListState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundLight)
  ) {
    Scaffold(
      modifier = Modifier.fillMaxSize(),
      containerColor = BackgroundLight,
      topBar = {
        PdpTopBar(
          product = uiState.product,
          selectedVariant = uiState.selectedVariant,
          isWishlisted = uiState.isWishlisted,
          cartCount = uiState.cartQuantity,
          onBackClick = {
            viewModel.addToCart() // Quick sample action or navigation
          },
          onSearchClick = {
            viewModel.selectVariant(uiState.product.variants.first())
          },
          onWishlistClick = {
            viewModel.setWishlistSheetVisible(true)
          },
          onCartClick = {
            // Already at PDP with active cart
          },
          modifier = Modifier.padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
        )
      },
      bottomBar = {
        PdpBottomBar(
          selectedVariant = uiState.selectedVariant,
          cartQuantity = uiState.cartQuantity,
          isNotified = uiState.isNotified,
          onAddToCart = { viewModel.addToCart() },
          onIncrementCart = { viewModel.incrementCart() },
          onDecrementCart = { viewModel.decrementCart() },
          onNotifyClick = { viewModel.onNotifyClick() },
          onToggleStockSim = { viewModel.toggleStockSimulation() }
        )
      }
    ) { innerPadding ->
      LazyColumn(
        state = listState,
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // 1. Hero Image Gallery with dots & NOT FRIED badge & Wishlist button
        item(key = "image_gallery") {
          PdpImageGallery(
            product = uiState.product,
            isWishlisted = uiState.isWishlisted,
            onToggleWishlist = { viewModel.toggleWishlist() }
          )
        }

        // 2. Main Product Details Card (matches reference screenshot)
        item(key = "details_card") {
          Box(modifier = Modifier.padding(horizontal = 14.dp)) {
            PdpDetailsCard(
              product = uiState.product,
              selectedVariant = uiState.selectedVariant,
              isNotified = uiState.isNotified,
              onSelectVariant = { viewModel.selectVariant(it) },
              onNotifyClick = { viewModel.onNotifyClick() },
              onBrandClick = {
                viewModel.setWishlistSheetVisible(true)
              }
            )
          }
        }

        // 3. Guarantees Card (No return & Fast Delivery, matches screenshot)
        item(key = "guarantees_card") {
          Box(modifier = Modifier.padding(horizontal = 14.dp)) {
            PdpGuaranteesCard()
          }
        }

        // 4. Accordion Cards: Highlights & Information (matches screenshot)
        item(key = "accordion_cards") {
          Box(modifier = Modifier.padding(horizontal = 14.dp)) {
            PdpAccordionCards(
              product = uiState.product,
              isHighlightsExpanded = uiState.isHighlightsExpanded,
              isInformationExpanded = uiState.isInformationExpanded,
              onToggleHighlights = { viewModel.toggleHighlightsExpanded() },
              onToggleInformation = { viewModel.toggleInformationExpanded() }
            )
          }
        }

        // Extra spacing at bottom
        item(key = "bottom_spacer") {
          Spacer(modifier = Modifier.height(16.dp))
        }
      }
    }

    // Floating Animated Toast Banner at top
    PdpToastBanner(
      message = uiState.activeToastMessage,
      onDismiss = { viewModel.dismissToast() },
      modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 56.dp)
    )

    // Wishlist Bottom Sheet
    if (uiState.showWishlistSheet) {
      WishlistBottomSheet(
        isWishlisted = uiState.isWishlisted,
        product = uiState.product,
        selectedVariant = uiState.selectedVariant,
        onDismiss = { viewModel.setWishlistSheetVisible(false) },
        onRemoveFromWishlist = { viewModel.toggleWishlist() },
        onAddToCartFromWishlist = { viewModel.addToCart() }
      )
    }
  }
}
