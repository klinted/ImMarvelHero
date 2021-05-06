package com.friple.domain.usecases.type

import com.friple.domain.entities.AppResult


interface UseCaseWithParameter<P, R: Any> {

    suspend fun invoke(parameter: P): AppResult<R>
}