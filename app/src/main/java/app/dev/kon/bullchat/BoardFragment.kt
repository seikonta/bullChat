package app.dev.kon.bullchat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_board.*

class BoardFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val groupName = activity?.intent?.getStringExtra("name")
//        if (groupName != null) {
//            GroupBoardText.text = groupName
//        }

        MakePostingButton.setOnClickListener {
            val intent = Intent(activity, CreatePostActivity::class.java)
            intent.putExtra("id", activity?.intent?.getStringExtra("id"))
            startActivity(intent)
        }
    }
}