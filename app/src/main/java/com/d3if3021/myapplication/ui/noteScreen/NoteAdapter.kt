package com.d3if3021.myapplication.ui.noteScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.d3if3021.myapplication.R
import com.d3if3021.myapplication.db.NoteEntity

class NoteAdapter(
    private val noteViewModel: NoteViewModel,
    private val fragment: Fragment
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes: List<NoteEntity> = emptyList()

    // Function to set data on the adapter
    fun setData(notes: List<NoteEntity>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        val context = holder.itemView.context
        holder.tvMood.text = context.getString(R.string.note_mood, note.mood)
        holder.tvActivity.text = context.getString(R.string.note_activity, note.activity)
        holder.tvFood.text = context.getString(R.string.note_food, note.food)
        holder.tvNote.text = context.getString(R.string.note_note, note.note)
        holder.itemView.setOnLongClickListener {
            showDeleteConfirmationDialog(note)
            true
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMood: TextView = itemView.findViewById(R.id.tv_mood)
        val tvActivity: TextView = itemView.findViewById(R.id.tv_activity)
        val tvFood: TextView = itemView.findViewById(R.id.tv_food)
        val tvNote: TextView = itemView.findViewById(R.id.tv_note)
    }

    private fun showDeleteConfirmationDialog(note: NoteEntity) {
        val alertDialogBuilder = AlertDialog.Builder(fragment.requireContext())

        alertDialogBuilder.apply {
            setTitle("Konfirmasi Penghapusan")
            setMessage("Apakah Anda yakin ingin menghapus catatan ini?")
            setPositiveButton("Ya") { dialog, _ ->
                noteViewModel.delete(note.id)
                dialog.dismiss()
            }
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}




