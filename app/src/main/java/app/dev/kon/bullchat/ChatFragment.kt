package app.dev.kon.bullchat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment: Fragment() {
    val db = FirebaseFirestore.getInstance()
    var chatMessages: ArrayList<Chat> = ArrayList()
    var auth: FirebaseAuth = Firebase.auth
    var storage: FirebaseStorage = Firebase.storage
    var userToBitmapMap: MutableMap<String, Bitmap> = mutableMapOf("commonIcon" to BitmapFactory.decodeResource(context?.resources ,R.drawable.baseline_account_circle_black_48))
    var commonUserBitmap: Bitmap? = userToBitmapMap["commonIcon"]
    val ONE_MEGABYTE: Long = 1024*1024

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId = activity?.intent?.getStringExtra("id")

        if (groupId != null) {
            readChatMessages(groupId)
        }

        storage.reference.child("icons/commonUser.png").getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            commonUserBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            commonUserBitmap = null
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
        val user = auth.currentUser
        if (user != null) {
            val userName = user!!.uid

            val chat: Chat = Chat(
                UserName = userName,
                ChatContent = content,
                ChatDate = Date()
            )

            if (id != null) {
                db.collection("groups")
                    .document(groupId)
                    .collection("chat")
                    .add(hashMapOf(
                        "chatContent" to chat.ChatContent,
                        "chatDate" to chat.ChatDate,
                        "userName" to chat.UserName
                    ))
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error on sending chatting message", e)
                    }
            }

            chattingEditText.text.clear()
        }
    }

    private fun readChatMessages(groupId: String) {
        val storageReference = storage.reference
        val iconRef = storageReference.child("icons")

        db.collection("groups")
            .document(groupId)
            .collection("chat")
            .orderBy("chatDate", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error == null) {
                    chatMessages.clear()

                    if (value != null) {
                        for (mes in value) {

                            val uid = mes["userName"] as String

                            db.collection("users")
                                .document(uid)
                                .get()
                                .addOnSuccessListener { doc ->
                                    var userName: String
                                    if (doc.exists()) {
                                        userName = doc["name"] as String

                                        var message = Chat(
                                            UserName = userName,
                                            ChatContent = mes["chatContent"] as String,
                                            ChatDate = (mes["chatDate"] as Timestamp).toDate()
                                        )

                                        if (userToBitmapMap[uid] != null) {
                                            message.IconImage = userToBitmapMap[uid]
                                        }
                                        else {
                                            val chatIconRef = iconRef.child(uid+".png")

                                            chatIconRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                                                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                                                message.IconImage = bitmap
                                                userToBitmapMap.put(uid, bitmap)
                                            }.addOnFailureListener {
                                                message.IconImage = commonUserBitmap
                                            }
                                        }

                                        Log.d("content", message.toString())

                                        chatMessages.add(message)
                                        Log.d("chat", "load chat successfully")
                                        setRecyclerView()
                                    }
                                    else {
                                        userName = "削除されたアカウントです"

                                        val message = Chat(
                                            UserName = userName,
                                            ChatContent = mes["chatContent"] as String,
                                            ChatDate = (mes["chatDate"] as Timestamp).toDate(),
                                            IconImage = commonUserBitmap
                                        )

                                        chatMessages.add(message)
                                    }


                                }
                                .addOnFailureListener {
                                    Toast.makeText(requireContext(), "送信に失敗しました", Toast.LENGTH_SHORT).show()
                                }

//                            val message = Chat(
//                                UserName = mes["userName"] as String,
//                                ChatContent = mes["chatContent"] as String,
//                                ChatDate = (mes["chatDate"] as Timestamp).toDate()
//                            )

//                            chatMessages.add(message)

                        }
                    }
//                    setRecyclerView()
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