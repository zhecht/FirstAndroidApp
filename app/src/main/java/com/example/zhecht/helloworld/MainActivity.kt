package com.example.zhecht.helloworld
import kotlinx.android.synthetic.main.activity_main.*


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.io.File
import java.io.OutputStreamWriter

//import android.widget.TextView
import java.util.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager

import android.view.View
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val date_split = Date().toString().split(" ")
        textViewTime.text = date_split[1]+" "+date_split[2]

        val f = assets.open("todays_history.txt")
        val is_1 = f.available()
        val reader = BufferedReader(InputStreamReader(f))
        val is_3 = reader.readLine()
        val is_4 = 0
    }

    fun sendMessage(view: View) {
        val i = Intent(this@MainActivity,SecondActivity::class.java)
        val which = view.getTag().toString()
        i.putExtra("com.example.zhecht.MESSAGE", which)
        startActivity(i)
    }


}

