package com.syber.ssspltd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.syber.ssspltd.R
import com.syber.ssspltd.ui.view.addorder.ItemEntry


class ItemEntryAdapter(
    private val items: MutableList<ItemEntry>,
    private val availableItems: List<String>,
    private val onAddClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ItemEntryAdapter.ItemViewHolder>()
{

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemInput: MaterialAutoCompleteTextView = view.findViewById(R.id.etItem)
        val quantityInput: AppCompatEditText = view.findViewById(R.id.etQuantity)
        val addDeleteBtn: AppCompatImageView = view.findViewById(R.id.btnAddDelete)

        init {
            // Setup dropdown adapter once
            val dropAdapter = ArrayAdapter(
                itemInput.context,
                android.R.layout.simple_spinner_dropdown_item,
                availableItems
            )
            // make dropdown fill parent width
            itemInput.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT)
            itemInput.setAdapter(dropAdapter)
            itemInput.threshold = 0

            // Safe show dropdown
            itemInput.setOnClickListener {
                itemInput.post { itemInput.showDropDown() }
            }
        }

        fun bind(item: ItemEntry, position: Int) {
            itemInput.setText(item.item, false) // false = don't trigger filtering
            quantityInput.setText(item.quantity)

            if (position == items.size - 1) {
                addDeleteBtn.setImageResource(R.drawable.add)
                addDeleteBtn.setOnClickListener { onAddClick(position) }
            } else {
                addDeleteBtn.setImageResource(R.drawable.minus_button)
                addDeleteBtn.setOnClickListener { onDeleteClick(position) }
            }

            // ✅ Use safe adapter position checks
            itemInput.doAfterTextChanged { text ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION && pos < items.size) {
                    items[pos].item = text.toString()
                }
            }

            quantityInput.doAfterTextChanged { text ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION && pos < items.size) {
                    items[pos].quantity = text.toString()
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pack_data_input, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}
