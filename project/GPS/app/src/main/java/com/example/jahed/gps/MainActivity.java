package com.example.jahed.gps;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button btnShowLocation;
    private static int  REQUEST_CODE_PERMISSION=15;
    String mPermission= Manifest.permission.ACCESS_FINE_LOCATION;

    Gps gps;
    TextView location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            if(ActivityCompat.checkSelfPermission(this,mPermission)!= MockPackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{mPermission},REQUEST_CODE_PERMISSION);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        btnShowLocation=(Button)findViewById(R.id.button);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps=new Gps(MainActivity.this);
                location=(TextView)findViewById(R.id.locationtext);
                if(gps.canGetlocation()){
                    double latitude=gps.getLatitude();
                    double longitude=gps.getLongitude();
                    database=FirebaseDatabase.getInstance();
                    location.setText(latitude+" "+longitude);
                    myRef=database.getReference("Location");
                    myRef.setValue(latitude+","+longitude);
                }
                else
                {
                    gps.showSettingsAlert();
                }
            }
        });
    }
}
