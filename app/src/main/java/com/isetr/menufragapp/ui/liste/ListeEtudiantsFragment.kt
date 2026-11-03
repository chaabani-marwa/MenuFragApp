package com.isetr.menufragapp.ui.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isetr.menufragapp.R
import com.isetr.menufragapp.databinding.FragmentListeEtudiantsBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel

class ListeEtudiantsFragment : Fragment() {
    private var _binding: FragmentListeEtudiantsBinding? = null
    private val binding get() = _binding!!
    private lateinit var etudiantAdapter: EtudiantAdapter
    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListeEtudiantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Initialiser le RecyclerView et son Adapter
        fillRecyclerView()

        // 2. Observer les modifications de la liste d'étudiants
        etudiantViewModel.etudiants.observe(viewLifecycleOwner) { nouvelleListe ->
           // etudiantAdapter.submitList(nouvelleListe)
            etudiantAdapter.updateList(nouvelleListe)
        }
    }

    private fun fillRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerViewEtudiants
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        etudiantAdapter = EtudiantAdapter(
            etudiants = mutableListOf(),
            onItemClicked = { etudiant ->
                Toast.makeText(
                    requireContext(),
                    "Étudiant sélectionné : ${etudiant.email}",
                    Toast.LENGTH_SHORT
                ).show()

                // Mettre à jour l'étudiant sélectionné dans le ViewModel
                etudiantViewModel.selectionnerEtudiant(etudiant)

                // Naviguer vers le Fragment Détail
                findNavController().navigate(R.id.action_nav_liste_etudiants_to_nav_detail_etudiant)
            }
        )
        recyclerView.adapter = etudiantAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
