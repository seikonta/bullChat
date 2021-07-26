package app.dev.kon.bullchat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_group.*

class CreateGroupActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        BackButton.setOnClickListener {
            finish()
        }

        CreateGroupButton.setOnClickListener {
            // データをFirestoreに渡す
            var text : String;
            val duration = Toast.LENGTH_SHORT
            if (GroupNameTextInputEditText.text.toString() != "" && GroupIntroductionEditText.text.toString() != "" && GroupTagsEditText.toString() != "") {
//                val tagList =  listOf<String>(GroupTagsEditText.text.toString())
                val tagList = GroupTagsEditText.text.toString().split(",", "、")
                val group = Group(GroupNameTextInputEditText.text.toString(), GroupIntroductionEditText.text.toString(), tagList, "")
                db.collection("groups").add(group)
                text = "作成完了"
                Toast.makeText(applicationContext, text, duration).show()
                finish()
            }
            else {
                text = "内容を全て埋めてください"
                Toast.makeText(applicationContext, text, duration).show()
            }
        }
    }
}