package com.mvvm.ui.home.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.mvvm.R
import com.mvvm.base.BaseFragment
import com.mvvm.databinding.FragmentOneBinding
import com.mvvm.global.helper.Navigation
import com.mvvm.global.helper.ViewModelFactory
import com.mvvm.ui.home.task4.FourFragment
import javax.inject.Inject


class OneFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: OneViewModel


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_one, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OneViewModel::class.java)

        // Register the UI for XMLBinding
        val bind = FragmentOneBinding.bind(view)
        bind.viewModel = viewModel
        bind.lifecycleOwner = viewLifecycleOwner

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerBaseObserver(viewModel)
        registerOneObservers()
    }


    /**
     * register UI One Observers
     */
    private fun registerOneObservers() {
        //TODO
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigation: Navigation) {
        when (navigation.navigateTo) {
            FourFragment::class -> {
                val actionTask1ToTask11 = OneFragmentDirections.actionTask1ToTask4(navigation.extra[0] as String)
                findNavController()?.navigate(actionTask1ToTask11)
            }
        }
    }
}





