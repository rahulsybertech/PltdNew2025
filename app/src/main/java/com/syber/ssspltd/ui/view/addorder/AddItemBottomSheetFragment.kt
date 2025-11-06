package com.syber.ssspltd.ui.view.addorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.syber.ssspltd.R
import com.syber.ssspltd.adapter.ItemEntryAdapter

class AddItemBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemEntryAdapter
    private val itemList = mutableListOf<ItemEntry>()
    private val availableItemNames = listOf("Item 1", "Item 2", "Item 3") // example data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_item_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        val saveBtn = view.findViewById<AppCompatTextView>(R.id.addItem)
        val closeBtn = view.findViewById<AppCompatTextView>(R.id.closeDialog)

        itemList.clear()
        itemList.add(ItemEntry())

        adapter = ItemEntryAdapter(
            itemList,
            availableItemNames,
            onAddClick = { position ->
                itemList.add(ItemEntry())
                adapter.notifyItemInserted(itemList.size - 1)
                adapter.notifyItemChanged(itemList.size - 2)
                recyclerView.post { recyclerView.scrollToPosition(itemList.size - 1) }
            },
            onDeleteClick = { position ->
                if (position in itemList.indices) {
                    itemList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, itemList.size)
                    if (itemList.isNotEmpty()) {
                        adapter.notifyItemChanged(itemList.size - 1)
                    }
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        saveBtn.setOnClickListener {
            if (validateAllEntries()) {
                Toast.makeText(requireContext(), "Valid data submitted!", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        closeBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun validateAllEntries(): Boolean {
        // Your validation logic here
        return true
    }
}
