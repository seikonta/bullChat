package app.dev.kon.bullchat

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val UserImageView: ImageView = view.findViewById(R.id.avatarImage)
    val UserNameView: TextView = view.findViewById(R.id.userNameTextView)
    val ChatContentView: TextView = view.findViewById(R.id.chatContentTextView)
}