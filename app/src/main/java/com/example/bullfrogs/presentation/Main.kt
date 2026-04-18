package com.example.bullfrogs.presentation
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.GestureDetector
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.example.bullfrogs.R
import com.example.bullfrogs.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.database
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Properties
import kotlin.concurrent.timerTask
import kotlin.random.Random



class Main : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var mDetector: GestureDetector
    private var toggle = 1
    private var giggle1 = true
    private var twoggle = true
    private var threeggle = true
    private var froggie = true
    private var toggle5 = true
    private var funnyvar3 = true
    private var funnyvar2 = true
    private var funnyvar = true
    var returnFrog = false
    var listnum = 0
    var bslist = mutableListOf<String>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    var testerMode = true
    var pressesMinus = false
    var falseA = false
    var tgoal = 1000 *60 * 15

    override fun onCreate(savedInstanceState: Bundle?) {

        //make watchface


        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)

        var modeScreen = findViewById<RelativeLayout>(R.id.modeScreen)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)
        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)
        val TheFrog = findViewById<ImageView>(R.id.Bullfrog)
        val fone = findViewById<ImageView>(R.id.phone1)
        var reset = false
        var dogDays = findViewById<TextView>(R.id.time12)
        var starRating = findViewById<RatingBar>(R.id.starRating)
        var statusUpdate = findViewById<EditText>(R.id.statusUpdate)
        var apple = findViewById<ImageView>(R.id.food)
        var rest = findViewById<ImageView>(R.id.rest)
        val ticket = findViewById<ImageView>(R.id.ticket1)
        var date = findViewById<TextView>(R.id.date)
        var time = findViewById<TextClock>(R.id.time)
        var starMode = findViewById<ImageView>(R.id.starMode)
        var day = LocalDate.now().dayOfMonth.toString()
        var month = LocalDate.now().monthValue.toString()
        var year = LocalDate.now().year.toString()
        var date1 = "$month/$day/$year"
        var pos = findViewById<TextView>(R.id.pos)
        var statusApprove = findViewById<TextView>(R.id.statusApprove)
        var caution = findViewById<ImageView>(R.id.caution)
        var goSkate = findViewById<ImageView>(R.id.goSkate)
        var hue = findViewById<ImageView>(R.id.hue)
        var colorMenu = findViewById<RelativeLayout>(R.id.colorMenu)
        var good = findViewById<ImageView>(R.id.good)
        var yellowcard = findViewById<ImageView>(R.id.yellowcard)
        var redcard = findViewById<ImageView>(R.id.redcard)
        var lime = findViewById<ImageView>(R.id.lime)
        var update1 = findViewById<ImageView>(R.id.update)
        var note2self = findViewById<ImageView>(R.id.appissue)
        var color1 = 1
        var theBSLog = findViewById<RelativeLayout>(R.id.theBSLog)
        var potentialSolutions = findViewById<EditText>(R.id.potentialSolutions)
        var bsl = findViewById<ListView>(R.id.bsl)
        var currentItemColor = findViewById<ImageView>(R.id.currentItemColor)
        var QTBtn = findViewById<ImageView>(R.id.QT)
        var studyMode = findViewById<ImageView>(R.id.studyMode)
        var nebulizer = findViewById<ImageView>(R.id.nebulizer)





        database = Firebase.database.reference
       //writeNewUser("jls", "jls", "jls@firebase.com")
        date.text = date1
        var main1 = findViewById<ConstraintLayout>(R.id.main)


        if(testerMode == true) {

            main1.setBackgroundColor(R.drawable.blew)
            Log.d("lily", "testerMode")

        }
        else{
            main1.setBackgroundColor(R.drawable.bleh)
        }

        sharedPreferences = getSharedPreferences("daysSince", MODE_PRIVATE)


       hue.setOnClickListener {
           //Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
           colorMenu.visibility = View.VISIBLE
           colorMenu.bringToFront()
       }

        //card adds date and time
        good.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.good)
            colorMenu.visibility = View.INVISIBLE
            color1 = 1
            logIt(potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "green", potentialSolutions.text.toString(),null)
        }

        yellowcard.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.yellowcard)
            colorMenu.visibility = View.INVISIBLE
            color1 = 2
            logIt(potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "yellow", potentialSolutions.text.toString(),null)
        }

        redcard.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.redcard)
            colorMenu.visibility = View.INVISIBLE
            color1 = 3
            logIt(potentialSolutions.text.toString(), color1)
            Log.d("lily", potentialSolutions.text.toString() + color1)
            writeNewPost("jls", "jls", "red", potentialSolutions.text.toString(),null)
        }

        lime.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.lime)
            colorMenu.visibility = View.INVISIBLE
            color1 = 1
            logIt(potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "lime", potentialSolutions.text.toString(),null)
        }

        update1.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.updates)
            colorMenu.visibility = View.INVISIBLE
            color1 = 1
            logIt(potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "update", potentialSolutions.text.toString(),null)
        }

        note2self.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.nonissue)
            colorMenu.visibility = View.INVISIBLE
            color1 = 1
            logIt(potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "note2self", potentialSolutions.text.toString(),null)
        }

        dateTime.visibility = View.VISIBLE

        TheFrog.setOnLongClickListener {
            switch2(true)
        }

        TheFrog.setOnClickListener {

            if(returnFrog){
                TheFrog.animate().scaleX(2F)
                TheFrog.animate().scaleY(2F)
                TheFrog.animate().x((ttf.width/2F)-(TheFrog.width/2))
                returnFrog = false

            }

            starRating.rating = 3F



            clean()

            toggle++
            if (toggle == 2) {

                //mode screen
                clean()
                modeScreen.visibility = View.VISIBLE
                modeScreen.bringToFront()
                goSkate.visibility = View.VISIBLE
                goSkate.bringToFront()
                fone.visibility = View.VISIBLE
                fone.bringToFront()



            } else if (toggle == 3) {
                fone.animate().rotation(0F)
               // Log.d("lily", "something else")
                clean()
                dateTime.visibility = View.INVISIBLE
                var newlayout = findViewById<RelativeLayout>(R.id.somethingElse1)
                newlayout.visibility = View.VISIBLE
                newlayout.bringToFront()
                ticket.visibility = View.VISIBLE

            }
            else if (toggle == 4){
                TheFrog.animate().scaleX(.5F)
                TheFrog.animate().scaleY(.5F)
                TheFrog.animate().x(10F)
                returnFrog = true
                theBSLog.visibility = View.VISIBLE
                theBSLog.bringToFront()
                Log.d("lily", bslist.toString() + " HERE's THE LIST")

                // orderList()
                dateTime.visibility = View.INVISIBLE
            }
            else if (toggle == 5){

                //back to home screen
              //  Log.d("lily2", "home screen")
                clean()
                dateTime.visibility = View.VISIBLE
                toggle = 1
            }
        /*    else if (toggle == 6){


            }*/

        }

        pos.setOnClickListener {
            logIt("Stars:" + starRating.rating.toString() + "/6 ", 0)
            writeNewPost("jls", "Rating", "Stars: ",starRating.rating.toString() + "/6 " + dateTime.toString(),starRating.toString().toInt())
        }

        var status1 = sharedPreferences.getBoolean("status1", false)


        statusApprove.setOnClickListener {

            if(statusUpdate.toString() == ""){
                status1 = sharedPreferences.edit().putBoolean("status1", false).commit()
            }
            else{
                status1 = sharedPreferences.edit().putBoolean("status1", true).commit()
                writeNewPost("jls", "Status", ": ",statusUpdate.toString() + " " + dateTime.toString(),null)
                logIt("Status" + statusUpdate.toString() + " " + dateTime.toString(), 0)
                sharedPreferences.edit().putString("statusUpdate" , statusUpdate.toString()).commit()


            }
        }
        statusUpdate.setText(sharedPreferences.getString("statusUpdate", ""))


        if(status1) {
            Toast.makeText(this, sharedPreferences.getString("statusUpdate", "Have a good day"), Toast.LENGTH_SHORT).show()
        }

        fone.setOnClickListener {
            if(reset == false){
                fone.animate().rotation(90F)
                reset = true
            }
            else if (reset) {
                fone.animate().rotation(0F)
                reset = false
                Log.d("lily", "reset")

            }
            logIt("Hang up " + dateTime.toString(), 0)
            writeNewPost("jls", "jls", "phone", "Hang up " + dateTime.toString(),null)
        }

        starMode.setOnClickListener {
            if (funnyvar2) {
                //this means I'm eating
                starMode.animate().scaleX(2.3F)
                starMode.animate().scaleY(2.3F)
                funnyvar2 = false
                writeNewPost("jls", "Mode", "starMode", "on",null)

            }
            else if (!funnyvar2){
                starMode.animate().scaleX(1F)
                starMode.animate().scaleY(1F)
                funnyvar2 = true
                writeNewPost("jls", "Mode", "starMode", "off",null)
            }
        }

        studyMode.setOnClickListener {
            if (funnyvar2) {
                //this means I'm studying
                studyMode.animate().scaleX(2.3F)
                studyMode.animate().scaleY(2.3F)
                funnyvar3 = false
                writeNewPost("jls", "Mode", "studyMode", "on: I'm working on computer science",null)

            }
            else if (!funnyvar2){
                studyMode.animate().scaleX(1F)
                studyMode.animate().scaleY(1F)
                funnyvar3 = true
                writeNewPost("jls", "Mode", "studyMode", "off",null)
            }
        }

        apple.setOnClickListener {
            if (threeggle) {
                //this means I'm eating
                apple.animate().scaleX(2.3F)
                apple.animate().scaleY(2.3F)
                threeggle = false
                writeNewPost("jls", "Mode", "eatingMode", "on",null)

            }
            else if (!threeggle){
                apple.animate().scaleX(1F)
                apple.animate().scaleY(1F)
                threeggle = true
                writeNewPost("jls", "Mode", "eatingMode", "off",null)
            }
        }

        rest.setOnClickListener {
            if (twoggle) {
                //this means I'm eating
                rest.animate().scaleX(2.3F)
                rest.animate().scaleY(2.3F)
                twoggle = false
                writeNewPost("jls", "Mode", "restMode", "on",null)

            }
            else if (!twoggle){
                rest.animate().scaleX(1F)
                rest.animate().scaleY(1F)
                twoggle = true
                writeNewPost("jls", "Mode", "restMode", "off",null)
            }

        }

/*
       settings.setOnClickListener {
            //should display the icons that the user can choose to show in the mode screen

            if (toggle5) {
                Toast.makeText(this, "Choose which icons to display", Toast.LENGTH_SHORT).show()
                clean()
                skateboard.visibility = View.VISIBLE
                skateboard.bringToFront()
                toggle5 = false
            }

            else if(!toggle5){

                skateboard.visibility = View.INVISIBLE
                toggle5 = true
            }
        }

        skateboard.setOnClickListener {
            if(showSkateboard == 2 ){

                skateboard.animate().rotation(360F)
                skateboard.animate().scaleX(1F)
                skateboard.animate().scaleY(1F)
                Log.d("lily2", "Hi")
                showSkateboard = 1
            }
            else if(showSkateboard == 1) {
                skateboard.animate().rotation(360F)
                skateboard.animate().scaleX(2.3F)
                skateboard.animate().scaleY(2.3F)
                showSkateboard = 2
                Log.d("lily2", "hello" + showSkateboard)
            }

        }
        */
        goSkate.setOnClickListener {
            if(funnyvar) {
                goSkate.animate().rotation(360F)
                goSkate.animate().scaleX(2.3F)
                goSkate.animate().scaleY(2.3F)
                funnyvar = false
                writeNewPost("jls", "Mode", "skatingMode", "on, safety first",null)

            }
            else if (!funnyvar){
                goSkate.animate().rotation(0F)
                goSkate.animate().scaleX(1F)
                goSkate.animate().scaleY(1F)
                funnyvar = true
                writeNewPost("jls", "Mode", "skatingMode", "off",null)
            }
        }


        nebulizer.setOnClickListener {
            var modeActivated = sharedPreferences.getBoolean("modeActivated", false)
            if(!modeActivated){
                Toast.makeText(this, "Quick Tickets is ON", Toast.LENGTH_SHORT).show()
                nebulizer.animate().scaleY(2.3F)
                nebulizer.animate().scaleX(2.3F)
                sharedPreferences.edit().putBoolean("modeActivated", true).commit()
                sharedPreferences.edit().putString("modeVar","Project Nebula count:").commit()

            }
            else{

                //note for the future: download the sharedpref log then search this string among tickets to see data. it should smell like the original product. You can ID them a few seconds before each timestaamp for an upgrade. There are rewards
                Toast.makeText(this, "Quick Tickets is OFF", Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putBoolean("modeActivated", false).commit()
                nebulizer.animate().scaleX(1F)
                nebulizer.animate().scaleY(1F)

            }
        }

        caution.setOnLongClickListener {

            lights1()
            return@setOnLongClickListener true
        }

        caution.setOnClickListener {
            if (froggie) {
                //this means I'm eating
                caution.animate().scaleX(2.3F)
                caution.animate().scaleY(2.3F)
                froggie = false
                writeNewPost("jls", "Mode", "cautionMode", "on, safety first" + dateTime,null)
                logIt("cautionMode: on, safety first", 3)
            }
            else if (!froggie){
                caution.animate().scaleX(1F)
                caution.animate().scaleY(1F)
                froggie = true
                writeNewPost("jls", "Mode", "cautionMode", "off" + dateTime,null)
                logIt("cautionMode: now off", 0)
            }
        }

        ticket.setOnLongClickListener {
            switch()

        }

        ticket.setOnClickListener {
            if (giggle1) {
                //opens a ticket
                ticket.visibility = View.INVISIBLE
                TheFrog.animate().scaleX(.5F)
                TheFrog.animate().scaleY(.5F)
                TheFrog.animate().x(10F)
                returnFrog = true
                clean()
                ttf.visibility = View.VISIBLE
                ttf.bringToFront()
                dateTime.visibility = View.VISIBLE
            }
        }

        var QT = sharedPreferences.getInt("QT", 0)
        var QTCount = sharedPreferences.getInt("QTCount",1)
        var current1 = sharedPreferences.getString("current","")

        dateTime.visibility = View.VISIBLE

        //gear
        //just tap to save day no need for final post
        QTBtn.setOnClickListener {
            QT = sharedPreferences.getInt("QT", 0)
            if(QT==0){
                QTBtn.animate().scaleX(2F)
                QTBtn.animate().scaleY(2F)
                QT = 1
                var theItem = potentialSolutions.text.toString() + "\n" + date1 + "\n" + time.text.toString() + " level: " + color1.toString() +  " "
                logIt(potentialSolutions.text.toString(), color1)
                writeNewPost("jls", date1, time.text.toString(), "current count:" + QTCount.toString(), null)
                sharedPreferences.edit().putString("current", theItem).commit()
                sharedPreferences.edit().putInt("QT", 1).commit()

                sharedPreferences.edit().putInt("pastDifference", LocalDateTime.now().minute).commit()
                sharedPreferences.edit().putInt("anHour4mNow", LocalDateTime.now().minute + tgoal).commit()
            }
            else {
                QTBtn.animate().scaleX(1F)
                QTBtn.animate().scaleY(1F)

                Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show()
                QT = 0
                QTCount -1
                sharedPreferences.edit().putInt("QT", 0).commit()
                sharedPreferences.edit().putInt("QTCount", 1).commit()
            }
        }


        /*
            //
            //
            //note to self: microneurology
        //no ai crlty*/

        Log.d("lily2", "days that have passed: " + sharedPreferences.getInt("dogdays", 0))


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

            //todo put button refresher here






        }
        sharedPreferences.edit().putInt("lsd", day.toInt()).commit()
        dogDays.text = counter.toString()




        //var i = 1





    /*
        for changing hue color  based on level

        for(i in 1 .. 4){
            var itemInList = sharedPreferences.getString(i.toString(), "1")
            if(itemInList?.contains("1") == true){
                //list item background color is green

            }
            else if(itemInList?.contains("2") == true ){
                //list item background color is green
            }
            else if(itemInList?.contains("3") == true){
                //list item background color is green
              //  bsl.get(i.toInt()).setBackgroundColor(R.drawable.redcard)
                bsl.get(i).setBackgroundResource(R.drawable.redcard)
                Log.d("lily", "HERE")
            }

          *//*  itemInList = itemInList?.removeSuffix("1")
            itemInList = itemInList?.removeSuffix("2")
            itemInList = itemInList?.removeSuffix("3")*//*

           // bslist.set(i.toInt(), itemInList.toString())
        }*/



        monaLista(0)

        bsl.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,bslist)


        bsl.setOnItemClickListener{ parent, view, position, id ->


            if(bslist.get(position).endsWith("1") == true){
                //list item background color is green
                currentItemColor.setImageResource(R.drawable.good)
                Log.d("lily", "1")

            }
            if(bslist.get(position).endsWith("2") == true ){
                //list item background color is green
                currentItemColor.setImageResource(R.drawable.yellowcard)
                Log.d("lily", "2")


            }
            if(bslist.get(position).endsWith("3") == true){
                currentItemColor.setImageResource(R.drawable.redcard)
                //list item background color is green

                Log.d("lily", "HERE")


            }

        }


        //all rewards are redeemable, open to discussion
        //if there is one good week:
        if(counter == 7){
            //you win, go to a beach, a club, a new farmers market, etc
            TheFrog.setImageResource(R.drawable.youwon)
        }
        //if there is one good month
        if(counter == 30){
            //YOU WON music festival tickets
            TheFrog.setImageResource(R.drawable.festivaltickets)
        }

        var minus1 = findViewById<TextView>(R.id.minus1)

        minus1.setOnClickListener {
            var QTCount = sharedPreferences.getInt("QTCount", 1)
            QTCount--
            sharedPreferences.edit().putInt("QTCount", QTCount).commit()
            potentialSolutions.setText("Count: " + QTCount.toString() + "\n" + current1 + time.text.toString())
            logIt("overwrite last count(s)", 0)
            writeNewPost("jls", date1, time.text.toString(), "overwrite last count(s)", null)
            pressesMinus = true

        }

        var plus1 = findViewById<TextView>(R.id.plus1)
        var plus2 = findViewById<TextView>(R.id.plus2)




        plus1.setOnClickListener {

            var modeCount = sharedPreferences.getInt("modeCount", 0)
            var modeActivated = sharedPreferences.getBoolean("modeActivated", false)
            var modeVar = sharedPreferences.getString("modeVar", "Current Mode count: ")

            QTCount = sharedPreferences.getInt("QTCount", 1)
            QTCount++
            Toast.makeText(this@Main, QTCount.toString(), Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putInt("QTCount", QTCount).commit()


            if (modeActivated == true){
                modeCount++
                Toast.makeText(this@Main, "QTC: " + QTCount.toString() + "\nMC: " + modeCount, Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putInt("modeCount", modeCount).commit()
                logIt(modeVar + modeCount + "\n", 0) //includes date, time, level
                writeNewPost("jls", date1, time.text.toString(), modeVar + modeCount, null)
            }

            potentialSolutions.setText("Tick: " + QTCount.toString() + "\n" + current1 + time.text.toString() + date1) //if you want to add a note after hue adds date time
            logIt("Tick: " + QTCount + "\n" + current1 + "\n", 0)
            writeNewPost("jls", date1, time.text.toString(), "current count:" + QTCount.toString() + " " + current1, null)

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(250)
            }
        }

        //use regular plus if switch is already on
        //todo test in field
        plus2.setOnClickListener {
            if(testerMode == true) {
            }
            else{
                switch()
            }


            //should match plus1

            var modeCount = sharedPreferences.getInt("modeCount", 0)
            var modeActivated = sharedPreferences.getBoolean("modeActivated", false)
            var modeVar = sharedPreferences.getString("modeVar", "Current Mode count: ")

            QTCount = sharedPreferences.getInt("QTCount", 1)
            QTCount++
            Toast.makeText(this@Main, QTCount.toString(), Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putInt("QTCount", QTCount).commit()


            if (modeActivated == true){
                modeCount++
                Toast.makeText(this@Main, "QTC: " + QTCount.toString() + "\nMC: " + modeCount, Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putInt("modeCount", modeCount).commit()
                logIt(modeVar + modeCount + "\n", 0) //includes date, time, level
                writeNewPost("jls", date1, time.text.toString(), modeVar + modeCount, null)
            }

            potentialSolutions.setText("Tick: " + QTCount.toString() + "\n" + current1 + time.text.toString() + date1) //if you want to add a note after hue adds date time
            logIt("Tick: " + QTCount + "\n" + current1 + "\n", 0)
            writeNewPost("jls", date1, time.text.toString(), "current count:" + QTCount.toString() + " " + current1, null)

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(250)
            }
      /*
         QTCount = sharedPreferences.getInt("QTCount", 1)

            QTCount++
            Toast.makeText(this@Main, QTCount.toString(), Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putInt("QTCount", QTCount).commit()
            potentialSolutions.setText("Count: " + QTCount.toString() + "\n" + current1 + time.text.toString())
            logIt("Tick: " + QTCount + "\n" + current1 + "\n", 0)
            writeNewPost("jls", date1, time.text.toString(), "current count:" + QTCount.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(250)
            }*/






            if(testerMode == false){
                switch()
            }

        }

        var change = findViewById<TextView>(R.id.change)

        change.setOnClickListener {
            var testletters = potentialSolutions.text

            testletters.toString().lowercase()

            var abslist = arrayOf(" ", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")
            var append1 = mutableListOf<Int>()
            for(i in 0..testletters.length-1){
                for(j in 0 .. abslist.size-1)
                    if (testletters.get(i).toString() == abslist.get(j)){
                        Log.d("lily", "not here" + j)
                        append1.add(j)
                    }
            }
            Log.d("lily", "here: "  + append1.toString())
            potentialSolutions.setText(append1.toString())
        }

       // writeNewPost("jls", "jls", time.text.toString(), "TEST current count:" + QTCount.toString(),null)










        checkHourGoal()










           // var currentItemColor = findViewById<ImageView>(R.id.currentItemColor)

        currentItemColor.setOnClickListener(){

            saveSharedPreferencestoExternal(this, "daysSince", "bsl" + month + day + year)
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
        }











































    }


/*    private fun orderList() {
        if (reversed) {
            bslist.reverse()
        }
        else{
            bslist.reverse()
        }
    }*/
    private fun checkHourGoal() {
        //the last behaviour has stopped for tgoal minutes

   /*     var QT = sharedPreferences.getInt("QT", 0)

        if(QT ==0) {
            //save current time
            sharedPreferences.edit().putInt("pastDifference", LocalDateTime.now().minute)
            sharedPreferences.edit().putInt("anHour4mNow", 2+LocalDateTime.now().minute)
        }
*/
        var pastDifference = sharedPreferences.getInt("pastDifference", LocalDateTime.now().minute)




        if(sharedPreferences.getInt("anHour4mNow", tgoal + pastDifference + LocalDateTime.now().minute) < LocalDateTime.now().minute + pastDifference){

            Log.d("lily", "You Did It")
            sharedPreferences.edit().putInt("pastDifference", LocalDateTime.now().minute)
            sharedPreferences.edit().putInt("anHour4mNow", LocalDateTime.now().minute + tgoal)

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(2000)
            }
        }
    }

    fun monaLista(j: Int){
        //loads the list
        var bsl = findViewById<ListView>(R.id.bsl)
        var i = j
        var totalItems = sharedPreferences.getInt("count1", listnum)
        if (totalItems == 0 || i >= totalItems){
            return
        }

        bslist.add(sharedPreferences.getString(i.toString(), "").toString())
        i++
        bsl.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bslist)
        monaLista(i)
    }


    fun logIt(text: String, color1: Int) {
        var day = LocalDate.now().dayOfMonth.toString()
        var month = LocalDate.now().monthValue.toString()
        var year = LocalDate.now().year.toString()
        var date1 = "$month/$day/$year"
        var time2 = findViewById<TextClock>(R.id.time)
        var dateTime1 = findViewById<RelativeLayout>(R.id.dateTime)


        var potentialSolutions = findViewById<EditText>(R.id.potentialSolutions)
        var bsl = findViewById<ListView>(R.id.bsl)

        dateTime1.visibility = View.VISIBLE


        listnum = sharedPreferences.getInt("count1", 0)

        //sharedPreferences.edit().putString(listnum.toString(), text).commit()

        var theItem =""
        if(color1 == 0 ){
            theItem = text + "\n" + date1 + "\n" + time2.text.toString()

        }
        else {
            theItem = text + "\n" + date1 + "\n" + time2.text.toString() + " level: " + color1.toString() + " "
        }
        sharedPreferences.edit().putString(listnum.toString(),  theItem).commit()
        bslist.add(theItem)
        //bslist.set(listnum, text)
        listnum++
        bsl.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bslist)
        sharedPreferences.edit().putInt("count1", listnum).commit()
       // bslist.add("")


    }
    fun clean() {
        var modeScreen = findViewById<RelativeLayout>(R.id.modeScreen)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)
        val ticket = findViewById<ImageView>(R.id.ticket1)
        var goSkate = findViewById<ImageView>(R.id.goSkate)
        var colorMenu = findViewById<RelativeLayout>(R.id.colorMenu)
        var theBSLog = findViewById<RelativeLayout>(R.id.theBSLog)
        var ticket3 = findViewById<ImageView>(R.id.ticket1)
        var newLayout = findViewById<RelativeLayout>(R.id.somethingElse1)
        var read = findViewById<ImageView>(R.id.read)
        var blew = findViewById<ImageView>(R.id.blew)
        colorMenu.visibility = View.INVISIBLE
        newLayout.visibility = View.INVISIBLE
        ticket3.visibility = View.INVISIBLE
        ttf.visibility = View.INVISIBLE
        theBSLog.visibility = View.INVISIBLE
        modeScreen.visibility = View.INVISIBLE
        ticket.visibility = View.INVISIBLE
        goSkate.visibility = View.INVISIBLE
        read.visibility = View.INVISIBLE
        blew.visibility = View.INVISIBLE

    }




    @Override
    override fun onResume() {

        checkHourGoal()

        switch2(false)

        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)
        var TheFrog = findViewById<ImageView>(R.id.Bullfrog)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)

        //back to home screen

        Log.d("lily2", "home screen")
        clean()
        if(returnFrog){
            TheFrog.animate().scaleX(2F)
            TheFrog.animate().scaleY(2F)
            TheFrog.animate().x((ttf.width/2F)-(TheFrog.width/2))
            returnFrog = false

        }
        //to go back to first screen on resume

        dateTime.visibility = View.VISIBLE
        toggle = 1

        lifecycleScope.launch {
            delay(2500)
            //this causes someone to h me

        var QT = sharedPreferences.getInt("QT", 0)
        var potentialSolutions = findViewById<EditText>(R.id.potentialSolutions)
        var QTBtn = findViewById<ImageView>(R.id.QT)
        var time = findViewById<TextClock>(R.id.time)
        var QTCount = sharedPreferences.getInt("QTCount", 1)
        var current1 = sharedPreferences.getString("current", "")
        var day = LocalDate.now().dayOfMonth.toString()
        var month = LocalDate.now().monthValue.toString()
        var year = LocalDate.now().year.toString()
        var date1 = "$month/$day/$year"

        if(falseA == false){

        if (QT == 1) {



            var modeCount = sharedPreferences.getInt("modeCount", 0)
            var modeActivated = sharedPreferences.getBoolean("modeActivated", false)

            var modeVar = sharedPreferences.getString("modeVar", "Current Mode count: ")




            QTCount++




            if (modeActivated == true){
                modeCount++
                Toast.makeText(this@Main, "QTC: " + QTCount.toString() + "\nMC: " + modeCount, Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putInt("modeCount", modeCount).commit()
                logIt(modeVar + modeCount + "\n", 0) //includes date, time, level
                writeNewPost("jls", date1, time.text.toString(), modeVar + modeCount, null)


            }
         /*   else{
               // to show count
             Toast.makeText(this@Main, QTCount.toString(), Toast.LENGTH_SHORT).show()

            }*/



            sharedPreferences.edit().putInt("QTCount", QTCount).commit()

            //  var theItem =  potentialSolutions.text.toString() + "\n\n" + date1 + "\n" + time.text.toString() + " " + color1.toString()
            potentialSolutions.setText("Count: " + QTCount.toString() + "\n" + current1 + time.text.toString())
            logIt("Tick: " + QTCount + "\n" + current1 + "\n", 0)
            QTBtn.animate().scaleX(2F)
            QTBtn.animate().scaleY(2F)
            //Toast.makeText(this@Main, "Make it count", Toast.LENGTH_SHORT).show()


            writeNewPost("jls", date1, time.text.toString(), "current count:" + QTCount.toString(), null)


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(250)
            }
/*


            LocalDateTime.now().hour
            LocalDateTime.now().minute



            while (QTCount == sharedPreferences.getInt("holdontothis", 0)) {

                sharedPreferences.edit().putInt("holdontothis", QTCount).commit()
            }


            lifecycleScope.launch {


                    var timeGoal = Random.nextInt(60000 * 30, 60000 * 90).toLong()
                    delay(timeGoal)
                    Log.d("lily", "this much time passed: " + timeGoal)

                    val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION")
                        v.vibrate(500)
                    }

                }

                //lifecycleScope.cancel()
*/





      /*      var hour = LocalDateTime.now().hour
            var min = LocalDateTime.now().minute*/

           // var changeInStatus = sharedPreferences.getBoolean("changeInStatus", false)


                sharedPreferences.edit().putInt("pastDifference", LocalDateTime.now().minute).commit()

            //todo randomize 60 min
            //todo test with smaller value
                sharedPreferences.edit().putInt("anHour4mNow", LocalDateTime.now().minute + tgoal).commit()


           /*     sharedPreferences.edit().putInt("dogmin",min).commit()
                sharedPreferences.edit().putInt("doghour", hour).commit()*/







        }

        }
        }
        falseA = false
        //checkHourGoal()
        super.onResume()
    }


    @IgnoreExtraProperties
    data class User(val username: String? = null, val email: String? = null) {
        // Null default values create a no-argument default constructor, which is needed
        // for deserialization from a DataSnapshot.
    }


    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)

        database.child("users").child(userId).setValue(user).addOnSuccessListener {
             Log.d("lily", "Success")
            }

    }
    @IgnoreExtraProperties
    data class Post(
        var uid: String? = "",
        var author: String? = "",
        var title: String? = "",
        var body: String? = "",
        var starCount: Int = 0,
        //var stars: MutableMap<String, Boolean> = HashMap(),
    ) {


        fun toMap(): Map<String, Any?> {
            return mapOf(
                "uid" to uid,
                "1 Title" to author,
                "2 description" to title,
                "3 message" to body,
                "starCount" to starCount,
                //"stars" to stars,
            )
        }
    }

    private fun writeNewPost(userId: String, username: String, title: String, body: String, stars: Int?) {
        if(testerMode==false) {
            Toast.makeText(this, "w", Toast.LENGTH_SHORT).show()
            // Create new post at /user-posts/$userid/$postid and at
            // /posts/$postid simultaneously
            val key = database.child("posts").push().key
            if (key == null) {
                Log.w("lily", "Couldn't get push key for posts")
                return
            }


            if(stars !=null){
                val post = Post(userId, username, title, body, stars)
                val postValues = post.toMap()

                val childUpdates = hashMapOf<String, Any>(
                    "/posts/$key" to postValues,
                    "/user-posts/$userId/$key" to postValues,
                )
            }
            else {

                val post = Post(userId, username, title, body)
                val postValues = post.toMap()

                val childUpdates = hashMapOf<String, Any>(
                    "/posts/$key" to postValues,
                    "/user-posts/$userId/$key" to postValues,
                )

                database.updateChildren(childUpdates).addOnSuccessListener {
                    Log.d("lily", "Success")
                }
            }
        }
    }


    private fun switch(): Boolean {
        var main1 = findViewById<ConstraintLayout>(R.id.main)

        if (testerMode == true) {
            testerMode = false
            main1.setBackgroundColor(R.drawable.blew)
        } else if (testerMode == false) {
            testerMode = true

            main1.setBackgroundColor(R.drawable.bleh)

            // writeNewPost("jls", "Mode", "testingMode", "on",null)
        }
        Toast.makeText(this, testerMode.toString(), Toast.LENGTH_SHORT).show()
        Log.d("lily", "testerMode: " + testerMode.toString())
        return testerMode
    }


    private fun switch2(onOff: Boolean): Boolean {
        if (onOff == true){
            falseA = true
            Toast.makeText(this,  "did nothing", Toast.LENGTH_SHORT).show()










        }
        else if (onOff == false){
            falseA = false
        }
        return falseA
    }




/*    private fun resetCountDown(){





        var hour = LocalDateTime.now().hour.toString()

        var lastSaveHour = sharedPreferences.getInt("lsh", hour.toInt())
        var hourCount1 = sharedPreferences.getInt("hourcounter", 0)




        //increments when day is different to yesterday
        if(minute.toInt() != lastSaveMinute){
            minCount1++
            sharedPreferences.edit().putInt("mincounter", minCount1).commit()
        }
        sharedPreferences.edit().putInt("lsm", minute.toInt()).commit()






    }*/












    private fun lights1() {

        var blew = findViewById<ImageView>(R.id.blew)
        var read = findViewById<ImageView>(R.id.read)
        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)
        var turnoff = sharedPreferences.getBoolean("turnoff", false)

        blew.setOnClickListener {
            turnoff = sharedPreferences.edit().putBoolean("turnoff", true).commit()
            return@setOnClickListener
        }
        read.setOnClickListener {
            turnoff = sharedPreferences.edit().putBoolean("turnoff", true).commit()
            return@setOnClickListener
        }

        if (turnoff == true) {
            clean()
            dateTime.visibility = View.VISIBLE
            toggle = 1
            turnoff = sharedPreferences.edit().putBoolean("turnoff", false).commit()
            return
        }

        lifecycleScope.launch {
            blew.visibility = View.VISIBLE
            blew.bringToFront()
            delay(250)
            read.visibility = View.VISIBLE
            read.bringToFront()
            delay(250)
            blew.bringToFront()
            delay(250)
            read.bringToFront()
            delay(250)

            lights1()
        }


    }




fun saveSharedPreferencestoExternal(context: Context, prefsName: String, fileName: String) {
    Log.d("lily", "SP")

    val preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    val keys = preferences.all
    val properties = Properties()

    for((key, value) in keys) {
        properties.setProperty(key, value.toString())

    }
    try {

        val externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(externalDir, fileName)
        Log.d("lily", "SP2")

        if (!externalDir.exists()) {
            Log.d("lily", "SP3")

            externalDir.mkdirs()
            Log.d("lily", "SP make dir")

        }
        Log.d("lily", "SP4")
        val fileOut = FileOutputStream(file)
        properties.storeToXML(fileOut, "External Preferences")
        fileOut.close()
        Log.d("lily", "SP saved to" + file.absolutePath)
    }
    catch( e: FileNotFoundException) {
        e.printStackTrace()

    }
    catch(e: IOException){
        e.printStackTrace()
    }




}



}


