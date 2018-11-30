package com.example.moon.planttrees;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private static final String TAG = "Onsearch";
    private GoogleMap mMap;
    String url = "https://rest.soilgrids.org/query?";
    //lon=88.6228466&lat=24.3665815
    MaterialSearchBar searchBar;
    String LocationName;
    LatLng dhaka;
    LocationManager locationManager;
    LatLng currentLOC;
    MarkerOptions markerOptions;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dhaka = new LatLng(23.7805733,90.2792402);
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //setUpSearchBar(googleMap);
        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions().position(dhaka)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title("DHAKA")
                .draggable(true);
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 16.0f));

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    circleImageView.setClickable(false);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .draggable(true);
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()), 16.0f));
                    circleImageView.setClickable(true);

                }else{
                    ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
                }

            }
        });


        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);


        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                mMap.clear();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Log.i(TAG, "onSearchConfirmed: ");
                LocationName = searchBar.getText().toString();
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addressList = geocoder.getFromLocationName(LocationName, 5);
                    // Toast.makeText(getApplicationContext(), addressList.get(0).toString(), Toast.LENGTH_SHORT).show();
                    if (addressList.size()>0 && addressList.get(0).hasLatitude() && addressList.get(0).hasLongitude()) {
                        double latitude = addressList.get(0).getLatitude();
                        double longitude = addressList.get(0).getLongitude();

                        Log.i(TAG, "onSearchConfirmed: " + latitude + " " + longitude);

                        LatLng searchedLocation = new LatLng(latitude, longitude);
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(searchedLocation)
                                .draggable(true)
                                .title(addressList.get(0).getLocality())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.g_tree));
                        mMap.clear();
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchedLocation, 16.0f));
                    }else{
                        searchBar.setText("");
                        Toast.makeText(getApplicationContext(),"RESULT NOT FOUND",Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onButtonClicked(int buttonCode) {
                switch (buttonCode) {
                    case MaterialSearchBar.BUTTON_NAVIGATION:
                        resetMap();
                        break;
                    case MaterialSearchBar.BUTTON_BACK:
                        break;

                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                double lat = marker.getPosition().latitude;
                double lon = marker.getPosition().longitude;
                String FinalString = url + "lon=" + lon + "&lat=" + lat;
                Intent intent = new Intent(MapsActivity.this, GetSoilEnfo.class);
                intent.putExtra(Info.urlName, FinalString);
                startActivity(intent);
                return true;
            }
        });

    }

    private void resetMap () {
        Log.i(TAG, "resetMap: ");
    }


    public void onLocationChanged(Location location) {
        mMap.clear();
        currentLOC = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptions = new MarkerOptions()
                .position(currentLOC)
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLOC, 16.0f));

    }


    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    public void onProviderEnabled(String provider) {

    }


    public void onProviderDisabled(String provider) {

    }

}
