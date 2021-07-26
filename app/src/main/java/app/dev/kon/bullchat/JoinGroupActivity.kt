package app.dev.kon.bullchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        BackButton.setOnClickListener {
            finish()
        }
    }
}