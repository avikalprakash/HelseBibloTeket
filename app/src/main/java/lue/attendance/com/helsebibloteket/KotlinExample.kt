package lue.attendance.com.helsebibloteket

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class KotlinExample : AppCompatActivity() {
    var myString = "myString";
    var mnc=1;
    var mnc2=8;
    var tvn=1.5;
    var add=0;




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_example)
// get reference to button
        val btn_click_me = findViewById(R.id.go) as Button
// set on-click listener
        btn_click_me.setOnClickListener {
           // Toast.makeText(this@KotlinExample, "You clicked me.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Activity2::class.java)
// To pass any data to next activity
           // intent.putExtra("keyIdentifier", value)
// start your next activity
            startActivity(intent)
        }

        //  Toast.makeText(applicationContext, myString, Toast.LENGTH_LONG).show();
        news();
    }

    fun news (){
        var name = "Avikal";
        add=mnc*mnc2;
        var s: String = add.toString()
        Toast.makeText(applicationContext, "Avikal this is solution : "+s, Toast.LENGTH_LONG).show();

    }
}
