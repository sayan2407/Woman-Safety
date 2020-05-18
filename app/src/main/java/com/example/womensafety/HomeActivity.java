package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    Toast backToast;
    long back;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Button add;
    CardView card1,card2,card3,card4;
    DatabaseHelper mydb;
    ProgressBar progressBar;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Double latitude;
    Double longitude;
    String address;
    String uri;

    ImageButton emergency;
    ArrayList<String> numList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupToolbar();
        add=findViewById(R.id.btn_add_number);
        progressBar=findViewById(R.id.p1);
        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);
        card3=findViewById(R.id.card3);
        card4=findViewById(R.id.card4);
        navigationView=findViewById(R.id.nav_view);
        mydb=new DatabaseHelper(getApplicationContext());

        numList=new ArrayList<String>();
        emergency=findViewById(R.id.btn_emergency);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);


        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cursor result=mydb.getNumber();
                    if (result.getCount()==0)
                    {
                        Toast.makeText(getApplicationContext(),"Add Number to send Emergency Alert",Toast.LENGTH_SHORT).show();
                    }else {
                        while (result.moveToNext()) {
                            numList.add(result.getString(0));
                        }
                    }
                    if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                    {
                       fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                           @Override
                           public void onComplete(@NonNull Task<Location> task) {
                               Location location=task.getResult();
                               if (location!=null)
                               {
                                   Geocoder geocoder=new Geocoder(HomeActivity.this,Locale.getDefault());
                                   try {
                                       List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                        latitude=addresses.get(0).getLatitude();
                                       longitude=addresses.get(0).getLongitude();
                                       address=addresses.get(0).getAddressLine(0);
                                       String msg ="Hi,I am in trouble,please help me by reaching to below location.Google Map Location ";
                                       String uri ="http://maps.google.com/maps?saddr="+latitude+","+longitude+address;;

                                       Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                                       PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                                       SmsManager sms=SmsManager.getDefault();
                                       for (int i=0;i<numList.size();i++)
                                       {
                                           sms.sendTextMessage(numList.get(i), null,uri, pi,null);

                                       }


                                       Toast.makeText(getApplicationContext(), "Emergency Alert Sent successfully!",
                                               Toast.LENGTH_LONG).show();

                                   } catch (IOException e) {
                                       e.printStackTrace();
                                   }
                               }

                           }
                       });
                    }
                    else {
                        ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                    }

                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ContactsActivity.class));
            }
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TipsActivity.class));
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EscapeActivity.class));
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LawActivity.class));
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),VideosActivity.class));
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.about:
                        AlertDialog.Builder alert=new AlertDialog.Builder(HomeActivity.this);
                        alert.setCancelable(false);
                        alert.setTitle(R.string.about_title);
                        alert.setMessage("Woman Safety app is developed for protecting lives of people in any emergency situations.In case of any unsafe situation,just TAP the"+
                                "Emergency button to the trusted contacts saved in the application.The Emergency alert will be in the form of SMS informing that you are unsafe and need help." +
                                "The SMS includes accurate current GPS location with address of the user along with google maps link.The trusted contacts can use this google maps link to get directions and navigate\n" +
                                "to the exact location of the distressed person.The app can be used for your personal safety,Woman safety and children safety.\n\nWoman safety app also provide tips for woman safety,tips to escape from threat,Indian penal code sections related to woman and videos that helps for self defence\n\nWoman Safety application is meant for Emergency alerts in case of any emergencies.So Developer is not responsible for any misuse of this application.\n");
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.create().show();
                        break;
                    case R.id.help:
                        AlertDialog.Builder alert1=new AlertDialog.Builder(HomeActivity.this);
                        alert1.setCancelable(false);
                        alert1.setTitle("Help");
                        alert1.setMessage("Coming Soon");
                        alert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert1.create().show();
                        break;
                    case R.id.rate:
                        final RatingBar ratingBar =new RatingBar(HomeActivity.this);
                        AlertDialog.Builder alert2=new AlertDialog.Builder(HomeActivity.this);
                        alert2.setView(ratingBar);
                        alert2.setCancelable(false);
                        alert2.setTitle("Rate this app");
                        alert2.setMessage("Tell what you think");
                        alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float ratingvalue=ratingBar.getRating();
                                sendEmail("sayanp166@gmail.com","Rating","Rating is :"+ratingvalue);
                                dialog.cancel();
                            }
                        });
                        alert2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });
                        alert2.create().show();
                        break;
                    case R.id.share:
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBody="https://github.com/sayan2407/Woman-Safety";
                        String shareSub="Woman Safety";
                        intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                        intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                        startActivity(Intent.createChooser(intent,"Share Using"));

                }
                return false;
            }
        });

    }
    public void setupToolbar()
    {
        drawerLayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onBackPressed() {

        if (back+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else {
            backToast= Toast.makeText(getApplicationContext(),"please back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        back=System.currentTimeMillis();
    }
    @SuppressLint("LongLogTag")
    protected void sendEmail(String to, String sub, String message) {
        Log.i("Send email", "");

        String TO = to;
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
        emailIntent.putExtra(Intent.EXTRA_TEXT,message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(HomeActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkpermission(String Permission)
    {
        int check= ContextCompat.checkSelfPermission(this,Permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }
}
