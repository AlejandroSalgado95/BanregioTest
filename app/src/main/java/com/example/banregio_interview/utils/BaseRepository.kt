package com.example.banregio_interview.utils

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


open class BaseRepository() {

    protected suspend fun <T : Any> executeRemoteResponse(response: Response<T>): Flow<Resource<T>> {
        return flow {
            try {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        val resultResponse = response.body() as T
                        emit(Resource.Success(resultResponse))
                    } else {
                        emit(Resource.Success())
                    }
                } else {
                    if (response.errorBody() != null) {
                        val gson = Gson()
                        val parsedError = gson.fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                        emit(Resource.Error(code = response.code(), errorResponse = parsedError))
                    } else {
                        emit(Resource.Error(code = response.code()))
                    }
                }
            } catch (e: HttpException) {
                emit(Resource.Error())
            } catch (e: IOException) {
                emit(Resource.Error())
            } catch (e: SocketTimeoutException) {
                emit(Resource.Error())
            }
        }.catch {
            emit(Resource.Error())
        }
    }
}