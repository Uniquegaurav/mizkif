package com.example.mymiso.domain.use_cases
import com.example.mymiso.presentation.profile_screen.model.Data
import com.example.mymiso.domain.repository.UserRepository
import retrofit2.Response

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Response<Data> {
        return userRepository.getAllUsers()
    }
}
