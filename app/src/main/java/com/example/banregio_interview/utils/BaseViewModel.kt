package com.example.banregio_interview.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel constructor(
) : ViewModel() {

    protected val _response = Channel<ViewState<Any>>(Channel.BUFFERED)
    val response = _response.receiveAsFlow()


    protected suspend fun <T : Any> executeRemoteUseCase(function: suspend () -> Flow<Resource<T>>) {
        try {
            _response.send(Loading())

            function.invoke()
                .catch {
                    _response.send(Error())
                }
                .collect { it ->
                    when (it) {
                        is Resource.Loading -> {
                            //NO-OP//
                        }
                        is Resource.Success -> {
                            _response.send(Success(data = it.data))
                        }
                        is Resource.Error -> {
                            _response.send(Error(code = it.code, errorResponse = it.errorResponse))
                        }
                    }
                }

        } catch (e: Throwable) {
            _response.send(Error())
        }
    }
}