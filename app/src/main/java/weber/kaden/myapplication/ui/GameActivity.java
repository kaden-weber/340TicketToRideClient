package weber.kaden.myapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import weber.kaden.myapplication.R;

public class GameActivity extends AppCompatActivity implements OnMapReadyCallback {

    //map Constants
    //consider using a resource file
    private static final float DEFAULT_ZOOM = (float) 4.2;
    private static final double DEFAULT_VIEW_LAT = 37;
    private static final double DEFAULT_VIEW_LONG = -92;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(41.87,-87.62)).title("chicago"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(DEFAULT_VIEW_LAT, DEFAULT_VIEW_LONG), DEFAULT_ZOOM));

    }
}
