package com.example.mily_alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mily_alpha.DB_Helper.DatabaseHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.mily_alpha.App.CHANNEL_1_ID;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    DatabaseHelper databaseHelper;

    private ImageView profileImage;

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getEmailTextView() {
        return emailTextView;
    }

    public TextView nameTextView;
    public TextView emailTextView;
    public String NameUser;
    public String EmailUser;

    private Button signOutButton;
    private Button profileButton;
    private Button addProductButton;
    private Button fridgeButton;
    private Button searchRecipeButton;

    private Button SeeShoopingList;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent startingIntent = getIntent();
        NameUser = startingIntent.getStringExtra("name");
        EmailUser = startingIntent.getStringExtra("email");

        notificationManager = NotificationManagerCompat.from(this);

        profileImage = findViewById(R.id.profileImageView);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        signOutButton = findViewById(R.id.signOutButton);

        profileButton =  findViewById(R.id.profile_button);
        addProductButton = findViewById(R.id.addProducts_button);
        fridgeButton = findViewById(R.id.frig_button);
        searchRecipeButton = findViewById(R.id.recipe_button);

        SeeShoopingList = findViewById(R.id.SeeShoopingList);

        Log.d("Namae User", "User name: " + NameUser);
        Log.d("Email User", "User email: " + EmailUser);

        databaseHelper = DatabaseHelper.getInstance(this);

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

        SeeShoopingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ProfileActivity.this, ShoppingListActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });



        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ProfileActivity.this, MainActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        fridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ProfileActivity.this, ListCategoryActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        searchRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ProfileActivity.this, SearchRecipeActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        if(NameUser != null && EmailUser != null){
            check_data_expiration();
        }

    }

    private void check_data_expiration() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd");
        Date date_now = new Date(System.currentTimeMillis());
        String dateNow = formatter.format(date_now);

        int id_user = databaseHelper.getUserID(NameUser, EmailUser) + 1;
        ArrayList<String> dates = databaseHelper.GetAllDataExpirationFromUser(id_user);
        if(dates != null) {
            for (String date : dates) {
                if(!date.equals("Nici o data adaugata!")) {
                    String[] year_month_day_expiration_date = date.split("/");
                    String[] year_month_day_now = dateNow.split("/");

                    if (Integer.parseInt(year_month_day_expiration_date[0]) >= Integer.parseInt(year_month_day_now[0])) {
                        if (Integer.parseInt(year_month_day_expiration_date[1]) >= Integer.parseInt(year_month_day_now[1])) {
                            if (Integer.parseInt(year_month_day_expiration_date[2]) > (Integer.parseInt(year_month_day_now[2]) + 4)) {
                                continue;
                            } else {
                                sendOnChannel1("Data de expirare", "Exista produse in frigiderul tau ce urmeaza sa expire!");
                            }
                        }
                    }
                }
            }
        }
    }


    private void gotoMainActivity() {

        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }


    private void gotoLoginActivity() {

        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    public void sendOnChannel1(String Title, String Message) {
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, ListCategoryActivity.class);
        resultIntent.putExtra("name", NameUser);
        resultIntent.putExtra("email",EmailUser);
//        startActivity(resultIntent);
//        finish();
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.ic_mily)
                .setContentTitle(Title)
                .setContentText(Message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(resultPendingIntent)
                .build();
        notificationManager.notify(1, notification);
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();
            NameUser = account.getDisplayName();
            EmailUser = account.getEmail();

            nameTextView.setText(NameUser);
            emailTextView.setText(EmailUser);
            AddData(account.getEmail(),account.getDisplayName());

            Picasso.get().load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(profileImage);

        }else{
            Toast.makeText(ProfileActivity.this,"Log in Failed", Toast.LENGTH_SHORT).show();
            Log.w("Log in String: failed! ","Logged in failed!");
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
        boolean insertData = databaseHelper.addUser(UserEmail,UserName);
//        if(insertData) {
////            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_SHORT).show();
//        }else{
////            Toast.makeText(this, "Data Insert Failed!", Toast.LENGTH_SHORT).show();
//        }
    }

    private Boolean goBack = false;
    @Override
    public void onBackPressed() {
        if (goBack == true) {
            Log.d("Go back:", "YES");
            super.onBackPressed();
            Intent sendStuff = new Intent(ProfileActivity.this, ProfileActivity.class);
            sendStuff.putExtra("name", NameUser);
            sendStuff.putExtra("email",EmailUser);
            startActivity(sendStuff);
            finish();
        } else {
            Log.d("Go back:", "NO");
        }
    }
}
