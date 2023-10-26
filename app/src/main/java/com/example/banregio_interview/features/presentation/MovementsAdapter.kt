package com.example.banregio_interview.features.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.banregio_interview.databinding.MovementItemBinding
import com.example.banregio_interview.features.domain.response.MovementDomain
import javax.inject.Inject



class MovementsAdapter @Inject constructor(
    private val glide: RequestManager
) : ListAdapter<MovementDomain, MovementsAdapter.MovementViewHolder>(MovementsDataDiff) {

    private var onItemClickListener: ((MovementDomain, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovementDomain, Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {
        return MovementViewHolder(
            MovementItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovementViewHolder(private val binding: MovementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovementDomain) = with(binding) {

            transactionAmount.text = "-$" + item.amount
            transactionDate.text = item.date
            transactionName.text = item.description



            /*val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
            glide.load(item.imageUrl).transition(
                DrawableTransitionOptions
                    .withCrossFade(factory)).into(productImage)

             */

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item, adapterPosition)
            }
        }
    }
}

object MovementsDataDiff : DiffUtil.ItemCallback<MovementDomain>() {
    override fun areContentsTheSame(
        oldItem: MovementDomain,
        newItem: MovementDomain
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: MovementDomain,
        newItem: MovementDomain
    ): Boolean {
        return oldItem.equals(newItem)
    }
}