package app.dev.kon.bullchat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.fragment_board.*
import java.time.LocalDateTime

class BoardFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var groupId = activity?.intent?.getStringExtra("id")
        super.onViewCreated(view, savedInstanceState)

//        val groupName = activity?.intent?.getStringExtra("name")
//        if (groupName != null) {
//            GroupBoardText.text = groupName
//        }
        var db = FirebaseFirestore.getInstance()

        var recyclerView = view.findViewById<RecyclerView>(R.id.GorupBoardRecyclerView)

        var posts: ArrayList<Post> = ArrayList()

//        posts = listOf(
//            Post(Title = "Title", Content = "content,content,content,content", PostDate = LocalDateTime.now())
//        )


        if (groupId != null) {
            db.collection("groups")
                .document(groupId)
                .collection("posts")
                .orderBy("PostDate", Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w("Firestore", "Listen Failed", error)
                        return@addSnapshotListener
                    }

                    posts.clear()
                    if (value != null) {
                        for (doc in value) {
                            var post = doc.toObject<Post>()

                            Log.d("post", post.toString())
                            posts.add(post)
                        }
                    }

                    var viewAdapter = BoardRecyclerAdapter(postList = posts, requireActivity())
                    recyclerView.adapter = viewAdapter
                    viewAdapter.notifyDataSetChanged()
                }
        }

//        recyclerView.adapter = BoardRecyclerAdapter(postList = posts, context = requireActivity())
        recyclerView.adapter = BoardRecyclerAdapter(postList = posts, requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        MakePostingButton.setOnClickListener {
            val intent = Intent(activity, CreatePostActivity::class.java)
            intent.putExtra("id", groupId)
            startActivity(intent)
        }
    }
}