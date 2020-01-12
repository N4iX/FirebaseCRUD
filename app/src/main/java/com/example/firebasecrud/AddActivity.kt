package com.example.firebasecrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    //access database table
    private var recyclableItemDatabase: DatabaseReference? = null//change
    //to get the current database pointer
    private var recyclableItemReference: DatabaseReference? = null//change
    private var recyclableItemListener: ChildEventListener? = null//change

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //to get the root folder
        recyclableItemDatabase = FirebaseDatabase.getInstance().reference//change
        //to access to the table
        recyclableItemReference = FirebaseDatabase.getInstance().getReference("recyclableItem")//change

        fab.setOnClickListener {
            addNewRecyclableItem()
            Toast.makeText(getApplicationContext(),"Data added", Toast.LENGTH_SHORT).show()
            finish()
        }

        buttonReset.setOnClickListener{
            editTextItemName.setText("")
            editTextItemImageString.setText("")
            editTextItemInfoLink.setText("")
        }
    }

    private fun addNewRecyclableItem(){
        val itemName = editTextItemName.text.toString() //change
        val imageLink = editTextItemImageString.text.toString() //change
        val itemInfoLink = editTextItemInfoLink.text.toString() //change

        val recyclableItem = RecyclableItem(itemName, imageLink, itemInfoLink) //change

        val recyclableItemValues = recyclableItem.toMap() //change
        val childUpdates = HashMap<String, Any>()

        val key = recyclableItemDatabase!!.child("recyclableItem").push().key //change

        childUpdates.put("/recyclableItem/" + key, recyclableItemValues) //MUST change

        recyclableItemDatabase!!.updateChildren(childUpdates) //change
    }
}
