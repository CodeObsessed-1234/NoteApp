package com.nandroid.noteapp.ui.fragments.notes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nandroid.noteapp.data.models.Notes
import com.nandroid.noteapp.databinding.FragmentDetailNoteBinding
import com.nandroid.noteapp.ui.activity.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.getValue

class DetailNoteFragment : Fragment() {
    private val viewModel: ViewModel by activityViewModels()

    private lateinit var binding: FragmentDetailNoteBinding
    private var bundle: Bundle? = null
    private var note: Notes? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailNoteBinding.inflate(inflater, container, false)
        bundle = arguments
        note = bundle?.getParcelable<Notes>("note")
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (bundle != null) {
            binding.noteHeading.setText(note!!.heading)
            binding.noteContentData.setText(note!!.content)
        }
        binding.saveNote.setOnClickListener {
            // for creating new note

            if (bundle != null) {
                note!!.heading = binding.noteHeading.text.toString()
                note!!.content = binding.noteContentData.text.toString()
                viewModel.updateNote(note!!)
                requireActivity().supportFragmentManager.popBackStack()
            } else if (binding.noteHeading.text.isNotEmpty() && binding.noteContentData.text.isNotEmpty()) {
                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                val currentDate = dateFormat.format(Date())
                val note = Notes(
                    0,
                    binding.noteHeading.text.toString(),
                    binding.noteContentData.text.toString(),
                    currentDate.toString()
                )
                viewModel.insertNote(note)
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

}