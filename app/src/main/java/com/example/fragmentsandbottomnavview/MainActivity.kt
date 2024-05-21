package com.example.fragmentsandbottomnavview

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.fragmentsandbottomnavview.databinding.ActivityMainBinding
import com.example.fragmentsandbottomnavview.fragments.LoginFragment
import com.example.fragmentsandbottomnavview.fragments.RegisterFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation = binding.navView
        navigation.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.commit {
            replace<LoginFragment>(R.id.fragmentContainer)
            addToBackStack("replacement")
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.itemLoginFragment -> {
                supportFragmentManager.commit {
                    replace<LoginFragment>(R.id.fragmentContainer)
                    addToBackStack("replacement")
                }
                true
            }


            R.id.itemRegisterFragment -> {
                supportFragmentManager.commit {
                    replace<RegisterFragment>(R.id.fragmentContainer)
                    addToBackStack("replacement")
                }
                true
            }

            else -> {
                false
            }
        }
    }



}

