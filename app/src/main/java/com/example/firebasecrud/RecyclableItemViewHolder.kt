package com.example.firebasecrud

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclable_item_view.view.*

class RecyclableItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    fun bindRecyclableItem(recyclableItem: RecyclableItem?){
        with(recyclableItem!!){
            itemView.textViewDataKey.text = dataKey
            itemView.textViewRecyclableItemName.text = itemName
            itemView.textViewItemInfoLink.text = itemInfoLink
            Picasso.get().load(itemImage).into(itemView.imageViewRecyclableItem)
            itemView.textViewImageLinkEdit.text = itemImage
        }
    }

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val dataKey = v?.textViewDataKey?.text.toString()
        val itemName = v?.textViewRecyclableItemName?.text.toString()
        val imageLink = v?.textViewImageLinkEdit?.text.toString()
        val itemInfoLink = v?.textViewItemInfoLink?.text.toString()
//        Log.d("Data key", dataKey)
//        Log.d("Item name", itemName)
//        Log.d("Image link", imageLink)
//        Log.d("Item info link", itemInfoLink)

        val intent = Intent(itemView.context, EditDeleteActivity::class.java)
        intent.putExtra(EXTRA_DATA_KEY, dataKey)
        intent.putExtra(EXTRA_ITEM_NAME, itemName)
        intent.putExtra(EXTRA_IMAGE_LINK, imageLink)
        intent.putExtra(EXTRA_ITEM_INFO_LINK, itemInfoLink)
        itemView.context.startActivity(intent)
    }

    companion object{
        const val EXTRA_DATA_KEY = "com.example.firebasecrud.DATA_KEY"
        const val EXTRA_ITEM_NAME = "com.example.firebasecrud.ITEM_NAME"
        const val EXTRA_IMAGE_LINK = "com.example.firebasecrud.IMAGE_LINK"
        const val EXTRA_ITEM_INFO_LINK = "com.example.firebasecrud.ITEM_INFO_LINK"
    }
}