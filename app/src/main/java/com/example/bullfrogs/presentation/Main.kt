package com.example.bullfrogs.presentation
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import android.content.BroadcastReceiver
import android.view.Gravity
import androidx.appcompat.app.AlertDialog


//show a mission statement on start up
//Plan For Success!
//No Cruelty
//No AI Cruelty
//Intention should matter

//the app does not come with ai

//sharedpref is located in /data/data/com.example.bullfrogs/shared_prefs/daysSince.xml



class Main : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var toggle = 6
    private var giggle1 = true

    var listnum = 0
    var backupReminderNum = 0
    var bslist = mutableListOf<String>()
    var backupReminders1 = mutableListOf<String>()
    var bml = mutableListOf<String>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    var testerMode = true
    var pressesMinus = false
    var falseA = false
    var tgoal = 30

    var preset1 = 500
    var TC = 0
    var removeFromClean = true
    var homePage = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

            // Permission granted, start recording
        } else {
            // Permission denied, handle accordingly
        }
    }



    private var mediaPlayer: MediaPlayer? = null


    //todo get userpresent to work
    private val screenStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_SCREEN_OFF -> {
                    // This triggers exactly when the screen times out or turns off
                    Log.d("ScreenStatus", "Screen timed out or turned off")
                    handleScreenOff()
                }
                Intent.ACTION_USER_PRESENT -> {
                    // This triggers when the user unlocks the screen
                    Log.d("ScreenStatus", "Device was unlocked by the user")
                    handleScreenUnlocked()
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        //when testing turn off counter before run





        //button to turn off banner ad
        // make new buttons to retire old buttons
        //dev menu to make commands work

        //remove intercommand stop

        //question mark button that guides user in every page

        //make logs erasable

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)

        var modeScreen = findViewById<RelativeLayout>(R.id.modeScreen)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)
        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)

        val fone = findViewById<ImageView>(R.id.hangupphone1)
        var reset = false
        var dogDays = findViewById<TextView>(R.id.time12)
        var starRating = findViewById<RatingBar>(R.id.starRating)
        var statusUpdate = findViewById<EditText>(R.id.statusUpdate)

        val ticket = findViewById<ImageView>(R.id.ticket1)
        var date = findViewById<TextView>(R.id.date)
        var time = findViewById<TextClock>(R.id.time)

        var dayOfTheWeek = LocalDate.now().dayOfWeek.toString().removeRange(2,LocalDate.now().dayOfWeek.toString().length)

        var day = LocalDate.now().dayOfMonth.toString()
        var month = LocalDate.now().monthValue.toString()
        var year = LocalDate.now().year.toString()
        var date1 = "$month/$day/$year $dayOfTheWeek"
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
        var alert2 = findViewById<ImageView>(R.id.alert1)
        var privView = findViewById<ImageView>(R.id.privacy)


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
        var infomessage = findViewById<ImageView>(R.id.infomessage)
        var ping = findViewById<ImageView>(R.id.ping)
        var infoping = findViewById<ImageView>(R.id.infoping)

        var QQTS = findViewById<TextView>(R.id.QQTS)



        var volumelever1 = findViewById<SeekBar>(R.id.volumeLever1)

        var convoTimer = findViewById<TextView>(R.id.convoTimer)
        var convoTime = findViewById<ImageView>(R.id.convotime)



        var searchTerm = findViewById<EditText>(R.id.search)
        var searchEnter = findViewById<TextView>(R.id.searchEnter)









//todo make activities










        volumelever1.progress = volumelever1.width/2


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


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

        }


        potentialSolutions.setOnLongClickListener {
            potentialSolutions.setText("")

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

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
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
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

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
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


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
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

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        update1.setOnClickListener {

            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.updates)
            colorMenu.visibility = View.INVISIBLE
            color1 = 0

            logIt(potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "update", potentialSolutions.text.toString(),null)


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        note2self.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.nonissue)
            colorMenu.visibility = View.INVISIBLE
            color1 = 0
            logIt("note to self: " + potentialSolutions.text.toString(), color1)
            writeNewPost("jls", "jls", "note2self", potentialSolutions.text.toString(),null)


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
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




        time.setOnClickListener {
            switch2(true)

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        time.setOnLongClickListener {
            froggle(9)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }



        var home1 = findViewById<RelativeLayout>(R.id.home1)
        var thirdRow = findViewById<LinearLayout>(R.id.thirdRow)
        var fourthRow = findViewById<LinearLayout>(R.id.fourthRow)
        var fifthRow = findViewById<LinearLayout>(R.id.fifthRow)
        var sixthRow = findViewById<LinearLayout>(R.id.sixthRow)
        var inputQT = findViewById<RelativeLayout>(R.id.inputQT)


        date.setOnClickListener {


            froggle(7)
            sharedPreferences.edit().putInt("toggle", 7).commit()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }


        }






        pos.setOnClickListener {

            var feedback = findViewById<EditText>(R.id.feedback)
            var version = sharedPreferences.getInt("version", 0)

            if (starRating.rating.toInt() >= 3.5) {
                version++
                backUpLog("Version: " + version + "\n" + "Stars:" + starRating.rating.toString() + "/6.0 \n" + feedback.text.toString())
                Toast.makeText(this, "requesting backup", Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putInt("version", version).commit()
                //writeNewUser("backup request", "backup request", "fake@mail.com")
                writeNewPost(
                    "jls",
                    "Rating",
                    "Stars: ",
                    "Version: " + version + "\n" + starRating.rating.toString() + "/6.0 \n" + feedback.text.toString() + date1 + time.text.toString(),
                    starRating.rating.toInt())

            } else {


            logIt("Stars:" + starRating.rating.toString() + "/6.0 \n" + feedback.text.toString(), 0)
            writeNewPost(
                "jls",
                "Rating",
                "Stars: ",
                starRating.rating.toString() + "/6.0 \n" + feedback.text.toString() + date1 + time.text.toString(),
                starRating.rating.toInt()
            )

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
        var status1 = sharedPreferences.getBoolean("status1", false)

        var homeStatus = findViewById<TextView>(R.id.homeStatus)

        statusApprove.setOnClickListener {

            if(statusUpdate.text.toString() == ""){
                status1 = sharedPreferences.edit().putBoolean("status1", false).commit()
                sharedPreferences.edit().putString("statusUpdate" , statusUpdate.text.toString()).commit()
                Toast.makeText(this, "status is off", Toast.LENGTH_SHORT).show()

            }
            else{
                status1 = sharedPreferences.edit().putBoolean("status1", true).commit()
                //writeNewPost("jls", "Status", ": ",statusUpdate.toString() + " " + dateTime.toString(),null)
                //logIt("Status" + statusUpdate.toString() + " " + dateTime.toString(), 0)
                sharedPreferences.edit().putString("statusUpdate" , statusUpdate.text.toString()).commit()
                Toast.makeText(this, statusUpdate.text.toString(), Toast.LENGTH_SHORT).show()
                homeStatus.setText(sharedPreferences.getString("statusUpdate", ""))

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
        statusUpdate.setText(sharedPreferences.getString("statusUpdate", ""))



        if(status1) {
           // Toast.makeText(this, sharedPreferences.getString("statusUpdate", "Have a good day"), Toast.LENGTH_SHORT).show()
            statusUpdate.setText(sharedPreferences.getString("statusUpdate", ""))
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


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
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
        var alert1 = sharedPreferences.getBoolean("alert1", true)
        var privShared = sharedPreferences.getBoolean("private1", true)




        var modeActivated = sharedPreferences.getBoolean("modeActivated", false)

        //icons should remain large if they were on


        if(!alert1){
            alert2.animate().scaleX(2.3F)
            alert2.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.alert1)
            dateTime.setBackgroundResource(R.drawable.alert1)


        }
        else{
            alert2.animate().scaleX(1F)
            alert2.animate().scaleY(1F)
            returnBackground()


        }


        if(!privShared){
            privView.animate().scaleX(2.3F)
            privView.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.private1)
            dateTime.setBackgroundResource(R.drawable.private1)

        }
        else{
            privView.animate().scaleX(1F)
            privView.animate().scaleY(1F)
            returnBackground()
        }


        if(!tea1){
            tea.animate().scaleX(2.3F)
            tea.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.tea)

        }
        else{
            tea.animate().scaleX(1F)
            tea.animate().scaleY(1F)
        }

        if(!clean1){
            clean.animate().scaleX(2.3F)
            clean.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.cleaning)

        }
        else{
            clean.animate().scaleX(1F)
            clean.animate().scaleY(1F)
        }

        if(!busy1){
            busy.animate().scaleX(2.3F)
            busy.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.busy)

        }
        else{
            busy.animate().scaleX(1F)
            busy.animate().scaleY(1F)
        }

        if(!groceries1){
            groceries.animate().scaleX(2.3F)
            groceries.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.groceries)

        }
        else{
            groceries.animate().scaleX(1F)
            groceries.animate().scaleY(1F)
        }


        if(!reeses1){
            reeses.animate().scaleX(2.3F)
            reeses.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.peanutbutter)

        }
        else{
            reeses.animate().scaleX(1F)
            reeses.animate().scaleY(1F)
        }
        if(!funnyvar2){
            starMode.animate().scaleX(2.3F)
            starMode.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.starmode)

        }
        else{
            starMode.animate().scaleX(1F)
            starMode.animate().scaleY(1F)
        }
        if(!funnyvar4){
            studyMode.animate().scaleX(2.3F)
            studyMode.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.studymode)

        }
        else{
            studyMode.animate().scaleX(1F)
            studyMode.animate().scaleY(1F)
        }

        if(!threeggle){
            apple.animate().scaleX(2.3F)
            apple.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.apple)

        }
        else{
            apple.animate().scaleX(1F)
            apple.animate().scaleY(1F)
        }
        if(!twoggle){
            rest.animate().scaleX(2.3F)
            rest.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.rest)
            dateTime.setBackgroundResource(R.drawable.alert1)

        }
        else{
            rest.animate().scaleX(1F)
            rest.animate().scaleY(1F)
            returnBackground()
        }

        if(!funnyvar){
            goSkate.animate().scaleX(2.3F)
            goSkate.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.skateboard)

        }
        else{
            goSkate.animate().scaleX(1F)
            goSkate.animate().scaleY(1F)
        }
        if(!froggie){
            caution.animate().scaleX(2.3F)
            caution.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.caution)

        }
        else{
            caution.animate().scaleX(1F)
            caution.animate().scaleY(1F)
        }

        if(!funnyvar3){
            nebulizer.animate().scaleX(2.3F)
            nebulizer.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.nebulizer)

        }
        else{
            nebulizer.animate().scaleX(1F)
            nebulizer.animate().scaleY(1F)
        }


        if(!funnyvar5){
            auto.animate().scaleX(2.3F)
            auto.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.automobile)

        }
        else{
            auto.animate().scaleX(1F)
            auto.animate().scaleY(1F)
        }
        if(!seriousvar1){
            sleep.animate().scaleX(2.3F)
            sleep.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.zulater)

        }
        else{
            sleep.animate().scaleX(1F)
            sleep.animate().scaleY(1F)
        }






        alert2.setOnClickListener {

            alert1 = sharedPreferences.getBoolean("alert1", true)

            if (alert1) {
                //this means I'm studying
                alert2.animate().scaleX(2.3F)
                alert2.animate().scaleY(2.3F)
                alert1 = false
                //writeNewPost("jls", "Mode", "teaMode", "I'm drinking tea dont bother me",null)
                sharedPreferences.edit().putBoolean("alert1", false).commit()

                //sharedPreferences.edit().putString("modeVar","tea mode issue count: ").commit()
                turnOffMode(30)
                ticket.setImageResource(R.drawable.alert1)
                dateTime.setBackgroundResource(R.drawable.alert1)


            }
            else if (!alert1){
                alert2.animate().scaleX(1F)
                alert2.animate().scaleY(1F)
                alert1 = true
                //writeNewPost("jls", "Mode", "teaMode", "off",null)
                sharedPreferences.edit().putBoolean("alert1", true).commit()
                //sharedPreferences.edit().putInt("modeCount", 0).commit()
                ticket.setImageResource(R.drawable.scribble)
                returnBackground()

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

        privView.setOnClickListener {
            privShared = sharedPreferences.getBoolean("private1", true)

            if (privShared) {
                //this means I want to be alone
                privView.animate().scaleX(2.3F)
                privView.animate().scaleY(2.3F)
                privShared = false
                //writeNewPost("jls", "Mode", "teaMode", "dont bother me",null)
                sharedPreferences.edit().putBoolean("private1", false).commit()
                turnOffMode(1)
                ticket.setImageResource(R.drawable.private1)

                dateTime.setBackgroundResource(R.drawable.private1)

            }
            else if (!privShared){
                privView.animate().scaleX(1F)
                privView.animate().scaleY(1F)
                privShared = true
                // writeNewPost("jls", "Mode", "teaMode", "off",null)
                sharedPreferences.edit().putBoolean("private1", true).commit()
                //sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)
                returnBackground()

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






        tea.setOnClickListener {

            tea1 = sharedPreferences.getBoolean("tea1" , true)

            if (tea1) {
                //this means I'm studying
                tea.animate().scaleX(2.3F)
                tea.animate().scaleY(2.3F)
                tea1 = false
                writeNewPost("jls", "Mode", "teaMode", "I'm drinking tea dont bother me",null)
                sharedPreferences.edit().putBoolean("tea1", false).commit()

                sharedPreferences.edit().putString("modeVar","tea mode issue count: ").commit()
                turnOffMode(20)

                ticket.setImageResource(R.drawable.tea)


            }
            else if (!tea1){
                tea.animate().scaleX(1F)
                tea.animate().scaleY(1F)
                tea1 = true
                writeNewPost("jls", "Mode", "teaMode", "off",null)
                sharedPreferences.edit().putBoolean("tea1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)

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




        clean.setOnClickListener {

            clean1 = sharedPreferences.getBoolean("clean1" , true)

            if (clean1) {
                //this means I'm studying
                clean.animate().scaleX(2.3F)
                clean.animate().scaleY(2.3F)
                clean1 = false
                writeNewPost("jls", "Mode", "cleanMode", "I'm busy cleaning",null)
                sharedPreferences.edit().putBoolean("clean1", false).commit()

                sharedPreferences.edit().putString("modeVar","clean mode issue count: ").commit()
                turnOffMode(25)
                ticket.setImageResource(R.drawable.cleaning)


            }
            else if (!clean1){
                clean.animate().scaleX(1F)
                clean.animate().scaleY(1F)
                clean1 = true
                writeNewPost("jls", "Mode", "cleanMode", "off",null)
                sharedPreferences.edit().putBoolean("clean1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()
                ticket.setImageResource(R.drawable.scribble)

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



        busy.setOnClickListener {

           busy1 = sharedPreferences.getBoolean("busy1" , true)

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

                ticket.setImageResource(R.drawable.busy)


            }
            else if (!busy1){
                busy.animate().scaleX(1F)
                busy.animate().scaleY(1F)
                busy1 = true
                writeNewPost("jls", "Mode", "busyMode", "off",null)
                sharedPreferences.edit().putBoolean("busy1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()
                ticket.setImageResource(R.drawable.scribble)

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



        groceries.setOnClickListener {

          groceries1 = sharedPreferences.getBoolean("groceries1" , true)


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

                ticket.setImageResource(R.drawable.groceries)


            }
            else if (!groceries1){
                groceries.animate().scaleX(1F)
                groceries.animate().scaleY(1F)
                groceries1 = true
                writeNewPost("jls", "Mode", "groceriesMode", "off",null)
                sharedPreferences.edit().putBoolean("groceries1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)

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



        reeses.setOnClickListener {

            reeses1 = sharedPreferences.getBoolean("reeses1" , true)


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

                ticket.setImageResource(R.drawable.peanutbutter)



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

                ticket.setImageResource(R.drawable.scribble)


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


        starMode.setOnClickListener {


           funnyvar2 = sharedPreferences.getBoolean("funnyvar2", true)


            if (funnyvar2) {
                starMode.animate().scaleX(2.3F)
                starMode.animate().scaleY(2.3F)
                funnyvar2 = false
                writeNewPost("jls", "Mode", "starMode", "on",null)
                sharedPreferences.edit().putBoolean("funnyvar2", false).commit()


                sharedPreferences.edit().putString("modeVar","star mode issue count: ").commit()

                turnOffMode(10)

                ticket.setImageResource(R.drawable.starmode)


            }
            else if (!funnyvar2){
                starMode.animate().scaleX(1F)
                starMode.animate().scaleY(1F)
                funnyvar2 = true
                writeNewPost("jls", "Mode", "starMode", "off",null)
                sharedPreferences.edit().putBoolean("funnyvar2", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)


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

        studyMode.setOnClickListener {

            var funnyvar4 = sharedPreferences.getBoolean("funnyvar4", true)


            if (funnyvar4) {
                //this means I'm studying
                studyMode.animate().scaleX(2.3F)
                studyMode.animate().scaleY(2.3F)
                funnyvar4 = false
                writeNewPost("jls", "Mode", "studyMode", "on: I'm working on computer science",null)
                sharedPreferences.edit().putBoolean("funnyvar4", false).commit()

                sharedPreferences.edit().putString("modeVar","study mode issue count: ").commit()
                turnOffMode(25)

                ticket.setImageResource(R.drawable.studymode)


            }
            else if (!funnyvar4){
                studyMode.animate().scaleX(1F)
                studyMode.animate().scaleY(1F)
                funnyvar4 = true
                writeNewPost("jls", "Mode", "studyMode", "off",null)
                sharedPreferences.edit().putBoolean("funnyvar4", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)

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

        apple.setOnClickListener {

         threeggle = sharedPreferences.getBoolean("threeggle", true)


            if (threeggle) {
                //this means I'm eating
                apple.animate().scaleX(2.3F)
                apple.animate().scaleY(2.3F)
                threeggle = false
                writeNewPost("jls", "Mode", "eatingMode", "on",null)
                sharedPreferences.edit().putBoolean("threeggle", false).commit()

                sharedPreferences.edit().putString("modeVar","eating mode issue count: ").commit()


                turnOffMode(20)

                ticket.setImageResource(R.drawable.apple)




            }
            else if (!threeggle){
                apple.animate().scaleX(1F)
                apple.animate().scaleY(1F)
                threeggle = true
                writeNewPost("jls", "Mode", "eatingMode", "off",null)
                sharedPreferences.edit().putBoolean("threeggle", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)

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

        rest.setOnClickListener {
            twoggle = sharedPreferences.getBoolean("twoggle", true)


            if (twoggle) {
                rest.animate().scaleX(2.3F)
                rest.animate().scaleY(2.3F)
                twoggle = false
                //writeNewPost("jls", "Mode", "restroomMode", "on",null)
                sharedPreferences.edit().putBoolean("twoggle", false).commit()
                sharedPreferences.edit().putString("modeVar","restroom mode issue count: ").commit()
                turnOffMode(10)

                ticket.setImageResource(R.drawable.rest)
                dateTime.setBackgroundResource(R.drawable.rest)



            }
            else if (!twoggle){
                rest.animate().scaleX(1F)
                rest.animate().scaleY(1F)
                twoggle = true
                //writeNewPost("jls", "Mode", "restMode", "off",null)
                sharedPreferences.edit().putBoolean("twoggle", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)

                returnBackground()
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

        goSkate.setOnClickListener {

            funnyvar = sharedPreferences.getBoolean("funnyvar", true)

            if(funnyvar) {
                goSkate.animate().rotation(360F)
                goSkate.animate().scaleX(2.3F)
                goSkate.animate().scaleY(2.3F)
                funnyvar = false
                writeNewPost("jls", "Mode", "skatingMode", "on, safety first",null)
                sharedPreferences.edit().putBoolean("funnyvar", false).commit()

                sharedPreferences.edit().putString("modeVar","skating mode issue count: ").commit()
                turnOffMode(30)

                ticket.setImageResource(R.drawable.skateboard)


            }
            else if (!funnyvar){
                goSkate.animate().rotation(0F)
                goSkate.animate().scaleX(1F)
                goSkate.animate().scaleY(1F)
                funnyvar = true
                writeNewPost("jls", "Mode", "skatingMode", "off",null)
                sharedPreferences.edit().putBoolean("funnyvar", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()
                ticket.setImageResource(R.drawable.scribble)

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

        nebulizer.setOnClickListener {

           funnyvar3 = sharedPreferences.getBoolean("funnyvar3", true)

            if(funnyvar3) {
                nebulizer.animate().scaleY(1.8F)
                nebulizer.animate().scaleX(1.8F)
                funnyvar3 = false
                sharedPreferences.edit().putBoolean("funnyvar3", false).commit()

                sharedPreferences.edit().putString("modeVar","smoke testing mode issue count: ").commit()
                turnOffMode(10)

                ticket.setImageResource(R.drawable.nebulizer)


            }
            else if (!funnyvar3){
                nebulizer.animate().scaleX(1F)
                nebulizer.animate().scaleY(1F)
                funnyvar3 = true
                sharedPreferences.edit().putBoolean("funnyvar3", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)

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

        auto.setOnClickListener {

        funnyvar5 = sharedPreferences.getBoolean("funnyvar5", true)


            if(funnyvar5) {
                auto.animate().scaleY(1.8F)
                auto.animate().scaleX(1.8F)
                funnyvar5 = false
                sharedPreferences.edit().putBoolean("funnyvar5", false).commit()

                sharedPreferences.edit().putString("modeVar","auto mode issue count: ").commit()

                Toast.makeText(this, "Auto Quick Tickets is ON", Toast.LENGTH_SHORT).show()
                turnOffMode(15)

                ticket.setImageResource(R.drawable.automobile)


            }
            else if (!funnyvar5){
                auto.animate().scaleX(1F)
                auto.animate().scaleY(1F)
                funnyvar5 = true
                sharedPreferences.edit().putBoolean("funnyvar5", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)

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

        sleep.setOnClickListener {

            seriousvar1 = sharedPreferences.getBoolean("seriousvar1", true)

            if(seriousvar1) {
                sleep.animate().scaleY(1.8F)
                sleep.animate().scaleX(1.8F)
                seriousvar1 = false
                sharedPreferences.edit().putBoolean("seriousvar1", false).commit()

                sharedPreferences.edit().putString("modeVar","sleep mode issue count: ").commit()

                Toast.makeText(this, "15 min Sleep Quick Tickets is ON", Toast.LENGTH_SHORT).show()
                turnOffMode(15)

                ticket.setImageResource(R.drawable.zulater)



            }
            else if (!seriousvar1){
                sleep.animate().scaleX(1F)
                sleep.animate().scaleY(1F)
                seriousvar1 = true
                sharedPreferences.edit().putBoolean("seriousvar1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.scribble)

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

        sleep.setOnLongClickListener {

            seriousvar1 = sharedPreferences.getBoolean("seriousvar1", true)


            if(seriousvar1) {
                sleep.animate().scaleY(1.8F)
                sleep.animate().scaleX(1.8F)
                seriousvar1 = false
                sharedPreferences.edit().putBoolean("seriousvar1", false).commit()

                sharedPreferences.edit().putString("modeVar","sleep mode issue count: ").commit()

                Toast.makeText(this, "7 hr Sleep Quick Tickets is ON", Toast.LENGTH_SHORT).show()
                turnOffMode(60*7)

                ticket.setImageResource(R.drawable.zulater)



            }
            else if (!seriousvar1){
                sleep.animate().scaleX(1F)
                sleep.animate().scaleY(1F)
                seriousvar1 = true
                sharedPreferences.edit().putBoolean("seriousvar1", true).commit()
                sharedPreferences.edit().putInt("modeCount", 0).commit()

                ticket.setImageResource(R.drawable.zulater)

            }
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

            return@setOnLongClickListener true
        }






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

                ticket.setImageResource(R.drawable.caution)


            }
            else if (!froggie){
                caution.animate().scaleX(1F)
                caution.animate().scaleY(1F)
                froggie = true
                writeNewPost("jls", "Mode", "cautionMode", "off" + date1 + time.text.toString(),null)
                logIt("cautionMode: now off", 0)
                sharedPreferences.edit().putBoolean("froggie", true).commit()
                ticket.setImageResource(R.drawable.caution)

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

        if(sharedPreferences.getBoolean("safety", true)){
        }
        else{
            Toast.makeText(this, "safety is off", Toast.LENGTH_SHORT).show()
        }








        ticket.setOnLongClickListener {
            switch()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        var guide = findViewById<TextView>(R.id.guide)
        ticket.setOnClickListener {

            if (giggle1) {
                //opens a ticket

                clean()
                ttf.visibility = View.VISIBLE
                ttf.bringToFront()
                dateTime.visibility = View.VISIBLE
                adjust.bringToFront()

                guide.setOnClickListener{
                    showDialog(this, "Type in ticket info then select the color to indicate severity.")
                }
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









        var QTCount = sharedPreferences.getInt("QTCount",1)
        var current1 = sharedPreferences.getString("current","")

        dateTime.visibility = View.VISIBLE

        //gear should be sized in oncreate and resume scenarios
        var QT = sharedPreferences.getInt("QT", 0)
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
                logIt("#" + TC + "\n" + "Tick: " + QTCount + "\n" + potentialSolutions.text.toString() + "\n", color1)

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
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
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
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
            //reset time displayed to 0

        }







        //increments when day is different to yesterday
        if(day.toInt() != lastSaveDate){

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

            //comment can be underneath
            var QT1C = sharedPreferences.getInt("QT1C", 0)
            var QT2C = sharedPreferences.getInt("QT2C", 0)
            var QT3C = sharedPreferences.getInt("QT3C", 0)
            var QT4C = sharedPreferences.getInt("QT4C", 0)
            var QT5C = sharedPreferences.getInt("QT5C", 0)
            var QT6C = sharedPreferences.getInt("QT6C", 0)
            var QT7C = sharedPreferences.getInt("QT7C", 0)
            var QT8C = sharedPreferences.getInt("QT8C", 0)
            var QT9C = sharedPreferences.getInt("QT9C", 0)
            var QT10C = sharedPreferences.getInt("QT10C", 0)
            var QT11C = sharedPreferences.getInt("QT11C", 0)
            var QT12C = sharedPreferences.getInt("QT12", 0)
            var QT13C = sharedPreferences.getInt("QT13", 0)
            var QT14C = sharedPreferences.getInt("QT14", 0)
            var QT15C = sharedPreferences.getInt("QT15", 0)
            var QT16C = sharedPreferences.getInt("QT16", 0)
            var QT17C = sharedPreferences.getInt("QT17", 0)
            var QT18C = sharedPreferences.getInt("QT18", 0)

            //get word from ticket title append








            logIt("Previous Day Total Count: " + TC + "\n" + sharedPreferences.getString("QTB1","") + " Count: " + QT1C + "\n" +
                    sharedPreferences.getString("QTB2","") + " Count: " + QT2C + "\n" +
                    sharedPreferences.getString("QTB3","") + " Count: " + QT3C + "\n" +
                    sharedPreferences.getString("QTB4","") + " Count: " + QT4C + "\n" +
                    sharedPreferences.getString("QTB5","") + " Count: " + QT5C + "\n" +
                    sharedPreferences.getString("QTB6","") + " Count: " + QT6C + "\n" +
                    sharedPreferences.getString("QTB7","") + " Count: " + QT7C + "\n" +
                    sharedPreferences.getString("QTB8","") + " Count: " + QT8C + "\n" +
                    sharedPreferences.getString("QTB9","") + " Count: " + QT9C + "\n" +
                    sharedPreferences.getString("QTB10","") + " Count: " + QT10C + "\n" +
                    sharedPreferences.getString("QTB11","") + " Count: " + QT11C + "\n" +
                    sharedPreferences.getString("QTB12","") + " Count: " + QT12C + "\n" +
                    sharedPreferences.getString("QTB13","") + " Count: " + QT13C + "\n" +
                    sharedPreferences.getString("QTB14","") + " Count: " + QT14C + "\n" +
                    sharedPreferences.getString("QTB15","") + " Count: " + QT15C + "\n" +
                    sharedPreferences.getString("QTB16","") + " Count: " + QT16C + "\n" +
                    sharedPreferences.getString("QTB17","") + " Count: " + QT17C + "\n" +
                    sharedPreferences.getString("QTB18","") + " Count: " + QT18C
                , 0)































            counter++
            sharedPreferences.edit().putInt("counter", counter).commit()




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

            sharedPreferences.edit().putInt("daycounter", 0).commit()


            QT13.text = ""
            QT14.text = ""
            QT15.text = ""
            QT16.text = ""
            QT17.text = ""
            QT18.text = ""


            sharedPreferences.edit().putString("QTB13","").commit()
            sharedPreferences.edit().putString("QTB14","").commit()
            sharedPreferences.edit().putString("QTB15","").commit()
            sharedPreferences.edit().putString("QTB16","").commit()
            sharedPreferences.edit().putString("QTB17","").commit()
            sharedPreferences.edit().putString("QTB18","").commit()












        }



        sharedPreferences.edit().putInt("lsd", day.toInt()).commit()
        dogDays.text = counter.toString()



        bsl.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,bslist)




        monaLista(0)

        monaLista2(0)
        bsl.setOnItemClickListener{ parent, view, position, id ->




            if(bslist.get(position).contains("level: 1") ){
                     currentItemColor.setImageResource(R.drawable.good)
                     Log.d("lily", "1")
            }
            if(bslist.get(position).contains("level: 2") ){
                currentItemColor.setImageResource(R.drawable.lime)
                Log.d("lily", "1")
            }
            if(bslist.get(position).contains("level: 3") ){
                currentItemColor.setImageResource(R.drawable.yellowcard)
                Log.d("lily", "1")
            }
            if(bslist.get(position).contains("level: 4") ){
                currentItemColor.setImageResource(R.drawable.redcard)
                Log.d("lily", "1")
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




        if(counter == 7){

            ticket.setImageResource(R.drawable.youwon)
        }

        if(counter == 30){

            ticket.setImageResource(R.drawable.winner1)
        }









        var minus1 = findViewById<TextView>(R.id.minus1)


        minus1.setOnClickListener {


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
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }


        checkHourGoal(false)


































        var input3 = findViewById<EditText>(R.id.input2)
        var inputEnter2 = findViewById<TextView>(R.id.inputEnter2)
        var QB2 = 0

        var Q1 = findViewById<TextView>(R.id.one1)
        var Q2 = findViewById<TextView>(R.id.two1)
        var Q3 = findViewById<TextView>(R.id.three1)
        var Q4 = findViewById<TextView>(R.id.four1)
        var Q5 = findViewById<TextView>(R.id.five1)
        var Q6 = findViewById<TextView>(R.id.six1)
        var Q7 = findViewById<TextView>(R.id.seven1)
        var Q8 = findViewById<TextView>(R.id.eight1)
        var Q9 = findViewById<TextView>(R.id.nine1)
        var Q10 = findViewById<TextView>(R.id.ten1)
        var Q11 = findViewById<TextView>(R.id.eleven1)
        var Q12 = findViewById<TextView>(R.id.twelve1)
        var Q13 = findViewById<TextView>(R.id.thirteen1)
        var Q14 = findViewById<TextView>(R.id.fourteen1)
        var Q15 = findViewById<TextView>(R.id.fifteen1)
        var Q16 = findViewById<TextView>(R.id.sixteen1)
        var Q17 = findViewById<TextView>(R.id.seventeen1)
        var Q18 = findViewById<TextView>(R.id.eighteen1)

        Q1.setText(sharedPreferences.getString("QB21",""))
        Q2.setText(sharedPreferences.getString("QB22",""))
        Q3.setText(sharedPreferences.getString("QB23",""))
        Q4.setText(sharedPreferences.getString("QB24",""))
        Q5.setText(sharedPreferences.getString("QB25",""))
        Q6.setText(sharedPreferences.getString("QB26",""))
        Q7.setText(sharedPreferences.getString("QB27",""))
        Q8.setText(sharedPreferences.getString("QB28",""))
        Q9.setText(sharedPreferences.getString("QB29",""))
        Q10.setText(sharedPreferences.getString("QB210",""))
        Q11.setText(sharedPreferences.getString("QB211",""))
        Q12.setText(sharedPreferences.getString("QB212",""))
        Q13.setText(sharedPreferences.getString("QB213",""))
        Q14.setText(sharedPreferences.getString("QB214",""))
        Q15.setText(sharedPreferences.getString("QB215",""))
        Q16.setText(sharedPreferences.getString("QB216",""))
        Q17.setText(sharedPreferences.getString("QB217",""))
        Q18.setText(sharedPreferences.getString("QB218",""))

        Q1.setOnLongClickListener {
            QB2 = 1
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q2.setOnLongClickListener {
            QB2 = 2
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q3.setOnLongClickListener {
            QB2 = 3
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q4.setOnLongClickListener {
            QB2 = 4
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q5.setOnLongClickListener {
            QB2 = 5
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q6.setOnLongClickListener {
            QB2 = 6
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q7.setOnLongClickListener {
            QB2 = 7
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q8.setOnLongClickListener {
            QB2 = 8
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q9.setOnLongClickListener {
            QB2 = 9
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q10.setOnLongClickListener {
            QB2 = 10
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q11.setOnLongClickListener {
            QB2 = 11
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q12.setOnLongClickListener {
            QB2 = 12
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q13.setOnLongClickListener {
            QB2 = 13
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q14.setOnLongClickListener {
            QB2 = 14
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q15.setOnLongClickListener {
            QB2 = 15
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q16.setOnLongClickListener {
            QB2 = 16
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q17.setOnLongClickListener {
            QB2 = 17
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q18.setOnLongClickListener {
            QB2 = 18
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }








        Q1.setOnClickListener {

           

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            Toast.makeText(this, "1y", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("1y", "1sound"))

        }
        Q2.setOnClickListener {
            

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            Toast.makeText(this, "y2", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("2y", "1sound"))
        }
        Q3.setOnClickListener {
            Toast.makeText(this, "y3", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("3y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            
            
        }
        Q4.setOnClickListener {
            Toast.makeText(this, "y4", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("4y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            
            
        }
        Q5.setOnClickListener {
            Toast.makeText(this, "y5", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("5y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            
            
            
        }


        Q6.setOnClickListener {
            Toast.makeText(this, "y6", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("6y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            
            
        }
        Q7.setOnClickListener {
            Toast.makeText(this, "y7", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("7y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            
            
        }
        Q8.setOnClickListener {
            Toast.makeText(this, "y8", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("8y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            
            
        }
        Q9.setOnClickListener {
            Toast.makeText(this, "y9", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("9y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            
            
        }
        Q10.setOnClickListener {
            Toast.makeText(this, "y10", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("10y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            
            
        }
        Q11.setOnClickListener {
            Toast.makeText(this, "y11", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("11y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            
            

        }
        Q12.setOnClickListener {
            Toast.makeText(this, "y12", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("12y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

        }

        Q13.setOnClickListener {
            Toast.makeText(this, "y13", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("13y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q14.setOnClickListener {
            Toast.makeText(this, "y14", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("14y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q15.setOnClickListener {
            Toast.makeText(this, "y15", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("15y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }


        Q16.setOnClickListener {
            Toast.makeText(this, "y16", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("16y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q17.setOnClickListener {
            Toast.makeText(this, "y17", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("17y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q18.setOnClickListener {
            Toast.makeText(this, "y18", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("18y", "1sound"))
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }





        inputEnter2.setOnClickListener {

            if(QB2 !=0){
                sharedPreferences.edit().putString("QB2" + QB2.toString(), input3.text.toString()).commit()

                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putBoolean("multiclick", true).commit()
                val intent = Intent(this, Main::class.java)
                startActivity(intent)
            }



            if(QB2 == 1) {
                Q1.text = input3.text

            }
            if(QB2 == 2) {
                Q2.text = input3.text
            }
            if(QB2 == 3) {
                Q3.text = input3.text
            }
            if(QB2 == 4) {
                Q4.text = input3.text

            }
            if(QB2 == 5) {
                Q5.text = input3.text

            }
            if(QB2 == 6) {
                Q6.text = input3.text

            }
            if(QB2 == 7) {
                Q7.text = input3.text

            }
            if(QB2 == 8) {
                Q8.text = input3.text

            }
            if(QB2 == 9) {
                Q9.text = input3.text

            }
            if(QB2 == 10) {
                Q10.text = input3.text
            }
            if(QB2 == 11) {
                Q11.text = input3.text
            }
            if(QB2 == 12) {
                Q12.text = input3.text
            }
            if(QB2 == 13) {
                Q13.text = input3.text
            }
            if(QB2 == 14) {
                Q14.text = input3.text
            }
            if(QB2 == 15) {
                Q15.text = input3.text
            }
            if(QB2 == 16) {
                Q16.text = input3.text
            }
            if(QB2 == 17) {
                Q17.text = input3.text
            }
            if(QB2 == 18) {
                Q18.text = input3.text
            }



            QB2 = 0


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(250)
            }

        }





        input3.setOnLongClickListener {
            input3.setText("")
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }



        var change2 = findViewById<TextView>(R.id.change2)

        change2.setOnClickListener {
            var testletters2 = input3.text

            testletters2.toString().lowercase()

            var abslist2 = arrayOf(" ", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")
            var append2 = mutableListOf<Int>()
            for(i in 0..testletters2.length-1){
                for(j in 0 .. abslist2.size-1)
                    if (testletters2.get(i).toString() == abslist2.get(j)){
                        Log.d("lily", "not here" + j)
                        append2.add(j)
                    }
            }
            Log.d("lily", "here: "  + append2.toString())
            input3.setText(append2.toString())
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }



        var intercommand3y = true


        var intercommand2y = findViewById<ImageView>(R.id.intercommand2)
        intercommand2y.setOnClickListener {


            if(intercommand3y) {
                //Log.d("lily","intercommand")
               // Toast.makeText(this, "intercommand2y", Toast.LENGTH_SHORT).show()
                var time3y = LocalDateTime.now().hour
                var time4y = LocalDateTime.now().minute

                var timeNdate2y =  LocalDate.now().dayOfMonth.toString() + time3y + time4y.toString()



                sharedPreferences.edit().putString(QB2.toString() + "y", timeNdate2y).commit()
                checkAndRequestAudioPermission()
                intercommand3y = false
                Toast.makeText(this, "intercommand", Toast.LENGTH_SHORT).show()
            }
            else {
                //Log.d("lily","press enter")
                Toast.makeText(this, "press enter", Toast.LENGTH_SHORT).show()

                intercommand3y = true
            }



        }






































        var input4 = findViewById<EditText>(R.id.input3)
        var inputEnter3 = findViewById<TextView>(R.id.inputEnter3)
        var QB3 = 0

        var Q1v = findViewById<TextView>(R.id.one2)
        var Q2v = findViewById<TextView>(R.id.two2)
        var Q3v = findViewById<TextView>(R.id.three2)
        var Q4v = findViewById<TextView>(R.id.four2)
        var Q5v = findViewById<TextView>(R.id.five2)
        var Q6v = findViewById<TextView>(R.id.six2)
        var Q7v = findViewById<TextView>(R.id.seven2)
        var Q8v = findViewById<TextView>(R.id.eight2)
        var Q9v = findViewById<TextView>(R.id.nine2)
        var Q10v = findViewById<TextView>(R.id.ten2)
        var Q11v = findViewById<TextView>(R.id.eleven2)
        var Q12v = findViewById<TextView>(R.id.twelve2)
        var Q13v = findViewById<TextView>(R.id.thirteen2)
        var Q14v = findViewById<TextView>(R.id.fourteen2)
        var Q15v = findViewById<TextView>(R.id.fifteen2)
        var Q16v = findViewById<TextView>(R.id.sixteen2)
        var Q17v = findViewById<TextView>(R.id.seventeen2)
        var Q18v = findViewById<TextView>(R.id.eighteen2)

        Q1v.setText(sharedPreferences.getString("QB31v","      "))
        Q2v.setText(sharedPreferences.getString("QB32v","      "))
        Q3v.setText(sharedPreferences.getString("QB33v","      "))
        Q4v.setText(sharedPreferences.getString("QB34v","      "))
        Q5v.setText(sharedPreferences.getString("QB35v","      "))
        Q6v.setText(sharedPreferences.getString("QB36v","      "))
        Q7v.setText(sharedPreferences.getString("QB37v","      "))
        Q8v.setText(sharedPreferences.getString("QB38v","      "))
        Q9v.setText(sharedPreferences.getString("QB39v","      "))
        Q10v.setText(sharedPreferences.getString("QB310v","      "))
        Q11v.setText(sharedPreferences.getString("QB311v","      "))
        Q12v.setText(sharedPreferences.getString("QB312v","      "))
        Q13v.setText(sharedPreferences.getString("QB313v","      "))
        Q14v.setText(sharedPreferences.getString("QB314v","      "))
        Q15v.setText(sharedPreferences.getString("QB315v","      "))
        Q16v.setText(sharedPreferences.getString("QB316v","      "))
        Q17v.setText(sharedPreferences.getString("QB317v","      "))
        Q18v.setText(sharedPreferences.getString("QB318v","      "))

        Q1v.setOnLongClickListener {
            QB3 = 1
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q2v.setOnLongClickListener {
            QB3 = 2
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q3v.setOnLongClickListener {
            QB3 = 3
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q4v.setOnLongClickListener {
            QB3 = 4
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q5v.setOnLongClickListener {
            QB3 = 5
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q6v.setOnLongClickListener {
            QB3 = 6
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q7v.setOnLongClickListener {
            QB3 = 7
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q8v.setOnLongClickListener {
            QB3 = 8
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q9v.setOnLongClickListener {
            QB3 = 9
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q10v.setOnLongClickListener {
            QB3 = 10
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q11v.setOnLongClickListener {
            QB3 = 11
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q12v.setOnLongClickListener {
            QB3 = 12
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q13v.setOnLongClickListener {
            QB3 = 13
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q14v.setOnLongClickListener {
            QB3 = 14
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q15v.setOnLongClickListener {
            QB3 = 15
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q16v.setOnLongClickListener {
            QB3 = 16
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q17v.setOnLongClickListener {
            QB3 = 17
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q18v.setOnLongClickListener {
            QB3 = 18
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }






        Q1v.setOnClickListener {

            Toast.makeText(this, "Activate 1", Toast.LENGTH_SHORT).show()

            startPlaying(sharedPreferences.getString("1j", "1sound"))


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }


        }
        Q2v.setOnClickListener {
            Toast.makeText(this, "Activate 2", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("2j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q3v.setOnClickListener {
            Toast.makeText(this, "Activate 3", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("3j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q4v.setOnClickListener {
            Toast.makeText(this, "Activate 4", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("4j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q5v.setOnClickListener {
            Toast.makeText(this, "Activate 5", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("5j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }


        Q6v.setOnClickListener {
            Toast.makeText(this, "Activate 6", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("6j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q7v.setOnClickListener {
            Toast.makeText(this, "Activate 7", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("7j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q8v.setOnClickListener {
            Toast.makeText(this, "Activate 8", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("8j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q9v.setOnClickListener {
            Toast.makeText(this, "Activate 9", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("9j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q10v.setOnClickListener {
            Toast.makeText(this, "Activate 10", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("10j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q11v.setOnClickListener {
            Toast.makeText(this, "Activate 11", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("11j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q12v.setOnClickListener {
            Toast.makeText(this, "Activate 12", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("12j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }

        Q13v.setOnClickListener {
            Toast.makeText(this, "Activate 13", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("13j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q14v.setOnClickListener {
            Toast.makeText(this, "Activate 14", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("14j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q15v.setOnClickListener {
            Toast.makeText(this, "Activate 15", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("15j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }


        Q16v.setOnClickListener {
            Toast.makeText(this, "Activate 16", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("16j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q17v.setOnClickListener {
            Toast.makeText(this, "Activate 17", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("17j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q18v.setOnClickListener {
            Toast.makeText(this, "Activate 18", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("18j", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }









        inputEnter3.setOnClickListener {

            if(QB3 !=0){
                sharedPreferences.edit().putString("QB3" + QB3.toString() + "v", input4.text.toString()).commit()


                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putBoolean("multiclick", true).commit()
                val intent = Intent(this, Main::class.java)
                startActivity(intent)

            }



            if(QB3 == 1) {
                Q1v.text = input4.text

            }
            if(QB3 == 2) {
                Q2v.text = input4.text
            }
            if(QB3 == 3) {
                Q3v.text = input4.text
            }
            if(QB3 == 4) {
                Q4v.text = input4.text

            }
            if(QB3 == 5) {
                Q5v.text = input4.text

            }
            if(QB3 == 6) {
                Q6v.text = input4.text

            }
            if(QB3 == 7) {
                Q7v.text = input4.text

            }
            if(QB3 == 8) {
                Q8v.text = input4.text

            }
            if(QB3 == 9) {
                Q9v.text = input4.text

            }
            if(QB3 == 10) {
                Q10v.text = input4.text
            }
            if(QB3 == 11) {
                Q11v.text = input4.text
            }
            if(QB3 == 12) {
                Q12v.text = input4.text
            }
            if(QB3 == 13) {
                Q13v.text = input4.text
            }
            if(QB3 == 14) {
                Q14v.text = input4.text
            }
            if(QB3 == 15) {
                Q15v.text = input4.text
            }
            if(QB3 == 16) {
                Q16v.text = input4.text
            }
            if(QB3 == 17) {
                Q17v.text = input4.text
            }
            if(QB3 == 18) {
                Q18v.text = input4.text
            }



            QB3 = 0


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(250)
            }

        }





        input4.setOnLongClickListener {
            input4.setText("")

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }



        var change3 = findViewById<TextView>(R.id.change3)

        change3.setOnClickListener {
            var testletters3 = input4.text

            testletters3.toString().lowercase()

            var abslist3 = arrayOf(" ", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")
            var append3 = mutableListOf<Int>()
            for(i in 0..testletters3.length-1){
                for(j in 0 .. abslist3.size-1)
                    if (testletters3.get(i).toString() == abslist3.get(j)){
                        Log.d("lily", "not here" + j)
                        append3.add(j)
                    }
            }
            Log.d("lily", "here: "  + append3.toString())
            input4.setText(append3.toString())
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }









        var intercommand3 = true


        var intercommand2 = findViewById<ImageView>(R.id.intercommand1)
        intercommand2.setOnClickListener {


                if(intercommand3) {
                    Log.d("lily","intercommand")
                    Toast.makeText(this, "intercommand", Toast.LENGTH_SHORT).show()
                    var time3 = LocalDateTime.now().hour
                    var time4 = LocalDateTime.now().minute

                    var timeNdate2 =  LocalDate.now().dayOfMonth.toString() + time3 + time4.toString()

                    sharedPreferences.edit().putString(QB3.toString() + "j", timeNdate2).commit()
                    checkAndRequestAudioPermission()
                    intercommand3 = false
                }
                else {
                    Log.d("lily","press enter")
                    Toast.makeText(this, "press enter", Toast.LENGTH_SHORT).show()

                    intercommand3 = true
                }



        }

























        var inputx = findViewById<EditText>(R.id.inputx)
        var inputEnterx = findViewById<TextView>(R.id.inputEnterx)
        var QBx = 0

        var Q1x = findViewById<TextView>(R.id.one3)
        var Q2x = findViewById<TextView>(R.id.two3)
        var Q3x = findViewById<TextView>(R.id.three3)
        var Q4x = findViewById<TextView>(R.id.four3)
        var Q5x = findViewById<TextView>(R.id.five3)
        var Q6x = findViewById<TextView>(R.id.six3)
        var Q7x = findViewById<TextView>(R.id.seven3)
        var Q8x = findViewById<TextView>(R.id.eight3)
        var Q9x = findViewById<TextView>(R.id.nine3)
        var Q10x = findViewById<TextView>(R.id.ten3)
        var Q11x = findViewById<TextView>(R.id.eleven3)
        var Q12x = findViewById<TextView>(R.id.twelve3)
        var Q13x = findViewById<TextView>(R.id.thirteen3)
        var Q14x = findViewById<TextView>(R.id.fourteen3)
        var Q15x = findViewById<TextView>(R.id.fifteen3)
        var Q16x = findViewById<TextView>(R.id.sixteen3)
        var Q17x = findViewById<TextView>(R.id.seventeen3)
        var Q18x = findViewById<TextView>(R.id.eighteen3)

        Q1x.setText(sharedPreferences.getString("QB1x","      "))
        Q2x.setText(sharedPreferences.getString("QB2x","      "))
        Q3x.setText(sharedPreferences.getString("QB3x","      "))
        Q4x.setText(sharedPreferences.getString("QB4x","      "))
        Q5x.setText(sharedPreferences.getString("QB5x","      "))
        Q6x.setText(sharedPreferences.getString("QB6x","      "))
        Q7x.setText(sharedPreferences.getString("QB7x","      "))
        Q8x.setText(sharedPreferences.getString("QB8x","      "))
        Q9x.setText(sharedPreferences.getString("QB9x","      "))
        Q10x.setText(sharedPreferences.getString("QB10x","      "))
        Q11x.setText(sharedPreferences.getString("QB11x","      "))
        Q12x.setText(sharedPreferences.getString("QB12x","      "))
        Q13x.setText(sharedPreferences.getString("QB13x","      "))
        Q14x.setText(sharedPreferences.getString("QB14x","      "))
        Q15x.setText(sharedPreferences.getString("QB15x","      "))
        Q16x.setText(sharedPreferences.getString("QB16x","      "))
        Q17x.setText(sharedPreferences.getString("QB17x","      "))
        Q18x.setText(sharedPreferences.getString("QB18x","      "))

        Q1x.setOnLongClickListener {
            QBx = 1
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q2x.setOnLongClickListener {
            QBx = 2
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q3x.setOnLongClickListener {
            QBx = 3
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q4x.setOnLongClickListener {
            QBx = 4
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q5x.setOnLongClickListener {
            QBx = 5
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q6x.setOnLongClickListener {
            QBx = 6
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q7x.setOnLongClickListener {
            QBx = 7
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q8x.setOnLongClickListener {
            QBx = 8
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q9x.setOnLongClickListener {
            QBx = 9
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q10x.setOnLongClickListener {
            QBx = 10
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q11x.setOnLongClickListener {
            QBx = 11
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q12x.setOnLongClickListener {
            QBx = 12
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q13x.setOnLongClickListener {
            QBx = 13
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q14x.setOnLongClickListener {
            QBx = 14
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        Q15x.setOnLongClickListener {
            QBx = 15
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q16x.setOnLongClickListener {
            QBx = 16
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q17x.setOnLongClickListener {
            QBx = 17
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        Q18x.setOnLongClickListener {
            QBx = 18
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }






        Q1x.setOnClickListener {

            Toast.makeText(this, "1x", Toast.LENGTH_SHORT).show()

            startPlaying(sharedPreferences.getString("1x", "1sound"))


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }


        }
        Q2x.setOnClickListener {
            Toast.makeText(this, "2x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("2x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q3x.setOnClickListener {
            Toast.makeText(this, "3x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("3x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q4x.setOnClickListener {
            Toast.makeText(this, "4x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("4x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q5x.setOnClickListener {
            Toast.makeText(this, "5x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("5x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }


        Q6x.setOnClickListener {
            Toast.makeText(this, "6x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("6x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q7x.setOnClickListener {
            Toast.makeText(this, "7x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("7x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q8x.setOnClickListener {
            Toast.makeText(this, "8x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("8x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q9x.setOnClickListener {
            Toast.makeText(this, "9x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("9x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q10x.setOnClickListener {
            Toast.makeText(this, "10x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("10x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q11x.setOnClickListener {
            Toast.makeText(this, "11x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("11x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q12x.setOnClickListener {
            Toast.makeText(this, "12x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("12x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }

        Q13x.setOnClickListener {
            Toast.makeText(this, "13x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("13x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q14x.setOnClickListener {
            Toast.makeText(this, "14x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("14x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q15x.setOnClickListener {
            Toast.makeText(this, "15x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("15x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }


        Q16x.setOnClickListener {
            Toast.makeText(this, "16x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("16x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q17x.setOnClickListener {
            Toast.makeText(this, "17x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("17x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }
        Q18x.setOnClickListener {
            Toast.makeText(this, "18x", Toast.LENGTH_SHORT).show()
            startPlaying(sharedPreferences.getString("18x", "1sound"))

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
        }








        inputEnterx.setOnClickListener {

            if(QBx !=0){
                sharedPreferences.edit().putString("QB" + QBx.toString() + "x", inputx.text.toString()).commit()


                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putBoolean("multiclick", true).commit()
                val intent = Intent(this, Main::class.java)
                startActivity(intent)

            }



            if(QBx == 1) {
                Q1x.text = inputx.text

            }
            if(QBx == 2) {
                Q2x.text = inputx.text
            }
            if(QBx == 3) {
                Q3x.text = inputx.text
            }
            if(QBx == 4) {
                Q4x.text = inputx.text

            }
            if(QBx == 5) {
                Q5x.text = inputx.text

            }
            if(QBx == 6) {
                Q6x.text = inputx.text

            }
            if(QBx == 7) {
                Q7x.text = inputx.text

            }
            if(QBx == 8) {
                Q8x.text = inputx.text

            }
            if(QBx == 9) {
                Q9x.text = inputx.text

            }
            if(QBx == 10) {
                Q10x.text = inputx.text
            }
            if(QBx == 11) {
                Q11x.text = inputx.text
            }
            if(QBx == 12) {
                Q12x.text = inputx.text
            }
            if(QBx == 13) {
                Q13x.text = inputx.text
            }
            if(QBx == 14) {
                Q14x.text = inputx.text
            }
            if(QBx == 15) {
                Q15x.text = inputx.text
            }
            if(QBx == 16) {
                Q16x.text = inputx.text
            }
            if(QBx == 17) {
                Q17x.text = inputx.text
            }
            if(QBx == 18) {
                Q18x.text = inputx.text
            }



            QBx = 0


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(250)
            }

        }





        inputx.setOnLongClickListener {
            inputx.setText("")
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }



        var changex = findViewById<TextView>(R.id.changex)

        changex.setOnClickListener {
            var testletters3x = inputx.text

            testletters3x.toString().lowercase()

            var abslistx = arrayOf(" ", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")
            var appendx = mutableListOf<Int>()
            for(i in 0..testletters3x.length-1){
                for(j in 0 .. abslistx.size-1)
                    if (testletters3x.get(i).toString() == abslistx.get(j)){
                        Log.d("lily", "not here" + j)
                        appendx.add(j)
                    }
            }
            Log.d("lily", "here: "  + appendx.toString())
            inputx.setText(appendx.toString())

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }










//grey boxes
        
        
        var intercommand3x = true


        var intercommand2x = findViewById<ImageView>(R.id.intercommandx)
        intercommand2x.setOnClickListener {


            if(intercommand3x) {
                Log.d("lily","intercommand")
                //Toast.makeText(this, "intercommand1", Toast.LENGTH_SHORT).show()
                var time3x = LocalDateTime.now().hour
                var time4x = LocalDateTime.now().minute

                var timeNdate2x =  LocalDate.now().dayOfMonth.toString() + time3x + time4x.toString()

                sharedPreferences.edit().putString(QBx.toString() + "x", timeNdate2x).commit()
                checkAndRequestAudioPermission()
                intercommand3x = false
                Toast.makeText(this, "intercommand", Toast.LENGTH_SHORT).show()
            }
            else {
                //Log.d("lily","press enter")
                Toast.makeText(this, "press enter", Toast.LENGTH_SHORT).show()

                intercommand3x = true
            }



        }




































        var subtract = findViewById<TextView>(R.id.subtract)
        subtract.setOnClickListener {

            Toast.makeText(this, "overwritten", Toast.LENGTH_SHORT).show()

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

        QTOne.setText(sharedPreferences.getString("QTB1",""))
        QT2.setText(sharedPreferences.getString("QTB2",""))
        QT3.setText(sharedPreferences.getString("QTB3",""))
        QT4.setText(sharedPreferences.getString("QTB4",""))
        QT5.setText(sharedPreferences.getString("QTB5",""))
        QT6.setText(sharedPreferences.getString("QTB6",""))
        QT7.setText(sharedPreferences.getString("QTB7",""))
        QT8.setText(sharedPreferences.getString("QTB8",""))
        QT9.setText(sharedPreferences.getString("QTB9",""))
        QT10.setText(sharedPreferences.getString("QTB10",""))
        QT11.setText(sharedPreferences.getString("QTB11",""))
        QT12.setText(sharedPreferences.getString("QTB12",""))
        QT13.setText(sharedPreferences.getString("QTB13",""))
        QT14.setText(sharedPreferences.getString("QTB14",""))
        QT15.setText(sharedPreferences.getString("QTB15",""))
        QT16.setText(sharedPreferences.getString("QTB16",""))
        QT17.setText(sharedPreferences.getString("QTB17",""))
        QT18.setText(sharedPreferences.getString("QTB18",""))







        QTOne.setOnLongClickListener {
            QTB = 1
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT2.setOnLongClickListener {
            QTB = 2
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT3.setOnLongClickListener {
            QTB = 3
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT4.setOnLongClickListener {
            QTB = 4
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT5.setOnLongClickListener {
            QTB = 5
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT6.setOnLongClickListener {
            QTB = 6
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT7.setOnLongClickListener {
            QTB = 7
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT8.setOnLongClickListener {
            QTB = 8
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT9.setOnLongClickListener {
            QTB = 9
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT10.setOnLongClickListener {
            QTB = 10
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
          QT11.setOnLongClickListener {
              QTB = 11
              Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
              val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
              if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
              }
              else {
                  @Suppress("DEPRECATION")
                  v.vibrate(200)
              }
              return@setOnLongClickListener true
          }

        QT12.setOnLongClickListener {
            QTB = 12
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        QT13.setOnLongClickListener {
            QTB = 13
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        QT14.setOnLongClickListener {
            QTB = 14
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        QT15.setOnLongClickListener {
            QTB = 15
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        QT16.setOnLongClickListener {
            QTB = 16
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        QT17.setOnLongClickListener {
            QTB = 17
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }
        QT18.setOnLongClickListener {
            QTB = 18
            Toast.makeText(this, "Set the description", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }



        QTOne.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            checkHourGoal(true)
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT1C = sharedPreferences.getInt("QT1C", 0)

            QT1C++
            sharedPreferences.edit().putInt("QT1C", QT1C).commit()
            Toast.makeText(this, "QT1C: " + QT1C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QTOne.text.toString() + " Count: " + QT1C, 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QTOne.text.toString() + " Count:" + QT1C.toString(), null)



        }
        QT2.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            checkHourGoal(true)

            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT2C = sharedPreferences.getInt("QT2C", 0)
            QT2C++
            sharedPreferences.edit().putInt("QT2C", QT2C).commit()
            Toast.makeText(this, "QT2C: " + QT2C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT2.text.toString() + " Count: " + QT2C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT2.text.toString() + " Count:" + QT2C.toString(), null)


        }
        QT3.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT3C = sharedPreferences.getInt("QT3C", 0)
            QT3C++
            sharedPreferences.edit().putInt("QT3C", QT3C).commit()
            Toast.makeText(this, "QT3C: " + QT3C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT3.text.toString() + " Count: " + QT3C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT3.text.toString() + " Count:" + QT3C.toString(), null)

            checkHourGoal(true)

        }
        QT4.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT4C = sharedPreferences.getInt("QT4C", 0)
            QT4C++
            sharedPreferences.edit().putInt("QT4C", QT4C).commit()
            Toast.makeText(this, "QT4C: " + QT4C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT4.text.toString() + " Count: " + QT4C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT4.text.toString() + " Count:" + QT4C.toString(), null)

            checkHourGoal(true)

        }
        QT5.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT5C = sharedPreferences.getInt("QT5C", 0)
            QT5C++
            sharedPreferences.edit().putInt("QT5C", QT5C).commit()
            Toast.makeText(this, "QT5C: " + QT5C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT5.text.toString() + " Count: " + QT5C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT5.text.toString() + " Count:" + QT5C.toString(), null)

            checkHourGoal(true)

        }


        QT6.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT6C = sharedPreferences.getInt("QT6C", 0)
            QT6C++
            sharedPreferences.edit().putInt("QT6C", QT6C).commit()
            Toast.makeText(this, "QT6C: " + QT6C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT6.text.toString() + " Count: " + QT6C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT6.text.toString() + " Count:" + QT6C.toString(), null)

            checkHourGoal(true)

        }
        QT7.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT7C = sharedPreferences.getInt("QT7C", 0)
            QT7C++
            sharedPreferences.edit().putInt("QT7C", QT7C).commit()
            Toast.makeText(this, "Seven: " + QT7C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT7.text.toString() + " Count: " + QT7C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT7.text.toString() + " Count:" + QT7C.toString(), null)

            checkHourGoal(true)

        }

/*
        QT7.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT7C = sharedPreferences.getInt("QT7C", 0)
            QT7C++
            sharedPreferences.edit().putInt("QT7C", QT7C).commit()
            Toast.makeText(this, "QT7C: " + QT7C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT7.text.toString() + " Count: " + QT7C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT7.text.toString() + " Count:" + QT7C.toString(), null)

            checkHourGoal(true)

        }*/

        QT8.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT8C = sharedPreferences.getInt("QT8C", 0)
            QT8C++
            sharedPreferences.edit().putInt("QT8C", QT8C).commit()
            Toast.makeText(this, "QT8C: " + QT8C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT8.text.toString() + " Count: " + QT8C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT8.text.toString() + " Count:" + QT8C.toString(), null)

            checkHourGoal(true)

        }
        QT9.setOnClickListener {

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT9C = sharedPreferences.getInt("QT9C", 0)
            QT9C++
            sharedPreferences.edit().putInt("QT9C", QT9C).commit()
            Toast.makeText(this, "QT9C: " + QT9C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT9.text.toString() + " Count: " + QT9C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT9.text.toString() + " Count:" + QT9C.toString(), null)

            checkHourGoal(true)

        }
        QT10.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT10C = sharedPreferences.getInt("QT10C", 0)
            QT10C++
            sharedPreferences.edit().putInt("QT10C", QT10C).commit()
            Toast.makeText(this, "QT10C: " + QT10C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT10.text.toString() + " Count: " + QT10C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT10.text.toString() + " Count:" + QT10C.toString(), null)

            checkHourGoal(true)

        }
        QT11.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT11C = sharedPreferences.getInt("QT11C", 0)
            QT11C++
            sharedPreferences.edit().putInt("QT11C", QT11C).commit()
            Toast.makeText(this, "QT11C: " + QT11C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT11.text.toString() + " Count: " + QT11C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT11.text.toString() + " Count:" + QT11C.toString(), null)

            checkHourGoal(true)

        }


        QT12.setOnClickListener {

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT12C = sharedPreferences.getInt("QT12C", 0)
            QT12C++
            sharedPreferences.edit().putInt("QT12C", QT12C).commit()
            logIt("#" + TC + "\n" + QT12.text.toString() + " Count: " + QT12C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT12.text.toString() + " Count:" + QT12C.toString(), null)

            checkHourGoal(true)

        }
      /*  QT12.setOnClickListener {

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT12C = sharedPreferences.getInt("QT12C", 0)
            QT12C++
            sharedPreferences.edit().putInt("QT12C", QT12C).commit()
            Toast.makeText(this, "QT12C: " + QT12C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT12.text.toString() + " Count: " + QT12C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT12.text.toString() + " Count:" + QT12C.toString(), null)

            checkHourGoal(true)

        }*/

        QT13.setOnClickListener {

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT13C = sharedPreferences.getInt("QT13C", 0)
            QT13C++
            sharedPreferences.edit().putInt("QT13C", QT13C).commit()
            Toast.makeText(this, "QT13C: " + QT13C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT13.text.toString() + " Count: " + QT13C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT13.text.toString() + " Count:" + QT13C.toString(), null)

            checkHourGoal(true)

        }
        QT14.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT14C = sharedPreferences.getInt("QT14C", 0)
            QT14C++
            sharedPreferences.edit().putInt("QT14C", QT14C).commit()
            Toast.makeText(this, "QT14C: " + QT14C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT14.text.toString() + " Count: " + QT14C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT14.text.toString() + " Count:" + QT14C.toString(), null)

            checkHourGoal(true)

        }
        QT15.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT15C = sharedPreferences.getInt("QT15C", 0)
            QT15C++
            sharedPreferences.edit().putInt("QT15C", QT15C).commit()
            Toast.makeText(this, "QT15C: " + QT15C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT15.text.toString() + " Count: " + QT15C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT15.text.toString() + " Count:" + QT15C.toString(), null)

            checkHourGoal(true)

        }


        QT16.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT16C = sharedPreferences.getInt("QT16C", 0)
            QT16C++
            sharedPreferences.edit().putInt("QT16C", QT16C).commit()
            Toast.makeText(this, "QT16C: " + QT16C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT16.text.toString() + " Count: " + QT16C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT16.text.toString() + " Count:" + QT16C.toString(), null)

            checkHourGoal(true)

        }
        QT17.setOnClickListener {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT17C = sharedPreferences.getInt("QT17C", 0)
            QT17C++
            sharedPreferences.edit().putInt("QT17C", QT17C).commit()
            Toast.makeText(this, "QT17C: " + QT17C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT17.text.toString() + " Count: " + QT17C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT17.text.toString() + " Count:" + QT17C.toString(), null)

            checkHourGoal(true)

        }
        QT18.setOnClickListener {

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }
            TC = sharedPreferences.getInt("TC", 0)

            TC++
            sharedPreferences.edit().putInt("TC", TC).commit()
            var QT18C = sharedPreferences.getInt("QT18C", 0)
            QT18C++
            sharedPreferences.edit().putInt("QT18C", QT18C).commit()
            Toast.makeText(this, "QT18C: " + QT18C, Toast.LENGTH_SHORT).show()
            logIt("#" + TC + "\n" + QT18.text.toString() + " Count: " + QT18C + "\n", 2)
            writeNewPost("jls", date1, time.text.toString(), "#" + TC + "\n" + QT18.text.toString() + " Count:" + QT18C.toString(), null)

            checkHourGoal(true)

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
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
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
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }





/*

        var intercommand3y = true


        var intercommand2y = findViewById<ImageView>(R.id.intercommandx)
        intercommand2y.setOnClickListener {


            if(intercommand3y) {
                Log.d("lily","intercommand")
                Toast.makeText(this, "intercommand", Toast.LENGTH_SHORT).show()
                var time3y = LocalDateTime.now().hour
                var time4y = LocalDateTime.now().minute

                //todo change name from hour and minute to something that never overlaps
                var timeNdate2y =  LocalDate.now().dayOfMonth.toString() + time3y + time4y.toString()

                sharedPreferences.edit().putString(QTB.toString(), timeNdate2y).commit()
                checkAndRequestAudioPermission()
                intercommand3x = false
            }
            else {
                Log.d("lily","press enter")
                Toast.makeText(this, "stop intercommand", Toast.LENGTH_SHORT).show()

                intercommand3x = true
            }



        }
*/




























        guitar.setOnClickListener {

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
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }


        }

        walkie.setOnClickListener {
            //another button can write
            Toast.makeText(this, "talkie", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.pingle2)

            Log.d("lily", "ping")
            mediaPlayer?.setVolume(1f,1f)

            mediaPlayer?.start()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

        }

        infomessage.setOnClickListener {
            //another button can write
            Toast.makeText(this, "infomessage", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.jingle)

            Log.d("lily", "informessage")
            mediaPlayer?.setVolume(1f,1f)

            mediaPlayer?.start()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }


        }


        ping.setOnClickListener {
            //another button can write
            Toast.makeText(this, "ping", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.keyboard)

            Log.d("lily", "ping")
            mediaPlayer?.setVolume(1f,1f)

            mediaPlayer?.start()


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        infoping.setOnClickListener {
            //another button can write
            Toast.makeText(this, "infoping", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.bell)

            Log.d("lily", "ping2")
            mediaPlayer?.setVolume(1f,1f)

            mediaPlayer?.start()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

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





        lost.setOnClickListener {
            Toast.makeText(this, "they lost", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.lost)
            Log.d("lily", "lost")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }



        bye.setOnClickListener {
            Toast.makeText(this, "bye", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.bye)
            Log.d("lily", "bye")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }


        //turns off their bbgn
        turnitoff.setOnClickListener {
            Toast.makeText(this, "turn it off", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.turnitoff)
            Log.d("lily", "turn it off")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        var collect = findViewById<ImageView>(R.id.collect)



        collect.setOnClickListener {
            //make this a song
            Toast.makeText(this, "collect", Toast.LENGTH_SHORT).show()
            Log.d("lily", "collect")
          /*  mediaPlayer = MediaPlayer.create(this,)

            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()*/

        }






        stop.setOnClickListener {
            //make this a song
            Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.stop)
            Log.d("lily", "stop")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        off.setOnClickListener {
            //make this a song
            Toast.makeText(this, "shut off", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.off)
            Log.d("lily", "shutoff")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }



        nogood.setOnClickListener {
            //make this a song
            Toast.makeText(this, "good riddance", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.hesnogood)
            //Log.d("lily", "right on")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

        }

        leavemealone.setOnClickListener {
            //make this a song
            Toast.makeText(this, "leave me alone", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.leavejeffreyalone)
            //Log.d("lily", "right on")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }


        righton.setOnClickListener {
            //make this a song
            Toast.makeText(this, "right on", Toast.LENGTH_SHORT).show()
            mediaPlayer = MediaPlayer.create(this, R.raw.righton)
            Log.d("lily", "right on")
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

        }


        var boggle = true

        theButtonsButton.setOnClickListener {
            if(boggle) {
                froggle(6)
                sharedPreferences.edit().putInt("toggle", 6).commit()
                boggle = false
            }
            else{
                froggle(12)
                sharedPreferences.edit().putInt("toggle", 12).commit()
                boggle = true
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


        theButtonsButton.setOnLongClickListener {
            froggle(8)
            sharedPreferences.edit().putInt("toggle", 8).commit()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }




        phoneButton.setOnClickListener {
            froggle(2)
            sharedPreferences.edit().putInt("toggle", 2).commit()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        phoneButton.setOnLongClickListener {
            froggle(10)

            sharedPreferences.edit().putInt("toggle", 10).commit()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        modesButton.setOnClickListener {
            froggle(3)
            sharedPreferences.edit().putInt("toggle", 3).commit()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }



        var toggle2 = 0
        //go to quick screen on first click then on second go to second screen
        //after a few seeconds return to first site first and second second



        QQTS.setOnClickListener {




            if (toggle2 == 0) {
                sharedPreferences.edit().putBoolean("returnFrog", true).commit()

                sharedPreferences.edit().putInt("toggle", 4).commit()
                froggle(4)


                toggle2 = 1
            }
            else if(toggle2 == 1) {
                froggle(11)
                toggle2 = 2
            }
            else if (toggle2 == 2){
                froggle(13)
                toggle2 = 0
            }
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }


            lifecycleScope.launch {
                delay(2000)

                toggle2 = 0
            }


            //todo add another page
        }




        var boxes2 = findViewById<RelativeLayout>(R.id.boxes2)

     /*   QQTS.setOnLongClickListener {


            return@setOnLongClickListener true
        }*/


        bslistButton.setOnClickListener {
            froggle(5)
            sharedPreferences.edit().putInt("toggle", 5).commit()

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

        }
        var backupLayout = findViewById<RelativeLayout>(R.id.backupLayout)

        bslistButton.setOnLongClickListener {
            //show list
            clean()
            backupLayout.visibility = View.VISIBLE
            backupLayout.bringToFront()
            Log.d("lily", backupReminders1.toString() + " Here it is")
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }


            guide.setOnClickListener{
            showDialog(this, "This where the backup request tickets are stored for quick reference")

            }
            return@setOnLongClickListener true
        }






        var backup = findViewById<TextView>(R.id.backup)
        var resetBox = findViewById<ImageView>(R.id.resetBox)

        var version = sharedPreferences.getInt("version", 0)
        backup.setOnClickListener {
            version++
            backUpLog("Version: " + version + "\n"+ potentialSolutions.text.toString())
            Toast.makeText(this, "requesting backup", Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putInt("version", version).commit()
            //writeNewUser("backup request", "backup request", "fake@mail.com")
            writeNewPost("back up request", date1, time.text.toString(), "Version: " + version + "\n"+ potentialSolutions.text.toString(), null)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }


        }

        backup.setOnLongClickListener {
            //show list
            clean()

            backupLayout.visibility = View.VISIBLE
            backupLayout.bringToFront()
            Log.d("lily", backupReminders1.toString() + " Here it is")
            // orderList()
            dateTime.visibility = View.VISIBLE
            ticket.visibility = View.VISIBLE
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }


            return@setOnLongClickListener true
        }

        var safety1 = 0
        resetBox.setOnClickListener {
            safety1++
            if(safety1 == 7){
                Toast.makeText(this, "requesting reset", Toast.LENGTH_SHORT).show()
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                }
                else {
                    @Suppress("DEPRECATION")
                    v.vibrate(200)
                }
            }

        }





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
                    // react to click
                }

            }
        })





        var hour = LocalDateTime.now().hour




        //in the future replace with append and commas
        val externalDir1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val savedList = "HEREbsl" + month + day + year + ".txt"
        val file = File(externalDir1, savedList)
        file.writeText(bslist.joinToString("\n" ))

       /* var newlist = bslist

        for(i in 0 until newlist.size) {
            newlist.get(i).plus(",")
        }
        Log.d("lily",newlist.toString())

*/

        var many = 0
        //var currentItemColor = findViewById<ImageView>(R.id.currentItemColor)

        currentItemColor.setOnClickListener {


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(500)
            }

            file.writeText(bslist.joinToString("\n" ))

            saveSharedPreferencestoExternal(this, "daysSince", "bsl" + month + day + year)
            many++
            if(many == 5){
                bsl.visibility = View.INVISIBLE
            }
            if(many >= 10){
                file.writeText(bslist.joinToString("\n" ))


                for(i in 0.. bslist.size-1) {
                    sharedPreferences.edit().remove(i.toString()).commit()
                }
                sharedPreferences.edit().putInt("count1", 0).commit()

                bsl.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,bslist)

                many = 0
                val intent = Intent(this, Main::class.java)
                startActivity(intent)
            }
        }




        var ticketPage = findViewById<RelativeLayout>(R.id.ticketPage)
        var ticketTitle = findViewById<TextView>(R.id.ticketTitle)
        var ticketUpdate = findViewById<TextView>(R.id.ticketUpdate)
        var submitUpdate = findViewById<TextView>(R.id.submitUpdate)

        var green1 = findViewById<ImageView>(R.id.good1)
        var lime1 = findViewById<ImageView>(R.id.lime1)
        var yellow1 = findViewById<ImageView>(R.id.yellowcard1)
        var red1 = findViewById<ImageView>(R.id.redcard1)

        var position1 = 0

        bsl.setOnItemLongClickListener { parent, view, position, id ->

            submitUpdate.setOnClickListener {

                var title1 = bslist.get(position)

                sharedPreferences.edit().putString(position.toString() + "ticket", ticketUpdate.text.toString()).commit()

                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()

                bslist.removeAt(position)

                title1 = "...\n" + title1
                bslist.add(position,title1)
                ticketTitle.setText(bslist.get(position))
                sharedPreferences.edit().putString(position.toString(), title1).commit()



                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(500)

                }

                sharedPreferences.edit().putBoolean("multiclick", true).commit()
                val intent = Intent(this, Main::class.java)
                startActivity(intent)

            }
            position1 = position
            clean()
            ticketPage.visibility = View.VISIBLE

            ticketTitle.setText(bslist.get(position))
            Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
            ticketUpdate.setText(sharedPreferences.getString(position.toString() + "ticket", "unresolved"))



            //Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()


            var title1 = bslist.get(position)
            green1.setOnClickListener {
                bslist.removeAt(position)

                title1 = title1.dropLast(2)
                title1 = title1 + " 1"
                bslist.add(position,title1)
                Log.d("lily", title1)





                ticketTitle.setText(bslist.get(position))
                sharedPreferences.edit().putString(position.toString(), title1).commit()
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                }
                else {
                    @Suppress("DEPRECATION")
                    v.vibrate(200)
                }

            }
            lime1.setOnClickListener {
                bslist.removeAt(position)

                title1 = title1.dropLast(2)
                title1 = title1 + " 2"
                bslist.add(position,title1)
                Log.d("lily", title1)

                ticketTitle.setText(bslist.get(position))

                sharedPreferences.edit().putString(position.toString(), title1).commit()
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                }
                else {
                    @Suppress("DEPRECATION")
                    v.vibrate(200)
                }

            }
            yellow1.setOnClickListener {
                bslist.removeAt(position)

                title1 = title1.dropLast(2)
                title1 = title1 + " 3"
                bslist.add(position,title1)
                Log.d("lily", title1)

                ticketTitle.setText(bslist.get(position))

                sharedPreferences.edit().putString(position.toString(), title1).commit()
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                }
                else {
                    @Suppress("DEPRECATION")
                    v.vibrate(200)
                }

            }
            red1.setOnClickListener {
                bslist.removeAt(position)

                title1 = title1.dropLast(2)
                title1 = title1 + " 4"
                bslist.add(position,title1)
                Log.d("lily", title1)





                ticketTitle.setText(bslist.get(position))
                sharedPreferences.edit().putString(position.toString(), title1).commit()
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                }
                else {
                    @Suppress("DEPRECATION")
                    v.vibrate(200)
                }

            }





            var color2 = sharedPreferences.getString(position.toString() + bslist.get(position) + "level", "green")



            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnItemLongClickListener true






        }






        val timer = object : CountDownTimer(60000, 1000){
            override fun onFinish() {
                Toast.makeText(this@Main, "bye", Toast.LENGTH_SHORT).show()
                mediaPlayer = MediaPlayer.create(this@Main, R.raw.bye)
                Log.d("lily", "bye")
                mediaPlayer?.setVolume(1f,1f)
                mediaPlayer?.start()

                convoTimer.visibility = View.INVISIBLE
                convoTime.visibility = View.INVISIBLE

                homePage = false

            }

            override fun onTick(p0: Long) {

                val secondsRemaining = p0/1000
                convoTimer.text = "00:" + secondsRemaining.toString()
                if(homePage) {
                    convoTimer.visibility = View.VISIBLE
                    convoTime.visibility = View.VISIBLE
                }
            }
        }


        convoTime.setOnClickListener {
            timer.cancel()
            timer.start()
           removeFromClean = true
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }


        val timer1 = object : CountDownTimer(15 * 60000, 1000){
            override fun onFinish() {
                Toast.makeText(this@Main, "bye", Toast.LENGTH_SHORT).show()
                mediaPlayer = MediaPlayer.create(this@Main, R.raw.bye)
                Log.d("lily", "bye")
                mediaPlayer?.setVolume(1f,1f)
                mediaPlayer?.start()

                convoTimer.visibility = View.INVISIBLE
                convoTime.visibility = View.INVISIBLE

                homePage = false

            }

            override fun onTick(p0: Long) {

                val secondsRemaining = p0/1000
                convoTimer.text = "00:" + secondsRemaining.toString()
                if(homePage) {
                    convoTimer.visibility = View.VISIBLE
                    convoTime.visibility = View.VISIBLE
                }
            }
        }


        convoTime.setOnLongClickListener {
            timer1.cancel()
            timer1.start()
            removeFromClean = true
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }



        convoTimer.setOnClickListener {
            timer1.cancel()
            timer.cancel()
            convoTimer.text = "00:00"
        }
/*

        val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        } else {
            MediaRecorder()
        }
                var externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val sound = File(externalDir, "1sound.mp3")
*/




        var intercommand1 = true

        var intercommand = findViewById<ImageView>(R.id.intercommand)

        intercommand.setOnClickListener {

            if(intercommand1) {
                Log.d("lily","intercommand")


                Toast.makeText(this, "intercommand", Toast.LENGTH_SHORT).show()




                var time3 = LocalDateTime.now().hour
                var time4 = LocalDateTime.now().minute

                var timeNdate2 =  LocalDate.now().dayOfMonth.toString() + time3 + time4.toString()
                sharedPreferences.edit().putString("1sound", timeNdate2).commit()
                checkAndRequestAudioPermission()




                intercommand1 = false
            }
            else {
                Log.d("lily","stop rec")
                Toast.makeText(this, "stop intercommand", Toast.LENGTH_SHORT).show()


                sharedPreferences.edit().putBoolean("multiclick", true).commit()
                val intent = Intent(this, Main::class.java)
                startActivity(intent)
           /*     try {
                    recorder.stop()
                    Toast.makeText(this, "stop intercommand", Toast.LENGTH_SHORT).show()

                } catch (e: IllegalStateException) {

                    // Handle the error or log it
                } finally {
                    try {
                        recorder.reset()
                        Toast.makeText(this, "stop intercommand", Toast.LENGTH_SHORT).show()

                    } catch(e: IllegalStateException) {

                    }
                    recorder.release()
                    Toast.makeText(this, "stop intercommand", Toast.LENGTH_SHORT).show()

                }*/
                intercommand1 = true
            }
        }



        var play = findViewById<TextView>(R.id.play)
        var playOn = true
        play.setOnClickListener {
            if(playOn){
                Log.d("lily","playon")
                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show()

                startPlaying(sharedPreferences.getString("1sound", "1sound"))
                playOn = false
            }
            else{
                stopPlaying()
                playOn = true
                Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show()

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



        var searchList1 = findViewById<ListView>(R.id.searchlist1)

        var searchList2 = mutableListOf<String>()


        //searchList1 = bslist

        searchTerm.setOnLongClickListener {
            bsl.visibility = View.VISIBLE
            searchList1.visibility = View.INVISIBLE
            searchTerm.setText("")
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        searchEnter.setOnClickListener {
            searchList2.clear()
        for(i in 0 .. bslist.size-1){

            if (bslist.get(i).contains(searchTerm.text)) {

                searchList2.add(bslist.get(i))

            }

        }

        searchList1.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,searchList2)

            bsl.visibility = View.INVISIBLE
            searchList1.visibility = View.VISIBLE
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

        }
        //hide previouslist
        //display search list 2


        var points = findViewById<TextView>(R.id.points)
        var pointCount = sharedPreferences.getInt("points", 10)

        var downpoint = findViewById<TextView>(R.id.downpoint)
        var addition = findViewById<TextView>(R.id.addition)

        var withdraw = findViewById<TextView>(R.id.withdraw)

        addition.setOnClickListener {
            pointCount++
            sharedPreferences.edit().putInt("points", pointCount).commit()
            points.setText(pointCount.toString())
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        downpoint.setOnClickListener {
            pointCount--
            sharedPreferences.edit().putInt("points", pointCount).commit()

            points.setText(pointCount.toString())
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        withdraw.setOnClickListener {
            pointCount--
            sharedPreferences.edit().putInt("points", pointCount).commit()

            points.setText(pointCount.toString())
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        points.setText(pointCount.toString())






        var kudos = findViewById<TextView>(R.id.kudos)
        var pointCount1 = sharedPreferences.getInt("kudos", 10)

        var downpoint1 = findViewById<TextView>(R.id.downpoint1)
        var addition1 = findViewById<TextView>(R.id.addition1)

        var withdraw1 = findViewById<TextView>(R.id.withdraw1)

        addition1.setOnClickListener {
            pointCount1++
            sharedPreferences.edit().putInt("kudos", pointCount1).commit()
            kudos.setText(pointCount1.toString())
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        downpoint1.setOnClickListener {
            pointCount1--
            sharedPreferences.edit().putInt("kudos", pointCount1).commit()

            kudos.setText(pointCount1.toString())
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        withdraw1.setOnClickListener {
            pointCount1--
            sharedPreferences.edit().putInt("kudos", pointCount1).commit()

            kudos.setText(pointCount1.toString())
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }

        kudos.setText(pointCount1.toString())



      /*  var input2 = findViewById<EditText>(R.id.text1)
        var submitInput = findViewById<TextView>(R.id.submitInput)
        var boxNumber = 0

        var box1 = findViewById<TextView>(R.id.box1)
        var box2 = findViewById<TextView>(R.id.box2)
        var box3 = findViewById<TextView>(R.id.box3)
        var box4 = findViewById<TextView>(R.id.box4)
        var box5 = findViewById<TextView>(R.id.box5)

        var box6 = findViewById<TextView>(R.id.box6)
        var box7 = findViewById<TextView>(R.id.box7)
        var box8 = findViewById<TextView>(R.id.box8)
        var box9 = findViewById<TextView>(R.id.box9)
        var box10 = findViewById<TextView>(R.id.box10)

        var box1text = sharedPreferences.getString("box1text", "nothing")
        var box2text = sharedPreferences.getString("box2text", "nothing")
        var box3text = sharedPreferences.getString("box3text", "nothing")
        var box4text = sharedPreferences.getString("box4text", "nothing")
        var box5text = sharedPreferences.getString("box5text", "nothing")

        var box6text = sharedPreferences.getString("box6text", "nothing")
        var box7text = sharedPreferences.getString("box7text", "nothing")
        var box8text = sharedPreferences.getString("box8text", "nothing")
        var box9text = sharedPreferences.getString("box9text", "nothing")
        var box10text = sharedPreferences.getString("box10text", "nothing")





        box1.setOnClickListener {
            box1text = sharedPreferences.getString("box1text", "nothing")

            boxNumber = 1
            Toast.makeText(this, box1text, Toast.LENGTH_SHORT).show()

        }

        box1.setOnLongClickListener {
            Toast.makeText(this, box1text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true

        }





        box2.setOnClickListener {
            box2text = sharedPreferences.getString("box2text", "nothing")

            boxNumber = 2
            Toast.makeText(this, box2text, Toast.LENGTH_SHORT).show()

        }

        box2.setOnLongClickListener {
            Toast.makeText(this, box2text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }





        box3.setOnClickListener {
            box3text = sharedPreferences.getString("box3text", "nothing")

            boxNumber = 3
            Toast.makeText(this, box3text, Toast.LENGTH_SHORT).show()

        }

        box3.setOnLongClickListener {
            Toast.makeText(this, box3text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }





        box4.setOnClickListener {
            box4text = sharedPreferences.getString("box4text", "nothing")

            boxNumber = 4
            Toast.makeText(this, box4text, Toast.LENGTH_SHORT).show()

        }

        box4.setOnLongClickListener {
            Toast.makeText(this, box4text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }



        box5.setOnClickListener {
            box5text = sharedPreferences.getString("box5text", "nothing")

            boxNumber = 5
            Toast.makeText(this, box5text, Toast.LENGTH_SHORT).show()

        }

        box5.setOnLongClickListener {
            Toast.makeText(this, box5text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        submitInput.setOnClickListener {


                sharedPreferences.edit().putString("box${boxNumber}text", input2.text.toString()).commit()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

        }



        box6.setOnClickListener {
            box6text = sharedPreferences.getString("box6text", "nothing")

            boxNumber = 6
            Toast.makeText(this, box6text, Toast.LENGTH_SHORT).show()

        }

        box6.setOnLongClickListener {
            Toast.makeText(this, box6text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }


        box7.setOnClickListener {
            box7text = sharedPreferences.getString("box7text", "nothing")

            boxNumber = 7
            Toast.makeText(this, box7text, Toast.LENGTH_SHORT).show()

        }

        box7.setOnLongClickListener {
            Toast.makeText(this, box7text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }




        box8.setOnClickListener {
            box8text = sharedPreferences.getString("box8text", "nothing")

            boxNumber = 8
            Toast.makeText(this, box8text, Toast.LENGTH_SHORT).show()

        }

        box8.setOnLongClickListener {
            Toast.makeText(this, box8text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }





        box9.setOnClickListener {
            box9text = sharedPreferences.getString("box9text", "nothing")

            boxNumber = 9
            Toast.makeText(this, box9text, Toast.LENGTH_SHORT).show()

        }

        box9.setOnLongClickListener {
            Toast.makeText(this, box9text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }





        box10.setOnClickListener {
            box10text = sharedPreferences.getString("box10text", "nothing")

            boxNumber = 10
            Toast.makeText(this, box10text, Toast.LENGTH_SHORT).show()

        }

        box10.setOnLongClickListener {
            Toast.makeText(this, box10text + " do it", Toast.LENGTH_SHORT).show()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
            return@setOnLongClickListener true
        }

        */








        var returnToHome = findViewById<TextView>(R.id.returnToHome)

        returnToHome.setOnClickListener {
            var returnToHome1 = sharedPreferences.getString("returnToHome1", "on")

            if(returnToHome1 == "on"){
                sharedPreferences.edit().putString("returnToHome1", "off").commit()
                Toast.makeText(this, "return to home OFF", Toast.LENGTH_SHORT).show()
            }
            if(returnToHome1 == "off"){
                sharedPreferences.edit().putString("returnToHome1", "on").commit()


                Toast.makeText(this, "return to home ON", Toast.LENGTH_SHORT).show()

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







        var changeBackground = findViewById<TextView>(R.id.changeBackground)

        var toggled = sharedPreferences.getInt("toggled", 0)
        changeBackground.setOnClickListener {
            toggled++
            if(toggled == 9){
                toggled = 1
            }
            if(toggled == 1) {
                dateTime.setBackgroundResource(R.drawable.spiderbg)

            }
            if(toggled == 2) {
                dateTime.setBackgroundResource(R.drawable.newbg)
            }
            if(toggled == 3) {
                dateTime.setBackgroundResource(R.drawable.good)
            }
            if(toggled == 4) {
                dateTime.setBackgroundResource(R.drawable.lime)
            }
            if(toggled == 5) {
                dateTime.setBackgroundResource(R.drawable.updates)
            }
            if(toggled == 6) {
                dateTime.setBackgroundResource(R.drawable.bleh)
            }
            if(toggled == 7){
                dateTime.setBackgroundResource(R.drawable.nonissue)
            }
            if(toggled == 8){
                dateTime.setBackgroundResource(R.drawable.bleh1)
            }


            sharedPreferences.edit().putInt("toggled", toggled).commit()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }







        var points1 = findViewById<TextView>(R.id.points1)

        var togglez = false
        var pointz = findViewById<RelativeLayout>(R.id.pointz)
        points1.setOnClickListener() {

            clean()
            points1.visibility = View.VISIBLE
            if(togglez) {

                pointz.visibility = View.INVISIBLE

            }
            else {
                pointz.visibility = View.VISIBLE

                points1.visibility = View.VISIBLE

            }


            togglez = false

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }

        }







        var firstRow = findViewById<LinearLayout>(R.id.firstRow)
        var secondRow = findViewById<LinearLayout>(R.id.secondRow)
        var quickPanel = findViewById<TextView>(R.id.quickPanel)

        quickPanel.setOnClickListener {
            var toggle1 = sharedPreferences.getBoolean("toggle1", false)

            if( toggle1) {
                //QTS.visibility = View.VISIBLE
               // firstRow.visibility = View.VISIBLE
               // secondRow.visibility = View.VISIBLE
                toggle1 = sharedPreferences.edit().putBoolean("toggle1", false).commit()
                Toast.makeText(this, "quick panel", Toast.LENGTH_SHORT).show()



            }

            else{
                firstRow.visibility = View.INVISIBLE
                secondRow.visibility = View.INVISIBLE

                toggle1 = sharedPreferences.edit().putBoolean("toggle1", true).commit()
                Toast.makeText(this, "quick panel", Toast.LENGTH_SHORT).show()
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





       /* var newButton1 = findViewById<TextView>(R.id.newButton1)
        var toggle3 = true
        newButton1.setOnClickListener {

            if(toggle3){
                //hide
                toggle3 = false
            }
            else {
                //unhide
                toggle3 = true
            }

        }*/





        var toggleGuide = findViewById<TextView>(R.id.toggleGuide)

        toggleGuide.setOnClickListener{
            if(sharedPreferences.getBoolean("toggleGuide", true)){
                sharedPreferences.edit().putBoolean("toggleGuide", false).commit()
                Toast.makeText(this, "Toggled guide", Toast.LENGTH_SHORT).show()

            }
            else {
                sharedPreferences.edit().putBoolean("toggleGuide", true).commit()
                Toast.makeText(this, "Toggled guide off", Toast.LENGTH_SHORT).show()

            }
        }







        var buttonMachineList = findViewById<ListView>(R.id.buttonMachineList)
        var inputEnterm = findViewById<TextView>(R.id.inputEnterm)
        var inputm = findViewById<EditText>(R.id.inputm)
        var bmlsize = sharedPreferences.getInt("count3", 0)

        monaLista3(0)
        inputEnterm.setOnClickListener{
            //instead of being size bml it should be string bml
            sharedPreferences.edit().putString(bmlsize.toString() + "bml", inputm.text.toString()).commit()
            bmlsize++
            sharedPreferences.edit().putInt("count3", bmlsize).commit()

            monaLista3(0)
            //Toast.makeText(this, " input " + sharedPreferences.getString(bmlsize.toString() + "bml", " nothing") + " " + bml.toString() + buttonMachineList.toString(), Toast.LENGTH_SHORT).show()


            val intent = Intent(this, Main::class.java)
            startActivity(intent)

        }
        buttonMachineList.setOnItemClickListener { parent, view, position, id ->

            Toast.makeText(this, bml.get(position).toString(), Toast.LENGTH_SHORT).show()

            startPlaying(sharedPreferences.getString(bml.get(position).toString(), "1sound"))


        }

        buttonMachineList.setOnItemLongClickListener { parent, view, position, id ->
            Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
            bmlsize = sharedPreferences.getInt("count3", 0)
            //should be removed as string not position then it should be saved as string not position
            sharedPreferences.edit().remove(position.toString() + "bml").commit()
           // bml.removeAt(position)

                //update existing positions in countbml sharedpref
            //bmlsize = bmlsize -1
            //sharedPreferences.edit().putInt("count3", bmlsize).commit()


          /*  for(j in 0.. bml.size-1){
                sharedPreferences.edit().putString(j.toString() + "bml", inputm.text.toString()).commit()
        }*/

            val intent = Intent(this, Main::class.java)
            startActivity(intent)
            return@setOnItemLongClickListener true

            //todo finish this
        }


        var changem = findViewById<TextView>(R.id.changem)

        changem.setOnClickListener {
            var testletters3m = inputm.text

            testletters3m.toString().lowercase()

            var abslistm = arrayOf(" ", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")
            var appendm = mutableListOf<Int>()
            for(i in 0..testletters3m.length-1){
                for(j in 0 .. abslistm.size-1)
                    if (testletters3m.get(i).toString() == abslistm.get(j)){
                        Log.d("lily", "not here" + j)
                        appendm.add(j)
                    }
            }
            Log.d("lily", "here: "  + appendm.toString())
            inputm.setText(appendm.toString())

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                @Suppress("DEPRECATION")
                v.vibrate(200)
            }
        }




        var intercommand3m = true


        var intercommand2m = findViewById<ImageView>(R.id.intercommandm)
        intercommand2m.setOnClickListener {


            if(intercommand3m) {
                //Log.d("lily","intercommand")
                // Toast.makeText(this, "intercommand2y", Toast.LENGTH_SHORT).show()
                var time3m = LocalDateTime.now().hour
                var time4m = LocalDateTime.now().minute

                var timeNdate2m =  LocalDate.now().dayOfMonth.toString() + time3m + time4m.toString()



                sharedPreferences.edit().putString(inputm.text.toString(), timeNdate2m).commit()
                checkAndRequestAudioPermission()
                intercommand3m = false
                Toast.makeText(this, "intercommand", Toast.LENGTH_SHORT).show()
            }
            else {
                //Log.d("lily","press enter")
                Toast.makeText(this, "press enter", Toast.LENGTH_SHORT).show()

                intercommand3m = true

                val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    MediaRecorder(this)
                } else {
                    MediaRecorder()
                }
                stopRecording(recorder)



            }



        }





















        //onCreate
    }


    fun showDialog(context: Context, theMessage: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(theMessage)
        builder.setPositiveButton("ok") { dialog, which ->
            dialog.dismiss()

        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
        // Center the message text
        val messageView = alertDialog.findViewById<TextView>(android.R.id.message)
        messageView?.gravity = Gravity.CENTER


    }



    private fun returnBackground() {
        var toggled = sharedPreferences.getInt("toggled", 0)

        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)
        if(toggled == 1) {
            dateTime.setBackgroundResource(R.drawable.spiderbg)

        }
        if(toggled == 2) {
            dateTime.setBackgroundResource(R.drawable.newbg)
        }
        if(toggled == 3) {
            dateTime.setBackgroundResource(R.drawable.good)
        }
        if(toggled == 4) {
            dateTime.setBackgroundResource(R.drawable.lime)
        }
        if(toggled == 5) {
            dateTime.setBackgroundResource(R.drawable.updates)
        }
        if(toggled == 6) {
            dateTime.setBackgroundResource(R.drawable.bleh)
        }
        if(toggled == 7){
            dateTime.setBackgroundResource(R.drawable.nonissue)
        }
        if(toggled == 8){
            dateTime.setBackgroundResource(R.drawable.bleh1)
        }
    }


    fun checkAndRequestAudioPermission() {
        var time3 = LocalDateTime.now().hour
        var time4 = LocalDateTime.now().minute

        var timeNdate =  LocalDate.now().dayOfMonth.toString() + time3 + time4.toString()



        val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        } else {
            MediaRecorder()
        }

        var externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val sound = File(externalDir, "$timeNdate.mp3")
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) ==
                    PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, start recording
                try {
                    recorder.apply {
                        setAudioSource(MediaRecorder.AudioSource.MIC) // Use the device microphone
                        setOutputFormat(MediaRecorder.OutputFormat.DEFAULT) // Standard 3GP format
                        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // Standard audio encoder
                        setOutputFile(sound.absolutePath) // Specify the storage path
                        prepare()
                        start()
                    }
                /*    recorder.prepare()
                    recorder.start()*/
                    Log.d("lily","start rec")

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) -> {
                // Optional: Explain why the permission is needed before requesting again
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
            else -> {
                // Directly request the permission
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }




    fun stopRecording(mediaRecorder: MediaRecorder?): MediaRecorder? {
        var recorder = mediaRecorder
        try {
            recorder?.apply {
                stop()       // Stops the recording process
                reset()      // Puts the object back to the idle state so it can be reused
                release()    // Frees up hardware resources (mic/camera)
            }
        } catch (e: RuntimeException) {
            // Handle cases where stop() is called immediately after start()
            // without any media data actually being received.
            e.printStackTrace()
        } finally {
            recorder = null  // Nullify to prevent memory leaks or reuse errors
        }
        return recorder
    }




    private fun startPlaying(sound1: String?) {

        val externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val sound = File(externalDir, sound1 + ".mp3")

        Log.d("lily","started")

        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(sound.getAbsolutePath())
                prepare()
                start()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun stopPlaying() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun toasted() {
        Toast.makeText(this, "Quiet Mode", Toast.LENGTH_SHORT).show()

        mediaPlayer = MediaPlayer.create(this, R.raw.quietmode)


        mediaPlayer?.setVolume(1f,1f)
        mediaPlayer?.start()
    }


    private fun checkHourGoal(tostartover: Boolean) {


        //it is checking when the app was last used not when the last ticket was created
        var pastDifference = sharedPreferences.getInt("pastDifference", LocalDateTime.now().minute)

        if(LocalDateTime.now().minute - pastDifference > tgoal){
            //the difference is greater than tgoal then youdidit)
            Log.d("lily", "We Did It")
            Toast.makeText(this,"We did it!", Toast.LENGTH_SHORT).show()

            mediaPlayer = MediaPlayer.create(this, R.raw.bell)
            mediaPlayer?.setVolume(1f,1f)
            mediaPlayer?.start()


            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v.vibrate(2000)
            }
        }
        if(tostartover == true){
        sharedPreferences.edit().putInt("pastDifference", LocalDateTime.now().minute).commit()
        }
        else{

        }
        resize()
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
            sharedPreferences.edit().putBoolean("alert1", true).commit()
            sharedPreferences.edit().putBoolean("private1", true).commit()





            sharedPreferences.edit().putInt("modeCount", 0).commit()


            //Toast.makeText(this@Main, "time", Toast.LENGTH_SHORT).show()
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

        sharedPreferences.edit().putString(listnum.toString() + theItem, "").commit()
        // bslist.add("")


    }


    fun monaLista2(j: Int){
        var backupReminderView = findViewById<ListView>(R.id.backupReminder)
        var i = j
        var totalItems = sharedPreferences.getInt("count2", listnum)
        if (totalItems == 0 || i >= totalItems){
            return
        }
        backupReminders1.add(sharedPreferences.getString(i.toString() + "backup", "").toString())
        i++
        backupReminderView.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,backupReminders1)
        monaLista2(i)
    }


    fun monaLista3(j: Int){
        var buttonMachineList = findViewById<ListView>(R.id.buttonMachineList)
        var i = j
        var totalItems = sharedPreferences.getInt("count3", listnum)
        if (totalItems == 0 || i >= totalItems){
            return
        }
        if(sharedPreferences.getString(i.toString() + "bml", "").toString() == ""){

        }
        else {
            bml.add(sharedPreferences.getString(i.toString() + "bml", "").toString())

        }
        i++
        buttonMachineList.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,bml)
        monaLista3(i)
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

       //todo test backuplog

        var theItem1 =""

            theItem1 = text + "\n" + date1 + "\n" + time2.text.toString()

        sharedPreferences.edit().putString(backupReminderNum.toString() + "backup",  theItem1).commit()
        backupReminders1.add(theItem1)

        backupReminderNum++
        backupReminderView.adapter = ArrayAdapter<String>(this,R.layout.custom_list1, R.id.custom_text,backupReminders1)
        sharedPreferences.edit().putInt("count2", backupReminderNum).commit()



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
        var ticketPg = findViewById<RelativeLayout>(R.id.ticketPage)

        var home1 = findViewById<RelativeLayout>(R.id.home1)

        var search1 = findViewById<RelativeLayout>(R.id.searchLayout)
        var bye = findViewById<ImageView>(R.id.bye)
        var boxes1 = findViewById<RelativeLayout>(R.id.boxes1)
        var settings1 = findViewById<RelativeLayout>(R.id.settings1)
        var reviews = findViewById<RelativeLayout>(R.id.reviews)

        var boxes2 = findViewById<RelativeLayout>(R.id.boxes2)
        var pointz = findViewById<RelativeLayout>(R.id.pointz)

        var newButtons = findViewById<RelativeLayout>(R.id.boxes3)

        var convoTime = findViewById<ImageView>(R.id.convotime)
        var convoTimer = findViewById<TextView>(R.id.convoTimer)

        var buttonMachine = findViewById<RelativeLayout>(R.id.buttonMachine)


        home1.visibility = View.INVISIBLE
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
        ticketPg.visibility = View.INVISIBLE
        search1.visibility = View.INVISIBLE
        bye.visibility = View.INVISIBLE
        boxes1.visibility = View.INVISIBLE
        settings1.visibility = View.INVISIBLE
        reviews.visibility = View.INVISIBLE

        boxes2.visibility = View.INVISIBLE
        pointz.visibility = View.INVISIBLE
        newButtons.visibility = View.INVISIBLE
        buttonMachine.visibility = View.INVISIBLE






        if(removeFromClean){
            convoTimer.visibility = View.INVISIBLE
            convoTime.visibility = View.INVISIBLE
        }
        var guide = findViewById<TextView>(R.id.guide)
        if(sharedPreferences.getBoolean("toggleGuide", false)){
            guide.visibility = View.INVISIBLE
        }
        else{
            guide.visibility = View.VISIBLE
        }

    }


/*    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer resources when the Activity is destroyed
        mediaPlayer?.release()
        mediaPlayer = null
    }*/

    @Override
    override fun onResume() {


        var dontdouble1 = sharedPreferences.getInt("dontdouble1", 0)
        dontdouble1++
        sharedPreferences.edit().putInt("dontdouble1", dontdouble1).commit()
//if onresume was just turned on set dontdouble to true


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


        var QT1C = sharedPreferences.getInt("QT1C", 0)
        var QT2C = sharedPreferences.getInt("QT2C", 0)
        var QT3C = sharedPreferences.getInt("QT3C", 0)
        var QT4C = sharedPreferences.getInt("QT4C", 0)
        var QT5C = sharedPreferences.getInt("QT5C", 0)
        var QT6C = sharedPreferences.getInt("QT6C", 0)
        var QT7C = sharedPreferences.getInt("QT7C", 0)
        var QT8C = sharedPreferences.getInt("QT8C", 0)
        var QT9C = sharedPreferences.getInt("QT9C", 0)
        var QT10C = sharedPreferences.getInt("QT10C", 0)
        var QT11C = sharedPreferences.getInt("QT11C", 0)
        var QT12C = sharedPreferences.getInt("QT12", 0)
        var QT13C = sharedPreferences.getInt("QT13", 0)
        var QT14C = sharedPreferences.getInt("QT14", 0)
        var QT15C = sharedPreferences.getInt("QT15", 0)
        var QT16C = sharedPreferences.getInt("QT16", 0)
        var QT17C = sharedPreferences.getInt("QT17", 0)
        var QT18C = sharedPreferences.getInt("QT18", 0)


        feedback1.setText("")
        starRating.rating = 3F

        //increments when day is different to yesterday
        if(day.toInt() != lastSaveDate){
            counter++
            sharedPreferences.edit().putInt("counter", counter).commit()



            logIt("Previous Day Total Count: " + TC + "\n" + sharedPreferences.getString("QTB1","") + " Count: " + QT1C + "\n" +
                    sharedPreferences.getString("QTB2","") + " Count: " + QT2C + "\n" +
                    sharedPreferences.getString("QTB3","") + " Count: " + QT3C + "\n" +
                    sharedPreferences.getString("QTB4","") + " Count: " + QT4C + "\n" +
                    sharedPreferences.getString("QTB5","") + " Count: " + QT5C + "\n" +
                    sharedPreferences.getString("QTB6","") + " Count: " + QT6C + "\n" +
                    sharedPreferences.getString("QTB7","") + " Count: " + QT7C + "\n" +
                    sharedPreferences.getString("QTB8","") + " Count: " + QT8C + "\n" +
                    sharedPreferences.getString("QTB9","") + " Count: " + QT9C + "\n" +
                    sharedPreferences.getString("QTB10","") + " Count: " + QT10C + "\n" +
                    sharedPreferences.getString("QTB11","") + " Count: " + QT11C + "\n" +
                    sharedPreferences.getString("QTB12","") + " Count: " + QT12C + "\n" +
                    sharedPreferences.getString("QTB13","") + " Count: " + QT13C + "\n" +
                    sharedPreferences.getString("QTB14","") + " Count: " + QT14C + "\n" +
                    sharedPreferences.getString("QTB15","") + " Count: " + QT15C + "\n" +
                    sharedPreferences.getString("QTB16","") + " Count: " + QT16C + "\n" +
                    sharedPreferences.getString("QTB17","") + " Count: " + QT17C + "\n" +
                    sharedPreferences.getString("QTB18","") + " Count: " + QT18C
                , 0)



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
            QT13.text = ""
            QT14.text = ""
            QT15.text = ""
            QT16.text = ""
            QT17.text = ""
            QT18.text = ""


            sharedPreferences.edit().putString("QTB13","").commit()
            sharedPreferences.edit().putString("QTB14","").commit()
            sharedPreferences.edit().putString("QTB15","").commit()
            sharedPreferences.edit().putString("QTB16","").commit()
            sharedPreferences.edit().putString("QTB17","").commit()
            sharedPreferences.edit().putString("QTB18","").commit()

            saveSharedPreferencestoExternal(this, "daysSince", "bsl" + month + day + year)
            Toast.makeText(this, "New Day", Toast.LENGTH_SHORT).show()









        }
        sharedPreferences.edit().putInt("lsd", day.toInt()).commit()




        turnOffMode(0)


        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)
        var alert1 = sharedPreferences.getBoolean("alert1", true)

        if(sharedPreferences.getBoolean("safety", true)){
            var toggled = sharedPreferences.getInt("toggled", 0)
            if(toggled == 1) {
                dateTime.setBackgroundResource(R.drawable.spiderbg)

            }
            if(toggled == 2) {
                dateTime.setBackgroundResource(R.drawable.newbg)
            }
            if(toggled == 3) {
                dateTime.setBackgroundResource(R.drawable.good)
            }
            if(toggled == 4) {
                dateTime.setBackgroundResource(R.drawable.lime)
            }
            if(toggled == 5) {
                dateTime.setBackgroundResource(R.drawable.updates)
            }
            if(toggled == 6) {
                dateTime.setBackgroundResource(R.drawable.bleh)
            }
            if(toggled == 7){
                dateTime.setBackgroundResource(R.drawable.nonissue)
            }
            if(toggled == 8){
                dateTime.setBackgroundResource(R.drawable.bleh1)
            }
            if(!alert1){

                dateTime.setBackgroundResource(R.drawable.alert1)

            }

        }
        else{
            dateTime.setBackgroundResource(R.drawable.blah)
        }

        var status1 = sharedPreferences.getBoolean("status1", false)

        var statusUpdate = findViewById<EditText>(R.id.statusUpdate)

        var homeStatus = findViewById<TextView>(R.id.homeStatus)

        if(status1) {
            Toast.makeText(this, sharedPreferences.getString("statusUpdate", "Have a good day"), Toast.LENGTH_SHORT).show()
            statusUpdate.setText(sharedPreferences.getString("statusUpdate", ""))
            homeStatus.setText(sharedPreferences.getString("statusUpdate", ""))

        }

        switch2(false)

        var delay1 = sharedPreferences.getInt("delay3", 2500)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)

        //back to home screen

        Log.d("lily2", "home screen")
        clean()

        dateTime.visibility = View.VISIBLE
        toggle = sharedPreferences.getInt("toggle", 6)
        froggle(toggle)

        lifecycleScope.launch {
            var time1 = findViewById<TextClock>(R.id.time)
            if(sharedPreferences.getBoolean("multiclick", true) == true){
                falseA = true
                time1.performClick()


                sharedPreferences.edit().putBoolean("multiclick", false).commit()
            }
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
            var froggie = sharedPreferences.getBoolean("froggie", true)
            var funnyvar5 = sharedPreferences.getBoolean("funnyvar5", true)
            var seriousvar1 = sharedPreferences.getBoolean("seriousvar1", true)

            var reeses1 = sharedPreferences.getBoolean("reeses1" , true)
            var tea1 = sharedPreferences.getBoolean("tea1" , true)
            var clean1 = sharedPreferences.getBoolean("clean1" , true)
            var busy1 = sharedPreferences.getBoolean("busy1" , true)
            var groceries1 = sharedPreferences.getBoolean("groceries1" , true)
            var alert1 = sharedPreferences.getBoolean("alert1", true)
            var privShared = sharedPreferences.getBoolean("private1", true)

            var modeCount = sharedPreferences.getInt("modeCount", 0)
            var modeVar = sharedPreferences.getString("modeVar", "Current Mode count: ")


            if(falseA == false) {


                checkHourGoal(true)
                dontdouble1 = sharedPreferences.getInt("dontdouble1", 2)
                if (dontdouble1>=2) {
                    //Toast.makeText(this@Main, "dontdouble count: \n" + dontdouble1, Toast.LENGTH_SHORT).show()
                    //do nothing
                }
                else if (dontdouble1 <= 1) {
                    sharedPreferences.edit().putInt("dontdouble1", 2)
                    if (!funnyvar || !funnyvar2 || !funnyvar3 || !funnyvar4 || !threeggle || !funnyvar5 || !seriousvar1 || !reeses1 || !tea1 || !clean1 || !busy1 || !groceries1) {

                        TC = sharedPreferences.getInt("TC", 0)
                        TC++
                        sharedPreferences.edit().putInt("TC", TC).commit()
                        modeCount++
                        sharedPreferences.edit().putInt("modeCount", modeCount).commit()

                        Toast.makeText(this@Main, "TC: " + TC.toString() + "\nMC: " + modeCount, Toast.LENGTH_SHORT).show()
                        logIt("#" + TC + "\n" + modeVar + modeCount + "\n", 1) //includes date, time, level
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
                        logIt("#" + TC + "\n" + "Tick: " + QTCount + "\n" + current1 + "\n", 1)
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

                lifecycleScope.launch {
                    delay(3000)
                    //Toast.makeText(this@Main, "set to zero", Toast.LENGTH_SHORT).show()
                    sharedPreferences.edit().putInt("dontdouble1", 0).commit()
                }


            }
            else if(falseA == true){
                checkHourGoal(false)
            }

        }


        falseA = false



        resize()

        super.onResume()
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_USER_PRESENT)
        }
        registerReceiver(screenStateReceiver, filter)
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
            //Toast.makeText(this,  "did nothing", Toast.LENGTH_SHORT).show()


        }
        else if (onOff == false){
            falseA = false
        }
        return falseA
    }






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




    //todo investigate background from mode going away after resume


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
        var privShared = sharedPreferences.getBoolean("private1", true)
        var alert1 = sharedPreferences.getBoolean("alert1", true)

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
        var privView = findViewById<ImageView>(R.id.privacy)
        var ticket = findViewById<ImageView>(R.id.ticket1)

        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)
        var alert2 = findViewById<ImageView>(R.id.alert1)


        if(!alert1){
            alert2.animate().scaleX(2.3F)
            alert2.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.alert1)
            dateTime.setBackgroundResource(R.drawable.alert1)

        }
        else
        {
            alert2.animate().scaleX(1F)
            alert2.animate().scaleY(1F)
            returnBackground()
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("alert1", true).commit()

        }



        if(!privShared){
            privView.animate().scaleX(2.3F)
            privView.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.private1)
            dateTime.setBackgroundResource(R.drawable.private1)


        }
        else{
            privView.animate().scaleX(1F)
            privView.animate().scaleY(1F)
            returnBackground()
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("private1", true).commit()
        }


        if(!tea1){
            tea.animate().scaleX(2.3F)
            tea.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.tea)

        }
        else{
            tea.animate().scaleX(1F)
            tea.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("tea1", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }
        if(!clean1){
            clean.animate().scaleX(2.3F)
            clean.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.cleaning)

        }
        else{
            clean.animate().scaleX(1F)
            clean.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("clean1", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()

        }

        if(!busy1){
            busy.animate().scaleX(2.3F)
            busy.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.busy)

        }
        else{
            busy.animate().scaleX(1F)
            busy.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("busy1", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }

        if(!groceries1){
            groceries.animate().scaleX(2.3F)
            groceries.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.groceries)

        }
        else{
            groceries.animate().scaleX(1F)
            groceries.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("groceries1", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }


        if(!reeses1){
            reeses.animate().scaleX(2.3F)
            reeses.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.peanutbutter)

        }
        else{
            reeses.animate().scaleX(1F)
            reeses.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("reeses1", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }
        if(!funnyvar2){
            starMode.animate().scaleX(2.3F)
            starMode.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.starmode)

        }
        else{
            starMode.animate().scaleX(1F)
            starMode.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("funnyvar2", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }
        if(!funnyvar4){
            studyMode.animate().scaleX(2.3F)
            studyMode.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.studymode)

        }
        else{
            studyMode.animate().scaleX(1F)
            studyMode.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("funnyvar4", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }

        if(!threeggle){
            apple.animate().scaleX(2.3F)
            apple.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.apple)

        }
        else{
            apple.animate().scaleX(1F)
            apple.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("threeggle", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }
        if(!twoggle){
            rest.animate().scaleX(2.3F)
            rest.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.rest)

        }
        else{
            rest.animate().scaleX(1F)
            rest.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("twoggle", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }

        if(!funnyvar){
            goSkate.animate().scaleX(2.3F)
            goSkate.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.skateboard)

        }
        else{
            goSkate.animate().scaleX(1F)
            goSkate.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("funnyvar", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }


        if(!funnyvar3){
            nebulizer.animate().scaleX(2.3F)
            nebulizer.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.nebulizer)

        }
        else{
            nebulizer.animate().scaleX(1F)
            nebulizer.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("funnyvar3", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }


        if(!funnyvar5){
            auto.animate().scaleX(2.3F)
            auto.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.automobile)

        }
        else{
            auto.animate().scaleX(1F)
            auto.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("funnyvar5", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }
        if(!seriousvar1){
            sleep.animate().scaleX(2.3F)
            sleep.animate().scaleY(2.3F)
            ticket.setImageResource(R.drawable.zulater)

        }
        else{
            sleep.animate().scaleX(1F)
            sleep.animate().scaleY(1F)
            ticket.setImageResource(R.drawable.scribble)
            sharedPreferences.edit().putBoolean("seriousvar1", true).commit()
            sharedPreferences.edit().putInt("modeCount", 0).commit()
        }
    }









    private fun froggle(toggle: Int) {
        val fone = findViewById<ImageView>(R.id.hangupphone1)
        val ticket = findViewById<ImageView>(R.id.ticket1)
        var modeScreen = findViewById<RelativeLayout>(R.id.modeScreen)
        var goSkate = findViewById<ImageView>(R.id.goSkate)
        var QTS = findViewById<RelativeLayout>(R.id.QTS)
        var theBSLog = findViewById<RelativeLayout>(R.id.theBSLog)
        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)

        var guitar = findViewById<ImageView>(R.id.connect2)
        var walkie = findViewById<ImageView>(R.id.connect)
        var theButtons = findViewById<RelativeLayout>(R.id.theButtons)

        var thirdRow = findViewById<LinearLayout>(R.id.thirdRow)
        var fourthRow = findViewById<LinearLayout>(R.id.fourthRow)
        var fifthRow = findViewById<LinearLayout>(R.id.fifthRow)
        var sixthRow = findViewById<LinearLayout>(R.id.sixthRow)
        var inputQT = findViewById<RelativeLayout>(R.id.inputQT)
        var home1 = findViewById<RelativeLayout>(R.id.home1)

        var lost = findViewById<ImageView>(R.id.lost)

        var search1 = findViewById<RelativeLayout>(R.id.searchLayout)

        var bye = findViewById<ImageView>(R.id.bye)

        var boxes = findViewById<RelativeLayout>(R.id.boxes1)

        var settings1 = findViewById<RelativeLayout>(R.id.settings1)
        var reviews = findViewById<RelativeLayout>(R.id.reviews)

        var pointz = findViewById<RelativeLayout>(R.id.pointz)
        var boxes2 = findViewById<RelativeLayout>(R.id.boxes2)
        var newButtons = findViewById<RelativeLayout>(R.id.boxes3)

        var convotime = findViewById<ImageView>(R.id.convotime)
        var convotimer = findViewById<TextView>(R.id.convoTimer)

        var firstRow = findViewById<LinearLayout>(R.id.firstRow)
        var secondRow = findViewById<LinearLayout>(R.id.secondRow)

        var guide = findViewById<TextView>(R.id.guide)

        if (toggle == 2) {
            fone.animate().rotation(0F)
            // Log.d("lily", "something else")
            clean()
            //dateTime.visibility = View.INVISIBLE
            var newlayout = findViewById<RelativeLayout>(R.id.somethingElse1)
            newlayout.visibility = View.VISIBLE
            newlayout.bringToFront()
            ticket.visibility = View.VISIBLE
            homePage = false


            guide.setOnClickListener{

                showDialog(this, "Click on the phone to hang up")
            }

        } else if (toggle == 3) {
            //mode screen
            clean()

            modeScreen.visibility = View.VISIBLE
            modeScreen.bringToFront()
            goSkate.visibility = View.VISIBLE
            goSkate.bringToFront()
            //fone.visibility = View.VISIBLE
            //fone.bringToFront()
            ticket.visibility = View.VISIBLE
            // guitar.bringToFront()
            // walkie.bringToFront()
            homePage = false
            guide.setOnClickListener{

                showDialog(this, "Click on a mode to quickly create tickets and alert others of your activities")
            }

        }
        else if (toggle == 4){
            clean()
            QTS.visibility = View.VISIBLE
            QTS.bringToFront()
            ticket.visibility = View.VISIBLE
            thirdRow.visibility = View.VISIBLE

            fourthRow.visibility = View.VISIBLE

            fifthRow.visibility = View.VISIBLE

            sixthRow.visibility = View.VISIBLE
            inputQT.visibility = View.VISIBLE
            homePage = false
            guide.setOnClickListener{

                showDialog(this, "Long press the button to set up its title and sound, and press the ✓ to submit. Then simply press the button to create a ticket that is logged")
            }
        }
        else if (toggle == 5){
            clean()



            theBSLog.visibility = View.VISIBLE
            theBSLog.bringToFront()
            Log.d("lily", bslist.toString() + " HERE's THE LIST")
            // orderList()
            dateTime.visibility = View.VISIBLE
            ticket.visibility = View.VISIBLE
            search1.visibility = View.VISIBLE
            homePage = false
            guide.setOnClickListener{

                showDialog(this, "Long press an entry to edit its contents")
            }

        }
        else if (toggle == 6){
            clean()

            newButtons.visibility = View.VISIBLE

/*
            theButtons.visibility = View.VISIBLE
            theButtons.bringToFront()
            bye.visibility = View.VISIBLE
            lost.bringToFront()
            Log.d("lily", "the Buttons")*/

            homePage = false
            guide.setOnClickListener{

                showDialog(this, "Long press the button to set up its title and sound, and press the ✓ to submit. Then simply press the button")
            }

        }

        else if (toggle == 7){

            //back to home screen
            //  Log.d("lily2", "home screen")
            clean()
            dateTime.visibility = View.VISIBLE
            ticket.visibility = View.VISIBLE
            home1.visibility = View.VISIBLE

            bye.visibility= View.VISIBLE

            if(sharedPreferences.getBoolean("toggle1", false) == true) {
                QTS.visibility = View.VISIBLE
                firstRow.visibility = View.VISIBLE
                secondRow.visibility = View.VISIBLE
            }
            else if (sharedPreferences.getBoolean("toggle1", false) == true){
                QTS.visibility = View.INVISIBLE
                firstRow.visibility = View.INVISIBLE
                secondRow.visibility = View.INVISIBLE
            }
            thirdRow.visibility = View.INVISIBLE
            fourthRow.visibility = View.INVISIBLE
            fifthRow.visibility = View.INVISIBLE
            sixthRow.visibility = View.INVISIBLE
            inputQT.visibility = View.INVISIBLE
            pointz.visibility = View.INVISIBLE

            if(!removeFromClean){
                convotimer.visibility = View.VISIBLE
                convotime.visibility = View.VISIBLE
            }
            homePage = true
            guide.setOnClickListener{

                showDialog(this, "The status can be read from here. Press bye to disconnect")
            }
        }

        else if (toggle == 8){
            clean()

            boxes.visibility = View.VISIBLE
            boxes.bringToFront()
            homePage = false

            guide.setOnClickListener{

                showDialog(this, "Long press the button to set up its title and sound, and press the ✓ to submit. Then simply press the button")
            }
        }

        else if (toggle == 9){
            clean()
            settings1.visibility = View.VISIBLE
            settings1.bringToFront()
            homePage = false
            guide.setOnClickListener{

                showDialog(this, "Quickly access and toggle the settings")
            }
        }
        else if (toggle == 10){
            clean()
            reviews.visibility = View.VISIBLE
            reviews.bringToFront()
            homePage = false
            guide.setOnClickListener{

                showDialog(this, "This is where backup requests and reviews are created")
            }
        }
        else if (toggle == 11){
            clean()
            boxes2.visibility = View.VISIBLE
            boxes2.bringToFront()
            homePage = false
            guide.setOnClickListener{

                showDialog(this, "Long press the button to set up its title and sound, and press the ✓ to submit. Then simply press the button")
            }
        }
        else if(toggle == 12){
            clean()
            theButtons.visibility = View.VISIBLE
            theButtons.bringToFront()
            convotimer.visibility = View.VISIBLE
            convotime.visibility = View.VISIBLE
            homePage = false
            guide.setOnClickListener{

                showDialog(this, "Press a ping to connect with others that have heard the sound")
            }
        }
        else if (toggle == 13){
            var buttonMachine = findViewById<RelativeLayout>(R.id.buttonMachine)
            clean()
            buttonMachine.visibility = View.VISIBLE
            buttonMachine.bringToFront()
            homePage = false
            guide.setOnClickListener{

                showDialog(this, "Create buttons as you go.")
            }
        }


        sharedPreferences.edit().putInt("toggle", toggle).commit()

        if(sharedPreferences.getString("returnToHome1", "on") == "on"){


        }
        if(sharedPreferences.getBoolean("toggleGuide", false)){
            guide.visibility = View.INVISIBLE
        }

    }




    override fun onPause() {
        super.onPause()
        unregisterReceiver(screenStateReceiver)
    }
    private fun handleScreenOff() {
            froggle(7)
    }
    private fun handleUserPresent(){
        Toast.makeText(this@Main, "it works", Toast.LENGTH_SHORT).show()

    }
    private fun handleScreenUnlocked()
    {

    }





}


