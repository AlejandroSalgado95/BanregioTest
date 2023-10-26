package com.example.banregio_interview.features.data

import com.example.banregio_interview.features.domain.response.CreditCardDTO
import com.example.banregio_interview.features.domain.response.MovementDTO
import com.example.banregio_interview.networking.ApiBanregio
import com.example.banregio_interview.utils.BaseRepository
import com.example.banregio_interview.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreditCardRepository @Inject constructor(
    private val remoteDataSource: ApiBanregio,
) : BaseRepository() {

    suspend fun getCreditCard(id : Int): Flow<Resource<CreditCardDTO>> {
        return withContext(Dispatchers.IO) {
            executeRemoteResponse(remoteDataSource.getCreditCard(id))
        }
    }

    suspend fun getCreditCardMovements(id : Int): Flow<Resource<List<MovementDTO>>> {
        return withContext(Dispatchers.IO) {
            executeRemoteResponse(remoteDataSource.getCreditCardMovements(id))
        }
    }
}