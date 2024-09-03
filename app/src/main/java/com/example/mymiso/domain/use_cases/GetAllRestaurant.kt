package com.example.mymiso.domain.use_cases

import com.example.mymiso.presentation.model.Restaurant

class GetAllRestaurant {
    operator fun invoke(): List<Restaurant> {
        return listOf<Restaurant>(
            Restaurant("1", "KFC", "Jl. Kaliurang", 4.5f, "https://www.kfc.co.id/"),
            Restaurant("2", "McDonalds", "Jl. Solo", 4.5f, "https://www.mcdonalds.co.id/"),
            Restaurant("3", "Burger King", "Jl. Magelang", 4.5f, "https://www.burgerking.co.id/"),
            Restaurant("4", "Pizza Hut", "Jl. Malioboro", 4.5f, "https://www.pizzahut.co.id/"),
        )
    }

}