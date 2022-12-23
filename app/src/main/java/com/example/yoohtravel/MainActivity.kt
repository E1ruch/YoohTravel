package com.example.yoohtravel

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {


    private lateinit var auth : FirebaseAuth
    private lateinit var signOutBtn : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = "Как туда попасть?"


        //Отображение фрагмент-окон через NavBar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fl_wrapper)
       bottomNavigationView.setupWithNavController(navController)




        auth = FirebaseAuth.getInstance()
        signOutBtn = findViewById(R.id.signOutBtn)
        signOutBtn.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, PhoneActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.search_toolbar ->{
                Toast.makeText(this,"Поиск(в разработке)", Toast.LENGTH_SHORT).show()
            }
            R.id.settings_toolbar ->{
                Toast.makeText(this,"Настройки(в разработке)", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}


