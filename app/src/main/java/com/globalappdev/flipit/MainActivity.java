package com.globalappdev.flipit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.globalappdev.flipit.models.Expense;
import com.globalappdev.flipit.models.Expenses;
import com.globalappdev.flipit.models.Investment;
import com.globalappdev.flipit.models.Investments;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback {
    /**
     * Request code for the autocomplete activity. This will be used to identify results from the
     * autocomplete activity in onActivityResult.
     */
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    GoogleMap googleMap;
    private TextView mPlaceDetailsText;

    private TextView mPlaceAttribution;
    public StorageReference mStorageRef;
    ViewPager mPager;
    DemoParallaxAdapter mAdapter;
    WebView myWebView;
    Context mContext;
    public int addPosition;
    protected GeoDataClient mGeoDataClient;
    protected FusedLocationProviderClient mFusedLocationProviderClient;
    protected PlaceDetectionClient mPlaceDetectionClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FireBase
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mContext = this.getBaseContext();
//        mPager = (ViewPager) findViewById(R.id.pager);
//        mPager.setBackgroundColor(0xFFFFFFFF);
//        int otherParallax[] = {
//                R.id.cv_total,
//                R.id.fab_bottom,
//                R.id.tv_address,
//                R.id.cv_topcard,
//                R.id.fab_left,
//                R.id.fab_right
//        };
//        ParallaxPageTransformer pt = new ParallaxPageTransformer(R.id.parallax_image, otherParallax);
//        pt.setBorder(9);
//        pt.setSpeed(0.6f);
//        mPager.setPageTransformer(false, pt);
//
//        mAdapter = new DemoParallaxAdapter(getSupportFragmentManager());
//        mAdapter.setPager(mPager); //only for this transformer

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dev").child(getResources().getString(R.string.dev_id_1));


//        ArrayList<Expense> expenses = new ArrayList<>();
//        Expense mfirst = new Expense("wooden flooring",100,0.25f,"url");
//        Expense mfirsttwo = new Expense("kitchen tiles",20,0.50f,"url");
//        expenses.add(mfirst);
//        expenses.add(mfirsttwo);
//        Expenses mdt = new Expenses(expenses);
//
//        ArrayList<Expense> expenses1 = new ArrayList<>();
//        Expense mfirst1 = new Expense("kitchen Sink",2,200.f,"url");
//        Expense mfirsttwo1 = new Expense("wall tiles",40,0.70f,"url");
//        expenses1.add(mfirst1);
//        expenses1.add(mfirsttwo1);
//        Expenses mdt1 = new Expenses(expenses1);
//
//        Investment mInv = new Investment("inv1","1444 w east street, st.petersburg, FL",mdt,"https://firebasestorage.googleapis.com/v0/b/chapp-5c477.appspot.com/o/inve1.jpg?alt=media&token=2bf7c91f-9611-4ebb-a39d-79fbb1fc895f");
//        Investment mInv2 = new Investment("inv2","3131 6th ave N, st.petersburg, FL",mdt1,"https://firebasestorage.googleapis.com/v0/b/chapp-5c477.appspot.com/o/inve2.jpg?alt=media&token=a1e6db07-c983-4bde-a4b0-e3ce4294f9ec");
//        ArrayList<Investment> investments = new ArrayList<>();
//        investments.add(mInv);
//        investments.add(mInv2);
//        Investments allInv = new Investments(mInv,mInv2);
//        myRef.setValue(allInv);
//
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                        user = dataSnapshot.getValue(User.class);
//                Log.d("data",dataSnapshot.getValue().toString());
//                Investments mInvestments = dataSnapshot.getValue(Investments.class);
//
//                List<Investment> investments = mInvestments.getInvestments();
//                Log.d("data2",String.valueOf(investments.size()));
//                buildInvestments(investments);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//
//        });


        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().hide();
        }



        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setAllowFileAccessFromFileURLs(true); //Maybe you don't need this rule
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new HelloWebViewClient());
        String html = "<html>\n" +
                "   <head>\n" +
                "      <script language=\"javascript\">\n" +
                "         function getData(){\n" +
                "               var xhr = new XMLHttpRequest();\n" +
                "               \n" +
                "               xhr.open(\"get\", \"https://www.google.com/search?hl=en&output=search&tbm=shop&q=hardwood+flooring&btnG=\", false);\n" +
                "               xhr.send();\n" +
                "          }\n" +
                "\n" +
                "  (function() {\n" +
                "    var cx = '009804600254052194488:vyvl0p3wa7m';\n" +
                "    var gcse = document.createElement('script');\n" +
                "    gcse.type = 'text/javascript';\n" +
                "    gcse.async = true;\n" +
                "    gcse.src = 'https://cse.google.com/cse.js?cx=' + cx;\n" +
                "    var s = document.getElementsByTagName('script')[0];\n" +
                "    s.parentNode.insertBefore(gcse, s);\n" +
                "  })();\n" +
                "</script>\n" +
                "<gcse:searchresults-only></gcse:searchresults-only>\n" +
                "   </head>\n" +
                "   <body>\n" +
                "      <center><b><u>Example</u></b></center>\n" +
                "      <p>\n" +
                "    </body>\n" +
                "</html>";


        myWebView.loadData(html,"text/HTML","UTF-8");


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("tag", "Place: " + place.getName());
                googleMap.addMarker(new MarkerOptions().position(place.getLatLng())
                        .title(place.getName().toString()));
                googleMap.addCircle(new CircleOptions().center(place.getLatLng()).strokeColor(Color.GREEN).radius(20.2)
                .clickable(true));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),18.f));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("tag", "An error occurred: " + status);
            }
        });

        // Get the SupportMapFragment and request notification
//        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void buildInvestments(List<Investment> investments) {
        int addLoc = 0;
        for(Investment i:investments){
            Bundle bNina = new Bundle();
            bNina.putString("image",i.getBg());
            bNina.putString("name", i.getAddress());
            DemoParallaxFragment pfNina = new DemoParallaxFragment();
            pfNina.setExpenses(i.getSub());
            pfNina.setParent(this);
            pfNina.setArguments(bNina);


            mAdapter.add(pfNina);
            addLoc++;
        }
        Bundle bAdd = new Bundle();
        bAdd.putInt("bg_image",R.drawable.bg_light_blur);
        bAdd.putString("name", "new");
        DemoParallaxFragment addFrag = new DemoParallaxFragment();
        addFrag.setParent(this);
        addFrag.setArguments(bAdd);
        mAdapter.add(addFrag);
        addLoc ++;
        addPosition = addLoc;


        mPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
//        mPager.setCurrentItem(addLoc,true);
    }
    public void refreshList(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dev").child(getResources().getString(R.string.dev_id_1));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                        user = dataSnapshot.getValue(User.class);
                Log.d("data",dataSnapshot.getValue().toString());
                Investments mInvestments = dataSnapshot.getValue(Investments.class);

                List<Investment> investments = mInvestments.getInvestments();
                Log.d("data2",String.valueOf(investments.size()));
                buildInvestments(investments);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e("TAG", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);



                // Format the place's details and display them in the TextView.
                mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress(), place.getPhoneNumber(),
                        place.getWebsiteUri()));

                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
                    mPlaceAttribution.setText("");
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("tag", "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }

    /**
     * Helper method to format information about a place nicely.
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e("tag", res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }
    public void scrollToAdd(){
        mPager.setCurrentItem(addPosition,true);
    }

    private void uploadTest(){
        Drawable mDrawe = getResources().getDrawable(R.drawable.bg_nina);

    }
    public void newInvestment(){
        Bundle bNina = new Bundle();
        bNina.putInt("image", R.drawable.inve1);
        bNina.putString("name", "1444 w east street, st.petersburg, FL");
        DemoParallaxFragment pfNina = new DemoParallaxFragment();
        pfNina.setArguments(bNina);
        mAdapter.add(pfNina);
        mAdapter.notifyDataSetChanged();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("i","I go map");
            this.googleMap = googleMap;

        googleMap.setMinZoomPreference(3.4f);
        // Create a LatLngBounds that includes Australia.
        LatLng mLoc = new LatLng(38.0,-97.0);
//googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(mLoc,0.f,0.f,0.f)));
// Set the camera to the greatest possible zoom level that includes the
// bounds
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc));
    }



    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
