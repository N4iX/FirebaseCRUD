package com.example.firebasecrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_delete.*

class EditDeleteActivity : AppCompatActivity() {
    private lateinit var dataKey: String

    //access database table
    private var recyclableItemDatabase: DatabaseReference? = null//change

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_delete)

        val intent = intent
        dataKey = intent.getStringExtra(EXTRA_DATA_KEY)
        val itemName = intent.getStringExtra(EXTRA_ITEM_NAME)
        val imageLink = intent.getStringExtra(EXTRA_IMAGE_LINK)
        val itemInfoLink = intent.getStringExtra(EXTRA_ITEM_INFO_LINK)

        editTextNameEdit.setText(itemName)
        editTextImageLinkEdit.setText(imageLink)
        editTextItemInfoLinkEdit.setText(itemInfoLink)
        Picasso.get().load(imageLink).into(imageViewItem)

        buttonEdit.setOnClickListener {
            editItem()
            finish()
        }

        buttonDelete.setOnClickListener {
            deleteItem()
            finish()
        }
    }

    private fun editItem(){

        val itemName = editTextNameEdit.text.toString()
        val imageLink = editTextImageLinkEdit.text.toString()
        val itemInfoLink = editTextItemInfoLinkEdit.text.toString()

        val recyclableItem = RecyclableItem(
            itemName,
            imageLink,
            itemInfoLink
        )

        //database pointer
        recyclableItemDatabase = FirebaseDatabase.getInstance().reference//change

        val recyclableItemValues = recyclableItem.toMap()
        val childUpdates = HashMap<String, Any>()
        childUpdates.put("/recyclableItem/" + dataKey, recyclableItemValues)
        recyclableItemDatabase!!.updateChildren((childUpdates))

        Toast.makeText(applicationContext, "Data updated", Toast.LENGTH_SHORT).show()
    }

    private fun deleteItem(){

        recyclableItemDatabase = FirebaseDatabase.getInstance().reference.child("recyclableItem").child(dataKey)//change
        recyclableItemDatabase!!.removeValue()

        Toast.makeText(applicationContext, "Data deleted", Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val EXTRA_DATA_KEY = "com.example.firebasecrud.DATA_KEY"
        const val EXTRA_ITEM_NAME = "com.example.firebasecrud.ITEM_NAME"
        const val EXTRA_IMAGE_LINK = "com.example.firebasecrud.IMAGE_LINK"
        const val EXTRA_ITEM_INFO_LINK = "com.example.firebasecrud.ITEM_INFO_LINK"
    }
}
