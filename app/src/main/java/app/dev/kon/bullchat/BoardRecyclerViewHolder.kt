package app.dev.kon.bullchat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BoardRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val PostTitleTextView: TextView = view.findViewById(R.id.BoardTitleTextView)
    val PostContentTextView: TextView = view.findViewById(R.id.BoardContentsTextView)
    val PostDateTextView: TextView = view.findViewById(R.id.BoardPostedDateTextView)
}