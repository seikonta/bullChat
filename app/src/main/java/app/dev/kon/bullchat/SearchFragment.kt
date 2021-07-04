package app.dev.kon.bullchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SearchFragment: Fragment() {

    override fun onCreate(
        inflater: LayoutInflater,
        contaier: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {
        return inflater.inflate(R.layout.activity_search, contaier, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}