package com.social.projectapp;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputBinding;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {








    ArrayAdapter arrayAdapter;
    String uname,pword;

    FirebaseDatabase database ;
    DatabaseReference refuname, refpword,ref;

    Users users;
    TextView textView ;
    ListView listView ;
    EditText username , password;
    Button Register; long backpressedtime;
    Toast toast;
    String mtitle[] = new String[170];
    String mDescription [] = new String[170];
    String imageurl[] = new String[170];
    int SPLASH_TIME_OUT = 4000;
    int n=170;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("selection1");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String string="";
                String str;
                for(int j=0;j<n;j++)
                {
                    string = "INGREDIENTS:\n\n";
                    str=""+j;
                    string += dataSnapshot.child(str).child("ingredients").getValue().toString();
                    String name;
                    name= dataSnapshot.child(str).child("name").getValue().toString();mtitle[j]=name;
                    string += "\n\nPROCEDURE:\n\n";
                    string += dataSnapshot.child(str).child("instructions").getValue().toString();
                    mDescription[j]=string;
                    imageurl[j] = dataSnapshot.child(str).child("image").toString();
                    imageurl[j]=imageurl[j].substring(36,imageurl[j].length()-2);
                    string ="";

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


        username = (EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);
        Register = (Button) findViewById(R.id.Register);
        username.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        Register.setVisibility(View.INVISIBLE);

    }

    public String[]  getmtitle(){
        return mtitle;

    }
    public String[] getmDescription(){
        return mDescription;
    }
    @Override
    public void onBackPressed() {
       // if(backpressedtime + 2000>System.currentTimeMillis()){
       //     toast.cancel();
            super.onBackPressed();
        //    return;
      /*  }
        else
        {
            toast = Toast.makeText(this, "Press Again to exit the app", Toast.LENGTH_LONG);
            toast.show();
        }
        backpressedtime = System.currentTimeMillis();
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;

        BottomNavigationView hello = (BottomNavigationView) findViewById(R.id.bottomnavigationmenu);

        switch (item.getItemId()) {
            case R.id.history:
                selectedFragment = new History_Fragment();
                hello.setVisibility(View.INVISIBLE);

                break;
            case R.id.about:
                selectedFragment = new Activity_Fragment();
                hello.setVisibility(View.INVISIBLE);
                break;
            case R.id.mainscreen:
                selectedFragment = new Home_Fragment(mtitle,imageurl,mDescription);
                hello.setVisibility(View.INVISIBLE);
                break;
           /* case R.id.logout:
                selectedFragment = new LogOut();
                break;
*/
        }
        if (item.getItemId() == R.id.history || item.getItemId() == R.id.about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, selectedFragment).addToBackStack(null).commit();
        } else if (R.id.mainscreen == item.getItemId()) {

            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, selectedFragment).addToBackStack(null).commit();
            hello.setVisibility(View.VISIBLE);

        } else{
         //   getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutmenu,HomeActivity).commit();
        }


        return super.onOptionsItemSelected(item);
    }
    BottomNavigationView menu;




    public void register(View v){


        uname = username.getText().toString();
        pword = password.getText().toString();


    }



    public void signup(View v){

        BottomNavigationView menu = (BottomNavigationView) findViewById(R.id.bottomnavigationmenu);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Button login = (Button) findViewById(R.id.logIn);

        Button signin = (Button) findViewById(R.id.signUp);

        login.setVisibility(View.INVISIBLE);

        signin.setVisibility(View.INVISIBLE);

        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        Register.setVisibility(View.VISIBLE);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


    }



    public void login(View v){




        BottomNavigationView menu = (BottomNavigationView) findViewById(R.id.bottomnavigationmenu);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Button login = (Button) findViewById(R.id.logIn);

        Button signin = (Button) findViewById(R.id.signUp);

        imageView.setVisibility(View.INVISIBLE);

        login.setVisibility(View.INVISIBLE);

        signin.setVisibility(View.INVISIBLE);

        menu.setVisibility(View.VISIBLE);

        menu.setOnNavigationItemSelectedListener(navlistener);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Home_Fragment(mtitle,imageurl,mDescription)).addToBackStack(null).commit();

            }
        },2500);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            BottomNavigationView hello = (BottomNavigationView) findViewById(R.id.bottomnavigationmenu);
            hello.setVisibility(View.VISIBLE);
            switch (item.getItemId()){
                case R.id.favorite :
                    selectedFragment = new Favorites_Fragment();
                    break;
                case R.id.home :
                    selectedFragment = new Home_Fragment(mtitle,imageurl,mDescription);

                    break;
                case R.id.activity :
                    selectedFragment = new Activity_Fragment();
                    break;
            }
             getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,selectedFragment).addToBackStack(null).commit();

            return true;
        }
    };


}