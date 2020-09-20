package com.example.belajar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.belajar.R
import com.example.belajar.core.data.source.Resource
import com.example.belajar.core.ui.TourismAdapter
import com.example.belajar.core.ui.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_error.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){
            val tourismAdapter  = TourismAdapter()

            val factory = ViewModelFactory.getInstance(requireContext())
            homeViewModel =ViewModelProvider(this, factory)[HomeViewModel::class.java]

            homeViewModel.tourism.observe(viewLifecycleOwner, Observer { tourism->
                if(tourism != null){
                    when(tourism){
                        is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                        is Resource.Success ->{
                            progress_bar.visibility = View.INVISIBLE
                            tourismAdapter.setData(tourism.data)
                        }
                        is Resource.Error->{
                            progress_bar.visibility = View.GONE
                            view_error.visibility = View.VISIBLE
                            tv_error.text = tourism.message ?:"Ops Something wnet wrong"
                        }
                    }
                }
            })
            with(rv_tourism){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tourismAdapter
            }
        }
    }
}