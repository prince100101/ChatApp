package com.example.chat

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth


class ChatAdapter(private val context: Context, private val messageList: ArrayList<Meaasge>): RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Adapter {

    val ITEM_RECEIVE=1
    val ITEM_SENT= 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==2){
            val view:View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
        else{
            val view:View= LayoutInflater.from(context).inflate(R.layout.received,parent,false)
            return ReceivedViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass==SentViewHolder::class.java){
            val viewHolder= holder as SentViewHolder
            holder.textSent.text= currentMessage.message
        }
        else{
            val viewHolder= holder as ReceivedViewHolder
            holder.textReceived.text= currentMessage.message
        }
    }

    override fun getItemCount(): Int {
            return messageList.size
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage= messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
            return ITEM_SENT
        else
            return ITEM_RECEIVE

    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textSent = itemView.findViewById<TextView>(R.id.tvSentMessage)
    }
    class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textReceived= itemView.findViewById<TextView>(R.id.tvReceivedMessage)
    }
}