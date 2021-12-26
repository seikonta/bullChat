package app.dev.kon.bullchat

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_logined.*

class LoginedFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logined, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        var user = auth.currentUser
        var uid = user!!.uid

        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val userName = document.getField<String>("name").toString()
                UserNameTextInputEditText.setText(userName)
            } else {
                Log.d("firestore", "no such a user")
            }
        }

        UserAccountImageView.setImageURI(Uri.parse(user!!.photoUrl.toString()))

        SaveChangesButton.setOnClickListener {
            docRef.set(hashMapOf("name" to UserNameTextInputEditText.text.toString()))
                .addOnSuccessListener {
                    Log.d("firestore", "change successfully")
                    Toast.makeText(requireContext(), "変更を保存しました", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Log.d("firestore", "cannot change")
                }
        }

        SignOutButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("サインアウトしますか")
                .setMessage("サインアウトすると再度ログインしないとチャット機能などが使えなくなります。")
                .setNegativeButton("キャンセル") { dialog, which ->
                    //なにもしない
                }
                .setPositiveButton("サインアウトする") { dialog, which ->
                    auth.signOut()
                    Toast.makeText(requireContext(), "サインアウトしました", Toast.LENGTH_SHORT).show()
                    replaceFragment(AccountFragment())
                }
                .show()
        }
    }

    private fun replaceFragment(fragemnt: Fragment) {
        val fragmentMangaer = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentMangaer.beginTransaction()
        fragmentTransaction.replace(R.id.groupFragmentContainerView, fragemnt)
        fragmentTransaction.commit()
    }
}