package no.hiof.snailey.familyplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_NAME = "userID";
    //moc userID - get from Firebase
    public static String userID = "12345";

    private NavController controller;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = Navigation.findNavController(this, R.id.fragment);

        drawerLayout = findViewById(R.id.drawer_layout);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                controller.navigate(menuItem.getItemId());
                menuItem.setChecked(true);
                return true;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(R.id.calendarFragment, R.id.shoppingListFragment, R.id.toDoFragment)
                .setOpenableLayout(drawerLayout).build();
        NavigationUI.setupWithNavController(toolbar, controller, configuration);
        NavigationView drawerView = findViewById(R.id.navigation_view);
        NavigationUI.setupWithNavController(drawerView, controller);

        drawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                controller.navigate(menuItem.getItemId());
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void onClickedLogOut(View view) {
        Toast.makeText(this, "BMW is selected for a drive!", Toast.LENGTH_SHORT).show();
    }
}