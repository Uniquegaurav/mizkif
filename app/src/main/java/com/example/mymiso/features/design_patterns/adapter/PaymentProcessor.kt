package com.example.mymiso.features.design_patterns.adapter


//Each payment method will have its own existing API or method for processing payments,
// which we need to adapt to the UnifiedPayment interface.

//These payment methods might have different interfaces or implementations.
// Using the Adapter Pattern, you can integrate all of them into a unified payment interface so that the app can interact with them in a consistent way,
// without worrying about their underlying differences.


class CardPayment {
    fun payWithCard(amount: Double) {
        print("Payment successful with card")
    }
}


class WalletPayment {
    fun payWithWallet(amount: Double) {
        print("Payment successful with wallet")
    }
}

class UPIPayment {
    fun payWithUPI(amount: Double) {
        print("Payment successful with UPI")
    }
}

//The UnifiedPayment interface will have a single method, pay(),
// that will be implemented by each adapter to process payments using the respective payment method's API.
interface UnifiedPayment {
    fun pay(amount: Double)
}


class CardPaymentAdapter(private val cardPayment: CardPayment) : UnifiedPayment {
    override fun pay(amount: Double) {
        cardPayment.payWithCard(amount)
    }

}

class WalletPaymentAdapter(private val walletPayment: WalletPayment) : UnifiedPayment {
    override fun pay(amount: Double) {
        walletPayment.payWithWallet(amount)
    }

}

class UPIPaymentAdapter(private val upiPayment: UPIPayment) : UnifiedPayment {
    override fun pay(amount: Double) {
        upiPayment.payWithUPI(amount)
    }

}


class PaymentProcessor {

    fun processPayment(paymentMethod: UnifiedPayment, amount: Double) {
        paymentMethod.pay(amount)
    }

}

fun main() {
    val paymentProcessor = PaymentProcessor()

    val cardPayment = CardPaymentAdapter(CardPayment())
    paymentProcessor.processPayment(cardPayment, 100.0)

    val walletPayment = WalletPaymentAdapter(WalletPayment())
    paymentProcessor.processPayment(walletPayment, 100.0)

    val upiPayment = UPIPaymentAdapter(UPIPayment())
    paymentProcessor.processPayment(upiPayment, 100.0)
}