package com.example.bullfrogs.presentation
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.core.view.GestureDetectorCompat
import com.example.bullfrogs.R
import com.example.bullfrogs.databinding.ActivityMainBinding
import com.google.android.gms.common.util.Strings
import java.time.LocalDate
import kotlin.jvm.java


class Main : AppCompatActivity() {
    private lateinit var mDetector: GestureDetector
    private var toggle = 1

    private var giggle1 = true
    private var twoggle = true
    private var threeggle = true

    private var froggie = true
    private var toggle5 = true
    private var toggle6 = true
    private var sevenoggle = true

    var returnFrog = false

    var listnum = 0
    var bslist = mutableListOf<String>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

//todo create log


        //todo work on settings screen
        // todo make sure retapping the ticket icon goes to the home screen


        //todo finish skateboard

        //Todo make face
        //todo broadcast signal

        //todo lower rating sensitivity
        //todo make text field larger
        //

        //todo submit button, save button for potential solutions
        //todo add milisec
        //todo quick ticket listen to button

        // do not display overlay

        //unlimit animations

        super.onCreate(savedInstanceState)

        //set up binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)


        var modeScreen = findViewById<RelativeLayout>(R.id.modeScreen)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)
      //  var somethingElse = findViewById<RelativeLayout>(R.id.somethingElse)
        var dateTime = findViewById<RelativeLayout>(R.id.dateTime)

        var volumeLever = findViewById<SeekBar>(R.id.volumeLever)
        val TheFrog = findViewById<ImageView>(R.id.Bullfrog)
        val phone = findViewById<ImageView>(R.id.phone)
        var reset = false
        var dogDays = findViewById<TextView>(R.id.time1)
        var stars = findViewById<RatingBar>(R.id.stars)
        var statusUpdate = findViewById<EditText>(R.id.statusUpdate)
        var apple = findViewById<ImageView>(R.id.food)
        var rest = findViewById<ImageView>(R.id.rest)
        val ticket = findViewById<ImageView>(R.id.ticket1)
        //val ticket1 = findViewById<ImageView>(R.id.dontTkit)
        var date = findViewById<TextView>(R.id.date)
        var time = findViewById<TextClock>(R.id.time)
        var starMode = findViewById<ImageView>(R.id.starMode)
        var day = LocalDate.now().dayOfMonth.toString()
        var month = LocalDate.now().monthValue.toString()
        var year = LocalDate.now().year.toString()
        var date1 = "$month/$day/$year"
        var pos = findViewById<TextView>(R.id.pos)
       // var crit = findViewById<TextView>(R.id.critical)
       // var noncrit = findViewById<TextView>(R.id.noncrit)
        var caution = findViewById<ImageView>(R.id.caution)
        var settings = findViewById<ImageView>(R.id.gear)
       // var skateboard = findViewById<ImageView>(R.id.skateboard)
        var showSkateboard = 1
        var goSkate = findViewById<ImageView>(R.id.goSkate)

        var progress = findViewById<SeekBar>(R.id.progress)
       // var logotoggle = findViewById<RelativeLayout>(R.id.logotoggle)
        //var layout2 = findViewById<RelativeLayout>(R.id.layout2)
        var hue = findViewById<ImageView>(R.id.hue)
        var colorMenu = findViewById<RelativeLayout>(R.id.colorMenu)



        var good = findViewById<ImageView>(R.id.good)
        var yellowcard = findViewById<ImageView>(R.id.yellowcard)
        var redcard = findViewById<ImageView>(R.id.redcard)
        var color1 = 1

        var theBSLog = findViewById<RelativeLayout>(R.id.theBSLog)

        //var potsolution = potentialSolutions.text

        var potentialSolutions = findViewById<EditText>(R.id.potentialSolutions)
        var bsl = findViewById<ListView>(R.id.bsl)
       // var bslist = arrayOf("1","2","3")

        var currentItemColor = findViewById<ImageView>(R.id.currentItemColor)




        var QTBtn = findViewById<ImageView>(R.id.QT)


        var boolean1 = true







        date.text = date1





        Log.d("lily2" , progress.toString())


        sharedPreferences = getSharedPreferences("daysSince", MODE_PRIVATE)





       hue.setOnClickListener {
           //Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
           colorMenu.visibility = View.VISIBLE
           colorMenu.bringToFront()
       }


        good.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.good)
            colorMenu.visibility = View.INVISIBLE
            color1 = 1
            logIt(potentialSolutions.text.toString(), color1)


        }
        yellowcard.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.yellowcard)
            colorMenu.visibility = View.INVISIBLE
            color1 = 2
            logIt(potentialSolutions.text.toString(), color1)


        }
        redcard.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
            hue.setImageResource(R.drawable.redcard)
            colorMenu.visibility = View.INVISIBLE
            color1 = 3
            logIt(potentialSolutions.text.toString(), color1)

            Log.d("lily", potentialSolutions.text.toString() + color1)

        }

        //logIt("This is a test", 3)

        // logotoggle.visibility = View.VISIBLE

       //todo controls should be visible
       // ticket.visibility = View.VISIBLE
       // ticket1.visibility = View.VISIBLE

        dateTime.visibility = View.VISIBLE

        if (reset) {
            var theMath = 360 - 132 + 180
            phone.animate().rotation(theMath.toFloat())
            reset = false
        }













        //todo
     /*   //
        var QTBtn = findViewById<ImageView>(R.id.QT)

        var QT = sharedPreferences.getInt("QT", 1)

        var QTCount = 1

        if(QT == 1){
            QTCount++
        }
        QTBtn.setOnClickListener {
            var boolean1 = true
            if(boolean1){
                QTBtn.animate().scaleX(2F)
                QT = 1
                var theItem = potentialSolutions.text.toString() + "\n\n" + date1 + "\n" + time.text.toString() + " " + color1.toString()
                sharedPreferences.edit().putString("current", theItem).commit()
                sharedPreferences.edit().putInt("QT", 1).commit()
            }
            else if(!boolean1){
                QTBtn.animate().scaleX(1F)
                QT = 0
                QTCount -1

                sharedPreferences.edit().putString(bsl.size.toString(),sharedPreferences.getString("current", "No Data") + QTCount)
                sharedPreferences.edit().putInt("QT", 0).commit()
            }
        }*/













        TheFrog.setOnClickListener {

            if(returnFrog){
                TheFrog.animate().scaleX(2F)
                TheFrog.animate().scaleY(2F)
                TheFrog.animate().x((ttf.width/2F)-(TheFrog.width/2))
                returnFrog = false

            }

            stars.rating = 0F

            clean()



            toggle++
            if (toggle == 2) {

                //mode screen
                clean()
                modeScreen.visibility = View.VISIBLE
                modeScreen.bringToFront()



        /*
               if(showSkateboard == 2){
                    goSkate.visibility = View.VISIBLE
                    goSkate.bringToFront()
                }*/








            } else if (toggle == 3) {
                Log.d("lily", "something else")
                clean()
                dateTime.visibility = View.INVISIBLE
                var newlayout = findViewById<RelativeLayout>(R.id.somethingElse1)
                newlayout.visibility = View.VISIBLE
                newlayout.bringToFront()
                //var ticket3 = findViewById<ImageView>(R.id.ticket1)
                ticket.visibility = View.VISIBLE
                /*        controls.visibility = View.VISIBLE
                        controls.bringToFront()*/



            }
            else if (toggle == 4){
                TheFrog.animate().scaleX(.5F)
                TheFrog.animate().scaleY(.5F)
                TheFrog.animate().x(10F)
                returnFrog = true

                theBSLog.visibility = View.VISIBLE
                theBSLog.bringToFront()

                dateTime.visibility = View.INVISIBLE

            }
            else if (toggle == 5){

                //back to home screen

                Log.d("lily2", "home screen")
                clean()
                dateTime.visibility = View.VISIBLE
                toggle = 1
            }
        /*    else if (toggle == 6){

                //back to home screen

                Log.d("lily2", "home screen")
                clean()
                dateTime.visibility = View.VISIBLE
               *//* var intent1 = Intent(this, Main::class.java)

                startActivity(intent1)*//*

            }*/

        }


        phone.setOnClickListener {
            phone.animate().rotation((132).toFloat())
            reset = true
        }


        starMode.setOnClickListener {
            if (toggle6) {
                //this means I'm eating
                starMode.animate().scaleX(2.3F)
                starMode.animate().scaleY(2.3F)
                toggle6 = false
            }
            else if (!toggle6){
                starMode.animate().scaleX(1F)
                starMode.animate().scaleY(1F)
                toggle6 = true
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
            if (twoggle) {
                //this means I'm eating
                rest.animate().scaleX(2.3F)
                rest.animate().scaleY(2.3F)
                twoggle = false
            }
            else if (!twoggle){
                rest.animate().scaleX(1F)
                rest.animate().scaleY(1F)
                twoggle = true
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
            if(sevenoggle) {
                goSkate.animate().rotation(360F)
                goSkate.animate().scaleX(2.3F)
                goSkate.animate().scaleY(2.3F)
            }
            else{
                goSkate.animate().rotation(360F)
                goSkate.animate().scaleX(1F)
                goSkate.animate().scaleY(1F)
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



                //giggle1 = false
            } /*else if (!giggle1) {

                //returns to control screen


                clean()

                dateTime.visibility = View.VISIBLE

                //returns frog
                //todo double check back button



                //returns phone to default


                //verify toggle = 1
                giggle1 = true
            } */
            if (reset) {
            var theMath = 360 - 132 + 180
            phone.animate().rotation(theMath.toFloat())
            reset = false
        }
        }
        var QT = sharedPreferences.getInt("QT", 0)

        var QTCount = sharedPreferences.getInt("QTCount",1)
        var current1 = sharedPreferences.getString("current","")
        if(QT == 1){

            QTCount++
            sharedPreferences.edit().putInt("QTCount", QTCount).commit()

          //  var theItem =  potentialSolutions.text.toString() + "\n\n" + date1 + "\n" + time.text.toString() + " " + color1.toString()
            potentialSolutions.setText("Count: " + QTCount.toString() + "\n" + current1 + time.text.toString())
            QTBtn.animate().scaleX(2F)
            QTBtn.animate().scaleY(2F)
            Toast.makeText(this, QTCount.toString(), Toast.LENGTH_SHORT).show()

        }


        QTBtn.setOnClickListener {



            if(QT==0){
                QTBtn.animate().scaleX(2F)
                QTBtn.animate().scaleY(2F)

                QT = 1
                var theItem = potentialSolutions.text.toString() + "\n" + date1 + "\n" + time.text.toString() + " level: " + color1.toString() +  " "
                sharedPreferences.edit().putString("current", theItem).commit()
                sharedPreferences.edit().putInt("QT", 1).commit()
                boolean1 = false
            }
            else if(QT==1){
                QTBtn.animate().scaleX(1F)
                QTBtn.animate().scaleY(1F)

                Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show()
                QT = 0
                QTCount -1
               // listnum = sharedPreferences.getInt("count1", 0)

               // var theItem = "Count: " + QTCount.toString() + "\n" + potentialSolutions.text.toString() + "\n\n" + date1 + "\n" + time.text.toString() + " " + color1.toString()

                //sharedPreferences.getString("current", "No Data") + QTCount
               // sharedPreferences.edit().putString(listnum.toString(), theItem)
                sharedPreferences.edit().putInt("QT", 0).commit()
                sharedPreferences.edit().putInt("QTCount", 1).commit()

                boolean1 = true
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
        }
        sharedPreferences.edit().putInt("lsd", day.toInt()).commit()
        dogDays.text = counter.toString()




        //var i = 1





    /*    for(i in 1 .. 4){
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





       // listnum = sharedPreferences.getInt("count1", 0)


       // var i = 1
        //for(i in 0.. bslist.size+1){

        monaLista(0)




    /*    var i = 0
        if(i < bslist.size) {
            if(bslist.size.equals(0)){
            }
            else{
                bslist.set(i, sharedPreferences.getString(i.toString(), "").toString())
                i++
            }
        }*/




        bsl.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bslist)


        //bsl.getItemAtPosition(1).toString().removeSuffix("1")


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






      //  bsl.get(1).setBackgroundColor(R.drawable.good)







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


        var potentialSolutions = findViewById<EditText>(R.id.potentialSolutions)
        var bsl = findViewById<ListView>(R.id.bsl)









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

        Log.d("lily", bslist.toString() + " HERE's THE LIST")

    }
    fun clean() {
        var modeScreen = findViewById<RelativeLayout>(R.id.modeScreen)
        var ttf = findViewById<RelativeLayout>(R.id.ticketTakeitnFixIt)
        var somethingElse = findViewById<RelativeLayout>(R.id.somethingElse)
        var volumeLever = findViewById<SeekBar>(R.id.volumeLever)
        val phone = findViewById<ImageView>(R.id.phone)
        var dogDays = findViewById<TextView>(R.id.time1)
        var stars = findViewById<RatingBar>(R.id.stars)
        val ticket = findViewById<ImageView>(R.id.ticket1)
        //var degree = findViewById<RatingBar>(R.id.degree)
       // var PotentialSolutions1 = findViewById<EditText>(R.id.title)
        var pos = findViewById<TextView>(R.id.pos)
       // var crit = findViewById<TextView>(R.id.critical)
      //  var noncrit = findViewById<TextView>(R.id.noncrit)
       // var skateBoard = findViewById<ImageView>(R.id.skateboard)
        var goSkate = findViewById<ImageView>(R.id.goSkate)
        var colorMenu = findViewById<RelativeLayout>(R.id.colorMenu)

        var theBSLog = findViewById<RelativeLayout>(R.id.theBSLog)

        var ticket3 = findViewById<ImageView>(R.id.ticket1)
        var newLayout = findViewById<RelativeLayout>(R.id.somethingElse1)

        newLayout.visibility = View.INVISIBLE
        ticket3.visibility = View.INVISIBLE

        ttf.visibility = View.INVISIBLE
        theBSLog.visibility = View.INVISIBLE


        modeScreen.visibility = View.INVISIBLE
        somethingElse.visibility = View.INVISIBLE

        volumeLever.visibility = View.INVISIBLE
        phone.visibility = View.INVISIBLE
        ticket.visibility = View.INVISIBLE

        goSkate.visibility = View.INVISIBLE

      //  skateBoard.visibility = View.INVISIBLE
        dogDays.visibility = View.INVISIBLE
        stars.visibility = View.INVISIBLE
        //degree.visibility = View.INVISIBLE
        pos.visibility = View.INVISIBLE
        //PotentialSolutions1.visibility = View.INVISIBLE
        //crit.visibility = View.INVISIBLE
       // noncrit.visibility = View.INVISIBLE


    }

/*    @Override
    override fun onRestart() {
        monaLista(0)
        super.onRestart()
    }*/


    @Override
    override fun onResume() {

        var QT = sharedPreferences.getInt("QT", 0)
        var potentialSolutions = findViewById<EditText>(R.id.potentialSolutions)
        var QTBtn = findViewById<ImageView>(R.id.QT)
        var time = findViewById<TextClock>(R.id.time)

        var QTCount = sharedPreferences.getInt("QTCount", 1)
        var current1 = sharedPreferences.getString("current", "")
        if (QT == 1) {

            QTCount++
            sharedPreferences.edit().putInt("QTCount", QTCount).commit()

            //  var theItem =  potentialSolutions.text.toString() + "\n\n" + date1 + "\n" + time.text.toString() + " " + color1.toString()
            potentialSolutions.setText("Count: " + QTCount.toString() + "\n" + current1 + time.text.toString())
            QTBtn.animate().scaleX(2F)
            QTBtn.animate().scaleY(2F)
            Toast.makeText(this, QTCount.toString(), Toast.LENGTH_SHORT).show()

        }
        super.onResume()
    }
}


