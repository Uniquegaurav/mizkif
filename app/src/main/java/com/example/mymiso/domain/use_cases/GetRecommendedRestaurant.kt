package com.example.mymiso.domain.use_cases

import com.example.mymiso.presentation.model.Restaurant

class GetRecommendedRestaurant {
    operator fun invoke(): List<Restaurant> {
        return listOf<Restaurant>(
            Restaurant("1", "KFC", "Jl. Kaliurang", 4.5f, "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"),
            Restaurant("2", "McDonalds", "Jl. Solo", 4.5f, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9oBl8oMj8unCKsHx9WuzVKgxc34HJnei-Qw&s"),
            Restaurant("3", "Burger King", "Jl. Magelang", 4.5f, "https://santafe.com/wp-content/uploads/2020/06/geronimo_2.jpg"),
            Restaurant("4", "Pizza Hut", "Jl. Malioboro", 4.5f, "https://santafe.com/wp-content/uploads/2020/06/geronimo_2.jpg"),
            Restaurant("5", "Dominos Pizza", "Jl. Kaliurang", 4.5f, "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"),
            Restaurant("6", "A&W", "Jl. Solo", 4.5f, "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"),
            Restaurant("7", "Starbucks", "Jl. Magelang", 4.5f, "https://s3-media0.fl.yelpcdn.com/bphoto/oXvU3Sde9b1OV-7qkrZPnw/258s.jpg"),
            Restaurant("8", "J.Co", "Jl. Malioboro", 4.5f, "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"),
            Restaurant("9", "Baskin Robbins", "Jl. Kaliurang", 4.5f, "https://b.zmtcdn.com/data/collections/684397cd092de6a98862220e8cc40aca_1709810207.png"),
            Restaurant("10", "Dunkin Donuts", "Jl. Solo", 4.5f, "https://santafe.com/wp-content/uploads/2020/06/geronimo_2.jpg")
        )
    }
}