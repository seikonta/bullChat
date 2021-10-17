package app.dev.kon.bullchat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_group_list.*

class GroupListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val groups = mutableListOf<Group>()
        Log.d("groups", groups.toString())

        val itemClickListener = object : GroupListAdapter.OnItemClickListener {
            override fun onItemClick(holder: GroupListViewHolder) {
                val group_name = groups[holder.adapterPosition].name
                val group_intro = groups[holder.adapterPosition].introduction
                val group_id = groups[holder.adapterPosition].id

                var intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("name", group_name)
                intent.putExtra("introduction", group_intro)
                intent.putExtra("id", group_id)
                startActivity(intent)
            }
        }
        val db = FirebaseFirestore.getInstance()
        val idPref = activity?.getSharedPreferences("DataStore", Context.MODE_PRIVATE)?: return
        val idSet = idPref.getStringSet("join_groups", mutableSetOf())
        var adapter = GroupListAdapter(groups, requireActivity())
        Log.d("idSet", idSet.toString())
        if (idSet != null) {
            val collection = db.collection("groups")
            for (id in idSet) {
                Log.d("id", id)
                val doc = collection.document(id)
//                var group: Group
                doc.get().addOnSuccessListener { document ->
                    if (document != null) {
                        var text = document["tags"].toString()
//                        group = Group(document["name"].toString(), document["introduction"].toString(), text.substring(1, text.length-1).split(", "), document.id)
                        groups.add(Group(document["name"].toString(), document["introduction"].toString(), text.substring(1, text.length-1).split(", "), document.id))
                    }
                    Log.d("groups", groups.toString())
                    adapter = GroupListAdapter(groups, requireActivity())
                    HomeRecyclerView.adapter = adapter
                    HomeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

                    adapter.itemClickListener = itemClickListener
                }
            }
        }
//        groups.addAll(
//            listOf<Group>(
//                Group("クラシック同好会", "k",listOf("クラシック", "音楽")),
//                Group("競プロ同好会", "k",listOf("プログラミング", "競プロ", "競技プログラミング", "コンピュータ")),
//                Group("コンピュータ部", "k",listOf("コンピュータ", "プログラミング")),
//                Group("クイズ研究会", "k",listOf("早押し", "クイズ", "早押しクイズ", "同好会"))
//            )
//        )



        Log.d("groups", groups.toString())



        HomeRecyclerView.adapter = adapter
        HomeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        adapter.itemClickListener = itemClickListener
    }

}