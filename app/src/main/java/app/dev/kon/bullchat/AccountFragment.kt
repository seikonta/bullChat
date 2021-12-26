package app.dev.kon.bullchat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountFragment: Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

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

    private fun replaceFragment(fragemnt: Fragment) {
        val fragmentMangaer = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentMangaer.beginTransaction()
        fragmentTransaction.replace(R.id.groupFragmentContainerView, fragemnt)
        fragmentTransaction.commit()
    }
}