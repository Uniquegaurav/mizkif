package com.example.mymiso.features.design_patterns.builder


data class Order(
    val restaurantId: String,
    val items: List<OrderItem>,
    val deliveryAddress: String?,
    val paymentMethod: PaymentMethod?,
    val specialInstructions: String?
)

enum class PaymentMethod {
    CASH, CARD, PAYPAL
}

data class OrderItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Float
)

class OrderBuilder {
    private val items = mutableListOf<OrderItem>()
    private var restaurantId: String = ""
    private var deliveryAddress: String? = null
    private var paymentMethod: PaymentMethod? = null
    private var specialInstructions: String? = null

    fun addItem(item: OrderItem) = apply { items.add(item) }
    fun setDeliveryAddress(address: String) = apply { deliveryAddress = address }
    fun setPaymentMethod(method: PaymentMethod) = apply { paymentMethod = method }

    fun setSpecialInstructions(instructions: String) = apply { specialInstructions = instructions }

    fun build() = Order(
        restaurantId,
        items.toList(),
        deliveryAddress,
        paymentMethod,
        specialInstructions
    )

}

fun main() {
    val order = OrderBuilder()
        .addItem(OrderItem("1", "Burger", 2, 5.0f))
        .addItem(OrderItem("2", "Fries", 1, 2.0f))
        .setDeliveryAddress("123 Main St")
        .setPaymentMethod(PaymentMethod.CARD)
        .setSpecialInstructions("No onions")
        .build()

    println(order)
}