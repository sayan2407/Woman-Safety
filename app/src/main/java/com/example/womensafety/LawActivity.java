package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LawActivity extends AppCompatActivity {

    TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);

        back=findViewById(R.id.i1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });

        txt1=(TextView)findViewById(R.id.link1);
        txt1.setMovementMethod(LinkMovementMethod.getInstance());

        txt2=(TextView)findViewById(R.id.link2);
        txt2.setMovementMethod(LinkMovementMethod.getInstance());

        txt3=(TextView)findViewById(R.id.link3);
        txt3.setMovementMethod(LinkMovementMethod.getInstance());

        txt4=(TextView)findViewById(R.id.link4);
        txt4.setMovementMethod(LinkMovementMethod.getInstance());

        txt5=(TextView)findViewById(R.id.link5);
        txt5.setMovementMethod(LinkMovementMethod.getInstance());

        txt6=(TextView)findViewById(R.id.link6);
        txt6.setMovementMethod(LinkMovementMethod.getInstance());

        txt7=(TextView)findViewById(R.id.link7);
        txt7.setMovementMethod(LinkMovementMethod.getInstance());

        txt8=(TextView)findViewById(R.id.link8);
        txt8.setMovementMethod(LinkMovementMethod.getInstance());

        txt9=(TextView)findViewById(R.id.link9);
        txt9.setMovementMethod(LinkMovementMethod.getInstance());

        txt10=(TextView)findViewById(R.id.link10);
        txt10.setMovementMethod(LinkMovementMethod.getInstance());

        txt11=(TextView)findViewById(R.id.link11);
        txt11.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
