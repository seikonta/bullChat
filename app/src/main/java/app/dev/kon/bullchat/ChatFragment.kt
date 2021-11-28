package app.dev.kon.bullchat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment: Fragment() {
    val db = FirebaseFirestore.getInstance()
    var chatMessages: ArrayList<Chat> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId = activity?.intent?.getStringExtra("id")

        if (groupId != null) {
            readChatMessages(groupId)
        }

        chattingFAB.setOnClickListener {
            // textを保存する
            var chatContent = chattingEditText.text.toString()
//            var userName = "UserName";

            if (chatContent.isNotEmpty()) {
                if (groupId != null) {
                    sendChat(chatContent, groupId)
                }
            }

        }
    }

    private fun sendChat(content: String, groupId: String) {
        val userName = "UserName"

        val chat: Chat = Chat(
            UserName = userName,
            ChatContent = content,
            ChatDate = Date()
        )

        if (id != null) {
            db.collection("groups")
                .document(groupId)
                .collection("chat")
                .add(chat)
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error on sending chatting message", e)
                }
        }

        chattingEditText.text.clear()
    }

    fun readChatMessages(groupId: String) {
        db.collection("groups")
            .document(groupId)
            .collection("chat")
            .orderBy("chatDate", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error == null) {
                    chatMessages.clear()

                    if (value != null) {
                        for (mes in value) {

                            val message = Chat(
                                UserName = mes["userName"] as String,
                                ChatContent = mes["chatContent"] as String,
                                ChatDate = (mes["chatDate"] as Timestamp).toDate()
                            )

                            chatMessages.add(message)

                        }
                    }
                    setRecyclerView()
                }
                else {
                    Log.e("Firestore", "Error on reading chatting messages",error)
                }
            }
    }

    fun setRecyclerView() {
        var adapter = ChatRecyclerViewAdapter(chatMessages, requireActivity())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity()).apply {
            stackFromEnd = true
        }
    }
}