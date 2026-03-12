package com.infocal.nav

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.infocal.nav.databinding.MainActivityBinding
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= MainActivityBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setupNavHost()
        setContentView(binding.root)
    }

    //configurar el nav host
    private fun setupNavHost() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        //todo add funtions search
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actions_search->{
                //todo actions button
                true
            }
            R.id.actions_settings->{
                //implementar toast toast
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

