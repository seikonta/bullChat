package app.dev.kon.bullchat

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.contains
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment() {

    val db = FirebaseFirestore.getInstance()
    val groups = mutableListOf<Group>()

    val itemClickListner = object : GroupListAdapter.OnItemClickListener {
        override fun onItemClick(holder: GroupListViewHolder) {
            val group_name = groups[holder.adapterPosition].name
            val group_intro = groups[holder.adapterPosition].introduction
            val group_id = groups[holder.adapterPosition].id

            var intent = Intent(activity, JoinGroupActivity::class.java)
            intent.putExtra("name", group_name)
            intent.putExtra("introduction", group_intro)
            intent.putExtra("id", group_id)
            startActivity(intent)
        }
    }

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

        SearchButton.setOnClickListener {
            groups.clear()

            val query = db.collection("groups").whereArrayContains("tags", SearchInputEditText.text.toString())
            var countGroup = 0

            query
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        countGroup += 1
                        Log.d(TAG, "${document.id} => ${document.data}")
                        var text = document["tags"].toString()
                        groups.add(Group(document["name"].toString(), document["introduction"].toString(), text.substring(1, text.length-1).split(", "), document.id))
                    }
                    val adapter = GroupListAdapter(groups, requireActivity())
                    adapter.itemClickListener = itemClickListner
                    SearchRecyclerView.adapter = adapter

                    val duration = Toast.LENGTH_SHORT
                    Toast.makeText(context, countGroup.toString()+"個のグループが見つかりました", duration).show()
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }

        }

        val adapter = GroupListAdapter(groups, requireActivity())

        SearchRecyclerView.adapter = adapter
        SearchRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        adapter.itemClickListener = itemClickListner
    }
}