package app.dev.kon.bullchat

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_post.*
import java.time.LocalDateTime
import java.util.*

class CreatePostActivity: AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val id = intent.getStringExtra("id")

        BackFromPostButton.setOnClickListener {
            finish()
        }

        CreatePostButton.setOnClickListener {
            if (PostTitleTextInputEditText.text.toString() != "" && PostContentEditText.text.toString() != "") {
                sendPost(
                    title = PostTitleTextInputEditText.text.toString(),
                    content = PostContentEditText.text.toString(),
                    id = id
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendPost(title: String, content: String, id: String?) {
        val db = FirebaseFirestore.getInstance()
        val post = Post(
            Title = title,
            Content = content,
            PostDate = Date()
        )

        if (id != null) {
            db.collection("groups")
                .document(id)
                .collection("posts")
                .add(post)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "投稿を送信しました", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error Sending Post", e)
                }
        }
    }
}