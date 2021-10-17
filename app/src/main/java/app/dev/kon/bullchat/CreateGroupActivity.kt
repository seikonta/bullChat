package app.dev.kon.bullchat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_group.*

class CreateGroupActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        BackFromPostButton.setOnClickListener {
            finish()
        }

        CreateGroupButton.setOnClickListener {
            // データをFirestoreに渡す
            var text : String;
            val duration = Toast.LENGTH_SHORT
            if (GroupNameTextInputEditText.text.toString() != "" && GroupIntroductionEditText.text.toString() != "") {
//                val tagList =  listOf<String>(GroupTagsEditText.text.toString())
                val newGroupDoc = db.collection("groups").document()
                val tagList: MutableList<String>
                if (GroupTagsEditText.text.toString() != "") {
                    tagList = GroupTagsEditText.text.toString().split(",", "、") as MutableList<String>
                    tagList += mutableListOf(newGroupDoc.id)
                }
                else {
                    tagList = mutableListOf(newGroupDoc.id)
                }
                val group = Group(GroupNameTextInputEditText.text.toString(), GroupIntroductionEditText.text.toString(), tagList, newGroupDoc.id)
                newGroupDoc.set(group)
                text = "作成完了"
                Toast.makeText(applicationContext, text, duration).show()
                finish()
            }
            else {
                text = "グループ名と紹介文を埋めてください"
                Toast.makeText(applicationContext, text, duration).show()
            }
        }
    }
}