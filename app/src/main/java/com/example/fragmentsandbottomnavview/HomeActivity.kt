package com.example.fragmentsandbottomnavview

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.fragmentsandbottomnavview.databinding.ActivityHomeBinding
import com.example.fragmentsandbottomnavview.fragments.FirstFragment
import com.example.fragmentsandbottomnavview.fragments.PlazoFijoFragment
import com.example.fragmentsandbottomnavview.fragments.RegisterFragment
import com.example.fragmentsandbottomnavview.fragments.SecondFragment
import com.example.fragmentsandbottomnavview.fragments.ThirdFragment
import com.example.fragmentsandbottomnavview.fragments.TransFragment
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //seteo del componente toolbar que desplega el menu drawable
        val toolbar = binding.toolBarHome
        setSupportActionBar(toolbar)

        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_menu)

        val navigationView = binding.navViewHome
        navigationView.setNavigationItemSelectedListener(this)

        //renderizar automaticamente el HomeFragment cuando se navega a Home
        if (savedInstanceState == null){
            replaceFragment(FirstFragment())
            // indicar a la opcion del menu drawer que ya nos encontramos en ese fragment
            navigationView.setCheckedItem(R.id.nav_item_1)
        }

        /*binding.btnLogout.setOnClickListener{
            val preferencias = getSharedPreferences(RegisterFragment.CREDENTIALS, AppCompatActivity.MODE_PRIVATE)
            val edit = preferencias.edit()
            edit.putBoolean("autoLogin", false)
            edit.apply()
            finish()
        }*/
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_item_1 -> {
                Toast.makeText(this, "Home", Toast.LENGTH_LONG).show()
                replaceFragment(FirstFragment())
            }
            R.id.nav_item_2 -> {
                Toast.makeText(this, "Depositar", Toast.LENGTH_LONG).show()
                replaceFragment(SecondFragment())
            }
            R.id.nav_item_3 -> {
                Toast.makeText(this, "Retirar", Toast.LENGTH_LONG).show()
                replaceFragment(ThirdFragment())
            }

            R.id.nav_item_4 -> {
                Toast.makeText(this, "Transferir", Toast.LENGTH_LONG).show()
                replaceFragment(TransFragment())
            }

            R.id.nav_item_5 -> {
                Toast.makeText(this, "Plazo Fijo", Toast.LENGTH_LONG).show()
                replaceFragment(PlazoFijoFragment())
            }

            R.id.nav_item_6 -> {
                Toast.makeText(this, "Cerrando Sesion", Toast.LENGTH_LONG).show()
                val preferencias = getSharedPreferences(RegisterFragment.CREDENTIALS, AppCompatActivity.MODE_PRIVATE)
                val edit = preferencias.edit()
                edit.putBoolean("autoLogin", false)
                edit.apply()
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed(){
        //super.onBackPressed()
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // reemplazar la view fragmentContainer por el fragment correspondiente
    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerHome, fragment)
        transaction.commit()
    }
}