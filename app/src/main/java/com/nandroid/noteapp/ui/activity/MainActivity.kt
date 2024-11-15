package com.nandroid.noteapp.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nandroid.noteapp.R
import com.nandroid.noteapp.databinding.ActivityMainBinding
import com.nandroid.noteapp.ui.fragments.login.LoginFragment
import com.nandroid.noteapp.ui.fragments.notes.HomeFragment

class MainActivity : AppCompatActivity() {
    private val viewModels: ViewModel by viewModels()  // ViewModel for the Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (!getSharedPreferences("user-login", MODE_PRIVATE).getBoolean("user-login", false)) {
            fragmentTransaction.add(R.id.mainFragment, LoginFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
            fragmentTransaction.commit()
        } else {
            fragmentTransaction.add(R.id.mainFragment, HomeFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
            fragmentTransaction.commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount <= 0) finish()

    }
}