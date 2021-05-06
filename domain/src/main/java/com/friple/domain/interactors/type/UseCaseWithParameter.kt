package com.friple.domain.interactors.type

import com.friple.domain.entities.AppResult


interface UseCaseWithParameter<P, R: Any> {

    suspend fun execute(parameter: P): AppResult<R>
}