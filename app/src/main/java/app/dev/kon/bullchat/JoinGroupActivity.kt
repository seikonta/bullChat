package app.dev.kon.bullchat

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_group.*

class JoinGroupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_group)

        val db = FirebaseFirestore.getInstance()

        val group_name = intent.getStringExtra("name")
        val group_intro = intent.getStringExtra("introduction")
        val group_id = intent.getStringExtra("id")

        JoinGroupText.text = group_name
        JoinGroupTextView.text = group_intro

        BackFromPostButton.setOnClickListener {
            finish()
        }

        JoinGroupButton.setOnClickListener {
            joinGroupAndFinish(group_id!!)
        }
    }

    private fun joinGroupAndFinish(id: String) {
        val sharedPreferences = getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val joinedGroupIdSet = sharedPreferences.getStringSet("join_groups", mutableSetOf())!!.toMutableSet()
        joinedGroupIdSet.add(id)
        editor.putStringSet("join_groups", joinedGroupIdSet)
        editor.apply()
        finish()
    }
}