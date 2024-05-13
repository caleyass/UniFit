package com.unifit.unifit.domain.usecases

import com.unifit.unifit.data.local.entity.PillEntity
import com.unifit.unifit.domain.repositories.PillRepository
import java.util.concurrent.Flow
import javax.inject.Inject

class GetPillsUseCase @Inject constructor(private val pillRepository: PillRepository) {
    suspend fun execute() = pillRepository.getAllPills()

}