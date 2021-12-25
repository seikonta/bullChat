package app.dev.kon.bullchat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class AccountFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpButton = view.findViewById<Button>(R.id.GoSignUpButton)
        val logInButton = view.findViewById<Button>(R.id.GoLogInButton)


        val intent = Intent(activity, AccountActivity::class.java)

        signUpButton.setOnClickListener {
            intent.putExtra("type", "sign up")
            startActivity(intent)
        }

        logInButton.setOnClickListener {
            intent.putExtra("type", "log in")
            startActivity(intent)
        }
    }
}