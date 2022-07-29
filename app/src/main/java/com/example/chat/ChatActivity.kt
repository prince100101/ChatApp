package com.example.chat


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var rvChat:RecyclerView
    private lateinit var etChat: EditText
    private lateinit var btnChat: Button
    private lateinit var messageAdapter: ChatAdapter
    private lateinit var messageList: ArrayList<Meaasge>
    private lateinit var mDbRef: DatabaseReference
    private var senderRoom: String?= null
    private var receiverRoon: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiveruid = intent.getStringExtra("uid")
        val senderuid= FirebaseAuth.getInstance().currentUser?.uid

        receiverRoon= senderuid + receiveruid
        senderRoom= receiveruid + senderuid

        supportActionBar?.title= name
        mDbRef= FirebaseDatabase.getInstance().getReference()
        rvChat= findViewById(R.id.rvChat)
        etChat= findViewById(R.id.etChat)
        btnChat= findViewById(R.id.btnSend)
        messageList= ArrayList()
        messageAdapter= ChatAdapter(this, messageList)

        rvChat.layoutManager =LinearLayoutManager(this)
        rvChat.adapter= messageAdapter

        mDbRef.child("chats").child(senderRoom!!).child("messages").
        addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
               for(postSnapshot in snapshot.children){
                   val message = postSnapshot.getValue(Meaasge::class.java)
                   messageList.add(message!!)
               }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        btnChat.setOnClickListener {
            val message= etChat.text.toString()
            val messageObject= Meaasge(message, senderuid )

                mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageObject).addOnSuccessListener {
                        mDbRef.child("chats").child(receiverRoon!!).child("messages").push()
                            .setValue(messageObject)
                    }
            etChat.setText("")
        }


    }
}