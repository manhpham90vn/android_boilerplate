package com.example.baseandroid

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.baseandroid.data.LocalStorage

class HomeActivity : Activity() {

    private lateinit var local: LocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        local = LocalStorage(this)

        val logout = findViewById<Button>(R.id.logout)
        val refresh = findViewById<Button>(R.id.refresh)

        logout.setOnClickListener {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            local.clear(LocalStorage.token)
            local.clear(LocalStorage.refreshToken)
            finish()
        }

        refresh.setOnClickListener {
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
        }
    }

}