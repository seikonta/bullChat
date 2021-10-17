package app.dev.kon.bullchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_create_group.*
import kotlinx.android.synthetic.main.activity_gorup.*

class GroupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gorup)

        GroupBottomNavigationView.setOnNavigationItemSelectedListener {
            true
        }

        val boardFragment = BoardFragment()
        val chatFragment = ChatFragment()

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")


        if (name != null) {
            GroupBoardText.text = name
        }

        val listner = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_board -> {
                    replaceFragment(boardFragment)
                    true
                }
                R.id.nav_chat -> {
                    replaceFragment(chatFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }

        GroupBottomNavigationView.setOnNavigationItemSelectedListener(listner)

        BackFromGroupFloatingActionButton.setOnClickListener {
            finish()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.groupFragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}