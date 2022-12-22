package com.example.yoohtravel

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
}


