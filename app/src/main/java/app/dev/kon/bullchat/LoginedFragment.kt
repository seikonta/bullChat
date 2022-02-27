package app.dev.kon.bullchat

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.media.MediaBrowserServiceCompat.RESULT_OK
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_logined.*
import java.lang.Exception
import kotlin.math.log

class LoginedFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    lateinit var storage: FirebaseStorage
    lateinit var storageRef: StorageReference
    lateinit var iconFolderRef: StorageReference
    lateinit var iconRef: StorageReference


    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }

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
        storage = Firebase.storage

        var user = auth.currentUser
        var uid = user!!.uid

        storageRef = storage.reference
        iconFolderRef = storageRef.child("icons")
        iconRef = iconFolderRef.child(uid + ".png")

        val docRef = db.collection("users").document(uid)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val userName = document.getField<String>("name").toString()
                UserNameTextInputEditText.setText(userName)
            } else {
                Log.d("firestore", "no such a user")
            }
        }

        val ONE_MEAGABYTE: Long = 1024*1024

        iconRef.getBytes(ONE_MEAGABYTE).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            UserAccountImageView.setImageBitmap(bitmap)
        }

//        UserAccountImageView.setImageURI(Uri.parse(user!!.photoUrl.toString()))

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
        
        UserAccountImageView.setOnClickListener { 
            selectPhoto()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (resultCode != RESULT_OK) {
//            return
//        }
        when (requestCode) {
            READ_REQUEST_CODE -> {
                try {
                    data?.data?.also { uri ->
                        val inputStream = activity?.contentResolver?.openInputStream(uri)
                        val imageBitmap = BitmapFactory.decodeStream(inputStream)

                        if (uri != null) {
                            iconRef.putFile(uri).addOnSuccessListener {
                                Toast.makeText(requireContext(), "アップロードに成功しました。", Toast.LENGTH_SHORT).show()
                                UserAccountImageView.setImageBitmap(imageBitmap)
                            }.addOnFailureListener {
                                Toast.makeText(requireContext(), "アップロードに失敗しました。", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else {
                            Toast.makeText(requireContext(), "画像の取得に失敗しました。", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                catch(e: Exception) {
                    Toast.makeText(requireContext(), "アップロードに失敗しました。", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun replaceFragment(fragemnt: Fragment) {
        val fragmentMangaer = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentMangaer.beginTransaction()
        fragmentTransaction.replace(R.id.groupFragmentContainerView, fragemnt)
        fragmentTransaction.commit()
    }

    // 画像を取得して画像データを返す

//    @SuppressLint("RestrictedApi")
//    private val launcher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode != RESULT_OK) {
//            return@registerForActivityResult
//        }
//        else {
//            try {
//                result.data?.data?.also { uri: Uri ->
//                    val inputStream = contentResolver
//                }
//            }
//            catch (e: Exception) {}
//        }
//    }

    fun selectPhoto() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, READ_REQUEST_CODE)


    }

    // 画像データを元にそれをCloud Strageにアップロードする
    fun uploadPhot(image: ByteArray) {

    }
}