package app.dev.kon.bullchat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_create_group.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        contaier: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        return inflater.inflate(R.layout.fragment_search, contaier, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SearchAddButton.setOnClickListener {
            var intent = Intent(activity, CreateGroupActivity::class.java)
            startActivity(intent)
        }
    }
}