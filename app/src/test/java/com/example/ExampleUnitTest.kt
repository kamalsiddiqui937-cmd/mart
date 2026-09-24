package com.example

import com.example.model.SampleProductData
import com.example.viewmodel.PdpViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testWishlistToggleAndAnimationState() {
    val viewModel = PdpViewModel()
    assertFalse(viewModel.uiState.value.isWishlisted)

    // Add to wishlist
    viewModel.toggleWishlist()
    assertTrue(viewModel.uiState.value.isWishlisted)
    assertEquals("Added to Wishlist! ❤️", viewModel.uiState.value.activeToastMessage)

    // Remove from wishlist
    viewModel.toggleWishlist()
    assertFalse(viewModel.uiState.value.isWishlisted)
    assertEquals("Removed from Wishlist", viewModel.uiState.value.activeToastMessage)
  }

  @Test
  fun testNotifyMeSingleTapAndDisabledBehavior() {
    val viewModel = PdpViewModel()
    assertFalse(viewModel.uiState.value.isNotified)

    // First tap on notify
    viewModel.onNotifyClick()
    assertTrue(viewModel.uiState.value.isNotified)
    assertNotNull(viewModel.uiState.value.activeToastMessage)

    // Second tap should remain disabled/notified
    viewModel.onNotifyClick()
    assertTrue(viewModel.uiState.value.isNotified)
  }

  @Test
  fun testVariantSelectionAndCartFlow() {
    val viewModel = PdpViewModel()
    assertEquals("var-45g", viewModel.uiState.value.selectedVariantId)

    // Select 100g variant
    val variant100g = SampleProductData.defaultProduct.variants[1]
    viewModel.selectVariant(variant100g)
    assertEquals("var-100g", viewModel.uiState.value.selectedVariantId)
    assertEquals(149, viewModel.uiState.value.selectedVariant.price)

    // Cart flow
    assertEquals(0, viewModel.uiState.value.cartQuantity)
    viewModel.addToCart()
    assertEquals(1, viewModel.uiState.value.cartQuantity)

    viewModel.incrementCart()
    assertEquals(2, viewModel.uiState.value.cartQuantity)

    viewModel.decrementCart()
    assertEquals(1, viewModel.uiState.value.cartQuantity)
  }
}

