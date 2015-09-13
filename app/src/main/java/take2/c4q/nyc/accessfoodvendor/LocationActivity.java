package take2.c4q.nyc.accessfoodvendor;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Hoshiko on 9/7/15.
 */
public class LocationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnCameraChangeListener, OnMapReadyCallback {


    private LocationRequest mLocationRequest;
    public static Location mLastLocation;
    private Location mCurrentLocation;
    private boolean mRequestingLocationUpdates;
    private String mLastUpdateTime;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private LatLng mLatLng;
    public static final double DEFAULT_LATITUDE = 40.740981;
    public static final double DEFAULT_LONGITUDE = -73.899102;

    private String addressString;

    private TextView crossStOne;
    private TextView crossStTwo;

    String truckId;

    double lat;
    double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        truckId = intent.getStringExtra("truckId");

        setContentView(R.layout.activity_location);

        crossStOne = (TextView)findViewById(R.id.crossst_a);
//        crossStTwo = (TextView)findViewById(R.id.crossst_b);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();

        buildGoogleApiClient();
        createLocationRequest();
        mGoogleApiClient.connect();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Vendor");
        query.getInBackground(truckId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject currentVendor, ParseException e) {
                if (e == null) {
                    String registeredAddress = currentVendor.getString("address");

//                    if(registeredAddress.equals("")) {
//                        return;
//                    }else{
//
//                        String[] parts = registeredAddress.split(" & ");
//                        String address1 = parts[0];
//                        String address2 = parts[1];
//
//                        crossStOne.setText(address1);
//                        crossStTwo.setText(address2);
//                    }
                    if (registeredAddress != null && !registeredAddress.equals(""))
                        crossStOne.setText(registeredAddress);





                }
            }
        });




        Button mapSaveButton = (Button) findViewById(R.id.map_save_button);
        mapSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addressString = (String.valueOf(crossStOne.getText()) + " & "+ (String.valueOf(crossStTwo.getText())));
                addressString = String.valueOf(crossStOne.getText());
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Vendor");
                query.getInBackground(truckId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject currentVendor, ParseException e) {
                        if (e == null) {
                            currentVendor.put("address", addressString);
                            ParseGeoPoint point = new ParseGeoPoint(lat, lng);
                            currentVendor.put("location", point);
                            currentVendor.saveInBackground();

                            Toast.makeText(LocationActivity.this, "Location is saved!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });



        Button mapNextutton = (Button)findViewById(R.id.map_skip_button);
        mapNextutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                intent.putExtra("truckId", truckId);
                startActivity(intent);
                }
        });
    }



        @Override
        public void onMapReady(final GoogleMap googleMap) {
            googleMap.setMyLocationEnabled(true);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setOnCameraChangeListener(this);

            googleMap.getUiSettings().setZoomControlsEnabled(true);


            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {
                    googleMap.clear();
    //                MarkerOptions marker = new MarkerOptions().position(
    //                        new LatLng(point.latitude, point.longitude)).title("New Marker");


                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


                    googleMap.addMarker(new MarkerOptions().position(point).title("Your Location")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bluelogo_s)).draggable(true));


                    lat = point.latitude;
                    lng = point.longitude;
                    Toast.makeText(LocationActivity.this,
                            String.valueOf(point),
                            Toast.LENGTH_LONG).show();

                }
            });

            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                @Override
                public void onMarkerDrag(Marker arg0) {
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    LatLng latlng = marker.getPosition();

                    lat = latlng.latitude;
                    lng = latlng.longitude;
                    Toast.makeText(LocationActivity.this,
                            String.valueOf(latlng),
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onMarkerDragStart(Marker marker) {
                }
            });

        }

        protected void createLocationRequest() {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(15000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }

        LatLng defaultLatLng;
        @Override
        public void onConnected(Bundle bundle) {
            Log.i("MapsActivity", "Connected to Map!!!!!!!!");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Vendor");
            query.getInBackground(truckId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject currentVendor, ParseException e) {
                            if (e == null) {

                                ParseGeoPoint userLocation = currentVendor.getParseGeoPoint("location");


                                if(userLocation!=null){
                                    LatLng regLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

                                    mMap.addMarker(new MarkerOptions().position(regLatLng)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bluelogo_s)).draggable(true));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(regLatLng, 15));
                                }
                                else{
                                    defaultLatLng = new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLatLng));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                                    mMap.addMarker(new MarkerOptions().position(defaultLatLng)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bluelogo_s)).draggable(true));
                                }
                            }
                        }
                    });







            if (mRequestingLocationUpdates) {
                startLocationUpdates();

            }

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
        }


        protected void startLocationUpdates() {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mCurrentLocation = location;
                    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                }
            };
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, locationListener);
        }


        @Override
        public void onConnectionSuspended(int i) {
            mGoogleApiClient.connect();
        }

        @Override
        public void onCameraChange(CameraPosition cameraPosition) {

        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }

        @Override
        protected void onStop() {
            mGoogleApiClient.disconnect();
            super.onStop();
            Log.i("MapsActivity", "it stops!!!!!!!");
        }

        protected synchronized void buildGoogleApiClient() {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }
