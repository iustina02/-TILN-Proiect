package com.example.mily_alpha;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mily_alpha.DB_Helper.UserDBHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    UserDBHelper userDBHelper;

    private ImageView profileImage;
    private TextView nameTextView;
    private TextView emailTextView;
    private Button signOutButton;
    private Button profileButton;
    private Button addProductButton;
    private Button fridgeButton;
    private Button categButton;

    private Button seeUsers;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profileImageView);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        signOutButton = findViewById(R.id.signOutButton);

        profileButton =  findViewById(R.id.profile_button);
        addProductButton = findViewById(R.id.addProducts_button);
        fridgeButton = findViewById(R.id.frig_button);
        categButton = findViewById(R.id.categ_button);

        seeUsers = findViewById(R.id.SeeUsers);

        userDBHelper = new UserDBHelper(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            String checkbox = preferences.getString("remember","");
                            if(checkbox.equals("true")){
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("remember","false");
                                editor.apply();
                            }
                            gotoLoginActivity();
                        }
                        else
                            Toast.makeText(ProfileActivity.this,"Log Out Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        seeUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ProfileActivity.this, ListUsers.class);
                startActivity(intent);
            }
        });


        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                finish();
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });

        fridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ListUsers.class));
                finish();
            }
        });

        categButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, CategoryList.class));
                finish();
            }
        });



    }


    private void gotoMainActivity() {

        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }


    private void gotoLoginActivity() {

        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();
            nameTextView.setText(account.getDisplayName());
            emailTextView.setText(account.getEmail());
            AddData(account.getEmail(),account.getDisplayName());

            Picasso.get().load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(profileImage);
        }else{
           gotoMainActivity();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSignInResult(result);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void AddData(String UserEmail, String UserName){
        boolean insertData = userDBHelper.addUser(UserEmail,UserName);
        if(insertData) {
            //Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(this, "Data Insert Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
