package app.dev.kon.bullchat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup

class GroupListViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val nameTextView: TextView = view.findViewById(R.id.GroupDataCellGroupTitleTextView)
    val tagChipGroup: ChipGroup = view.findViewById(R.id.GroupTagChipGroup)
}