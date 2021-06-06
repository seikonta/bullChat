package app.dev.kon.bullchat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip

class GroupListAdapter(val groupList: List<Group>, val context: Context) : RecyclerView.Adapter<GroupListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_group_data_cell, parent, false)
        return GroupListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
        val group = groupList[position]
        holder.nameTextView.text = group.name

        for (tag in group.tags) {
            val chipView: Chip? = LayoutInflater.from(context).inflate(R.layout.item_group_tag_data_cell, null, false) as? Chip

            if (chipView != null) {
                chipView.isCloseIconVisible = false //クローズボタンを非表示
                chipView.isCheckedIconVisible = false //Checkアイコンを非表示
                chipView.text = tag
                holder.tagChipGroup.addView(chipView)
            }
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }
}