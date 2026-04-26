package com.example.bullfrogs.presentation
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bullfrogs.R
import com.example.bullfrogs.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.database
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Properties
import kotlin.math.abs

//Plan For Success!
//No Cruelty
//No AI Cruelty
//Intention should matter




//add voice to some buttons
//ex hang up
// "quiet mode"
//"right on"

//fixed frog movement on start
//updated frog carousel
//updated qtb positions
//todo test



//new day toast saved

//added total count with sharedpref
//emoji should retain size after close, gear


//make it look more proffessional


//sharedpref is located in /data/data/com.example.bullfrogs/shared_prefs/daysSince.xml

class Main : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var toggle = 6
    private var giggle1 = true

    var returnFrog = true
    var listnum = 0
    var backupReminderNum = 0
    var bslist = mutableListOf<String>()
    var backupReminders1 = mutableListOf<String>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    var testerMode = true
    var pressesMinus = false
    var falseA = false
    var tgoal = 4

    var preset1 = 500
    var TC = 0
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        //make watchface
        //when testing turn off counter before run


        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)

        var modeScreen = findViewById<RelativeLayout>(R.id.modeScreen)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)
        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)
       // val TheFrog = findViewById<ImageView>(R.id.Bullfrog)
        val fone = findViewById<ImageView>(R.id.hangupphone1)
        var reset = false
        var dogDays = findViewById<TextView>(R.id.time12)
        var starRating = findViewById<RatingBar>(R.id.starRating)
        var statusUpdate = findViewById<EditText>(R.id.statusUpdate)

        val ticket = findViewById<ImageView>(R.id.ticket1)
        var date = findViewById<TextView>(R.id.date)
        var time = findViewById<TextClock>(R.id.time)

        var day = LocalDate.now().dayOfMonth.toString()
        var month = LocalDate.now().monthValue.toString()
        var year = LocalDate.now().year.toString()
        var date1 = "$month/$day/$year"
        var pos = findViewById<TextView>(R.id.pos)
        var statusApprove = findViewById<TextView>(R.id.statusApprove)

        var starMode = findViewById<ImageView>(R.id.starMode)
        var apple = findViewById<ImageView>(R.id.food)
        var rest = findViewById<ImageView>(R.id.rest)
        var sleep = findViewById<ImageView>(R.id.sleep)
        var studyMode = findViewById<ImageView>(R.id.studyMode)
        var nebulizer = findViewById<ImageView>(R.id.nebulizer)
        var goSkate = findViewById<ImageView>(R.id.goSkate)
        var auto = findViewById<ImageView>(R.id.automobile)
        var reeses = findViewById<ImageView>(R.id.reeses)
        var tea = findViewById<ImageView>(R.id.tea)
        var clean = findViewById<ImageView>(R.id.cleaning)
        var busy = findViewById<ImageView>(R.id.busy)
        var groceries = findViewById<ImageView>(R.id.groceries)


        var caution = findViewById<ImageView>(R.id.caution)

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

        var adjust = findViewById<TextView>(R.id.adjust)

        var QTS = findViewById<RelativeLayout>(R.id.QTS)
        var theButtons = findViewById<RelativeLayout>(R.id.theButtons)


        var walkie = findViewById<ImageView>(R.id.connect)

        var guitar = findViewById<ImageView>(R.id.connect2)

        var QQTS = findViewById<TextView>(R.id.QQTS)

        var infomessage = findViewById<ImageView>(R.id.infomessage)
        var ping = findViewById<ImageView>(R.id.ping)

        var infoping = findViewById<ImageView>(R.id.infoping)


        var volumelever1 = findViewById<SeekBar>(R.id.volumeLever1)
        volumelever1.progress = volumelever1.width/2

        //var daycounter = 0



        database = Firebase.database.reference
        //writeNewUser("jls", "jls", "jls@firebase.com")
        date.text = date1


        if(testerMode == true) {
            Log.d("lily", "testerMode")

        }

        sharedPreferences = getSharedPreferences("daysSince", MODE_PRIVATE)



        if(sharedPreferences.getBoolean("safety", true)){
            dateTime.setBackgroundResource(R.drawable.updates)
            //TheFrog.bringToFront()
        }
        else{
            dateTime.setBackgroundResource(R.drawable.blah)
            //TheFrog.bringToFront()
        }

        TC = sharedPreferences.getInt("TC", 0)

        var on1 = true
        hue.setOnClickListener {

            if(on1){
                colorMenu.visibility = View.VISIBLE
                colorMenu.bringToFront()
                on1 = false}
            else {
                colorMenu.visibility = View.INVISIBLE
                on1 = true
            }
        }


        potentialSolutions.setOnLongClickListener {
            potentialSolutions.setText("")
            return@setOnLongClickListener true
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
            TC = sharedPreferences.getInt("TC", 0)

            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.yellowcard)
            colorMenu.visibility = View.INVISIBLE
            color1 = 3
            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            logIt("#" + TC + "\n" + potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "yellow", "#" + TC + "\n" + potentialSolutions.text.toString(),null)
        }

        redcard.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.redcard)
            colorMenu.visibility = View.INVISIBLE
            color1 = 4
            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            logIt("#" + TC + "\n" + potentialSolutions.text.toString(), color1)
            Log.d("lily", potentialSolutions.text.toString() + color1)
            writeNewPost("jls", "jls", "red", "#" + TC + "\n" + potentialSolutions.text.toString(),null)
        }

        lime.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.lime)
            colorMenu.visibility = View.INVISIBLE
            color1 = 2
            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            logIt("#" + TC + "\n" + potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "lime", "#" + TC + "\n" + potentialSolutions.text.toString(),null)
        }

        update1.setOnClickListener {

            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.updates)
            colorMenu.visibility = View.INVISIBLE
            color1 = 0

            logIt(potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "update", potentialSolutions.text.toString(),null)
        }

        note2self.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.nonissue)
            colorMenu.visibility = View.INVISIBLE
            color1 = 0
            logIt("note to self: " + potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "note2self", potentialSolutions.text.toString(),null)
        }







        adjust.setOnClickListener {
            var delay1 = sharedPreferences.getInt("delay3", preset1)
            if(delay1 == preset1){
                sharedPreferences.edit().putInt("delay3", 2500).commit()
                Toast.makeText(this, "adjusted to " + 2500,Toast.LENGTH_SHORT).show()
            }
            else if (delay1 == 2500){
                sharedPreferences.edit().putInt("delay3", preset1).commit()
                Toast.makeText(this, "adjusted to " + preset1,Toast.LENGTH_SHORT).show()
            }

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        dateTime.visibility = View.VISIBLE

    /*    TheFrog.setOnLongClickListener {
            switch2(true)
        }*/
 /*       toggle = sharedPreferences.getInt("toggle", 6)


            froggle(toggle)*/



        time.setOnClickListener {
            switch2(true)
        }












/*

        TheFrog.setOnClickListener {
            returnFrog = sharedPreferences.getBoolean("returnFrog", true)

            if(returnFrog){
                TheFrog.visibility = View.INVISIBLE
                TheFrog.animate().scaleX(2F)
                TheFrog.animate().scaleY(2F)
                TheFrog.animate().x((ttf.width/2F)-(TheFrog.width/2))
                TheFrog.visibility = View.VISIBLE
                returnFrog = false
                sharedPreferences.edit().putBoolean("returnFrog", false).commit()

            }

            starRating.rating = 3F

            clean()

            //toggle++
        */
/*    returnFrog = true
            sharedPreferences.edit().putBoolean("returnFrog", true).commit()

            froggle(6)
            sharedPreferences.edit().putInt("toggle", toggle).commit()*//*


        */
/*    if(toggle >=6){
                toggle = 1
                sharedPreferences.edit().putInt("toggle", toggle).commit()
            }*//*

        }
*/







        pos.setOnClickListener {
            var feedback = findViewById<EditText>(R.id.feedback)
            logIt("Stars:" + starRating.rating.toString() + "/6.0 \n" + feedback.text.toString(), 0)
            writeNewPost("jls", "Rating", "Stars: ",starRating.rating.toString() + "/6.0 \n" + feedback.text.toString() + date1 + time.text.toString(),starRating.rating.toInt())


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

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
            logIt("Hang up ", 0)
            writeNewPost("jls", "jls", "phone", "Hang up " + date1 + time.text.toString(),null)

            mediaPlayer = MediaPlayer.create(this, R.raw.hangup)


            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()
        }








        var funnyvar = sharedPreferences.getBoolean("funnyvar", true)
        var funnyvar2 = sharedPreferences.getBoolean("funnyvar2", true)
        var funnyvar3 = sharedPreferences.getBoolean("funnyvar3", true)
        var funnyvar4 = sharedPreferences.getBoolean("funnyvar4", true)
        var threeggle = sharedPreferences.getBoolean("threeggle", true)
        var twoggle = sharedPreferences.getBoolean("twoggle", true)
        var froggie = sharedPreferences.getBoolean("froggie", true)
        var funnyvar5 = sharedPreferences.getBoolean("funnyvar5", true)
        var seriousvar1 = sharedPreferences.getBoolean("seriousvar1", true)

        var reeses1 = sharedPreferences.getBoolean("reeses1" , true)

        var tea1 = sharedPreferences.getBoolean("tea1" , true)
        var clean1 = sharedPreferences.getBoolean("clean1" , true)
        var busy1 = sharedPreferences.getBoolean("busy1" , true)
        var groceries1 = sharedPreferences.getBoolean("groceries1" , true)





        var modeActivated = sharedPreferences.getBoolean("modeActivated", false)



        if(!tea1){
            tea.animate().scaleX(2.3F)
            tea.animate().scaleY(2.3F)
        }
        else{
            tea.animate().scaleX(1F)
            tea.animate().scaleY(1F)
        }

        if(!clean1){
            clean.animate().scaleX(2.3F)
            clean.animate().scaleY(2.3F)
        }
        else{
            clean.animate().scaleX(1F)
            clean.animate().scaleY(1F)
        }

        if(!busy1){
            busy.animate().scaleX(2.3F)
            busy.animate().scaleY(2.3F)
        }
        else{
            busy.animate().scaleX(1F)
            busy.animate().scaleY(1F)
        }

        if(!groceries1){
            groceries.animate().scaleX(2.3F)
            groceries.animate().scaleY(2.3F)
        }
        else{
            groceries.animate().scaleX(1F)
            groceries.animate().scaleY(1F)
        }


        if(!reeses1){
            reeses.animate().scaleX(2.3F)
            reeses.animate().scaleY(2.3F)
        }
        else{
            reeses.animate().scaleX(1F)
            reeses.animate().scaleY(1F)
        }
        if(!funnyvar2){
            starMode.animate().scaleX(2.3F)
            starMode.animate().scaleY(2.3F)
        }
        else{
            starMode.animate().scaleX(1F)
            starMode.animate().scaleY(1F)
        }
        if(!funnyvar4){
            studyMode.animate().scaleX(2.3F)
            studyMode.animate().scaleY(2.3F)
        }
        else{
            studyMode.animate().scaleX(1F)
            studyMode.animate().scaleY(1F)
        }

        if(!threeggle){
            apple.animate().scaleX(2.3F)
            apple.animate().scaleY(2.3F)
        }
        else{
            apple.animate().scaleX(1F)
            apple.animate().scaleY(1F)
        }
        if(!twoggle){
            rest.animate().scaleX(2.3F)
            rest.animate().scaleY(2.3F)
        }
        else{
            rest.animate().scaleX(1F)
            rest.animate().scaleY(1F)
        }

        if(!funnyvar){
            goSkate.animate().scaleX(2.3F)
            goSkate.animate().scaleY(2.3F)
        }
        else{
            goSkate.animate().scaleX(1F)
            goSkate.animate().scaleY(1F)
        }
        if(!froggie){
            caution.animate().scaleX(2.3F)
            caution.animate().scaleY(2.3F)
        }
        else{
            caution.animate().scaleX(1F)
            caution.animate().scaleY(1F)
        }

        if(!funnyvar3){
            nebulizer.animate().scaleX(2.3F)
            nebulizer.animate().scaleY(2.3F)
        }
        else{
            nebulizer.animate().scaleX(1F)
            nebulizer.animate().scaleY(1F)
        }


        if(!funnyvar5){
            auto.animate().scaleX(2.3F)
            auto.animate().scaleY(2.3F)
        }
        else{
            auto.animate().scaleX(1F)
            auto.animate().scaleY(1F)
        }
        if(!seriousvar1){
            sleep.animate().scaleX(2.3F)
            sleep.animate().scaleY(2.3F)
        }
        else{
            sleep.animate().scaleX(1F)
            sleep.animate().scaleY(1F)
        }

























        tea.setOnClickListener {
            if (tea1) {
                //this means I'm studying
                tea.animate().scaleX(2.3F)
                tea.animate().scaleY(2.3F)
                tea1 = false
                writeNewPost("jls", "Mode", "teaMode", "I'm drinking tea dont bother me",null)
                sharedPreferences.edit().putBoolean("tea1", false).commit()

                sharedPreferences.edit().putString("modeVar","tea mode issue count: ").commit()
                turnOffMode(20)


            }
            else if (!tea1){
                tea.animate().scaleX(1F)
                tea.animate().scaleY(1F)
                tea1 = true
                writeNewPost("jls", "Mode", "teaMode", "off",null)
                sharedPreferences.edit().putBoolean("tea1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }




        clean.setOnClickListener {
            if (clean1) {
                //this means I'm studying
                clean.animate().scaleX(2.3F)
                clean.animate().scaleY(2.3F)
                clean1 = false
                writeNewPost("jls", "Mode", "cleanMode", "I'm busy cleaning",null)
                sharedPreferences.edit().putBoolean("clean1", false).commit()

                sharedPreferences.edit().putString("modeVar","clean mode issue count: ").commit()
                turnOffMode(25)


            }
            else if (!clean1){
                clean.animate().scaleX(1F)
                clean.animate().scaleY(1F)
                clean1 = true
                writeNewPost("jls", "Mode", "cleanMode", "off",null)
                sharedPreferences.edit().putBoolean("clean1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }



        busy.setOnClickListener {
            if (busy1) {
                //this means I'm studying
                busy.animate().scaleX(2.3F)
                busy.animate().scaleY(2.3F)
                busy1 = false
                writeNewPost("jls", "Mode", "busyMode", "I am busy please do not bother me",null)
                sharedPreferences.edit().putBoolean("busy1", false).commit()

                Toast.makeText(this, "Busy Quick Tickets is ON for 25m", Toast.LENGTH_SHORT).show()

                sharedPreferences.edit().putString("modeVar","busy mode issue count: ").commit()
                turnOffMode(25)


            }
            else if (!busy1){
                busy.animate().scaleX(1F)
                busy.animate().scaleY(1F)
                busy1 = true
                writeNewPost("jls", "Mode", "busyMode", "off",null)
                sharedPreferences.edit().putBoolean("busy1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }



        groceries.setOnClickListener {
            if (groceries1) {
                //this means I'm studying
                groceries.animate().scaleX(2.3F)
                groceries.animate().scaleY(2.3F)
                groceries1 = false
                //before leaving to groceries I turn this mode on, then I can plan to go safely with support ex. go now it's safe
                writeNewPost("jls", "Mode", "groceriesMode", "I am grocery shopping",null)
                sharedPreferences.edit().putBoolean("groceries1", false).commit()
                Toast.makeText(this, "Groceries Quick Tickets is ON for 15m", Toast.LENGTH_SHORT).show()

                sharedPreferences.edit().putString("modeVar","grocery mode issue count: ").commit()
                turnOffMode(15)


            }
            else if (!groceries1){
                groceries.animate().scaleX(1F)
                groceries.animate().scaleY(1F)
                groceries1 = true
                writeNewPost("jls", "Mode", "groceriesMode", "off",null)
                sharedPreferences.edit().putBoolean("groceries1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }



        reeses.setOnClickListener {
            if (reeses1) {
                reeses.animate().scaleX(2.3F)
                reeses.animate().scaleY(2.3F)
                reeses1 = false
                writeNewPost("jls", "Mode", "reesesMode", "on",null)
                sharedPreferences.edit().putBoolean("reeses1", false).commit()

                sharedPreferences.edit().putString("modeVar","Reeses mode issue count: ").commit()

                turnOffMode(5)




                mediaPlayer = MediaPlayer.create(this, R.raw.reeses)


                mediaPlayer?.setVolume(1f,1f)
                mediaPlayer?.start()



            }
            else if (!reeses1){
                reeses.animate().scaleX(1F)
                reeses.animate().scaleY(1F)
                reeses1 = true
                writeNewPost("jls", "Mode", "reesesMode", "off",null)
                sharedPreferences.edit().putBoolean("reeses1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()


                // Release the MediaPlayer resources when the Activity is destroyed
                mediaPlayer?.release()
                mediaPlayer = null

            }
        }


        starMode.setOnClickListener {
            if (funnyvar2) {
                starMode.animate().scaleX(2.3F)
                starMode.animate().scaleY(2.3F)
                funnyvar2 = false
                writeNewPost("jls", "Mode", "starMode", "on",null)
                sharedPreferences.edit().putBoolean("funnyvar2", false).commit()


                sharedPreferences.edit().putString("modeVar","star mode issue count: ").commit()

                turnOffMode(10)


            }
            else if (!funnyvar2){
                starMode.animate().scaleX(1F)
                starMode.animate().scaleY(1F)
                funnyvar2 = true
                writeNewPost("jls", "Mode", "starMode", "off",null)
                sharedPreferences.edit().putBoolean("funnyvar2", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()


            }
        }

        studyMode.setOnClickListener {
            if (funnyvar4) {
                //this means I'm studying
                studyMode.animate().scaleX(2.3F)
                studyMode.animate().scaleY(2.3F)
                funnyvar4 = false
                writeNewPost("jls", "Mode", "studyMode", "on: I'm working on computer science",null)
                sharedPreferences.edit().putBoolean("funnyvar4", false).commit()

                sharedPreferences.edit().putString("modeVar","study mode issue count: ").commit()
                turnOffMode(25)


            }
            else if (!funnyvar4){
                studyMode.animate().scaleX(1F)
                studyMode.animate().scaleY(1F)
                funnyvar4 = true
                writeNewPost("jls", "Mode", "studyMode", "off",null)
                sharedPreferences.edit().putBoolean("funnyvar4", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }

        apple.setOnClickListener {
            if (threeggle) {
                //this means I'm eating
                apple.animate().scaleX(2.3F)
                apple.animate().scaleY(2.3F)
                threeggle = false
                writeNewPost("jls", "Mode", "eatingMode", "on",null)
                sharedPreferences.edit().putBoolean("threeggle", false).commit()

                sharedPreferences.edit().putString("modeVar","eating mode issue count: ").commit()


                //save minute when clicked on then compare current minute to saved minute if greater than set time for mode then turn off and reset counter

                turnOffMode(20)




            }
            else if (!threeggle){
                apple.animate().scaleX(1F)
                apple.animate().scaleY(1F)
                threeggle = true
                writeNewPost("jls", "Mode", "eatingMode", "off",null)
                sharedPreferences.edit().putBoolean("threeggle", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }

        rest.setOnClickListener {
            if (twoggle) {
                rest.animate().scaleX(2.3F)
                rest.animate().scaleY(2.3F)
                twoggle = false
                writeNewPost("jls", "Mode", "restMode", "on",null)
                sharedPreferences.edit().putBoolean("twoggle", false).commit()
                sharedPreferences.edit().putString("modeVar","rest mode issue count: ").commit()
                turnOffMode(10)


            }
            else if (!twoggle){
                rest.animate().scaleX(1F)
                rest.animate().scaleY(1F)
                twoggle = true
                writeNewPost("jls", "Mode", "restMode", "off",null)
                sharedPreferences.edit().putBoolean("twoggle", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }

        }

        goSkate.setOnClickListener {
            if(funnyvar) {
                goSkate.animate().rotation(360F)
                goSkate.animate().scaleX(2.3F)
                goSkate.animate().scaleY(2.3F)
                funnyvar = false
                writeNewPost("jls", "Mode", "skatingMode", "on, safety first",null)
                sharedPreferences.edit().putBoolean("funnyvar", false).commit()

                sharedPreferences.edit().putString("modeVar","skating mode issue count: ").commit()
                turnOffMode(30)


            }
            else if (!funnyvar){
                goSkate.animate().rotation(0F)
                goSkate.animate().scaleX(1F)
                goSkate.animate().scaleY(1F)
                funnyvar = true
                writeNewPost("jls", "Mode", "skatingMode", "off",null)
                sharedPreferences.edit().putBoolean("funnyvar", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }

        nebulizer.setOnClickListener {
            if(funnyvar3) {
                nebulizer.animate().scaleY(1.8F)
                nebulizer.animate().scaleX(1.8F)
                funnyvar3 = false
                sharedPreferences.edit().putBoolean("funnyvar3", false).commit()

                sharedPreferences.edit().putString("modeVar","smoke testing mode issue count: ").commit()
                turnOffMode(10)


            }
            else if (!funnyvar3){
                nebulizer.animate().scaleX(1F)
                nebulizer.animate().scaleY(1F)
                funnyvar3 = true
                sharedPreferences.edit().putBoolean("funnyvar3", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }

        auto.setOnClickListener {
            if(funnyvar5) {
                auto.animate().scaleY(1.8F)
                auto.animate().scaleX(1.8F)
                funnyvar5 = false
                sharedPreferences.edit().putBoolean("funnyvar5", false).commit()

                sharedPreferences.edit().putString("modeVar","auto mode issue count: ").commit()

                Toast.makeText(this, "Auto Quick Tickets is ON", Toast.LENGTH_SHORT).show()
                turnOffMode(15)



            }
            else if (!funnyvar5){
                auto.animate().scaleX(1F)
                auto.animate().scaleY(1F)
                funnyvar5 = true
                sharedPreferences.edit().putBoolean("funnyvar5", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }

        sleep.setOnClickListener {
            if(seriousvar1) {
                sleep.animate().scaleY(1.8F)
                sleep.animate().scaleX(1.8F)
                seriousvar1 = false
                sharedPreferences.edit().putBoolean("seriousvar1", false).commit()

                sharedPreferences.edit().putString("modeVar","sleep mode issue count: ").commit()

                Toast.makeText(this, "15 min Sleep Quick Tickets is ON", Toast.LENGTH_SHORT).show()
                turnOffMode(15)



            }
            else if (!seriousvar1){
                sleep.animate().scaleX(1F)
                sleep.animate().scaleY(1F)
                seriousvar1 = true
                sharedPreferences.edit().putBoolean("seriousvar1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
        }

        sleep.setOnLongClickListener {
            if(seriousvar1) {
                sleep.animate().scaleY(1.8F)
                sleep.animate().scaleX(1.8F)
                seriousvar1 = false
                sharedPreferences.edit().putBoolean("seriousvar1", false).commit()

                sharedPreferences.edit().putString("modeVar","sleep mode issue count: ").commit()

                Toast.makeText(this, "7 hr Sleep Quick Tickets is ON", Toast.LENGTH_SHORT).show()
                turnOffMode(60*7)



            }
            else if (!seriousvar1){
                sleep.animate().scaleX(1F)
                sleep.animate().scaleY(1F)
                seriousvar1 = true
                sharedPreferences.edit().putBoolean("seriousvar1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

            }
            return@setOnLongClickListener true
        }
        /*        var modeActivated = sharedPreferences.getBoolean("modeActivated", false)

                nebulizer.setOnClickListener {
                    modeActivated = sharedPreferences.getBoolean("modeActivated", true)
                    if(!modeActivated){
                        Toast.makeText(this, "Nebulizer Quick Tickets is ON", Toast.LENGTH_SHORT).show()
                        nebulizer.animate().scaleY(1.8F)
                        nebulizer.animate().scaleX(1.8F)
                        sharedPreferences.edit().putBoolean("modeActivated", true).commit()
                        sharedPreferences.edit().putString("modeVar","Project Nebula count:").commit()

                        oneMode("projectMode")
                    }
                    else{

                        //note for the future: download the sharedpref log then search this string among tickets to see data. it should smell like the original product. You can ID them a few seconds before each timestaamp for an upgrade. There are rewards
                        Toast.makeText(this, "Nebulizer Quick Tickets is OFF", Toast.LENGTH_SHORT).show()
                        sharedPreferences.edit().putBoolean("modeActivated", false).commit()
                        nebulizer.animate().scaleX(1F)
                        nebulizer.animate().scaleY(1F)


                    }
                }
                if(modeActivated){
                    nebulizer.animate().scaleY(1.8F)
                    nebulizer.animate().scaleX(1.8F)
                }
                else{
                    nebulizer.animate().scaleX(1F)
                    nebulizer.animate().scaleY(1F)
                }







                var auto = findViewById<ImageView>(R.id.automobile)



                var modeActivated1 = sharedPreferences.getBoolean("modeActivated1", false)

                auto.setOnClickListener {
                    modeActivated1 = sharedPreferences.getBoolean("modeActivated1", true)
                    if(!modeActivated){
                        Toast.makeText(this, "Auto Quick Tickets is ON", Toast.LENGTH_SHORT).show()
                        nebulizer.animate().scaleY(1.8F)
                        nebulizer.animate().scaleX(1.8F)
                        sharedPreferences.edit().putBoolean("modeActivated", true).commit()
                        sharedPreferences.edit().putString("modeVar","Driving count:").commit()

                        oneMode("drivingMode")
                    }
                    else{

                        //note for the future: download the sharedpref log then search this string among tickets to see data. it should smell like the original product. You can ID them a few seconds before each timestaamp for an upgrade. There are rewards
                        Toast.makeText(this, "Auto Quick Tickets is OFF", Toast.LENGTH_SHORT).show()
                        sharedPreferences.edit().putBoolean("modeActivated", false).commit()
                        nebulizer.animate().scaleX(1F)
                        nebulizer.animate().scaleY(1F)


                    }
                }
                if(modeActivated){
                    nebulizer.animate().scaleY(1.8F)
                    nebulizer.animate().scaleX(1.8F)
                }
                else{
                    nebulizer.animate().scaleX(1F)
                    nebulizer.animate().scaleY(1F)
                }*/















        caution.setOnLongClickListener {
            lights1()
            return@setOnLongClickListener true
        }

        caution.setOnClickListener {
            if (froggie) {
                caution.animate().scaleX(2.3F)
                caution.animate().scaleY(2.3F)
                froggie = false
                writeNewPost("jls", "Mode", "cautionMode", "on, safety first" + dateTime,null)
                logIt("cautionMode: on, safety first", 3)
                sharedPreferences.edit().putBoolean("froggie", false).commit()


            }
            else if (!froggie){
                caution.animate().scaleX(1F)
                caution.animate().scaleY(1F)
                froggie = true
                writeNewPost("jls", "Mode", "cautionMode", "off" + date1 + time.text.toString(),null)
                logIt("cautionMode: now off", 0)
                sharedPreferences.edit().putBoolean("froggie", true).commit()

            }
        }

        if(sharedPreferences.getBoolean("safety", true)){
        }
        else{
            Toast.makeText(this, "safety is off", Toast.LENGTH_SHORT).show()
        }








        ticket.setOnLongClickListener {
            switch()
        }

        ticket.setOnClickListener {

            if (giggle1) {
                //opens a ticket todo add sharedpref
       /*         TheFrog.animate().scaleX(.5F)
                TheFrog.animate().scaleY(.5F)*/
                //TheFrog.animate().x(10F)
                //returnFrog = true
                //TheFrog.visibility = View.INVISIBLE
                sharedPreferences.edit().putBoolean("returnFrog", true).commit()
                clean()
                ttf.visibility = View.VISIBLE
                ttf.bringToFront()
                dateTime.visibility = View.VISIBLE
                adjust.bringToFront()
            }
        }


















        var QT = sharedPreferences.getInt("QT", 0)
        var QTCount = sharedPreferences.getInt("QTCount",1)
        var current1 = sharedPreferences.getString("current","")

        dateTime.visibility = View.VISIBLE

        //gear should be sized in oncreate and resume scenarios
        QT = sharedPreferences.getInt("QT", 0)
        if(QT==1) {
            QTBtn.animate().scaleX(2F)
            QTBtn.animate().scaleY(2F)
        }
        else {
            QTBtn.animate().scaleX(1F)
            QTBtn.animate().scaleY(1F)
        }

        //gear
        //just tap to save count no need for final post
        QTBtn.setOnClickListener {
            QT = sharedPreferences.getInt("QT", 0)
            if(QT==0){

                QTCount = sharedPreferences.getInt("QTCount", 0)
                TC = sharedPreferences.getInt("TC", 0)
                QTCount++
                TC++
                sharedPreferences.edit().putInt("TC", TC).commit()

                sharedPreferences.edit().putInt("QTCount", QTCount).commit()

                QTBtn.animate().scaleX(2F)
                QTBtn.animate().scaleY(2F)
                QT = 1
                var theItem = potentialSolutions.text.toString() + "\n" + date1 + "\n" + time.text.toString() + " level: " + color1.toString() +  " "
                // var theItem = potentialSolutions.text.toString() + "\n" + date1 + "\n" + time.text.toString() + " level: " + color1.toString() +  " "
                logIt("#" + TC + "\n" + "Tick: " + QTCount + "\n" + potentialSolutions.text.toString() + "\n", 0)

                //logIt(potentialSolutions.text.toString(), color1)
                writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + "Tick:" + QTCount.toString(), null)
                sharedPreferences.edit().putString("current", theItem).commit()
                sharedPreferences.edit().putInt("QT", 1).commit()

                /*          sharedPreferences.edit().putInt("pastDifference", LocalDateTime.now().minute).commit()
                          sharedPreferences.edit().putInt("anHour4mNow", LocalDateTime.now().minute + tgoal).commit()*/
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

        Log.d("lily2", "days that have passed: " + sharedPreferences.getInt("dogdays", 0))

















        var lastSaveDate = sharedPreferences.getInt("lsd", day.toInt())
        var counter = sharedPreferences.getInt("counter", 0)

        var daycounter = sharedPreferences.getInt("daycounter", 0)

        //make button or text clickable
        //set var to zero on click
        //long click to reset
        dogDays.setOnLongClickListener {
            counter = 0
            sharedPreferences.edit().putInt("counter", counter).commit()
            dogDays.text = counter.toString()
            return@setOnLongClickListener true
            //reset time displayed to 0
        }

        //increments when day is different to yesterday






        if(day.toInt() != lastSaveDate){
            counter++
            sharedPreferences.edit().putInt("counter", counter).commit()


            daycounter++
            sharedPreferences.edit().putInt("daycounter", daycounter).commit()


            //reset counters
            sharedPreferences.edit().putInt("QTCount", 0).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
            sharedPreferences.edit().putInt("QT1C", 0).commit()
            sharedPreferences.edit().putInt("QT2C", 0).commit()
            sharedPreferences.edit().putInt("QT3C", 0).commit()
            sharedPreferences.edit().putInt("QT4C", 0).commit()
            sharedPreferences.edit().putInt("QT5C", 0).commit()
            sharedPreferences.edit().putInt("QT6C", 0).commit()
            sharedPreferences.edit().putInt("QT7C", 0).commit()
            sharedPreferences.edit().putInt("QT8C", 0).commit()
            sharedPreferences.edit().putInt("QT9C", 0).commit()
            sharedPreferences.edit().putInt("QT10C", 0).commit()
            sharedPreferences.edit().putInt("QT11C", 0).commit()
            sharedPreferences.edit().putInt("QT12C", 0).commit()
            sharedPreferences.edit().putInt("QT13C", 0).commit()
            sharedPreferences.edit().putInt("QT14C", 0).commit()
            sharedPreferences.edit().putInt("QT15C", 0).commit()
            sharedPreferences.edit().putInt("QT16C", 0).commit()
            sharedPreferences.edit().putInt("QT17C", 0).commit()
            sharedPreferences.edit().putInt("QT18C", 0).commit()

            sharedPreferences.edit().putInt("TC", 0).commit()

            saveSharedPreferencestoExternal(this, "daysSince", "bsl" + month + day + year)
            Toast.makeText(this, "New Day", Toast.LENGTH_SHORT).show()


        }
        sharedPreferences.edit().putInt("lsd", day.toInt()).commit()
        dogDays.text = counter.toString()

        monaLista(0)

        bsl.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,bslist)


        monaLista2(0)
        bsl.setOnItemClickListener{ parent, view, position, id ->


            if(bslist.get(position).contains("level: 1") == true){
                //list item background color is green
                currentItemColor.setImageResource(R.drawable.good)
                Log.d("lily", "1")

            }
            if(bslist.get(position).contains("level: 2") == true ){
                //list item background color is green
                currentItemColor.setImageResource(R.drawable.yellowcard)
                Log.d("lily", "2")


            }
            if(bslist.get(position).contains("level: 3") == true){
                currentItemColor.setImageResource(R.drawable.redcard)
                //list item background color is green

                Log.d("lily", "HERE")


            }

        }


        //all rewards are redeemable, open to discussion
        //if there is one good week:
        if(counter == 7){
            //you win, go to a beach, a club, a new farmers market, etc
            ticket.setImageResource(R.drawable.youwon)
        }
        //if there is one good month, that is 30 days
        if(counter == 30){
            //You win a prize, choose an outing, a request, vote on one thing
            ticket.setImageResource(R.drawable.winner1)
        }


































        var minus1 = findViewById<TextView>(R.id.minus1)


        minus1.setOnClickListener {

            //todo if mode is on subtract from mode too
            funnyvar = sharedPreferences.getBoolean("funnyvar", true)
            funnyvar2 = sharedPreferences.getBoolean("funnyvar2", true)
            funnyvar3 = sharedPreferences.getBoolean("funnyvar3", true)
            funnyvar4 = sharedPreferences.getBoolean("funnyvar4", true)
            threeggle = sharedPreferences.getBoolean("threeggle", true)
            twoggle = sharedPreferences.getBoolean("twoggle", true)
            funnyvar5 = sharedPreferences.getBoolean("funnyvar5", true)
            seriousvar1 = sharedPreferences.getBoolean("seriousvar1", true)
            reeses1 = sharedPreferences.getBoolean("reeses1", true)
            tea1 = sharedPreferences.getBoolean("tea1" , true)
            clean1 = sharedPreferences.getBoolean("clean1" , true)
            busy1 = sharedPreferences.getBoolean("busy1" , true)
            groceries1 = sharedPreferences.getBoolean("groceries1" , true)




            var modeCount = sharedPreferences.getInt("modeCount", 0)
            if (!funnyvar || !funnyvar2 || !funnyvar3 || !funnyvar4 || !threeggle || !twoggle || !funnyvar5 || !seriousvar1 || !reeses1 || !tea1 || !clean1 || !busy1 || !groceries1) {
                if(modeCount <= 0){

                }
                else {
                    modeCount--
                    sharedPreferences.edit().putInt("modeCount", modeCount).commit()
                }
            }
            var QTCount = sharedPreferences.getInt("QTCount", 1)
            if(QTCount <= 0){

            }
            else {

                QTCount--
                sharedPreferences.edit().putInt("QTCount", QTCount).commit()
            }
            //potentialSolutions.setText("Count: " + QTCount.toString() + "\n" + current1 + time.text.toString())
            logIt("overwrite last count(s)", 0)
            writeNewPost("jls", date1, time.text.toString(), "overwrite last count(s)", null)
            pressesMinus = true
            TC = sharedPreferences.getInt("TC", 0)

            if(TC <=0){

            }
            else {
                TC--
                sharedPreferences.edit().putInt("TC", TC).commit()
            }
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

        }

        var plus1 = findViewById<TextView>(R.id.plus1)
        var plus2 = findViewById<TextView>(R.id.plus2)




        plus1.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var modeCount = sharedPreferences.getInt("modeCount", 0)
            var modeActivated = sharedPreferences.getBoolean("modeActivated", false)
            var modeVar = sharedPreferences.getString("modeVar", "Current Mode count: ")

            QTCount = sharedPreferences.getInt("QTCount", 1)
            QTCount++
            Toast.makeText(this@Main, QTCount.toString(), Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putInt("QTCount", QTCount).commit()

            // current1 = sharedPreferences.getString("current","")

            //consider using ticket for mode or status for mode
            if (modeActivated == true){
                modeCount++
                Toast.makeText(this@Main, "#" + TC + "\n" + "QTC: " + QTCount.toString() + "\nMC: " + modeCount, Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putInt("modeCount", modeCount).commit()
                logIt("#" + TC + "\n" + modeVar + modeCount + "\n", 0) //includes date, time, level
                writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + modeVar + modeCount, null)
            }

            potentialSolutions.setText("Tick: " + QTCount.toString() + "\n" + current1 + time.text.toString() + date1) //if you want to add a note after hue adds date time
            logIt("#" + TC + "\n" + "Tick: " + QTCount + "\n" + current1 + "\n", 0)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + "current count:" + QTCount.toString() + " " + current1, null)

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
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

            var modeCount = sharedPreferences.getInt("modeCount", 0)
            var modeActivated = sharedPreferences.getBoolean("modeActivated", false)
            var modeVar = sharedPreferences.getString("modeVar", "Current Mode count: ")

            QTCount = sharedPreferences.getInt("QTCount", 1)
            QTCount++
            Toast.makeText(this@Main, QTCount.toString(), Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putInt("QTCount", QTCount).commit()
            TC = sharedPreferences.getInt("TC", 0)+

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            if (modeActivated == true){
                modeCount++
                Toast.makeText(this@Main, "QTC: " + QTCount.toString() + "\nMC: " + modeCount, Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putInt("modeCount", modeCount).commit()
                logIt("#" + TC + "\n" + modeVar + modeCount + "\n", 0) //includes date, time, level
                writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + modeVar + modeCount, null)
            }

            potentialSolutions.setText("#" + TC + "\n" + "Tick: " + QTCount.toString() + "\n" + current1 + time.text.toString() + date1) //if you want to add a note after hue adds date time
            logIt("#" + TC + "\n" + "Tick: " + QTCount + "\n" + current1 + "\n", 0)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + "current count:" + QTCount.toString() + " " + current1, null)

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(250)
            }



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


        checkHourGoal()

        currentItemColor.setOnClickListener(){

            saveSharedPreferencestoExternal(this, "daysSince", "bsl" + month + day + year)
        }



        var input1 = findViewById<EditText>(R.id.input)
        var inputEnter = findViewById<TextView>(R.id.inputEnter)
        var QTB = 0

        var QTOne = findViewById<TextView>(R.id.one)
        var QT2 = findViewById<TextView>(R.id.two)
        var QT3 = findViewById<TextView>(R.id.three)
        var QT4 = findViewById<TextView>(R.id.four)
        var QT5 = findViewById<TextView>(R.id.five)
        var QT6 = findViewById<TextView>(R.id.six)
        var QT7 = findViewById<TextView>(R.id.seven)
        var QT8 = findViewById<TextView>(R.id.eight)
        var QT9 = findViewById<TextView>(R.id.nine)
        var QT10 = findViewById<TextView>(R.id.ten)
        var QT11 = findViewById<TextView>(R.id.eleven)
        var QT12 = findViewById<TextView>(R.id.twelve)
        var QT13 = findViewById<TextView>(R.id.thirteen)
        var QT14 = findViewById<TextView>(R.id.fourteen)
        var QT15 = findViewById<TextView>(R.id.fifteen)
        var QT16 = findViewById<TextView>(R.id.sixteen)
        var QT17 = findViewById<TextView>(R.id.seventeen)
        var QT18 = findViewById<TextView>(R.id.eighteen)


        QTOne.setText(sharedPreferences.getString("QTB1","      "))
        QT2.setText(sharedPreferences.getString("QTB2","      "))
        QT3.setText(sharedPreferences.getString("QTB3","      "))
        QT4.setText(sharedPreferences.getString("QTB4","      "))
        QT5.setText(sharedPreferences.getString("QTB5","      "))
        QT6.setText(sharedPreferences.getString("QTB6","      "))
        QT7.setText(sharedPreferences.getString("QTB7","      "))
        QT8.setText(sharedPreferences.getString("QTB8","      "))
        QT9.setText(sharedPreferences.getString("QTB9","      "))
        QT10.setText(sharedPreferences.getString("QTB10","      "))
        QT11.setText(sharedPreferences.getString("QTB11","      "))
        QT12.setText(sharedPreferences.getString("QTB12","      "))
        QT13.setText(sharedPreferences.getString("QTB13","      "))
        QT14.setText(sharedPreferences.getString("QTB14","      "))
        QT15.setText(sharedPreferences.getString("QTB15","      "))
        QT16.setText(sharedPreferences.getString("QTB16","      "))
        QT17.setText(sharedPreferences.getString("QTB17","      "))
        QT18.setText(sharedPreferences.getString("QTB18","      "))




        QTOne.setOnLongClickListener {
            QTB = 1
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT2.setOnLongClickListener {
            QTB = 2
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT3.setOnLongClickListener {
            QTB = 3
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT4.setOnLongClickListener {
            QTB = 4
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT5.setOnLongClickListener {
            QTB = 5
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT6.setOnLongClickListener {
            QTB = 6
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT7.setOnLongClickListener {
            QTB = 7
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT8.setOnLongClickListener {
            QTB = 8
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT9.setOnLongClickListener {
            QTB = 9
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT10.setOnLongClickListener {
            QTB = 10
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
          QT11.setOnLongClickListener {
              QTB = 11
              Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
              return@setOnLongClickListener true
          }

        QT12.setOnLongClickListener {
            QTB = 12
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
        QT13.setOnLongClickListener {
            QTB = 13
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
        QT14.setOnLongClickListener {
            QTB = 14
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        QT15.setOnLongClickListener {
            QTB = 15
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
        QT16.setOnLongClickListener {
            QTB = 16
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
        QT17.setOnLongClickListener {
            QTB = 17
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
        QT18.setOnLongClickListener {
            QTB = 18
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }



        QTOne.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT1C = sharedPreferences.getInt("QT1C", 0)

            QT1C++
            sharedPreferences.edit().putInt("QT1C", QT1C).commit()
            Toast.makeText(this, "QT1C: " + QT1C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QTOne.text.toString() + " Count: " + QT1C, 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QTOne.text.toString() + " Count:" + QT1C.toString(), null)

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }


        }
        QT2.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT2C = sharedPreferences.getInt("QT2C", 0)
            QT2C++
            sharedPreferences.edit().putInt("QT2C", QT2C).commit()
            Toast.makeText(this, "QT2C: " + QT2C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT2.text.toString() + " Count: " + QT2C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT2.text.toString() + " Count:" + QT2C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT3.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT3C = sharedPreferences.getInt("QT3C", 0)
            QT3C++
            sharedPreferences.edit().putInt("QT3C", QT3C).commit()
            Toast.makeText(this, "QT3C: " + QT3C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT3.text.toString() + " Count: " + QT3C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT3.text.toString() + " Count:" + QT3C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT4.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT4C = sharedPreferences.getInt("QT4C", 0)
            QT4C++
            sharedPreferences.edit().putInt("QT4C", QT4C).commit()
            Toast.makeText(this, "QT4C: " + QT4C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT4.text.toString() + " Count: " + QT4C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT4.text.toString() + " Count:" + QT4C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT5.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT5C = sharedPreferences.getInt("QT5C", 0)
            QT5C++
            sharedPreferences.edit().putInt("QT5C", QT5C).commit()
            Toast.makeText(this, "QT5C: " + QT5C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT5.text.toString() + " Count: " + QT5C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT5.text.toString() + " Count:" + QT5C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }


        //todo finish background color
        QT6.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT6C = sharedPreferences.getInt("QT6C", 0)
            QT6C++
            sharedPreferences.edit().putInt("QT6C", QT6C).commit()
            Toast.makeText(this, "QT6C: " + QT6C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT6.text.toString() + " Count: " + QT6C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT6.text.toString() + " Count:" + QT6C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT7.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT7C = sharedPreferences.getInt("QT7C", 0)
            QT7C++
            sharedPreferences.edit().putInt("QT7C", QT7C).commit()
            Toast.makeText(this, "QT7C: " + QT7C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT7.text.toString() + " Count: " + QT7C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT7.text.toString() + " Count:" + QT7C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT8.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT8C = sharedPreferences.getInt("QT8C", 0)
            QT8C++
            sharedPreferences.edit().putInt("QT8C", QT8C).commit()
            Toast.makeText(this, "QT8C: " + QT8C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT8.text.toString() + " Count: " + QT8C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT8.text.toString() + " Count:" + QT8C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT9.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT9C = sharedPreferences.getInt("QT9C", 0)
            QT9C++
            sharedPreferences.edit().putInt("QT9C", QT9C).commit()
            Toast.makeText(this, "QT9C: " + QT9C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT9.text.toString() + " Count: " + QT9C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT9.text.toString() + " Count:" + QT9C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT10.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT10C = sharedPreferences.getInt("QT10C", 0)
            QT10C++
            sharedPreferences.edit().putInt("QT10C", QT10C).commit()
            Toast.makeText(this, "QT10C: " + QT10C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT10.text.toString() + " Count: " + QT10C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT10.text.toString() + " Count:" + QT10C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT11.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT11C = sharedPreferences.getInt("QT11C", 0)
            QT11C++
            sharedPreferences.edit().putInt("QT11C", QT11C).commit()
            Toast.makeText(this, "QT11C: " + QT11C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT11.text.toString() + " Count: " + QT11C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT11.text.toString() + " Count:" + QT11C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT12.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT12C = sharedPreferences.getInt("QT12C", 0)
            QT12C++
            sharedPreferences.edit().putInt("QT12C", QT12C).commit()
            Toast.makeText(this, "QT12C: " + QT12C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT12.text.toString() + " Count: " + QT12C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT12.text.toString() + " Count:" + QT12C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }

        QT13.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT13C = sharedPreferences.getInt("QT13C", 0)
            QT13C++
            sharedPreferences.edit().putInt("QT13C", QT13C).commit()
            Toast.makeText(this, "QT13C: " + QT13C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT13.text.toString() + " Count: " + QT13C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT13.text.toString() + " Count:" + QT13C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT14.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT14C = sharedPreferences.getInt("QT14C", 0)
            QT14C++
            sharedPreferences.edit().putInt("QT14C", QT14C).commit()
            Toast.makeText(this, "QT14C: " + QT14C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT14.text.toString() + " Count: " + QT14C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT14.text.toString() + " Count:" + QT14C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT15.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT15C = sharedPreferences.getInt("QT15C", 0)
            QT15C++
            sharedPreferences.edit().putInt("QT15C", QT15C).commit()
            Toast.makeText(this, "QT15C: " + QT15C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT15.text.toString() + " Count: " + QT15C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT15.text.toString() + " Count:" + QT15C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }


        QT16.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT16C = sharedPreferences.getInt("QT16C", 0)
            QT16C++
            sharedPreferences.edit().putInt("QT16C", QT16C).commit()
            Toast.makeText(this, "QT16C: " + QT16C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT16.text.toString() + " Count: " + QT16C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT16.text.toString() + " Count:" + QT16C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT17.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT17C = sharedPreferences.getInt("QT17C", 0)
            QT17C++
            sharedPreferences.edit().putInt("QT17C", QT17C).commit()
            Toast.makeText(this, "QT17C: " + QT17C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT17.text.toString() + " Count: " + QT17C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT17.text.toString() + " Count:" + QT17C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }
        QT18.setOnClickListener {
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT18C = sharedPreferences.getInt("QT18C", 0)
            QT18C++
            sharedPreferences.edit().putInt("QT18C", QT18C).commit()
            Toast.makeText(this, "QT18C: " + QT18C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT18.text.toString() + " Count: " + QT18C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT18.text.toString() + " Count:" + QT18C.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }





        inputEnter.setOnClickListener {

            if(QTB !=0){
                sharedPreferences.edit().putString("QTB" + QTB.toString(), input1.text.toString()).commit()
                //if a button is selected then its count is reset on enter click
                sharedPreferences.edit().putInt("QT"+QTB+"C",0).commit()
            }

            if(QTB == 1) {
                QTOne.text = input1.text
                sharedPreferences.edit().putInt("QT1C", 0).commit()
                QTB = 0

            }
            if(QTB == 2) {
                QT2.text = input1.text
                sharedPreferences.edit().putInt("QT2C", 0).commit()
                QTB = 0
            }
            if(QTB == 3) {
                QT3.text = input1.text
                sharedPreferences.edit().putInt("QT3C", 0).commit()
                QTB = 0
            }
            if(QTB == 4) {
                QT4.text = input1.text
                sharedPreferences.edit().putInt("QT4C", 0).commit()
                QTB = 0
            }
            if(QTB == 5) {
                QT5.text = input1.text
                sharedPreferences.edit().putInt("QT5C", 0).commit()
                QTB = 0
            }
            if(QTB == 6) {
                QT6.text = input1.text
                sharedPreferences.edit().putInt("QT6C", 0).commit()
                QTB = 0
            }
            if(QTB == 7) {
                QT7.text = input1.text
                sharedPreferences.edit().putInt("QT7C", 0).commit()
                QTB = 0
            }
            if(QTB == 8) {
                QT8.text = input1.text
                sharedPreferences.edit().putInt("QT8C", 0).commit()
                QTB = 0
            }
            if(QTB == 9) {
                QT9.text = input1.text
                sharedPreferences.edit().putInt("QT9C", 0).commit()
                QTB = 0
            }
            if(QTB == 10) {
                QT10.text = input1.text
                sharedPreferences.edit().putInt("QT10C", 0).commit()
                QTB = 0
            }
                 if(QTB == 11) {
                     QT11.text = input1.text
                     sharedPreferences.edit().putInt("QT11C", 0).commit()
                     QTB = 0
                 }
            if(QTB == 12) {
                QT12.text = input1.text
                sharedPreferences.edit().putInt("QT12C", 0).commit()
                QTB = 0
            }
            if(QTB == 13) {
                QT13.text = input1.text
                sharedPreferences.edit().putInt("QT13C", 0).commit()
                QTB = 0
            }
            if(QTB == 14) {
                QT14.text = input1.text
                sharedPreferences.edit().putInt("QT14C", 0).commit()
                QTB = 0
            }
            if(QTB == 15) {
                QT15.text = input1.text
                sharedPreferences.edit().putInt("QT15C", 0).commit()
                QTB = 0
            }
            if(QTB == 16) {
                QT16.text = input1.text
                sharedPreferences.edit().putInt("QT16C", 0).commit()
                QTB = 0
            }
            if(QTB == 17) {
                QT17.text = input1.text
                sharedPreferences.edit().putInt("QT17C", 0).commit()
                QTB = 0
            }
            if(QTB == 18) {
                QT18.text = input1.text
                sharedPreferences.edit().putInt("QT18C", 0).commit()
                QTB = 0
            }

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(250)
            }

        }




        input1.setOnLongClickListener {
            input1.setText("")
            return@setOnLongClickListener true
        }



        var change1 = findViewById<TextView>(R.id.change1)

        change1.setOnClickListener {
            var testletters = input1.text

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
            input1.setText(append1.toString())
        }


        guitar.setOnClickListener {

            //todo test this
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.release()
                mediaPlayer = null
            }
            else {
                mediaPlayer = MediaPlayer.create(this, R.raw.pingle)


                mediaPlayer?.setVolume(1f, 1f)
                mediaPlayer?.start()

                Toast.makeText(this, "pingle", Toast.LENGTH_SHORT).show()

                Log.d("lily", "dfiq")

            }



        }

        walkie.setOnClickListener {
            //another button can write
            Toast.makeText(this, "talkie", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.pingle2)

            Log.d("lily", "ping")
            mediaPlayer?.setVolume(1f,1f)

            mediaPlayer?.start()



        }

        infomessage.setOnClickListener {
            //another button can write
            Toast.makeText(this, "infomessage", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.jingle)

            Log.d("lily", "informessage")
            mediaPlayer?.setVolume(1f,1f)

            mediaPlayer?.start()



        }


        ping.setOnClickListener {
            //another button can write
            Toast.makeText(this, "ping", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.keyboard)

            Log.d("lily", "ping")
            mediaPlayer?.setVolume(1f,1f)

            mediaPlayer?.start()



        }

        infoping.setOnClickListener {
            //another button can write
            Toast.makeText(this, "infoping", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.bell)

            Log.d("lily", "ping2")
            mediaPlayer?.setVolume(1f,1f)

            mediaPlayer?.start()



        }


        //free up resources
        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }











        //the buttons
        var modesButton = findViewById<TextView>(R.id.modesButton)
        var phoneButton = findViewById<TextView>(R.id.phoneButton)
        var bslistButton = findViewById<TextView>(R.id.bslistbutton)
        var theButtonsButton = findViewById<TextView>(R.id.buttons)

        var righton = findViewById<ImageView>(R.id.righton)

        var nogood = findViewById<ImageView>(R.id.nogood)
        var leavemealone = findViewById<ImageView>(R.id.leavemealone)

        var stop = findViewById<ImageView>(R.id.stop)
        var off = findViewById<ImageView>(R.id.off)
        var lost = findViewById<ImageView>(R.id.lost)

        var bye = findViewById<ImageView>(R.id.bye)
        var turnitoff = findViewById<ImageView>(R.id.turnitoff)





        bye.setOnClickListener {
            Toast.makeText(this, "bye", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.bye)
            Log.d("lily", "bye")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

        }


        //turns off their bbgn
        turnitoff.setOnClickListener {
            Toast.makeText(this, "turn it off", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.turnitoff)
            Log.d("lily", "turn it off")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

        }








        lost.setOnClickListener {
            //make this a song
            Toast.makeText(this, "you lost", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.lost)
            Log.d("lily", "they lost")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

        }

        stop.setOnClickListener {
            //make this a song
            Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.stop)
            Log.d("lily", "stop")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

        }

        off.setOnClickListener {
            //make this a song
            Toast.makeText(this, "shut off", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.off)
            Log.d("lily", "shutoff")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

        }



        nogood.setOnClickListener {
            //make this a song
            Toast.makeText(this, "good riddance", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.hesnogood)
            //Log.d("lily", "right on")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

        }

        leavemealone.setOnClickListener {
            //make this a song
            Toast.makeText(this, "leave me alone", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.leavejeffreyalone)
            //Log.d("lily", "right on")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

        }


        righton.setOnClickListener {
            //make this a song
            Toast.makeText(this, "right on", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.righton)
            Log.d("lily", "right on")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

        }


        theButtonsButton.setOnClickListener {
            froggle(6)
            sharedPreferences.edit().putInt("toggle", 6).commit()
        }

        phoneButton.setOnClickListener {
            froggle(2)
            sharedPreferences.edit().putInt("toggle", 2).commit()

        }
        modesButton.setOnClickListener {
            froggle(3)
            sharedPreferences.edit().putInt("toggle", 3).commit()

        }






        QQTS.setOnClickListener {
            sharedPreferences.edit().putBoolean("returnFrog", true).commit()

            returnFrog = true
            sharedPreferences.edit().putInt("toggle", 4).commit()
            froggle(4)

        }


        bslistButton.setOnClickListener {
            froggle(5)
            sharedPreferences.edit().putInt("toggle", 5).commit()

        }









//todo hide list after opening
        // make sure frog is in the correct place all the time





        //TheFrog.performClick()

        var backup = findViewById<TextView>(R.id.backup)
        var resetBox = findViewById<ImageView>(R.id.resetBox)
        var backupLayout = findViewById<RelativeLayout>(R.id.backupLayout)

        var version = sharedPreferences.getInt("version", 0)
        backup.setOnClickListener {
            version++
            backUpLog("Version: " + version + "\n"+ potentialSolutions.text.toString())
            Toast.makeText(this, "requesting backup", Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putInt("version", version).commit()
            //writeNewUser("backup request", "backup request", "fake@mail.com")
            writeNewPost("back up request", date1, time.text.toString(), "Version: " + version + "\n"+ potentialSolutions.text.toString(), null)


        }

        backup.setOnLongClickListener {
            //show list
            clean()
       /*     TheFrog.animate().scaleX(.75F)
            TheFrog.animate().scaleY(.75F)
            TheFrog.animate().x(10F)*/
            returnFrog = true
            sharedPreferences.edit().putBoolean("returnFrog", true).commit()
            backupLayout.visibility = View.VISIBLE
            backupLayout.bringToFront()
            Log.d("lily", backupReminders1.toString() + " Here it is")
            // orderList()
            dateTime.visibility = View.VISIBLE
            ticket.visibility = View.VISIBLE


            return@setOnLongClickListener true
        }

        var safety1 = 0
        resetBox.setOnClickListener {
            safety1++
            if(safety1 == 7){
                Toast.makeText(this, "requesting reset", Toast.LENGTH_SHORT).show()
            }

        }




/*

        TheFrog.visibility = View.INVISIBLE
        TheFrog.animate().scaleX(2F)
        TheFrog.animate().scaleY(2F)
        TheFrog.animate().x((ttf.width/2F)-(TheFrog.width/2))
        TheFrog.visibility = View.VISIBLE
        returnFrog = false
        sharedPreferences.edit().putBoolean("returnFrog", false).commit()

*/





       /* volumelever1.setOnSeekBarChangeListener() {
        }*/
    /*    if (volumelever1.progress <= 0) {
                Toast.makeText(this, "Volume Off", Toast.LENGTH_SHORT).show()

            }
            Toast.makeText(this, "This means quiet mode", Toast.LENGTH_SHORT).show()

        */




        volumelever1.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            private var mProgressAtStartTracking = 0
            private val SENSITIVITY = 0

            override fun onProgressChanged(seekBar: SeekBar?, i: Int, b: Boolean) {
                // handle progress change



            }



            override fun onStartTrackingTouch(seekBar: SeekBar) {
                mProgressAtStartTracking = seekBar.getProgress()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (abs(mProgressAtStartTracking - seekBar.getProgress()) <= SENSITIVITY) {

                    toasted()
                    // react to thumb click
                }


            }
        })











        //todo confirm this works

        if(daycounter == 1){

            sharedPreferences.edit().putInt("daycounter", 0).commit()


            QT13.text = ""
            QT14.text = ""
            QT15.text = ""
            QT16.text = ""
            QT17.text = ""
            QT18.text = ""


            sharedPreferences.edit().putString("QTB13","      ").commit()
            sharedPreferences.edit().putString("QTB14","      ").commit()
            sharedPreferences.edit().putString("QTB15","      ").commit()
            sharedPreferences.edit().putString("QTB16","      ").commit()
            sharedPreferences.edit().putString("QTB17","      ").commit()
            sharedPreferences.edit().putString("QTB18","      ").commit()
        }






  /*      //in the future replace with append and commas
        val externalDir1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val savedList = "savedbsl" + month + day + year + ".txt"
        val file = File(externalDir1, savedList)
        file.writeText(bslist.joinToString("\n" ))*/

       /* var newlist = bslist

        for(i in 0 until newlist.size) {
            newlist.get(i).plus(",")
        }
        Log.d("lily",newlist.toString())

*/



































        //onCreate
    }



    private fun toasted() {
        Toast.makeText(this, "Quiet Mode", Toast.LENGTH_SHORT).show()

        mediaPlayer = MediaPlayer.create(this, R.raw.quietmode)


        mediaPlayer?.setVolume(1f,1f)
        mediaPlayer?.start()
    }


    private fun checkHourGoal() {

        //todo change to fun effect
        var pastDifference = sharedPreferences.getInt("pastDifference", LocalDateTime.now().minute)

        if(LocalDateTime.now().minute - pastDifference > tgoal){
            //the difference is greater than tgoal then youdidit)
            Log.d("lily", "We Did It")
            Toast.makeText(this,"We did it!", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(2000)
            }
        }
        sharedPreferences.edit().putInt("pastDifference", LocalDateTime.now().minute).commit()

    }

    private fun turnOffMode(minutes1: Int) {

        var minutes2 = minutes1
        if(minutes1 == 0){
            minutes2 = sharedPreferences.getInt("minutes1", 1)
        }
        else{
            sharedPreferences.edit().putInt("minutes1", minutes1).commit()
            sharedPreferences.edit().putInt("pastDifference1", LocalDateTime.now().minute).commit()

        }

        var pastDifference1 = sharedPreferences.getInt("pastDifference1", LocalDateTime.now().minute)

        if(LocalDateTime.now().minute - pastDifference1 > minutes2){
            //the difference is greater than tgoal then youdidit)
            //turns them all off
            sharedPreferences.edit().putBoolean("threeggle", true).commit()
            sharedPreferences.edit().putBoolean("funnyvar", true).commit()
            sharedPreferences.edit().putBoolean("funnyvar2", true).commit()
            sharedPreferences.edit().putBoolean("funnyvar3", true).commit()
            sharedPreferences.edit().putBoolean("funnyvar4", true).commit()
            sharedPreferences.edit().putBoolean("funnyvar5", true).commit()
            sharedPreferences.edit().putBoolean("twoggle", true).commit()
            sharedPreferences.edit().putBoolean("seriousVar1", true).commit()
            sharedPreferences.edit().putBoolean("reeses1", true).commit()


            sharedPreferences.edit().putBoolean("tea1", true).commit()
            sharedPreferences.edit().putBoolean("clean1", true).commit()
            sharedPreferences.edit().putBoolean("busy1", true).commit()
            sharedPreferences.edit().putBoolean("groceries1", true).commit()






            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }









    }

    fun monaLista(j: Int){
        var bsl = findViewById<ListView>(R.id.bsl)
        var i = j
        var totalItems = sharedPreferences.getInt("count1", listnum)
        if (totalItems == 0 || i >= totalItems){
            return
        }
        bslist.add(sharedPreferences.getString(i.toString(), "").toString())
        i++
        bsl.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,bslist)
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
        bsl.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,bslist)
        sharedPreferences.edit().putInt("count1", listnum).commit()
        // bslist.add("")


    }


    fun monaLista2(j: Int){
        var backupReminderView = findViewById<ListView>(R.id.backupReminder)
        var i = j
        var totalItems = sharedPreferences.getInt("count2", listnum)
        if (totalItems == 0 || i >= totalItems){
            return
        }
        backupReminders1.add(sharedPreferences.getString(i.toString(), "").toString())
        i++
        backupReminderView.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,backupReminders1)
        monaLista2(i)
    }

    fun backUpLog(text: String) {
        var day = LocalDate.now().dayOfMonth.toString()
        var month = LocalDate.now().monthValue.toString()
        var year = LocalDate.now().year.toString()
        var date1 = "$month/$day/$year"
        var time2 = findViewById<TextClock>(R.id.time)
        var dateTime1 = findViewById<RelativeLayout>(R.id.dateTime)



        //var list1 = findViewById<ListView>(R.id.bsl)
        var backupReminderView = findViewById<ListView>(R.id.backupReminder)


        dateTime1.visibility = View.VISIBLE


        backupReminderNum = sharedPreferences.getInt("count2", 0)

        sharedPreferences.edit().putString(backupReminderNum.toString(), text).commit()

        var theItem1 =""
       /* if(color1 == 0 ){
            theItem = text + "\n" + date1 + "\n" + time2.text.toString()

        }
        else {*/
            theItem1 = text + "\n" + date1 + "\n" + time2.text.toString()
       // }
        sharedPreferences.edit().putString(backupReminderNum.toString(),  theItem1).commit()
        backupReminders1.add(theItem1)
        //bslist.set(listnum, text)
        backupReminderNum++
        backupReminderView.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,backupReminders1)
        sharedPreferences.edit().putInt("count2", backupReminderNum).commit()
        // bslist.add("")


    }
    fun clean() {
        var modeScreen = findViewById<RelativeLayout>(R.id.modeScreen)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)
        var goSkate = findViewById<ImageView>(R.id.goSkate)
        var colorMenu = findViewById<RelativeLayout>(R.id.colorMenu)
        var theBSLog = findViewById<RelativeLayout>(R.id.theBSLog)
        var newLayout = findViewById<RelativeLayout>(R.id.somethingElse1)
        var read = findViewById<ImageView>(R.id.read)
        var blew = findViewById<ImageView>(R.id.blew)
        var QTS = findViewById<RelativeLayout>(R.id.QTS)
        var backupLayout = findViewById<RelativeLayout>(R.id.backupLayout)
        var theButtons = findViewById<RelativeLayout>(R.id.theButtons)


        colorMenu.visibility = View.INVISIBLE
        newLayout.visibility = View.INVISIBLE
        ttf.visibility = View.INVISIBLE
        theBSLog.visibility = View.INVISIBLE
        modeScreen.visibility = View.INVISIBLE
        goSkate.visibility = View.INVISIBLE
        read.visibility = View.INVISIBLE
        blew.visibility = View.INVISIBLE
        QTS.visibility = View.INVISIBLE
        backupLayout.visibility = View.INVISIBLE
        theButtons.visibility = View.INVISIBLE

    }


/*    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer resources when the Activity is destroyed
        mediaPlayer?.release()
        mediaPlayer = null
    }*/

    @Override
    override fun onResume() {




        var day = LocalDate.now().dayOfMonth.toString()
        var month = LocalDate.now().monthValue.toString()
        var year = LocalDate.now().year.toString()

        var hue = findViewById<ImageView>(R.id.hue)
        hue.setImageResource(R.drawable.good)

        var progress1 = findViewById<SeekBar>(R.id.progress)
        var volumelever1 = findViewById<SeekBar>(R.id.volumeLever1)
        volumelever1.progress = volumelever1.width/2
        progress1.progress = 0

        var lastSaveDate = sharedPreferences.getInt("lsd", day.toInt())
        var counter = sharedPreferences.getInt("counter", 0)
        var daycounter = sharedPreferences.getInt("daycounter", 0)

        var feedback1 = findViewById<EditText>(R.id.feedback)
        //make button or text clickable
        //set var to zero on click
        //long click to reset

        var starRating = findViewById<RatingBar>(R.id.starRating)


        var QT13 = findViewById<TextView>(R.id.thirteen)
        var QT14 = findViewById<TextView>(R.id.fourteen)
        var QT15 = findViewById<TextView>(R.id.fifteen)
        var QT16 = findViewById<TextView>(R.id.sixteen)
        var QT17 = findViewById<TextView>(R.id.seventeen)
        var QT18 = findViewById<TextView>(R.id.eighteen)


        feedback1.setText("")
        starRating.rating = 3F

        //increments when day is different to yesterday
        if(day.toInt() != lastSaveDate){
            counter++
            sharedPreferences.edit().putInt("counter", counter).commit()

            daycounter++
            sharedPreferences.edit().putInt("daycounter", daycounter).commit()


            //reset counters
            sharedPreferences.edit().putInt("QTCount", 0).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
            sharedPreferences.edit().putInt("QT1C", 0).commit()
            sharedPreferences.edit().putInt("QT2C", 0).commit()
            sharedPreferences.edit().putInt("QT3C", 0).commit()
            sharedPreferences.edit().putInt("QT4C", 0).commit()
            sharedPreferences.edit().putInt("QT5C", 0).commit()
            sharedPreferences.edit().putInt("QT6C", 0).commit()
            sharedPreferences.edit().putInt("QT7C", 0).commit()
            sharedPreferences.edit().putInt("QT8C", 0).commit()
            sharedPreferences.edit().putInt("QT9C", 0).commit()
            sharedPreferences.edit().putInt("QT10C", 0).commit()
            sharedPreferences.edit().putInt("QT11C", 0).commit()
            sharedPreferences.edit().putInt("QT12C", 0).commit()
            sharedPreferences.edit().putInt("QT13C", 0).commit()
            sharedPreferences.edit().putInt("QT14C", 0).commit()
            sharedPreferences.edit().putInt("QT15C", 0).commit()
            sharedPreferences.edit().putInt("QT16C", 0).commit()
            sharedPreferences.edit().putInt("QT17C", 0).commit()
            sharedPreferences.edit().putInt("QT18C", 0).commit()
            sharedPreferences.edit().putInt("TC", 0).commit()

            saveSharedPreferencestoExternal(this, "daysSince", "bsl" + month + day + year)
            Toast.makeText(this, "New Day", Toast.LENGTH_SHORT).show()


        }
        sharedPreferences.edit().putInt("lsd", day.toInt()).commit()

        if(daycounter == 1){

            sharedPreferences.edit().putInt("daycounter", 0).commit()


            QT13.text = ""
            QT14.text = ""
            QT15.text = ""
            QT16.text = ""
            QT17.text = ""
            QT18.text = ""


            sharedPreferences.edit().putString("QTB13","      ").commit()
            sharedPreferences.edit().putString("QTB14","      ").commit()
            sharedPreferences.edit().putString("QTB15","      ").commit()
            sharedPreferences.edit().putString("QTB16","      ").commit()
            sharedPreferences.edit().putString("QTB17","      ").commit()
            sharedPreferences.edit().putString("QTB18","      ").commit()

        }

        turnOffMode(0)
        resize()

        //var TheFrog = findViewById<ImageView>(R.id.Bullfrog)
        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)


        if(sharedPreferences.getBoolean("safety", true)){
            dateTime.setBackgroundResource(R.drawable.newbg)
            //TheFrog.bringToFront()
        }
        else{
            dateTime.setBackgroundResource(R.drawable.blah)
            //TheFrog.bringToFront()
        }
        checkHourGoal()

        var status1 = sharedPreferences.getBoolean("status1", false)

        if(status1) {
            Toast.makeText(this, sharedPreferences.getString("statusUpdate", "Have a good day"), Toast.LENGTH_SHORT).show()
        }

        switch2(false)

        var delay1 = sharedPreferences.getInt("delay3", 2500)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)

        //back to home screen

        Log.d("lily2", "home screen")
        clean()
        sharedPreferences.getBoolean("returnFrog", true)
/*        if(returnFrog){
            TheFrog.animate().scaleX(2F)
            TheFrog.animate().scaleY(2F)
            TheFrog.animate().x((ttf.width/2F)-(TheFrog.width/2))
            returnFrog = false
            sharedPreferences.edit().putBoolean("returnFrog", false).commit()

        }*/
        //to go back to first screen on resume

        dateTime.visibility = View.VISIBLE
        toggle = sharedPreferences.getInt("toggle", 6)
        froggle(toggle)

        lifecycleScope.launch {
            delay(delay1.toLong())
            //this causes someone to h me

            var QT = sharedPreferences.getInt("QT", 0)
            var potentialSolutions = findViewById<EditText>(R.id.potentialSolutions)
            var QTBtn = findViewById<ImageView>(R.id.QT)
            var time = findViewById<TextClock>(R.id.time)
            var QTCount = sharedPreferences.getInt("QTCount", 1)
            var current1 = sharedPreferences.getString("current", "")

            var date1 = "$month/$day/$year"


            //gear should be sized in oncreate and resume scenarios
            if(QT==1) {
                QTBtn.animate().scaleX(2F)
                QTBtn.animate().scaleY(2F)
            }
            else {
                QTBtn.animate().scaleX(1F)
                QTBtn.animate().scaleY(1F)
            }
            var funnyvar = sharedPreferences.getBoolean("funnyvar", true)
            var funnyvar2 = sharedPreferences.getBoolean("funnyvar2", true)
            var funnyvar3 = sharedPreferences.getBoolean("funnyvar3", true)
            var funnyvar4 = sharedPreferences.getBoolean("funnyvar4", true)
            var threeggle = sharedPreferences.getBoolean("threeggle", true)
            var twoggle = sharedPreferences.getBoolean("twoggle", true)
            var funnyvar5 = sharedPreferences.getBoolean("funnyvar5", true)
            var seriousvar1 = sharedPreferences.getBoolean("seriousvar1", true)
            var reeses1 = sharedPreferences.getBoolean("reeses1", true)
            var tea1 = sharedPreferences.getBoolean("tea1", true)
            var clean1 = sharedPreferences.getBoolean("clean1", true)
            var busy1 = sharedPreferences.getBoolean("busy1", true)
            var groceries1 = sharedPreferences.getBoolean("groceries1", true)



            var modeCount = sharedPreferences.getInt("modeCount", 0)
            var modeVar = sharedPreferences.getString("modeVar", "Current Mode count: ")

            if(falseA == false) {

                //todo get this
                if (!funnyvar || !funnyvar2 || !funnyvar3 || !funnyvar4 || !threeggle || !twoggle || !funnyvar5 || !seriousvar1 || !reeses1 || !tea1 || !clean1 || !busy1 || !groceries1) {

                    TC = sharedPreferences.getInt("TC", 0)
                    TC++
                    sharedPreferences.edit().putInt("TC", TC).commit()
                    modeCount++
                    sharedPreferences.edit().putInt("modeCount", modeCount).commit()

                    Toast.makeText(this@Main, "TC: " + TC.toString() + "\nMC: " + modeCount, Toast.LENGTH_SHORT).show()
                    logIt("#" + TC + "\n" + modeVar + modeCount + "\n", 0) //includes date, time, level
                    writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + modeVar + modeCount, null)

                    val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION")
                        v.vibrate(500)
                    }
                }


                if (QT == 1) {

                    TC = sharedPreferences.getInt("TC", 0)
                    QTCount++
                    TC++
                    sharedPreferences.edit().putInt("TC", TC).commit()

                    sharedPreferences.edit().putInt("QTCount", QTCount).commit()

                    potentialSolutions.setText("#" + TC + "\n" + "Tick: " + QTCount.toString() + "\n" + current1 + time.text.toString())
                    logIt("#" + TC + "\n" + "Tick: " + QTCount + "\n" + current1 + "\n", 0)
                    QTBtn.animate().scaleX(2F)
                    QTBtn.animate().scaleY(2F)
                    writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + "Tick:" + QTCount.toString(), null)

                    val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION")
                        v.vibrate(500)
                    }
                }
            }
        }
        falseA = false
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


    //todo test switch in field
    private fun switch(): Boolean {
       // var TheFrog = findViewById<ImageView>(R.id.Bullfrog)
        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)




        var testerMode1 = ""
        if (testerMode == true) {
            testerMode = false
            testerMode1 = "safety off"
            sharedPreferences.edit().putBoolean("safety", false).commit()



        } else if (testerMode == false) {
            testerMode = true
            testerMode1 = "safety on"
            sharedPreferences.edit().putBoolean("safety", true).commit()




        }
        Toast.makeText(this, testerMode1, Toast.LENGTH_SHORT).show()
        Log.d("lily", testerMode.toString())

        if(sharedPreferences.getBoolean("safety", true)){
            dateTime.setBackgroundResource(R.drawable.updates)
           // TheFrog.bringToFront()
        }
        else{
            dateTime.setBackgroundResource(R.drawable.blah)
            //TheFrog.bringToFront()
        }

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
            delay(125)
            read.visibility = View.VISIBLE
            read.bringToFront()
            delay(125)
            blew.bringToFront()
            delay(125)
            read.bringToFront()
            delay(125)

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






    private fun resize(){
        var funnyvar = sharedPreferences.getBoolean("funnyvar", true)
        var funnyvar2 = sharedPreferences.getBoolean("funnyvar2", true)
        var funnyvar3 = sharedPreferences.getBoolean("funnyvar3", true)
        var funnyvar4 = sharedPreferences.getBoolean("funnyvar4", true)
        var threeggle = sharedPreferences.getBoolean("threeggle", true)
        var twoggle = sharedPreferences.getBoolean("twoggle", true)
        var funnyvar5 = sharedPreferences.getBoolean("funnyvar5", true)
        var seriousvar1 = sharedPreferences.getBoolean("seriousvar1", true)
        var reeses1 = sharedPreferences.getBoolean("reeses1", true)

        var tea1 = sharedPreferences.getBoolean("tea1", true)
        var clean1 = sharedPreferences.getBoolean("clean1", true)
        var busy1 = sharedPreferences.getBoolean("busy1", true)
        var groceries1 = sharedPreferences.getBoolean("groceries1", true)

        var starMode = findViewById<ImageView>(R.id.starMode)
        var apple = findViewById<ImageView>(R.id.food)
        var rest = findViewById<ImageView>(R.id.rest)
        var sleep = findViewById<ImageView>(R.id.sleep)
        var studyMode = findViewById<ImageView>(R.id.studyMode)
        var nebulizer = findViewById<ImageView>(R.id.nebulizer)
        var goSkate = findViewById<ImageView>(R.id.goSkate)
        var auto = findViewById<ImageView>(R.id.automobile)
        var reeses = findViewById<ImageView>(R.id.reeses)

        var tea = findViewById<ImageView>(R.id.tea)
        var clean = findViewById<ImageView>(R.id.cleaning)
        var busy = findViewById<ImageView>(R.id.busy)
        var groceries = findViewById<ImageView>(R.id.groceries)



        if(!tea1){
            tea.animate().scaleX(2.3F)
            tea.animate().scaleY(2.3F)
        }
        else{
            tea.animate().scaleX(1F)
            tea.animate().scaleY(1F)
        }
        if(!clean1){
            clean.animate().scaleX(2.3F)
            clean.animate().scaleY(2.3F)
        }
        else{
            clean.animate().scaleX(1F)
            clean.animate().scaleY(1F)
        }

        if(!busy1){
            busy.animate().scaleX(2.3F)
            busy.animate().scaleY(2.3F)
        }
        else{
            busy.animate().scaleX(1F)
            busy.animate().scaleY(1F)
        }

        if(!groceries1){
            groceries.animate().scaleX(2.3F)
            groceries.animate().scaleY(2.3F)
        }
        else{
            groceries.animate().scaleX(1F)
            groceries.animate().scaleY(1F)
        }


        if(!reeses1){
            reeses.animate().scaleX(2.3F)
            reeses.animate().scaleY(2.3F)
        }
        else{
            reeses.animate().scaleX(1F)
            reeses.animate().scaleY(1F)
        }
        if(!funnyvar2){
            starMode.animate().scaleX(2.3F)
            starMode.animate().scaleY(2.3F)
        }
        else{
            starMode.animate().scaleX(1F)
            starMode.animate().scaleY(1F)
        }
        if(!funnyvar4){
            studyMode.animate().scaleX(2.3F)
            studyMode.animate().scaleY(2.3F)
        }
        else{
            studyMode.animate().scaleX(1F)
            studyMode.animate().scaleY(1F)
        }

        if(!threeggle){
            apple.animate().scaleX(2.3F)
            apple.animate().scaleY(2.3F)
        }
        else{
            apple.animate().scaleX(1F)
            apple.animate().scaleY(1F)
        }
        if(!twoggle){
            rest.animate().scaleX(2.3F)
            rest.animate().scaleY(2.3F)
        }
        else{
            rest.animate().scaleX(1F)
            rest.animate().scaleY(1F)
        }

        if(!funnyvar){
            goSkate.animate().scaleX(2.3F)
            goSkate.animate().scaleY(2.3F)
        }
        else{
            goSkate.animate().scaleX(1F)
            goSkate.animate().scaleY(1F)
        }


        if(!funnyvar3){
            nebulizer.animate().scaleX(2.3F)
            nebulizer.animate().scaleY(2.3F)
        }
        else{
            nebulizer.animate().scaleX(1F)
            nebulizer.animate().scaleY(1F)
        }


        if(!funnyvar5){
            auto.animate().scaleX(2.3F)
            auto.animate().scaleY(2.3F)
        }
        else{
            auto.animate().scaleX(1F)
            auto.animate().scaleY(1F)
        }
        if(!seriousvar1){
            sleep.animate().scaleX(2.3F)
            sleep.animate().scaleY(2.3F)
        }
        else{
            sleep.animate().scaleX(1F)
            sleep.animate().scaleY(1F)
        }
    }

















    private fun froggle(toggle: Int) {
        val fone = findViewById<ImageView>(R.id.hangupphone1)
        val ticket = findViewById<ImageView>(R.id.ticket1)
        var modeScreen = findViewById<RelativeLayout>(R.id.modeScreen)
        var goSkate = findViewById<ImageView>(R.id.goSkate)
       // val TheFrog = findViewById<ImageView>(R.id.Bullfrog)
        var QTS = findViewById<RelativeLayout>(R.id.QTS)
        var theBSLog = findViewById<RelativeLayout>(R.id.theBSLog)
        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)

        var guitar = findViewById<ImageView>(R.id.connect2)
        var walkie = findViewById<ImageView>(R.id.connect)
        var theButtons = findViewById<RelativeLayout>(R.id.theButtons)
        returnFrog = sharedPreferences.getBoolean("returnFrog", true)

        if(returnFrog){
        /*    TheFrog.visibility = View.INVISIBLE
            TheFrog.animate().scaleX(2F)
            TheFrog.animate().scaleY(2F)
            TheFrog.animate().x((modeScreen.width/2F)-(TheFrog.width/2))
            TheFrog.visibility = View.VISIBLE*/
            returnFrog = false
            sharedPreferences.edit().putBoolean("returnFrog", false).commit()

        }

        if (toggle == 2) {
            fone.animate().rotation(0F)
            // Log.d("lily", "something else")
            clean()
            //dateTime.visibility = View.INVISIBLE
            var newlayout = findViewById<RelativeLayout>(R.id.somethingElse1)
            newlayout.visibility = View.VISIBLE
            newlayout.bringToFront()
            ticket.visibility = View.VISIBLE
        } else if (toggle == 3) {
            //mode screen
            clean()
            //TheFrog.animate().scaleX(.75F)
           // TheFrog.animate().scaleY(.75F)
            modeScreen.visibility = View.VISIBLE
            modeScreen.bringToFront()
            goSkate.visibility = View.VISIBLE
            goSkate.bringToFront()
            //fone.visibility = View.VISIBLE
            //fone.bringToFront()
            ticket.visibility = View.VISIBLE
            // guitar.bringToFront()
            // walkie.bringToFront()


        }
        else if (toggle == 4){
            clean()
            //TheFrog.animate().scaleX(.75F)
            //TheFrog.animate().scaleY(.75F)
            //TheFrog.animate().x((modeScreen.width/2F)-(TheFrog.width/2))
            QTS.visibility = View.VISIBLE
            QTS.bringToFront()
            ticket.visibility = View.VISIBLE

        }
        else if (toggle == 5){
            clean()
           // TheFrog.animate().scaleX(.75F)
           // TheFrog.animate().scaleY(.75F)
           // TheFrog.animate().x(10F)
            returnFrog = true
            sharedPreferences.edit().putBoolean("returnFrog", true).commit()
            theBSLog.visibility = View.VISIBLE
            theBSLog.bringToFront()
            Log.d("lily", bslist.toString() + " HERE's THE LIST")
            // orderList()
            dateTime.visibility = View.VISIBLE
            ticket.visibility = View.VISIBLE

        }
        else if (toggle == 6){
            clean()

            theButtons.visibility = View.VISIBLE
            theButtons.bringToFront()
            Log.d("lily", "the Buttons")



        }

        else if (toggle == 7){

            //back to home screen
            //  Log.d("lily2", "home screen")
            clean()
            dateTime.visibility = View.VISIBLE
            ticket.visibility = View.VISIBLE

        }


        sharedPreferences.edit().putInt("toggle", toggle).commit()

    }










}


