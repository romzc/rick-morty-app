package com.romzc.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.romzc.app.databinding.ActivityMainBinding
import com.romzc.app.viewmodel.CharactersViewModel

class MainActivity : ActivityCallback,  AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        pushBackFragment(HomeFragment())
    }

    private fun pushBackFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fcvHome.id, fragment)
        transaction.setReorderingAllowed(true)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onLaunchFragmentFromFragment(sender: String, msg: Int) {
        if(sender == HomeFragment.CHANGE_TO_DETAIL) {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putInt(DetailFragment.ARG_USER_ID, msg)
            fragment.arguments = bundle
            pushBackFragment(fragment)
        }
    }
}