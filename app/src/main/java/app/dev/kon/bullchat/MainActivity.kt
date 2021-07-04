package app.dev.kon.bullchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_group_list.*

class MainActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HomeBottomNavigationView.setOnNavigationItemSelectedListener {
            true
        }

        val homeFragment = GroupListFragment()
        val searchFragment = SearchFragment()

        val listner = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(homeFragment)
                    true
                }
                R.id.nav_search -> {
                    replaceFragment(searchFragment)
                    true
                }
                else -> false
            }
        }

        HomeBottomNavigationView.setOnNavigationItemSelectedListener(listner)

    }

    fun replaceFragment(fragemnt: Fragment) {
        val fragmentMangaer = supportFragmentManager
        val fragmentTransaction = fragmentMangaer.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragemnt)
        fragmentTransaction.commit()
    }
}