package no.hiof.snailey.familyplaner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_calendar = findViewById(R.id.btn_calendar);
        Button btn_shopping = findViewById(R.id.btn_shopping);
        Button btn_todo = findViewById(R.id.btn_todo);

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


    }

}