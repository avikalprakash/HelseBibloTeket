package lue.attendance.com.helsebibloteket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity1 extends AppCompatActivity {
    Button click;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String a="click";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        click=(Button)findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences=getSharedPreferences("MyPref", MODE_PRIVATE);
                editor=sharedPreferences.edit();
                editor.putString("a", a);
                editor.commit();
             startActivity(new Intent(getApplicationContext(), KotlinExample.class));
            }
        });
    }
}
