package no.hiof.snailey.familyplaner;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    RecyclerView todoList;
    RecyclerView.Adapter adapter;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Button btn_calendar = findViewById(R.id.btn_calendar);
        Button btn_shopping = findViewById(R.id.btn_shopping);
        Button btn_todo = findViewById(R.id.btn_todo);
        Button btn_settings = findViewById(R.id.btn_settings);

        //menu
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        //take user to CalendarActivity
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);

           }
        });

        //take user to ShoppingListActivity
        btn_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
               startActivity(intent);

            }

        });

        //take user to ToDoActivity
        btn_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ToDoActivity.class);
                startActivity(intent);

            }

        });

        //take user to Settings
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);

            }

        });


    }

}