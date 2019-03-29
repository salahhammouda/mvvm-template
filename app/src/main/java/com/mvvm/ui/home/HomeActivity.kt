package com.mvvm.ui.home

import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.mvvm.R
import com.mvvm.base.BaseActivity
import com.mvvm.data.model.user.User
import com.mvvm.databinding.ActivityHomeBinding
import com.mvvm.global.helper.ViewModelFactory
import com.mvvm.global.utils.ExtraKeys
import com.mvvm.ui.home.task1.OneFragment
import com.mvvm.ui.home.task2.TwoFragment
import com.mvvm.ui.home.task3.ThreeFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject
import kotlin.reflect.KClass


class HomeActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    private lateinit var navController: NavController

    private var currentFragment: KClass<out Any>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)


        navController = Navigation.findNavController(this, R.id.navHostFragment)
        navController.setGraph(R.navigation.nav_graph, intent.extras)

        bottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected)

        registerBindingAndBaseObservers(binding)
        registerHomeObservers()
    }


    /**
     * register UI Home activity Observers
     */
    private fun registerHomeObservers() {
        //TODO
    }


    private fun onNavigationItemSelected(menu: MenuItem): Boolean {
        when (menu.itemId) {
            R.id.task1 -> viewModel.onActionOneClicked()
            R.id.task2 -> viewModel.onActionTwoClicked()
            R.id.task3 -> viewModel.onActionThreeClicked()
        }
        return true
    }


    /**
     * handling bottom navigation listener
     */
    override fun navigate(navigation: com.mvvm.global.helper.Navigation) {
        when (navigation.navigateTo) {
            OneFragment::class -> {
                if (currentFragment?.equals(OneFragment::class) == true) return
                currentFragment = OneFragment::class
                val navigation1 = NavOptions.Builder().setPopUpTo(R.id.task1, true).setLaunchSingleTop(true).build()
                val b = Bundle()
                b.putParcelable(ExtraKeys.HomeActivity.HOME_EXTRA_USER_KEY, navigation.extra[0] as User)
                navController.navigate(R.id.task1, b, navigation1)
            }

            TwoFragment::class -> {
                if (currentFragment?.equals(TwoFragment::class) == true) return
                currentFragment = TwoFragment::class
                val navigation2 = NavOptions.Builder().setPopUpTo(R.id.task1, true).setLaunchSingleTop(true).build()
                navController.navigate(R.id.task2, null, navigation2)

            }

            ThreeFragment::class -> {
                if (currentFragment?.equals(ThreeFragment::class) == true) return
                currentFragment = ThreeFragment::class
                val navigation3 = NavOptions.Builder().setPopUpTo(R.id.task1, true).setLaunchSingleTop(false).build()
                val bundleOf3 =
                    bundleOf(Pair(ExtraKeys.ThreeFragment.THREE_EXTRA_ARG_KEY, navigation.extra[0] as String))
                navController.navigate(R.id.task3, bundleOf3, navigation3)
            }
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityHomeBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }


    /**
     * Required for SupportNavigateUp
     */
    override fun onSupportNavigateUp() = Navigation
        .findNavController(this, R.id.navHostFragment).navigateUp()


    /**
     * Required for fragment jnjection
     */
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}
