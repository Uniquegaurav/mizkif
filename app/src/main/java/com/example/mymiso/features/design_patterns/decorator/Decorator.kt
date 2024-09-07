package com.example.mymiso.features.design_patterns.decorator

// Decorator Design Pattern
// The decorator pattern is a structural design pattern that allows adding new behaviors to objects
// dynamically by placing them inside special wrapper objects that contain these behaviors.


interface Restaurant {
    fun serve()
}

class BasicRestaurant : Restaurant {
    override fun serve() {
        println("Serving basic food")
    }
}

// The decorator class is an abstract class that extends the component interface and acts as a base class for concrete decorators.
abstract class RestaurantDecorator(private val decoratedRestaurant: Restaurant) : Restaurant {
    override fun serve() {
        decoratedRestaurant.serve()
    }
}

// Decorators are concrete classes that extend the abstract decorator class and add new behaviors to the component.
// serve() method is called first to serve the basic food and then the new behavior is added.

class RestaurantWithFreeDessert(private val decoratedRestaurant: Restaurant) :
    RestaurantDecorator(decoratedRestaurant) {
    override fun serve() {
        super.serve()
        println("Serving Free dessert")
    }
}

class RestaurantWithFreeDrink(private val decoratedRestaurant: Restaurant) :
    RestaurantDecorator(decoratedRestaurant) {
    override fun serve() {
        super.serve()
        println("Serving Free drink")
    }
}

fun main() {
    val basicRestaurant = BasicRestaurant()
    val restaurantWithFreeDessert = RestaurantWithFreeDessert(basicRestaurant)
    // The restaurantWithFreeDessert object is passed to the constructor of the RestaurantWithFreeDrink class to add a new behavior.
    val restaurantWithFreeDrink = RestaurantWithFreeDrink(restaurantWithFreeDessert)

    restaurantWithFreeDrink.serve()
}