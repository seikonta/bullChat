package app.dev.kon.bullchat

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val type = intent.getStringExtra("type")
        auth = Firebase.auth

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

        AccountButton.setOnClickListener {
            val emailAddressText = EmailTextInputEditText.text.toString()
            val passwordText = PasswordEditText.text.toString()

            if (forSignUp) {
                if (emailAddressText != "" && passwordText != "") {
                    auth.createUserWithEmailAndPassword(emailAddressText, passwordText)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("auth", "create an account")
                                Log.d("auth", "sent email")

                                val user = auth.currentUser
                                Toast.makeText(this, "サインアップが完了しました", Toast.LENGTH_SHORT).show()
                                finish()

                            } else {
                                Log.e("auth", "ERROR: cannot create an account")
                                val alertText: String
                                if (passwordText.length < 6) {
                                    alertText = "パスワードは6文字以上にしてください。"
                                }
                                else {
                                    alertText = "アカウントを作成することができませんでした。"
                                }

                                MaterialAlertDialogBuilder(this)
                                    .setTitle("アカウント作成失敗")
                                    .setMessage(alertText)
                                    .show()
                            }
                        }
                } else {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("アカウント作成失敗")
                        .setMessage("メールアドレスとパスワードの両方が必要です。")
                        .show()
                }
            } else {
                auth.signInWithEmailAndPassword(emailAddressText, passwordText)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("auth", "sign in successful")
                            Toast.makeText(this, "ログインに成功しました", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Log.e("auth", "ERROR: Log in failed")
                            MaterialAlertDialogBuilder(this)
                                .setTitle("ログイン失敗")
                                .setMessage("メールアドレスとパスワードを確認してください")
                                .show()
                        }
                    }
            }
        }
    }

}