package com.example.mymiso.features.design_patterns.observer


//The Observer Pattern is a behavioral design pattern where an object (the subject) maintains a list of dependents (observers)
// and automatically notifies them of any state changes,
// typically by calling one of their methods. This pattern is useful for creating a one-to-many
// dependency between objects, allowing multiple observers to stay updated whenever the subject's state changes.


// MenuUpdateObserver is an interface that defines the method that will be called by the subject (Menu) to notify the observer of any changes to the menu.
interface MenuUpdateObserver {
    fun onMenuUpdated(menu: List<MenuItem>)
}

data class MenuItem(val name: String, val price: Double)


// The Menu class is the subject that maintains a list of observers and notifies them of any changes to the menu.
class Menu {
    private val observers = mutableListOf<MenuUpdateObserver>()
    private val menu = mutableListOf<MenuItem>()

    fun subscribe(observer: MenuUpdateObserver) {
        observers.add(observer)
    }

    fun unsubscribe(observer: MenuUpdateObserver) {
        observers.remove(observer)
    }

    fun addToMenu(item: MenuItem) {
        menu.add(item)
        notifyObservers()
    }

    fun deleteFromMenu(item: MenuItem) {
        menu.remove(item)
        notifyObservers()
    }

    private fun notifyObservers() {
        observers.forEach { observer ->
            observer.onMenuUpdated(menu)
        }
    }
}

// Customer is an observer that implements the MenuUpdateObserver interface.
// It will be notified whenever the menu is updated.
class Customer : MenuUpdateObserver {
    override fun onMenuUpdated(menu: List<MenuItem>) {
        println("Seeing the updated menu: $menu")
    }
}

fun main() {
    val menu = Menu()
    val customer1 = Customer()
    val customer2 = Customer()

    menu.subscribe(customer1)
    menu.subscribe(customer2)

    menu.addToMenu(MenuItem("Burger", 5.0))
    menu.addToMenu(MenuItem("Pizza", 10.0))

    menu.unsubscribe(customer2)

    menu.deleteFromMenu(MenuItem("Burger", 5.0))
}