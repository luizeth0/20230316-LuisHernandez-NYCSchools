package com.challenge.nycs_challengejpmc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.challenge.nycs_challengejpmc.databinding.ActivityMainBinding
import com.challenge.nycs_challengejpmc.databinding.FragmentSchoolsBinding
import com.challenge.nycs_challengejpmc.view.SchoolAdapter
import com.challenge.nycs_challengejpmc.view.SchoolsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Initialize our binding UI
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Setup the container fragment for the navigation
        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        setupActionBarWithNavController(navHost.navController)
    }
    // Setup the arrow to return main fragment
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}