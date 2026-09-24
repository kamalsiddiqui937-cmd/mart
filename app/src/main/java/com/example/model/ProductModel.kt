package com.example.model

data class ProductVariant(
  val id: String,
  val weight: String,
  val price: Int,
  val mrp: Int,
  val discount: Int,
  val inStock: Boolean,
  val stockCount: Int = 0
)

data class ProductItem(
  val id: String,
  val brand: String,
  val name: String,
  val subtitle: String,
  val netQuantityDesc: String,
  val rating: Float,
  val reviewCount: Int,
  val deliveryMinutes: Int,
  val variants: List<ProductVariant>,
  val highlights: Map<String, String>,
  val extendedHighlights: Map<String, String>,
  val information: Map<String, String>,
  val nutritionalInfo: List<NutritionalItem>
)

data class NutritionalItem(
  val label: String,
  val valuePer100g: String
)

object SampleProductData {
  val defaultProduct = ProductItem(
    id = "mom-roasted-almonds-45g",
    brand = "MOM",
    name = "MOM - Roasted & Salted California Almonds | Dry Roasted | No Preservative",
    subtitle = "Dry Roasted | No Preservative",
    netQuantityDesc = "Net quantity: 1 pack (45 g or 40 g)",
    rating = 4.5f,
    reviewCount = 1334,
    deliveryMinutes = 10,
    variants = listOf(
      ProductVariant(
        id = "var-45g",
        weight = "45 g",
        price = 69,
        mrp = 99,
        discount = 30,
        inStock = true,
        stockCount = 18
      ),
      ProductVariant(
        id = "var-100g",
        weight = "100 g",
        price = 149,
        mrp = 199,
        discount = 50,
        inStock = true,
        stockCount = 9
      ),
      ProductVariant(
        id = "var-200g",
        weight = "200 g",
        price = 289,
        mrp = 379,
        discount = 90,
        inStock = false,
        stockCount = 0
      )
    ),
    highlights = mapOf(
      "Brand" to "MOM",
      "Product Type" to "California Almond"
    ),
    extendedHighlights = mapOf(
      "Diet Type" to "Vegetarian",
      "Form Factor" to "Dry Roasted Whole Nuts",
      "Roasting Type" to "Oil-Free Drum Roasted",
      "Salt Level" to "Lightly Salted with Pink Rock Salt",
      "Preservatives" to "Zero Added Preservatives",
      "Storage" to "Store in a cool, dry and hygienic place"
    ),
    information = mapOf(
      "Key Features" to "100% California Premium Nonpareil Almonds, slow drum-roasted for crisp crunch. Zero cholesterol, heart-healthy fats, and plant protein.",
      "Ingredients" to "California Almonds (98%), Himalayan Rock Salt (1.5%), Cold-pressed Olive Mist (0.5%)",
      "Allergen Info" to "Contains Tree Nuts (Almonds). Processed in a dedicated nut facility.",
      "Country of Origin" to "India (California imported origin)",
      "Shelf Life" to "9 Months from packaging date",
      "FSSAI License" to "10019011006543",
      "Manufacturer" to "MOM Gourmet Foods Pvt. Ltd., Sector 62, Noida, UP - 201309"
    ),
    nutritionalInfo = listOf(
      NutritionalItem("Energy", "579 kcal"),
      NutritionalItem("Protein", "21.2 g"),
      NutritionalItem("Dietary Fiber", "12.5 g"),
      NutritionalItem("Total Fat", "49.9 g"),
      NutritionalItem("Monounsaturated Fat", "31.5 g"),
      NutritionalItem("Carbohydrates", "21.6 g"),
      NutritionalItem("Natural Sugars", "4.4 g"),
      NutritionalItem("Sodium", "340 mg")
    )
  )
}
