package com.faridwaid.praytimeislamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mendefinisikan NavController yang nantinya akan digunakan untuk control fragment
        val navController: NavController = findNavController(R.id.nav_host_fragment)

        // setup smooth bar menu
        val popUpMenu =  PopupMenu(this, null)
        popUpMenu.inflate(R.menu.bottom_nav_menu)
        val menu = popUpMenu.menu
        val navBottom: SmoothBottomBar = findViewById(R.id.nav_bottom)
        navBottom.setupWithNavController(menu, navController)

    }
}