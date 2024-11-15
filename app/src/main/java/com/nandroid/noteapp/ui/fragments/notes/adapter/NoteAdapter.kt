package com.nandroid.noteapp.ui.fragments.notes.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.nandroid.noteapp.R
import com.nandroid.noteapp.data.models.Notes
import com.nandroid.noteapp.ui.activity.ViewModel
import com.nandroid.noteapp.ui.fragments.notes.DetailNoteFragment

@SuppressLint("NotifyDataSetChanged")
class NoteAdapter(
    private val notes: List<Notes>,
    val viewModel: ViewModel,
    val supportFragmentManager: FragmentManager,
    val currentFragment: Fragment
) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val view = View.inflate(parent.context, R.layout.note_view, null)
        return NoteViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.noteHeading.text = notes[position].heading
        holder.noteContent.text = openFewWords(notes[position].content)
        holder.deleteBtn.setOnClickListener {
            //todo delete note
            viewModel.deleteNote(notes[position].noteId)
        }
        holder.dateView.text = notes[position].dateOfCreation
        holder.parentNoteView.setOnClickListener {
            //todo open note
            openNoteDetailFragment(notes[position])
        }
    }

    private fun openNoteDetailFragment(notes: Notes) {
        val noteDetailFragment = DetailNoteFragment()
        val bundle = Bundle()
        bundle.putParcelable("note",notes)
        bundle.putInt("id", notes.noteId)
        noteDetailFragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        transaction
            .add(R.id.mainFragment, noteDetailFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openFewWords(string: String): String {
        return string.substring(0, string.length % 40).plus("...")
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NoteViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val noteHeading = item.findViewById<TextView>(R.id.heading)
        val noteContent = item.findViewById<TextView>(R.id.content)
        val deleteBtn = item.findViewById<ImageButton>(R.id.deleteBtn)
        val dateView = item.findViewById<TextView>(R.id.dateView)
        val parentNoteView = item.findViewById<ViewGroup>(R.id.parentNoteView)
    }
}