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

public class GameActivity extends AppCompatActivity implements OnMapReadyCallback, GameViewInterface {

    //map Constants
    //consider using a resource file
    //define minimum zoom
    private static final float DEFAULT_ZOOM = (float) 4.1;
    private static final double DEFAULT_VIEW_LAT = 40;
    private static final double DEFAULT_VIEW_LONG = -93;

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
                new LatLng(33.7490,-84.3880)).title("atlanta"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(42.3601,-71.0589)).title("boston"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(51.0486,-114.0708)).title("calgary"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(40.7128,-74.0060)).title("charleston"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(41.87,-87.62)).title("chicago"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(32.7767,-96.7970)).title("dallas"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(39.7392,-104.9903)).title("denver"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(46.7867,-92.1005)).title("duluth"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(31.7619,-106.4850)).title("el paso"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(46.5891,-112.0391)).title("helena"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(29.7604,-95.3698)).title("houston"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(39.0997,-94.5786)).title("kansas city"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(36.1699,-115.1398)).title("las vegas"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(34.7465,-92.2896)).title("little rock"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(34.0522,-118.2437)).title("los angeles"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(25.7617,-80.1918)).title("miami"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(45.5017,-73.5673)).title("montreal"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(36.1627,-86.7816)).title("nashville"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(29.9511,-90.0715)).title("new orleans"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(40.7128,-74.0060)).title("new york"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(35.4676,-97.5164)).title("oklahoma city"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(41.2565,-95.9345)).title("omaha"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(33.4484,-112.0740)).title("phoenix"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(40.4406,-79.9959)).title("pittsburgh"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(45.5122,-122.6587)).title("portland"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(35.7796,-78.6382)).title("raleigh"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(38.6270,-90.1994)).title("saint louis"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(40.7608,-111.8910)).title("salt lake city"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(37.7749,-122.4194)).title("san francisco"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(35.6870,-105.9378)).title("santa fe"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(46.4977,-84.3476)).title("sault st. marie"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(47.6062,-122.3321)).title("seattle"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(43.6532,-79.3832)).title("toronto"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(49.2827,-123.1207)).title("vancouver"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(38.9072,-77.0369)).title("washington"));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(49.8951,-97.1384)).title("winnipeg"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(DEFAULT_VIEW_LAT, DEFAULT_VIEW_LONG), DEFAULT_ZOOM));

    }
}
