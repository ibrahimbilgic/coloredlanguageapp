package com.coloredlanguageapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import static com.coloredlanguageapp.SettingsActivity.isLogoutTrue;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView hello,basicAct;
    private FirebaseAuth firebaseAuth;
    private Button settings;
    GoogleSignInClient googleSignInClient;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);


        hello = v.findViewById(R.id.hello);
        settings = v.findViewById(R.id.settings);
        basicAct = v.findViewById(R.id.BasicAct);


        basicAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToBasicAct = new Intent(getActivity(),BasicActivity.class);
                startActivity(goToBasicAct);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingsActivity.class);
                startActivity(intent);

            }
        });

        if(PreferenceUtils.isNightModeSystemCheck(getActivity())){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        else{
            if(PreferenceUtils.isNightModeSystemCheck(getActivity())){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else if(PreferenceUtils.isNightModeCheck(getActivity())){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }


        String name = PreferenceUtils.getName(getActivity());
        int unicode = 0x1F60E;
        String emoji = getEmoji(unicode);

        hello.setText("Hoş Geldin "+name+" "+emoji);

        return v;
    }
    private String getEmoji(int unicode){
        return new String(Character.toChars(unicode));
    }
}