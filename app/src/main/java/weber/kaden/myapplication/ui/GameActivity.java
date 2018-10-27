package weber.kaden.myapplication.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import weber.kaden.myapplication.R;

public class GameActivity extends AppCompatActivity implements OnMapReadyCallback, GameViewInterface {

    //map Constants
    private static final float DEFAULT_ZOOM = (float) 4.1;
    private static final float MIN_ZOOM = (float) 4.1;
    private static final float MAX_ZOOM = (float) 5.2;
    private static final double DEFAULT_VIEW_LAT = 40.5;
    private static final double DEFAULT_VIEW_LONG = -94;
    private static final double MAX_NORTH = 48;
    private static final double MAX_EAST = -80;
    private static final double MAX_SOUTH = 30;
    private static final double MAX_WEST = -110;


    Locations mLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocations = new Locations();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //set min and max zoom
        googleMap.setMinZoomPreference(MIN_ZOOM);
        googleMap.setMaxZoomPreference(MAX_ZOOM);
        // Move to initial view
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(DEFAULT_VIEW_LAT, DEFAULT_VIEW_LONG), DEFAULT_ZOOM));
        //constrain map to game area
        LatLngBounds bounds = new LatLngBounds(new LatLng(MAX_SOUTH, MAX_WEST), new LatLng(MAX_NORTH, MAX_EAST));
        googleMap.setLatLngBoundsForCameraTarget(bounds);

        //add city markers
        for (Location location : mLocations.getLocations()) {
            googleMap.addMarker(new MarkerOptions().position(location.getCoords()).title(location.getCity()));
        }

        //TODO: Add routes (Add route Dotting?)
        Polyline CalgaryWinnipeg = googleMap.addPolyline(new PolylineOptions().clickable(true)
            .add(mLocations.coords("Calgary"), mLocations.coords("Winnipeg")));
        CalgaryWinnipeg.setTag("6");
        CalgaryWinnipeg.setColor(Color.WHITE);


    }

}
