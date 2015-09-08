package take2.c4q.nyc.accessfoodvendor;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
    public static final double DEFAULT_LATITUDE = 40.740981;
    public static final double DEFAULT_LONGITUDE = -73.899102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();

        buildGoogleApiClient();
        createLocationRequest();
        mGoogleApiClient.connect();

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
//                googleMap.clear();
//                MarkerOptions marker = new MarkerOptions().position(
//                        new LatLng(point.latitude, point.longitude)).title("New Marker");


                googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                googleMap.addMarker(new MarkerOptions().position(point).title("Your Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.bluelogo_s)).draggable(true));
            }
        });

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {


            @Override
            public void onMarkerDrag(Marker arg0) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

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

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("MapsActivity", "Connected to Map!!!!!!!!");
        LatLng defaultLatLng = new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
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
