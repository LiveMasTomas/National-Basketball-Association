package com.tomas.nationalbasketballassociation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tomas.nationalbasketballassociation.R
import com.tomas.nationalbasketballassociation.adapter.PlayerPagedListAdapter
import com.tomas.nationalbasketballassociation.interfaces.PlayerListListener
import com.tomas.nationalbasketballassociation.network.PlayerViewState
import com.tomas.nationalbasketballassociation.view.PlayerListView.State.*
import com.tomas.nationalbasketballassociation.viewmodel.AllPlayersViewModel
import kotlinx.android.synthetic.main.fragment_all_players.*
import org.koin.android.viewmodel.ext.android.viewModel

class AllPlayersFragment : Fragment() {

    private val allPlayersViewModel: AllPlayersViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_all_players, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = object : PlayerListListener {
            //these will work for now, will make better later
            override fun onRefreshRequested() {
                allPlayersViewModel.invalidateData()
            }

            override fun onRetryRequested() {
                allPlayersViewModel.invalidateData()
            }
        }

        allPlayersFab.setOnClickListener {
            findNavController().navigate(R.id.searchPlayersFragment)
        }

        allPlayersListView.setPlayerListAdapter(PlayerPagedListAdapter(), listener)

        allPlayersViewModel.nbaPlayerResults.observe(viewLifecycleOwner, Observer {
            when (it) {
                is PlayerViewState.Loading -> {
                    allPlayersListView.state = LOADING
                }
                is PlayerViewState.Loaded -> {
                    allPlayersListView.state = LOAD_WITH_DATA
                }
                is PlayerViewState.Players -> {
                    allPlayersListView.setData(it.players)
                }
                is PlayerViewState.Empty -> {
                    allPlayersListView.state = LOAD_EMPTY
                }
                is PlayerViewState.Error -> {
                    allPlayersListView.state = ERROR
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        allPlayersViewModel.invalidateData()
    }
}