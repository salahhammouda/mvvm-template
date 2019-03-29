package com.mvvm.global.utils

object ExtraKeys {

    class HomeActivity {
        companion object {
            const val HOME_EXTRA_USER_KEY: String = "home_extra_user"
            const val HOME_INJECT_USER_KEY = "home_inject_user"
        }
    }

    class OneFragment() {
        companion object {
            const val ONE_INJECT_USER_KEY = "one_fragment_inject_user"
        }
    }


    class ThreeFragment() {
        companion object {
            const val THREE_EXTRA_ARG_KEY: String = "three_fragment_extra_arg"
            const val THREE_INJECT_ARG_KEY = "three_fragment_inject_arg"
        }
    }

    class FourFragment() {
        companion object {
            const val FOUR_INJECT_ARG1_KEY = "four_fragment_inject_arg1"
            const val FOUR_INJECT_ARG2_KEY = "four_fragment_inject_arg2"

        }
    }

    class NewsActivity() {
        companion object {
            const val NEWS_INJECT_ARG1_KEY = "news_inject_arg1"
        }
    }
}