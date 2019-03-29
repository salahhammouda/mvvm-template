package com.mvvm.ui.home.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.R
import com.mvvm.base.BaseFragment
import com.mvvm.databinding.FragmentTwoBinding
import com.mvvm.global.helper.Navigation
import com.mvvm.global.helper.ViewModelFactory
import com.mvvm.ui.home.task4.FourFragment
import kotlinx.android.synthetic.main.fragment_two.*
import javax.inject.Inject


class TwoFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var newsAdapter: NewsAdapter

    private lateinit var viewModel: TwoViewModel


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_two, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TwoViewModel::class.java)

        // Register the UI for XMLBinding
        val bind = FragmentTwoBinding.bind(view)
        bind.viewModel = viewModel
        bind.lifecycleOwner = viewLifecycleOwner

        return view
    }


    /**
     * Register the fragment for base observer
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerBaseObserver(viewModel)
        registerTwoObservers()
    }


    /**
     * register UI Two Observers
     */
    private fun registerTwoObservers() {
        registerRecycler()
    }

    private fun registerRecycler() {
        newsAdapter.twoViewModel = viewModel
        recyclerTwoContent.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerTwoContent.adapter = newsAdapter
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigation: Navigation) {
        when (navigation.navigateTo) {
            FourFragment::class -> {
                val actionTask2ToTask4 = TwoFragmentDirections.ActionTask2ToTask4(
                        navigation.extra[0] as String,
                        navigation.extra[1] as String
                )
                findNavController()?.navigate(actionTask2ToTask4)
            }
        }
    }
}





