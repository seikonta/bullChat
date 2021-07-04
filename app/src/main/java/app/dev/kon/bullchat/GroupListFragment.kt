package app.dev.kon.bullchat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_group_list.*

class GroupListFragment : Fragment() {

    val groups = mutableListOf<Group>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        groups.addAll(
//            listOf<Group>(
//                Group("クラシック同好会", listOf("クラシック", "音楽")),
//                Group("競プロ同好会", listOf("プログラミング", "競プロ", "競技プログラミング", "コンピュータ")),
//                Group("コンピュータ部", listOf("コンピュータ", "プログラミング")),
//                Group("クイズ研究会", listOf("早押し", "クイズ", "早押しクイズ", "同好会"))
//            )
//        )

        val adapter = GroupListAdapter(groups, requireActivity())

        HomeRecyclerView.adapter = adapter
        HomeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

}