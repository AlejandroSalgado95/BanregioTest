package com.example.banregio_interview.features.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.banregio_interview.features.data.CreditCardRepository
import com.example.banregio_interview.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditCardFragmentVM @Inject constructor(
    private val repository: CreditCardRepository,
    private val state: SavedStateHandle
) : BaseViewModel() {

    init {
        getCreditCard(1)
        getCreditCardMovements(3)
    }


    fun getCreditCard(id: Int) {
        viewModelScope.launch {
            executeRemoteUseCase {
                repository.getCreditCard(id)
            }
        }
    }

    fun getCreditCardMovements(id: Int) {
        viewModelScope.launch {
            executeRemoteUseCase {
                repository.getCreditCardMovements(id)
            }
        }
    }
}