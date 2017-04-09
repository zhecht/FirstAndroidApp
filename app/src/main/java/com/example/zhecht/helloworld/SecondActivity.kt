package com.example.zhecht.helloworld
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.text.Layout
import android.util.TypedValue
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        var message = intent.getStringExtra("com.example.zhecht.MESSAGE")
        val date_split = Date().toString().split(" ")
        val today = date_split[1]+" "+date_split[2]
        val all_events = read_todays_events(message.toInt(),today)

        var new_date = today+" "+message
        val all_rows = arrayOf(row0, row1,row2)
        val all_years= arrayOf(year0, year1,year2)
        val all_topics= arrayOf(topic0, topic1,topic2)
        val all_content = arrayOf(content0, content1,content2)
        var cnt = 0
        for (event in all_events) {
            val args = event.split(";")
            val year=args[0]
            val topic=args[1]
            val content=args[2]
            all_years[cnt].text = year
            all_topics[cnt].text = topic
            all_content[cnt].text = content
            //val row_view = build_view(cnt,year,topic,content)
            //mainTable.addView(row_view)
            cnt++
        }

        //mainTable.addView()
        new_date += "'s"
        textViewYear.text = new_date
    }

    fun read_todays_events(which_year: Int, todays_date: String): MutableList<String> {
        val file = assets.open("todays_history.txt")
        val reader = BufferedReader(InputStreamReader(file))
        var line = reader.readLine()
        var correct_year = false
        var all_articles = mutableListOf<String>()
        while (line != null) {
            val args = line.split(";")
            if (args.size == 2) {
                val check_date = args[0]
                val tot_articles = args[1].toInt()
                correct_year = false
                if (check_date == todays_date) {
                    correct_year = true
                }
            } else {
                val year = args[0].toInt()
                val topic = args[1]
                val content = args[2]
                val year_min = Math.floor((year / 10).toDouble()) * 10
                var year_max = year_min + 9
                if (correct_year && (which_year >= year_min && which_year <= year_max)) {
                    all_articles.add(line)
                }
            }
            line = reader.readLine()
        }
        return all_articles

    }



    // gave up on trying dynamic
    fun build_view(curr_cnt: Int, year: String, topic: String, content: String): ViewGroup {
        var row = build_linear_layout(LinearLayout.HORIZONTAL, MATCH_PARENT, 100)
        var year_view = build_linear_layout(LinearLayout.VERTICAL, 120, MATCH_PARENT)
        row.setBackgroundColor(0x41a259)
        var year_constraint = build_constraint_layout(MATCH_PARENT, MATCH_PARENT)
        var text_view = build_text_view(year, WRAP_CONTENT, WRAP_CONTENT)

        year_constraint.addView(text_view)
        year_view.addView(year_constraint)
        row.addView(year_view)
        return row
    }

    fun build_text_view(text: String, width: Int, height: Int): TextView {
        var layout_params = LinearLayout.LayoutParams(width,height)
        layout_params.setMargins(16,16,16,16)

        var text_view = TextView(this)
        text_view.text = text
        text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0F)
        text_view.setTypeface(null,Typeface.BOLD)
        text_view.layoutParams = layout_params
        return text_view
    }

    fun build_linear_layout(orientation: Int,width: Int, height: Int): LinearLayout {
        val layout_params = LinearLayout.LayoutParams(width,height)
        var layout = LinearLayout(this)
        layout.orientation = orientation
        layout.layoutParams = layout_params
        return layout
    }

    fun build_constraint_layout(width: Int, height: Int): ConstraintLayout {
        val layout_params = LinearLayout.LayoutParams(width,height)
        var constraint = ConstraintLayout(this)
        constraint.layoutParams = layout_params
        return constraint
    }





}