package com.d3if3021.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NoteAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        val context = holder.itemView.context
        holder.tvMood.text = context.getString(R.string.note_mood, note.mood)
        holder.tvActivity.text = context.getString(R.string.note_activity, note.activity)
        holder.tvFood.text = context.getString(R.string.note_food, note.food)
        holder.tvNote.text = context.getString(R.string.note_note, note.note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMood: TextView
        var tvActivity: TextView
        var tvFood: TextView
        var tvNote: TextView

        init {
            tvMood = itemView.findViewById(R.id.tv_mood)
            tvActivity = itemView.findViewById(R.id.tv_activity)
            tvFood = itemView.findViewById(R.id.tv_food)
            tvNote = itemView.findViewById(R.id.tv_note)
        }
    }
}

