package com.mvvm.ui.home.task4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.mvvm.R
import com.mvvm.base.BaseFragment
import com.mvvm.databinding.FragmentFourBinding
import com.mvvm.global.helper.Navigation
import com.mvvm.global.helper.ViewModelFactory
import com.mvvm.ui.home.news.NewsActivity
import javax.inject.Inject


class FourFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: FourViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_four, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FourViewModel::class.java)

        // Register the UI for XMLBinding
        val bind = FragmentFourBinding.bind(view)
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
        registerFourObservers()
    }


    /**
     * register UI Four Observers
     */
    private fun registerFourObservers() {
        //TODO
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigation: Navigation) {
        when (navigation.navigateTo) {
            NewsActivity::class -> {
                val actionTask4ToNews = FourFragmentDirections.actionTask4ToNews(navigation.extra[0] as String)
                findNavController()?.navigate(actionTask4ToNews)
            }
        }
    }
}





