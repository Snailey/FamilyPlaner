package no.hiof.snailey.familyplaner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RegisterFragment extends Fragment {

    public static final String KEY_NAME = "userID";

    //moc userID - get from Firebase
    private String userID = "12345";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Make login btn --> MainActivity
        Button btn_register = view.findViewById(R.id.btn_register);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);


        //take user to MainActivity
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                myIntent.putExtra(KEY_NAME, userID);
                getActivity().startActivity(myIntent);

            }
        });


        //take user to Register_Fragment
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LogInFragment loginFragment = new LogInFragment();

                Bundle arguments = new Bundle();
                arguments.putString(KEY_NAME, userID);
                loginFragment.setArguments(arguments);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_login, loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }

        });
    }
}