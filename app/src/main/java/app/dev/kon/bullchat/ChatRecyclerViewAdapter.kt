package app.dev.kon.bullchat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatRecyclerViewAdapter(var chatList: List<Chat>, val context: Context):
    RecyclerView.Adapter<ChatRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRecyclerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat_data_cell, parent, false)
        val holder = ChatRecyclerViewHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: ChatRecyclerViewHolder, position: Int) {
        holder.UserNameView.text = chatList[position].UserName
        holder.ChatContentView.text = chatList[position].ChatContent

        if (chatList[position].IconImage != null) {
            holder.UserImageView.setImageBitmap(chatList[position].IconImage)
        }
        else {
            holder.UserImageView.setImageResource(R.drawable.baseline_account_circle_black_48)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}