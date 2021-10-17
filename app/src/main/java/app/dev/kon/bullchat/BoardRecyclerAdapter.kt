package app.dev.kon.bullchat

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.time.format.DateTimeFormatter

class BoardRecyclerAdapter(val postList: List<Post>, val context: Context): RecyclerView.Adapter<BoardRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardRecyclerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_board_data_cell, parent, false)
        val holder = BoardRecyclerViewHolder(view)

        return holder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: BoardRecyclerViewHolder, position: Int) {
        val post = postList[position]
        val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        holder.PostTitleTextView.text = post.Title
        holder.PostContentTextView.text = post.Content
        holder.PostDateTextView.text = post.PostDate.format(dtf)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}