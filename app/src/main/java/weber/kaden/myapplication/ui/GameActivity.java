package weber.kaden.myapplication.ui;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;

import weber.kaden.myapplication.R;
import weber.kaden.myapplication.ui.map.DisplayRoute;
import weber.kaden.myapplication.ui.map.DisplayRoutes;
import weber.kaden.myapplication.ui.map.Location;
import weber.kaden.myapplication.ui.map.Locations;
import weber.kaden.myapplication.ui.turnmenu.ChooseDestinationCardsFragment;
import weber.kaden.myapplication.ui.turnmenu.ChooseTrainCardsFragment;
import weber.kaden.myapplication.ui.turnmenu.SeeOtherPlayersFragment;

public class GameActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GameViewInterface {

    //map Constants
    private static final float DEFAULT_ZOOM = (float) 4.0;
    private static final float MIN_ZOOM = (float) 4.0;
    private static final float MAX_ZOOM = (float) 5.2;
    private static final double DEFAULT_VIEW_LAT = 40;
    private static final double DEFAULT_VIEW_LONG = -95;
    private static final float DEFAULT_VIEW_BEARING = 9;
    private static final double MAX_NORTH = 46;
    private static final double MAX_EAST = -83;
    private static final double MAX_SOUTH = 34;
    private static final double MAX_WEST = -113;

    private static final float ROUTE_WIDTH = 22;
    private static final int ORANGE = Color.parseColor("#FFA500");
    private static final int PURPLE = Color.parseColor("#EE82EE");

    Locations mLocations;

    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Hide both the navigation bar and the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //add map
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocations = new Locations();

        //Nav Drawer
        mDrawerLayout = findViewById(R.id.activity_game_layout);

        NavigationView navigationView = findViewById(R.id.turn_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.turn_menu_destination_cards:
                        DialogFragment chooseDestinationCardsFragment = new ChooseDestinationCardsFragment();
                        chooseDestinationCardsFragment.show(getSupportFragmentManager(), "ChooseDestinationCardsFragment");
                        break;
                    case R.id.turn_menu_train_cards:
                        DialogFragment chooseTrainCardsFragment = new ChooseTrainCardsFragment();
                        chooseTrainCardsFragment.show(getSupportFragmentManager(), "ChooseTrainCardsFragment");
                        break;
                    case R.id.turn_menu_claim_route:
                        //Claim route is different since we're not opening up a dialog, we're using the map
                        break;
                    case R.id.turn_menu_see_other_players:
                        DialogFragment seeOtherPlayersFragment = new SeeOtherPlayersFragment();
                        seeOtherPlayersFragment.show(getSupportFragmentManager(), "ChooseTrainCardsFragment");
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //set min and max zoom
        googleMap.setMinZoomPreference(MIN_ZOOM);
        googleMap.setMaxZoomPreference(MAX_ZOOM);
        // Move to initial view
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(DEFAULT_VIEW_LAT, DEFAULT_VIEW_LONG), DEFAULT_ZOOM, 0, DEFAULT_VIEW_BEARING)));
        //constrain map to game area
        LatLngBounds bounds = new LatLngBounds(new LatLng(MAX_SOUTH, MAX_WEST), new LatLng(MAX_NORTH, MAX_EAST));
        googleMap.setLatLngBoundsForCameraTarget(bounds);
        //add city markers
        for (Location location : mLocations.getLocations()) {
            googleMap.addMarker(new MarkerOptions().position(location.getCoords()).title(location.getCity()));
        }
        //make route pattern
        List<PatternItem> pattern = Arrays.<PatternItem>asList(
                new Gap(10), new Dash(70) );
//        List<PatternItem> doublePattern = Arrays.<PatternItem>asList(
//                new Dash(70), new Gap(10) );

        //add routes TODO: Fix parallel routes
        DisplayRoutes routes = new DisplayRoutes(mLocations);
        for ( DisplayRoute route : routes.getRoutes()){
            Polyline line = googleMap.addPolyline(new PolylineOptions().clickable(true)
                .add(route.getCity1().getCoords(), route.getCity2().getCoords())
                .width(ROUTE_WIDTH)
                .geodesic(true)
                .pattern(pattern)
                .color(getRouteColor(route.getColor())));
            line.setTag(route.getLength());
        }
        //make routes clickable
        googleMap.setOnPolylineClickListener(this);

    }

    private int getRouteColor(String color) {
        switch (color){
            case "White":
                return Color.WHITE;
            case "Black":
                return Color.BLACK;
            case "Blue":
                return Color.BLUE;
            case "Green":
                return Color.GREEN;
            case "Red":
                return Color.RED;
            case "Purple":
                return PURPLE;
            case "Orange":
                return ORANGE;
            case "Yellow":
                return Color.YELLOW;
            case "Gray":
                return Color.GRAY;
        }
        return Color.GRAY;
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        //check if the route can be claimed
        //otherwise display route length
    }

}
