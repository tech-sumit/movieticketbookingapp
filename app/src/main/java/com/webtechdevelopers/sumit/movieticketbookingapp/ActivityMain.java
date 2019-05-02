package com.webtechdevelopers.sumit.movieticketbookingapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentAboutUs;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentBooking;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentLogin;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentMain;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentMovieDetails;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FragmentPayment;
import com.webtechdevelopers.sumit.movieticketbookingapp.fragments.FrgementOrders;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.OnFragmentInteractionListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.PersistentDataStorage;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.SerializationUtils;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

public class ActivityMain extends AppCompatActivity implements OnFragmentInteractionListener,PaymentResultWithDataListener{
    private int backCount=0;
    private boolean isDoubleClickAllowed=true;
    private Show show;
    private String lastFragment = "";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBar actionbar;
    private Toolbar toolbar;
    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        Checkout.preload(this);
        //setTheme(R.style.AppTheme_NoActionBar_Dark);
        setContentView(R.layout.layout_splash_screen);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 43);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
                SharedPreferences sharedPreferences=getSharedPreferences(Constants.LOGIN_PREF,MODE_PRIVATE);
                if(sharedPreferences.getBoolean(Constants.LOGIN,false)){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container,new FragmentMain())
                            .commitNow();
                    toolbar = findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);
                    actionbar = getSupportActionBar();
                    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
                    actionbar.setDisplayHomeAsUpEnabled(true);

                    drawerLayout = findViewById(R.id.drawer_layout);
                    navigationView = findViewById(R.id.navigation);
                    navigationView.setNavigationItemSelectedListener(
                            new NavigationView.OnNavigationItemSelectedListener() {
                                @Override
                                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                    Log.i("NavigationView",""+menuItem.getTitle());
                                    menuItem.setChecked(true);
                                    drawerLayout.closeDrawers();
                                    switch (menuItem.getItemId()) {
                                        case R.id.nav_theatre:
                                            Log.i("NavigationView","nav_theatre");
                                            if (!lastFragment.equals("main_fragment")) {
                                                lastFragment = "main_fragment";
                                                onFragmentInteractionResult("main_fragment", null);
                                            }
                                            break;
                                        case R.id.nav_booking:
                                            Log.i("NavigationView","nav_booking");
                                            lastFragment = "nav_booking";
                                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FrgementOrders()).commit();
                                            break;
                                        case R.id.nav_about_us:
                                            Log.i("NavigationView","nav_about_us");
                                            lastFragment = "nav_about_us";
                                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentAboutUs()).commit();
                                            break;
                                        case R.id.nav_signout:
                                            Log.i("NavigationView","nav_signout");
                                            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                                    .requestEmail()
                                                    .requestProfile()
                                                    .build();
                                            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(ActivityMain.this, googleSignInOptions);
                                            googleSignInClient.signOut()
                                                    .addOnCompleteListener(ActivityMain.this, new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (!lastFragment.equals("login_fragment")) {
                                                                getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE).edit().clear().apply();
                                                                getSharedPreferences("ticket_data", Context.MODE_PRIVATE).edit().clear().apply();
                                                                onFragmentInteractionResult("login_fragment", null);
                                                                lastFragment = "login_fragment";
                                                                navigationView.setCheckedItem(R.id.nav_theatre);
                                                            }
                                                        }
                                                    });
                                            break;

                                    }
                                    return true;
                                }
                            }
                    );
                    View header=navigationView.getHeaderView(0);
                    SimpleDraweeView profilePic = header.findViewById(R.id.profile_pic);
                    sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
                    String profilePicURL = sharedPreferences.getString(Constants.PROFILE_PIC, "");
                    profilePic.setImageURI(Uri.parse(profilePicURL));
                    TextView profileName = header.findViewById(R.id.profile_name);
                    profileName.setText(sharedPreferences.getString(Constants.NAME, ""));


                }else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container,new FragmentLogin())
                            .commitNowAllowingStateLoss();
                }
            }
        },2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(navigationView);
            break;
        }
        return true;

    }


    @Override
    public void onFragmentInteractionResult(@NonNull String fragmentName, Bundle bundle) {
        backCount=0;
        FragmentMovieDetails fragmentMovieDetails;
        switch (fragmentName){
            case "login_fragment":
                lastFragment = "login_fragment";
                isDoubleClickAllowed=true;
                if(toolbar!=null)
                    toolbar.setVisibility(View.GONE);
                FragmentLogin fragmentLogin=new FragmentLogin();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentLogin)
                        .commit();
                break;
            case "main_fragment":
                isDoubleClickAllowed=true;
                lastFragment = "main_fragment";
                toolbar.setVisibility(View.VISIBLE);
                FragmentMain fragmentMain =new FragmentMain();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentMain)
                        .commitNowAllowingStateLoss();
                break;
            case "movie_details":
                isDoubleClickAllowed=false;
                lastFragment = "movie_details";
                fragmentMovieDetails =FragmentMovieDetails.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentMovieDetails)
                        .addToBackStack("FragmentMovieDetails")
                        .commit();
                break;
            case "booking":
                isDoubleClickAllowed=false;
                lastFragment = "booking";
                FragmentBooking fragmentBooking =FragmentBooking.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentBooking)
                        .addToBackStack("FragmentBooking")
                        .commit();
                break;
            case "payment":
                isDoubleClickAllowed=false;
                lastFragment = "payment";
                FragmentPayment fragmentPayment =FragmentPayment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, fragmentPayment)
                        .commit();
                break;
            default:
                isDoubleClickAllowed=false;
                Log.e("CASE_ERROR","Invalid fragmentId ID:"+fragmentName);
        }
    }

    @Override
    public void onBackPressed() {
        if(isDoubleClickAllowed){
            backCount++;
            if(backCount>1){
                finish();
            }else {
                Toast.makeText(ActivityMain.this,"Press again to close",Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onBackPressed();
            backCount=0;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backCount=0;
            }
        },3000);
    }

    @Override
    public void onPaymentSuccess(final String s, PaymentData paymentData) {
        Log.i("PAYMENT_RESULT",""+s);
        try{
            String id=getSharedPreferences(Constants.LOGIN_PREF,MODE_PRIVATE).getString(Constants.ID,"");
            assert id != null;
            if(id.equals("")){
                Log.i("onPaymentSuccess","Empty Email at onPaymentSuccess id:"+id);
                return;
            }
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.BOOKINGS);
            databaseReference.child(id).child(databaseReference.push().getKey()).setValue(show.getSerializable());
            Log.i("onPaymentSuccess","Show data: "+show.getSerializable());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Show show= dataSnapshot.getValue(Show.class);
                    assert show != null;
                    Log.w("FirebaseDatabase", "add Show :onDataChange show: "+show.toString());
                    if (show.equals(ActivityMain.this.show)){
                        Log.w("FirebaseDatabase",
                                "add Show :onDataChange show not matched: show"
                                        +show.toString()
                                        +"\nOriginal show: "
                                        +ActivityMain.this.show);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("FirebaseDatabase", "add Show :onCancelled", databaseError.toException());
                }
            });
            PersistentDataStorage persistentDataStorage =new PersistentDataStorage(this);
            show.setPaymentData(paymentData);
            persistentDataStorage.addShow(show);
            Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show();
            isDoubleClickAllowed=true;
            onFragmentInteractionResult("main_fragment",null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.i("PAYMENT_RESULT","i: "+i+"\n"+s);
        switch (i){
            case Checkout.PAYMENT_CANCELED:
                Toast.makeText(this,"Payment Canceled",Toast.LENGTH_SHORT).show();
                break;
            case Checkout.NETWORK_ERROR:
                Toast.makeText(this,"Network Filed",Toast.LENGTH_SHORT).show();
                break;
            case Checkout.INVALID_OPTIONS:
                Toast.makeText(this,"Invalid Options",Toast.LENGTH_SHORT).show();
                break;
            case Checkout.TLS_ERROR:
                Toast.makeText(this,"TLS Error",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this,"Unknown error, Try again later",Toast.LENGTH_SHORT).show();
        }
    }
    public void setShow(Show show){
        this.show=show;
    }

}
