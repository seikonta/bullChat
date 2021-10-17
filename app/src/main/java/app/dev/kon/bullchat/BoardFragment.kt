package app.dev.kon.bullchat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_board.*
import java.time.LocalDateTime

class BoardFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val groupName = activity?.intent?.getStringExtra("name")
//        if (groupName != null) {
//            GroupBoardText.text = groupName
//        }
        var recyclerView = view.findViewById<RecyclerView>(R.id.GorupBoardRecyclerView)

        var posts: List<Post>

        posts = listOf(
            Post(Title = "Title", Content = "content,content,content,content", PostDate = LocalDateTime.now())
        )

        recyclerView.adapter = BoardRecyclerAdapter(postList = posts, context = requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        MakePostingButton.setOnClickListener {
            val intent = Intent(activity, CreatePostActivity::class.java)
            intent.putExtra("id", activity?.intent?.getStringExtra("id"))
            startActivity(intent)
        }
    }
}