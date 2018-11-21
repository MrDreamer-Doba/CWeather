package com.test.mykhailodobosh.cweather.screens;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.test.mykhailodobosh.cweather.R;
import com.test.mykhailodobosh.cweather.adapters.PlaceAutocompleteAdapter;
import com.test.mykhailodobosh.cweather.utils.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String WEATHER_SETTINGS = "WEATHER_SETTINGS";
    public static final String PREF_IS_FIRST_TIME = "PREF_IS_FIRST_TIME";
    public static final String PREF_LAT = "com.test.mykhailodobosh.cweather.screens.PREF_LAT";
    public static final String PREF_LON = "com.test.mykhailodobosh.cweather.screens.PREF_LON";

    private static final String TAG = MapActivity.class.getSimpleName();
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,  20), new LatLng(20, 34)
    );

    private GoogleMap mMap;
    private AutoCompleteTextView mSearchCityEditText;

    private Context mContext;
    private SharedPreferences mPreferences;
    private boolean mHasPermission;

    protected GeoDataClient mGeoDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mContext = this;

        mSearchCityEditText = findViewById(R.id.edt_input_text);
        getLocationPermission();

        mPreferences = getSharedPreferences(WEATHER_SETTINGS, Context.MODE_PRIVATE);
        boolean isFirstTime = mPreferences.getBoolean(PREF_IS_FIRST_TIME, true);

        if (!isFirstTime) {
            Intent intent = new Intent(mContext, WeatherDetailedActivity.class);
            startActivity(intent);
        }

        if (!NetworkUtil.isNetworkAvailable(this) && isFirstTime) {
            showExitPopup();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mHasPermission) {
            getCurrentLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
            mMap.setOnMapClickListener(this);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mPreferences.edit().putFloat(PREF_LAT, (float) latLng.latitude).apply();
        mPreferences.edit().putFloat(PREF_LON, (float) latLng.longitude).apply();
        moveToWeatherDetailsScreen();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHasPermission = false;

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mHasPermission = false;
                            return;
                        }
                    }
                    mHasPermission = true;
                    initGoogleMap();
                }
            }
        }
    }

    private void getCurrentLocation() {
        Log.d(TAG, "getCurrentLocation: getting the device current location");

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mHasPermission) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                         if (task.isSuccessful()) {
                             Location location = (Location) task.getResult();
                             LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                             moveCamera(latLng);
                         } else {
                             Toast.makeText(mContext, "Enabled to get current location", Toast.LENGTH_LONG).show();
                         }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getCurrentLocation:" + e.getMessage());
        }
    }

    private void init() {
        mGeoDataClient = Places.getGeoDataClient(this, null);

        PlaceAutocompleteAdapter placeAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient,
                LAT_LNG_BOUNDS, null);

        mSearchCityEditText.setAdapter(placeAutocompleteAdapter);
        mSearchCityEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH  ||
                    actionId == EditorInfo.IME_ACTION_DONE    ||
                    event.getAction() == KeyEvent.ACTION_DOWN ||
                    event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    locateCity();
                }

                return false;
            }
        });

        hideKeyboard();
    }

    private void locateCity() {
        String city = mSearchCityEditText.getText().toString();

        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> location = new ArrayList<>();
        try {
            location = geocoder.getFromLocationName(city, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!location.isEmpty()) {
            Address address = location.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()));
        }
    }

    private void moveCamera(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Show current weather");

        mMap.addMarker(options);

        hideKeyboard();
    }

    private void initGoogleMap() {
        Log.d(TAG, "initGoogleMap: initialize Google Map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void showExitPopup() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.warning_no_connection)
                .setCancelable(false)
                .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                })
                .show();
    }

    private void moveToWeatherDetailsScreen() {
        if (NetworkUtil.isNetworkAvailable(this)) {
            Intent intent = new Intent(mContext, WeatherDetailedActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void getLocationPermission() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mHasPermission = true;
                        initGoogleMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private void hideKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
