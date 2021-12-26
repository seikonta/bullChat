package app.dev.kon.bullchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        HomeBottomNavigationView.setOnNavigationItemSelectedListener {
            true
        }

        val homeFragment = GroupListFragment()
        val searchFragment = SearchFragment()
        val accountFragment = AccountFragment()
        val loginFragment = LoginedFragment()

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
                R.id.nav_account -> {
                    val user = auth.currentUser
                    if (user == null) {
                        replaceFragment(accountFragment)
                    }
                    else {
                        replaceFragment(loginFragment)
                    }
                    true
                }
                else -> false
            }
        }

        HomeBottomNavigationView.setOnNavigationItemSelectedListener(listner)

    }

    private fun replaceFragment(fragemnt: Fragment) {
        val fragmentMangaer = supportFragmentManager
        val fragmentTransaction = fragmentMangaer.beginTransaction()
        fragmentTransaction.replace(R.id.groupFragmentContainerView, fragemnt)
        fragmentTransaction.commit()
    }
}