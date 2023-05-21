package com.d3if3021.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.d3if3021.myapplication.data.SettingDataStore

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var settingDataStore: SettingDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        settingDataStore = SettingDataStore(this)

        navController = findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }
override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}