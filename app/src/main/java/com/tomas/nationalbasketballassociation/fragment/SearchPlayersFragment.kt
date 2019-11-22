package com.tomas.nationalbasketballassociation.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.tomas.nationalbasketballassociation.MainActivity
import com.tomas.nationalbasketballassociation.R
import kotlinx.android.synthetic.main.fragment_search_players.*

class SearchPlayersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_players, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setting up search in toolbar
        (activity as? MainActivity)?.setSupportActionBar(searchPlayersToolBar)
        (activity as? MainActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHasOptionsMenu(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
    }
}