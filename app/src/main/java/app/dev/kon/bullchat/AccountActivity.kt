package app.dev.kon.bullchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val type = intent.getStringExtra("type")

        BackFromAccountButton.setOnClickListener {
            finish()
        }

        if (type == "sign up") {
            accountFunction(true)
        }
        else {
            accountFunction(false)
        }
    }

    private fun accountFunction(forSignUp: Boolean) {
        if (forSignUp) {
            AccountText.text = "サインアップ"
            AccountButton.text = "SIGN UP"
        }
        else {
            AccountText.text = "サインイン"
            AccountButton.text = "SIGN IN"
        }
    }

}