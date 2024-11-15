package com.nandroid.noteapp.ui.fragments.notes

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandroid.noteapp.R
import com.nandroid.noteapp.databinding.FragmentHomeBinding
import com.nandroid.noteapp.ui.activity.ViewModel
import com.nandroid.noteapp.ui.fragments.notes.adapter.NoteAdapter

class HomeFragment : Fragment() {
    private val viewModel: ViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.addNote.setOnClickListener {
            val detailNoteFragment = DetailNoteFragment()
            detailNoteFragment.arguments = null
            val currentFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.mainFragment)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            if (currentFragment != null) {
                transaction.hide(currentFragment)
            }
            transaction.add(R.id.mainFragment, detailNoteFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // will fetch all notes from database
        viewModel.fetchNotes()
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            if (notes.isEmpty()) {
                binding.noNote.visibility = View.VISIBLE
            } else {
                binding.noNote.visibility = View.GONE
            }
            val adapter = NoteAdapter(
                notes,
                viewModel,
                requireActivity().supportFragmentManager,
                requireActivity().supportFragmentManager.findFragmentById(R.id.mainFragment)!!
            )
            recyclerView.adapter = adapter
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}