package app.dev.kon.bullchat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_group.*

class JoinGroupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_group)

        val group_name = intent.getStringExtra("name")
        val group_intro = intent.getStringExtra("introduction")
        val group_id = intent.getStringExtra("id")

        JoinGroupText.text = group_name
        JoinGroupTextView.text = group_intro

        BackFromPostButton.setOnClickListener {
            finish()
        }

        GoSignUpButton.setOnClickListener {
            joinGroupAndFinish(group_id!!)
        }
    }

    private fun joinGroupAndFinish(id: String) {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        var user = auth.currentUser

        if (user != null) {
            val userRef = db.collection("users").document(user.uid)
            userRef.update("groups", FieldValue.arrayUnion(id))
                .addOnSuccessListener {
                    Toast.makeText(this, "グループを追加しました", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "グループを追加できませんでした", Toast.LENGTH_SHORT).show()
                }

//            userRef.get()
//                .addOnSuccessListener { document ->
//                    var groups = mutableSetOf<String>()
//                    if (document["groups"] != null) {
//                        groups = document["groups"] as MutableSet<String>
//                    }
//                    groups.add(id)
//
//                    userRef.set(groups)
//                        .addOnSuccessListener {
//                            Log.d(null ,"add group to firestore")
//                            Toast.makeText(this, "グループを追加しました", Toast.LENGTH_SHORT).show()
//                        }
//                        .addOnFailureListener {
//                            Log.d(null, "fail to add group")
//                            Toast.makeText(this, "グループを追加できませんでした", Toast.LENGTH_SHORT).show()
//                        }
//
//
//                }
//                .addOnFailureListener {
//                    Log.d(null, "fail to get group set from firestore")
//                    Toast.makeText(this, "所属グループが取得できませんでした", Toast.LENGTH_SHORT).show()
//                }


//            val sharedPreferences = getSharedPreferences("DataStore", Context.MODE_PRIVATE)
//            val editor = sharedPreferences.edit()
//            val joinedGroupIdSet =
//                sharedPreferences.getStringSet("join_groups", mutableSetOf())!!.toMutableSet()
//            joinedGroupIdSet.add(id)
//            editor.putStringSet("join_groups", joinedGroupIdSet)
//            editor.apply()

            finish()
        }
        else {
            Toast.makeText(this, "先にサインアップもしくはサインインをしてください。", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}