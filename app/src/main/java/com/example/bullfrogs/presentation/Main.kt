package com.example.bullfrogs.presentation
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.text.input.TextFieldLineLimits
import com.example.bullfrogs.R
import com.example.bullfrogs.databinding.ActivityMainBinding
import java.time.LocalDate

class Main : AppCompatActivity() {
    private var toggle = 1
    private var giggle1 = true
    private var twoggle = true
    private var threeggle = true

    private var froggie = true
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {


        //todo add mode button
        //todo make days since easily testable
        //Todo make face
        //todo broadcast signal

        super.onCreate(savedInstanceState)

        //set up binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)

        var volumeLever = findViewById<SeekBar>(R.id.volumeLever)
        val TheFrog = findViewById<ImageView>(R.id.Bullfrog)
        val phone = findViewById<ImageView>(R.id.phone)
        var reset = false
        var dogDays = findViewById<TextView>(R.id.time1)
        var stars = findViewById<RatingBar>(R.id.stars)
        var statusUpdate = findViewById<EditText>(R.id.statusUpdate)
        var apple = findViewById<ImageView>(R.id.food)
        var rest = findViewById<ImageView>(R.id.rest)
        val ticket = findViewById<ImageButton>(R.id.ticket)
        var date = findViewById<TextView>(R.id.date)
        var time = findViewById<TextClock>(R.id.time)
        var degree = findViewById<RatingBar>(R.id.degree)
        var starMode = findViewById<ImageView>(R.id.starMode)
        var peaceTalks = findViewById<EditText>(R.id.peaceTalks)
        var day = LocalDate.now().dayOfMonth.toString()
        var month = LocalDate.now().monthValue.toString()
        var year = LocalDate.now().year.toString()
        var date1 = "$month/$day/$year"
        var pos = findViewById<TextView>(R.id.pos)
        var crit = findViewById<TextView>(R.id.critical)
        var noncrit = findViewById<TextView>(R.id.noncrit)
        var caution = findViewById<ImageView>(R.id.caution)

        date.text = date1

        ticket.setOnClickListener {
            if (giggle1) {
                //opens a ticket
                peaceTalks.visibility = View.VISIBLE
                peaceTalks.bringToFront()
                volumeLever.visibility = View.INVISIBLE
                phone.visibility = View.INVISIBLE
                //dogDays.visibility = View.INVISIBLE
                stars.visibility = View.INVISIBLE
                date.visibility = View.VISIBLE
                date.bringToFront()
                time.visibility = View.VISIBLE
                time.bringToFront()
                degree.visibility = View.VISIBLE
                degree.bringToFront()
                crit.visibility = View.VISIBLE
                noncrit.visibility = View.VISIBLE
                dogDays.visibility = View.INVISIBLE
                pos.visibility = View.INVISIBLE
                giggle1 = false
            } else if (!giggle1) {
                peaceTalks.visibility = View.INVISIBLE
                degree.visibility = View.INVISIBLE
                ticket.visibility = View.INVISIBLE
                phone.visibility = View.INVISIBLE
                ticket.visibility = View.INVISIBLE
                if (reset) {
                    var theMath = 360 - 132 + 180
                    phone.animate().rotation(theMath.toFloat())
                    reset = false
                }
                volumeLever.visibility = View.INVISIBLE
                dogDays.visibility = View.INVISIBLE
                stars.visibility = View.INVISIBLE

                date.visibility = View.VISIBLE
                time.visibility = View.VISIBLE
                degree.visibility = View.INVISIBLE
                crit.visibility = View.INVISIBLE
                noncrit.visibility = View.INVISIBLE

                toggle = 1
                giggle1 = true
            }
        }
        /*
            //note to self: microneurology
        //no ai crlty*/


        TheFrog.setOnClickListener {
            toggle++
            if (toggle == 2) {

                //mode screen
                starMode.visibility = View.VISIBLE
                apple.visibility = View.VISIBLE
                statusUpdate.visibility = View.VISIBLE
                rest.visibility = View.VISIBLE
                caution.visibility = View.VISIBLE

                dogDays.visibility = View.INVISIBLE
            }

            else if (toggle == 3) {
                //the control screen

                //turns the phone icon over
                if (reset) {
                    var theMath = 360 - 132 + 180
                    phone.animate().rotation(theMath.toFloat())
                    reset = false
                }


                pos.visibility = View.VISIBLE
                ticket.visibility = View.VISIBLE
                ticket.bringToFront()
                phone.visibility = View.VISIBLE
                phone.setRotation(0f)
                volumeLever.visibility = View.VISIBLE
                dogDays.visibility = View.VISIBLE
                stars.visibility = View.VISIBLE
                stars.bringToFront()
                dogDays.bringToFront()
                TheFrog.bringToFront()
                phone.bringToFront()
                date.visibility = View.INVISIBLE
                time.visibility = View.INVISIBLE
                Log.d("lily", "nothing")


                starMode.visibility = View.INVISIBLE
                apple.visibility = View.INVISIBLE
                rest.visibility = View.INVISIBLE
                statusUpdate.visibility = View.INVISIBLE
                caution.visibility = View.INVISIBLE



            } else if (toggle == 4) {

                //back to home screen
                date.visibility = View.VISIBLE
                time.visibility = View.VISIBLE
                phone.visibility = View.INVISIBLE
                ticket.visibility = View.INVISIBLE
                volumeLever.visibility = View.INVISIBLE
                dogDays.visibility = View.INVISIBLE
                stars.visibility = View.INVISIBLE
                degree.visibility = View.INVISIBLE
                pos.visibility = View.INVISIBLE
                peaceTalks.visibility = View.INVISIBLE
                crit.visibility = View.INVISIBLE
                noncrit.visibility = View.INVISIBLE

                toggle = 1
            }

        }


        phone.setOnClickListener {
            phone.animate().rotation((132).toFloat())
            reset = true
        }


        starMode.setOnClickListener {
            if (threeggle) {
                //this means I'm eating
                starMode.animate().scaleX(2.3F)
                starMode.animate().scaleY(2.3F)
                threeggle = false
            }
            else{
                starMode.animate().scaleX(1F)
                starMode.animate().scaleY(1F)
                threeggle = true
            }

        }


        apple.setOnClickListener {
            if (threeggle) {
                //this means I'm eating
                apple.animate().scaleX(2.3F)
                apple.animate().scaleY(2.3F)
                threeggle = false
            }
            else if (!threeggle){
                apple.animate().scaleX(1F)
                apple.animate().scaleY(1F)
                threeggle = true
            }

        }


        rest.setOnClickListener {
            if (threeggle) {
                //this means I'm eating
                rest.animate().scaleX(2.3F)
                rest.animate().scaleY(2.3F)
                threeggle = false
            }
            else if (!threeggle){
                rest.animate().scaleX(1F)
                rest.animate().scaleY(1F)
                threeggle = true
            }

        }

        caution.setOnClickListener {
            if (froggie) {
                //this means I'm eating
                caution.animate().scaleX(2.3F)
                caution.animate().scaleY(2.3F)
                froggie = false
            }
            else if (!froggie){
                caution.animate().scaleX(1F)
                caution.animate().scaleY(1F)
                froggie = true
            }

        }


        sharedPreferences = getSharedPreferences("daysSince", MODE_PRIVATE)
        Log.d("lily", "days that have passed: " + sharedPreferences.getInt("dogdays", 0))


        var lastSaveDate = sharedPreferences.getInt("lsd", day.toInt())
        var counter = sharedPreferences.getInt("counter", 0)
        //make button or text clickable
        //set var to zero on click


        //click to reset
        dogDays.setOnClickListener {
            counter = 0
            sharedPreferences.edit().putInt("counter", counter).commit()
            dogDays.text = counter.toString()
            //reset time displayed to 0
        }

        //increments when day is different to yesterday
        if(day.toInt() != lastSaveDate){
            counter++
            sharedPreferences.edit().putInt("counter", counter).commit()
        }
        sharedPreferences.edit().putInt("lsd", day.toInt()).commit()
        dogDays.text = counter.toString()
    }
}


