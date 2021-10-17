package app.dev.kon.bullchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val id = intent.getStringExtra("id")

        BackFromPostButton.setOnClickListener {
            finish()
        }
    }
}