package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.ProductItem
import com.example.model.ProductVariant
import com.example.model.SampleProductData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PdpUiState(
  val product: ProductItem = SampleProductData.defaultProduct,
  val selectedVariantId: String = "var-45g",
  val cartQuantity: Int = 0,
  val isWishlisted: Boolean = false,
  val isNotified: Boolean = false,
  val isHighlightsExpanded: Boolean = false,
  val isInformationExpanded: Boolean = false,
  val showWishlistSheet: Boolean = false,
  val activeToastMessage: String? = null,
  val isStockManuallyToggledOutOfStock: Boolean = false
) {
  val selectedVariant: ProductVariant
    get() {
      val found = product.variants.find { it.id == selectedVariantId } ?: product.variants.first()
      return if (isStockManuallyToggledOutOfStock) {
        found.copy(inStock = false)
      } else {
        found
      }
    }
}

class PdpViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(PdpUiState())
  val uiState: StateFlow<PdpUiState> = _uiState.asStateFlow()

  fun selectVariant(variant: ProductVariant) {
    _uiState.update { current ->
      // Reset notify state if switching to an out-of-stock variant so user can test notify,
      // or keep notify state per variant
      current.copy(
        selectedVariantId = variant.id,
        isNotified = false
      )
    }
  }

  fun toggleStockSimulation() {
    _uiState.update { current ->
      current.copy(
        isStockManuallyToggledOutOfStock = !current.isStockManuallyToggledOutOfStock,
        isNotified = false
      )
    }
  }

  fun addToCart() {
    _uiState.update { current ->
      current.copy(
        cartQuantity = 1,
        activeToastMessage = "Added ${current.selectedVariant.weight} to your cart! 🛍️"
      )
    }
  }

  fun incrementCart() {
    _uiState.update { current ->
      current.copy(cartQuantity = current.cartQuantity + 1)
    }
  }

  fun decrementCart() {
    _uiState.update { current ->
      val newQty = (current.cartQuantity - 1).coerceAtLeast(0)
      current.copy(
        cartQuantity = newQty,
        activeToastMessage = if (newQty == 0) "Removed from cart" else null
      )
    }
  }

  fun toggleWishlist() {
    _uiState.update { current ->
      val nextWishlistState = !current.isWishlisted
      val msg = if (nextWishlistState) {
        "Added to Wishlist! ❤️"
      } else {
        "Removed from Wishlist"
      }
      current.copy(
        isWishlisted = nextWishlistState,
        activeToastMessage = msg
      )
    }
  }

  /**
   * Handle user tapping the Notify button.
   * Requirement:
   * "notify ko fix karo or uspe ek bar tap kar ke baad disable ho jaye hai aur click karne ke baad button ko transparent kar dena"
   * - One tap disables it.
   * - Makes button transparent.
   * - Once tapped, cannot be tapped again.
   */
  fun onNotifyClick() {
    val current = _uiState.value
    if (current.isNotified) {
      // Already disabled, do nothing
      return
    }
    _uiState.update {
      it.copy(
        isNotified = true,
        activeToastMessage = "Restock alert set! We will notify you as soon as this pack arrives."
      )
    }
  }

  fun toggleHighlightsExpanded() {
    _uiState.update { it.copy(isHighlightsExpanded = !it.isHighlightsExpanded) }
  }

  fun toggleInformationExpanded() {
    _uiState.update { it.copy(isInformationExpanded = !it.isInformationExpanded) }
  }

  fun setWishlistSheetVisible(visible: Boolean) {
    _uiState.update { it.copy(showWishlistSheet = visible) }
  }

  fun dismissToast() {
    _uiState.update { it.copy(activeToastMessage = null) }
  }
}
