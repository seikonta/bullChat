package app.dev.kon.bullchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val groups = mutableListOf<Group>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        groups.addAll(
                listOf<Group>(
                        Group("クラシック同好会", listOf("クラシック", "音楽")),
                        Group("競プロ同好会", listOf("プログラミング", "競プロ", "競技プログラミング", "コンピュータ")),
                        Group("コンピュータ部", listOf("コンピュータ", "プログラミング")),
                        Group("クイズ研究会", listOf("早押し", "クイズ", "早押しクイズ", "同好会"))
                )
        )

        val adapter = GroupListAdapter(groups, this)

        HomeRecyclerView.adapter = adapter
        HomeRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}