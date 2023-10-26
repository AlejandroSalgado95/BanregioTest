package com.example.banregio_interview.features.presentation

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banregio_interview.R
import com.example.banregio_interview.databinding.FragmentCreditCardBinding
import com.example.banregio_interview.features.domain.response.CreditCardDTO
import com.example.banregio_interview.features.domain.response.MovementDTO
import com.example.banregio_interview.features.domain.response.toCreditCardDomain
import com.example.banregio_interview.features.domain.response.toMovementDomain
import com.example.banregio_interview.utils.Loading
import com.example.banregio_interview.utils.Success
import com.example.banregio_interview.utils.Error
import com.example.banregio_interview.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class CreditCardFragment : Fragment() {

    private var _binding: FragmentCreditCardBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var movementsAdapter: MovementsAdapter
    private val viewModel: CreditCardFragmentVM by viewModels()
    private var progressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movementsAdapter.setOnItemClickListener { item, adapterPosition ->
            Toast.makeText(
                requireContext(),
                "Clicked on item " + adapterPosition.toString(),
                Toast.LENGTH_SHORT
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreditCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (progressDialog == null)
            progressDialog = ProgressDialog(requireContext())

        setupRecyclerView()
        registerObservers()
    }

    fun setupRecyclerView() {
        binding.rvMovements.adapter = movementsAdapter
        binding.rvMovements.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun registerObservers() {
        lifecycleScope.launch {
            viewModel.response.collect {
                handleViewState(it)
            }
        }
    }


    private fun handleViewState(viewState: ViewState<Any>) {
        when (viewState) {
            is Success -> {
                progressDialog?.dismiss()
                if (viewState.data is CreditCardDTO) {
                    val responseDomain = viewState.data.toCreditCardDomain()
                    //Fill form with data//
                    binding.tvCardNumber.text = responseDomain.creditCardNumber
                    binding.tvCardHolder.text = responseDomain.cardOwner
                    binding.tvExpiryDate.text = responseDomain.expirationDate
                    binding.tvCVV.text = responseDomain.cvv.toString()

                } else{
                    Log.d("SUCCESS_MOVEMENTS", "YES")
                    val response: List<MovementDTO> = viewState.data as List<MovementDTO>
                    val responseDomain =  response.map { it.toMovementDomain() }
                    Log.d("SUCCESS_MOVEMENTS_DATA", responseDomain.toString())

                    movementsAdapter.submitList(responseDomain)
                    binding.rvMovements.scheduleLayoutAnimation()

                }
            }

            is Error -> {
                progressDialog?.dismiss()
                Toast.makeText(
                    requireContext(),
                    "There was an error",
                    Toast.LENGTH_SHORT
                )
            }

            is Loading -> {
                progressDialog?.setCancelable(false)
                progressDialog?.setMessage("Loading...")
                progressDialog?.show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}