package com.example.yoohtravel

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    private lateinit var auth : FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        auth =  Firebase.auth



        //Отображение фрагмент-окон через NavBar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fl_wrapper)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.favoriteFragment, R.id.profileFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
       bottomNavigationView.setupWithNavController(navController)


    }


        //ToolbarMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
        //Выходим из аккаунта
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.search_toolbar ->{
                Toast.makeText(this,"Поиск(в разработке)", Toast.LENGTH_SHORT).show()
            }
            R.id.settings_toolbar ->{
                Toast.makeText(this,"Настройки(в разработке)", Toast.LENGTH_SHORT).show()
            }
            R.id.signout_toolbar -> {
                auth.signOut()
                //startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
        }
        return true
    }
}


