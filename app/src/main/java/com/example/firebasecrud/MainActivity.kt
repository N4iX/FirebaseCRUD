package com.example.firebasecrud

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.ChangeEventListener
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    //access database table
    private var recyclableItemDatabase: DatabaseReference? = null//change
    //to get the current database pointer
    private var recyclableItemReference: DatabaseReference? = null//change

    private var recyclableItemAdapter: FirebaseRecyclerAdapter<RecyclableItem, RecyclableItemViewHolder>? = null

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()


        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView(){
        recyclableItemDatabase = FirebaseDatabase.getInstance().reference//change
        //to access to the table
        recyclableItemReference = FirebaseDatabase.getInstance().getReference("recyclableItem")//change

        firebaseListenerInit()

        recyclerViewRecyclableItem.layoutManager = LinearLayoutManager(this)
        val query = recyclableItemReference!!
        recyclableItemAdapter = object: FirebaseRecyclerAdapter<RecyclableItem, RecyclableItemViewHolder>(
            RecyclableItem::class.java, R.layout.recyclable_item_view, RecyclableItemViewHolder::class.java,query
        ){
            override fun populateViewHolder(viewHolder: RecyclableItemViewHolder?, model: RecyclableItem?, position: Int) {
                reference = getRef(position)
                model?.dataKey = reference.key.toString()
                viewHolder!!.bindRecyclableItem(model)
                //Log.d("key value", reference.key.toString())
            }

            override fun onChildChanged(
                type: ChangeEventListener.EventType?,
                snapshot: DataSnapshot?,
                index: Int,
                oldIndex: Int
            ) {
                super.onChildChanged(type, snapshot, index, oldIndex)
                //Log.d("key value", snapshot?.key.toString())
                //keyList.add(snapshot?.key.toString())
                //recyclerViewRecyclableItem.scrollToPosition(index)
            }
        }
        recyclerViewRecyclableItem.adapter = recyclableItemAdapter
    }

    private fun firebaseListenerInit() {
        val childEventListener = object: ChildEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "postMessages:onCancelled", databaseError!!.toException())
                //Toast.makeText(this, "Failed to load Message.", Toast.LENGTH_SHORT).show()
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, recyclableItem: String?) {
                Log.e(TAG, "onChildMoved:" + dataSnapshot!!.key)

                // A recyclable item has changed position
                val recyclableItem = dataSnapshot.getValue(RecyclableItem::class.java)
                //toast here
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, recyclableItem: String?) {
                Log.e(TAG, "onChildChanged: " + dataSnapshot!!.key)

                val recyclableItem = dataSnapshot.getValue(RecyclableItem::class.java)
                //toast here
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, recyclableItem: String?) {
                val recyclableItem = dataSnapshot!!.getValue(RecyclableItem::class.java)

                Log.e(TAG, "onChildAdded:" + recyclableItem!!)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.e(TAG, "onChildRemoved:" + dataSnapshot!!.key)

                // A message has been removed
                val recyclableItem = dataSnapshot.getValue(RecyclableItem::class.java)
            }

        }
    }
}
