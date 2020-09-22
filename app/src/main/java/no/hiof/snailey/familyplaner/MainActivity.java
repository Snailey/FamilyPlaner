package no.hiof.snailey.familyplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_NAME = "userID";
    //moc userID - get from Firebase
    public static String userID = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        NavController navController = Navigation.findNavController(this,
                R.id.fragment_main);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        NavigationUI.setupActionBarWithNavController(this, navController);


    }

}