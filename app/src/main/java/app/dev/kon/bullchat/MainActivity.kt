package app.dev.kon.bullchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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

    private fun replaceFragment(fragemnt: Fragment) {
        val fragmentMangaer = supportFragmentManager
        val fragmentTransaction = fragmentMangaer.beginTransaction()
        fragmentTransaction.replace(R.id.groupFragmentContainerView, fragemnt)
        fragmentTransaction.commit()
    }
}