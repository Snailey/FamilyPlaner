package no.hiof.snailey.familyplaner;

import android.app.Fragment;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;



public class LogInActivity extends AppCompatActivity {

    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

}