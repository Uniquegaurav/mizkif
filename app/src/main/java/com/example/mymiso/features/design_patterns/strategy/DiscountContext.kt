package com.example.mymiso.features.design_patterns.strategy

//
//Strategy Pattern focuses on behavior—defining different ways of executing an algorithm or task (e.g., calculating discount)
//and allowing the client to choose between them at runtime. It hides the specific implementation of each behavior and makes them interchangeable.


interface DiscountStrategy {
    fun applyDiscount(totalAmount: Double): Double
}

class FlatDiscount(private val discountAmount: Double) : DiscountStrategy {
    override fun applyDiscount(totalAmount: Double): Double {
        return totalAmount - discountAmount
    }
}

class PercentageDiscount(private val discountPercentage : Double) : DiscountStrategy {
    override fun applyDiscount(totalAmount: Double): Double {
        return totalAmount - (totalAmount * discountPercentage / 100)
    }
}

class FirstTimeUserDiscount() : DiscountStrategy {
    private val discountPercentage = 10.0
    override fun applyDiscount(totalAmount: Double): Double {
        return totalAmount - (totalAmount * discountPercentage / 100)
    }
}

class DiscountContext(private var discountStrategy: DiscountStrategy) {

    fun setDiscountStrategy(newStrategy: DiscountStrategy) {
        discountStrategy = newStrategy
    }

    fun applyDiscount(totalAmount: Double): Double {
        return discountStrategy.applyDiscount(totalAmount)
    }

}
fun main() {
    val discountContext = DiscountContext(FlatDiscount(10.0))
    println(discountContext.applyDiscount(100.0))

    discountContext.setDiscountStrategy(PercentageDiscount(10.0))
    println(discountContext.applyDiscount(100.0))

    discountContext.setDiscountStrategy(FirstTimeUserDiscount())
    println(discountContext.applyDiscount(100.0))
}