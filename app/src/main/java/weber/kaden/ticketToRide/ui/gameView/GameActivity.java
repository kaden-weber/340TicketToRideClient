package weber.kaden.ticketToRide.ui.gameView;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.view.GravityCompat;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weber.kaden.common.model.Model;
import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.PlayerColors;
import weber.kaden.common.model.Route;
import weber.kaden.common.model.TrainCard;

import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;
import weber.kaden.ticketToRide.serverProxy.Poller;
import weber.kaden.ticketToRide.ui.chat.ChatFragment;
import weber.kaden.ticketToRide.ui.gameOver.GameOverActivity;
import weber.kaden.ticketToRide.ui.turnmenu.ClaimRouteFragment;
import weber.kaden.ticketToRide.ui.map.DisplayRoute;
import weber.kaden.ticketToRide.ui.map.DisplayRoutes;
import weber.kaden.ticketToRide.ui.map.Location;
import weber.kaden.ticketToRide.ui.map.Locations;
import weber.kaden.ticketToRide.ui.tools.ConstantsDisplayConverter;
import weber.kaden.ticketToRide.ui.turnmenu.ChooseDestinationCardsFragment;
import weber.kaden.ticketToRide.ui.turnmenu.ChooseDestinationCardsPresenter;
import weber.kaden.ticketToRide.ui.turnmenu.ChooseTrainCardsFragment;
import weber.kaden.ticketToRide.ui.turnmenu.ChooseTrainCardsPresenter;
import weber.kaden.ticketToRide.ui.turnmenu.GameHistoryFragment;
import weber.kaden.ticketToRide.ui.turnmenu.SeeOtherPlayersFragment;

public class GameActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GameViewInterface {

    GameActivity instance = this;
    PointsAdapter pointsAdapter;
    List<DestinationCard> destCards = new ArrayList<>();
    List<TrainCard> trainCards = new ArrayList<>();
    HashMap<TrainCardType, Integer> playerTrainCards = new HashMap<>();
    List<Integer> points = new ArrayList<>();
    ClientFacade client = new ClientFacade();
    //map Constants
    private static final float DEFAULT_ZOOM = (float) 4.0;
    private static final float MIN_ZOOM = (float) 4.0;
    private static final float MAX_ZOOM = (float) 6.0;
    private static final double DEFAULT_VIEW_LAT = 40;
    private static final double DEFAULT_VIEW_LONG = -98;
    private static final float DEFAULT_VIEW_BEARING = 9;
    private static final double MAX_NORTH = 50; // 46
    private static final double MAX_EAST = -72; // -80
    private static final double MAX_SOUTH = 28; // 34
    private static final double MAX_WEST = -122; // -115

    private static final float ROUTE_WIDTH = 22;
    private static final double SECOND_ROUTE_OFFSET = 0.6;

    private ConstantsDisplayConverter mDisplayConverter;
    private Locations mLocations;
    private DisplayRoutes mRoutes;
    private Map mLineRouteMap;
    private Map mRouteLineMap;
    private List<PatternItem> unclaimedRoutePattern;
    private List<PatternItem> claimedRoutePattern;
    private GamePresenter mPresenter = new GamePresenter(this, new ClientFacade());
    private DrawerLayout mDrawerLayout;
    private boolean mClaimingRouteFlag;
    private TextView claimRoutePrompt;
    private Button showDestCards;
    private Button showTrainCards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //initialize all train card types
        for (TrainCardType type : TrainCardType.values()){
            playerTrainCards.put(type, 0);
        }
        showDestCards = findViewById(R.id.showDestCards);
        showDestCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMyDestCardsFragment fragment = new ShowMyDestCardsFragment();
                Bundle showDestinationCardsArgs = new Bundle();
                showDestinationCardsArgs.putSerializable("cards", (Serializable) destCards);
                fragment.setArguments(showDestinationCardsArgs);
                fragment.show(getSupportFragmentManager(), "ChooseDestinationCardsFragment");
            }
        });
        // set up the RecyclerView
        showTrainCards = findViewById(R.id.showTrainCards);
        showTrainCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMyTrainCardsFragment fragment = new ShowMyTrainCardsFragment();
                Bundle showDestinationCardsArgs = new Bundle();
                showDestinationCardsArgs.putSerializable("cards", (Serializable) playerTrainCards);
                fragment.setArguments(showDestinationCardsArgs);
                fragment.show(getSupportFragmentManager(), "ShowTrainCards");
            }
        });
        // set up the RecyclerView
        RecyclerView recyclerPoints = findViewById(R.id.myInfoRecyclePoints);
        recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
        pointsAdapter = new PointsAdapter(this, points);
        recyclerPoints.setAdapter(pointsAdapter);
        // end RecyclerView
        // Hide both the navigation bar and the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        claimRoutePrompt = findViewById(R.id.claim_route_prompt);
        if (mClaimingRouteFlag) claimRoutePrompt.setVisibility(View.VISIBLE);
        else claimRoutePrompt.setVisibility(View.GONE);

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
                        if (client.getCurrentTurnPlayer().equals(client.getCurrentUserID())){
                            DestinationCardsTask destinationCardsTask = new DestinationCardsTask();
                            destinationCardsTask.execute();
                        } else{
                            Toast.makeText(instance, "Wait for your turn!", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.turn_menu_train_cards:
                        if (client.getCurrentTurnPlayer().equals(client.getCurrentUserID())) {
                            ChooseTrainCardsFragment chooseTrainCardsFragment = new ChooseTrainCardsFragment();
                            ChooseTrainCardsPresenter trainCardsPresenter = new ChooseTrainCardsPresenter(chooseTrainCardsFragment, new ClientFacade());
                            trainCardsPresenter.removeAsObserver();

                            Bundle chooseTrainCardArgs = new Bundle();
                            chooseTrainCardArgs.putSerializable("faceUpCards", (Serializable) trainCardsPresenter.getFaceUpTrainCards());
                            chooseTrainCardsFragment.setArguments(chooseTrainCardArgs);

                            chooseTrainCardsFragment.show(getSupportFragmentManager(), "ChooseTrainCardsFragment");
                        } else{
                            Toast.makeText(instance, "Wait for your turn!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.turn_menu_claim_route:
                        if (client.getCurrentTurnPlayer().equals(client.getCurrentUserID())) {
                            //Claim route is different since we're not opening up a dialog, we're using the map
                            mClaimingRouteFlag = true;
                            claimRoutePrompt.setVisibility(View.VISIBLE);

                        } else{
                            Toast.makeText(instance, "Wait for your turn!", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.turn_menu_see_other_players:
                        DialogFragment seeOtherPlayersFragment = new SeeOtherPlayersFragment();
                        seeOtherPlayersFragment.show(getSupportFragmentManager(), "ChooseTrainCardsFragment");
                        break;
                    case R.id.turn_menu_game_history:
                        DialogFragment gameHistoryFragment = new GameHistoryFragment();
                        gameHistoryFragment.show(getSupportFragmentManager(), "GameHistoryFragment");
                        break;
                    case R.id.turn_menu_chat:
                        DialogFragment chatFragment = new ChatFragment();
                        ((ChatFragment) chatFragment).setMessages(Model.getInstance().getCurrentGame().getChat());
                        chatFragment.show(getSupportFragmentManager(), "ChatFragment");
                }

                return true;
            }
        });
        //make unclaimed route pattern
        unclaimedRoutePattern = Arrays.asList(
                new Gap(10), new Dash(70) );
        //make claimed route pattern
        claimedRoutePattern = Arrays.<PatternItem>asList(
                 new Dash(100) );

        mClaimingRouteFlag = false;

        mDisplayConverter = new ConstantsDisplayConverter();

        //init route list
        mLineRouteMap = new HashMap();
        mRouteLineMap = new HashMap();
    }

    @Override
    public void onResume(){
        super.onResume();
        if (mClaimingRouteFlag) claimRoutePrompt.setVisibility(View.VISIBLE);
        else claimRoutePrompt.setVisibility(View.GONE);
    }

    public void setNewValues(List<DestinationCard> nDestCards, List<TrainCard> nTrainCards, List<Integer> nPoints){
        destCards.clear();
        destCards.addAll(nDestCards);
        trainCards.clear();
        trainCards.addAll(nTrainCards);
        // reset all counts to zero
        for( TrainCardType type : TrainCardType.values()){
            playerTrainCards.put(type, 0);
        }
        for(TrainCard card : nTrainCards){
            int current = playerTrainCards.get(card.getType());
            current++;
            playerTrainCards.put(card.getType(), current);
        }
        points.clear();
        points.addAll(nPoints);
        pointsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateTextView(String player) {
        TextView textView = (TextView) findViewById(R.id.currentPlayer);
        textView.setText(player);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //set map style
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
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
            googleMap.addMarker(new MarkerOptions().position(location.getCoords())
                    .title(location.getCityName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.steamtrain_dark)));
        }
        //add routes TODO: make double routes look better
        mRoutes = new DisplayRoutes(mLocations);
        for ( DisplayRoute route : mRoutes.getRoutes()){
            //check for double route
            if(route.isSecondRoute()){
                createDoubleRoute(googleMap, route);
            } else {
                //add regular route
                addRoute(googleMap, route, route.getCity1().getCoords(), route.getCity2().getCoords());
            }
        }
        //make routes clickable
        googleMap.setOnPolylineClickListener(this);

    }
    private void addRoute(GoogleMap googleMap, DisplayRoute route, LatLng loc1, LatLng loc2){
        Polyline line = googleMap.addPolyline(new PolylineOptions().clickable(true)
                .add(loc1, loc2)
                .width(ROUTE_WIDTH)
                .geodesic(true)
                .pattern(unclaimedRoutePattern)
                .color(getRouteColor(route.getColor())));
        line.setTag(route.getLength());
        mLineRouteMap.put(line, route);
        mRouteLineMap.put(route, line);
    }
    private void createDoubleRoute(GoogleMap googleMap, DisplayRoute route) {
        double slope = slope(route.getCity1(), route.getCity2());
        LatLng newCity1 = offsetCoords(route.getCity1().getCoords(), slope);
        LatLng newCity2 = offsetCoords(route.getCity2().getCoords(), slope);
        addRoute(googleMap, route, newCity1, newCity2);
    }
    private LatLng offsetCoords(LatLng input, double slope){
        double perpendicularSlope = -1/slope;
        double theta = Math.atan(perpendicularSlope);
        double deltaX = SECOND_ROUTE_OFFSET * Math.cos(theta);
        double deltaY = SECOND_ROUTE_OFFSET * Math.sin(theta);
        double newX = input.longitude + deltaX;
        double newY = input.latitude + deltaY;
        return new LatLng(newY, newX);
    }
    private double slope(Location location1, Location location2){
        return (location2.getCoords().latitude - location1.getCoords().latitude)
               / (location2.getCoords().longitude - location1.getCoords().longitude);
    }
    private int getRouteColor(String color) {
        switch (color){
            case "White":
                return getResources().getColor(R.color.Passenger);
            case "Black":
                return getResources().getColor(R.color.Hopper);
            case "Blue":
                return getResources().getColor(R.color.Tanker);
            case "Green":
                return getResources().getColor(R.color.Caboose);
            case "Red":
                return getResources().getColor(R.color.Coal);
            case "Purple":
                return getResources().getColor(R.color.Box);
            case "Orange":
                return getResources().getColor(R.color.Freight);
            case "Yellow":
                return getResources().getColor(R.color.Reefer);
            case "Gray":
                return getResources().getColor(R.color.Gray);
        }
        return R.color.Gray;
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        //check if the route can be claimed
        //otherwise display route length
        String city1 = getCity1(polyline);
        String city2 = getCity2(polyline);

        if(mPresenter.routeClaimable((int)polyline.getTag(), getRouteType(polyline))) {
            if(mClaimingRouteFlag){
                DialogFragment fragment = new ClaimRouteFragment();
                ((ClaimRouteFragment) fragment).setParams(city1, city2, getRouteType(polyline),
                        (Integer) polyline.getTag(), ((DisplayRoute) mLineRouteMap.get(polyline)).isSecondRoute());
                fragment.show(getSupportFragmentManager(), "ClaimRouteFragment");
                mClaimingRouteFlag = false;
                claimRoutePrompt.setVisibility(View.GONE);
            } else {
                displayRouteCost(polyline);
            }
        } else {
            displayRouteCost(polyline);
        }
    }

    private void displayRouteCost(Polyline polyline) {
        Toast toast;
        if( getRouteType(polyline) == TrainCardType.LOCOMOTIVE){
            toast = Toast.makeText(this, "Cost: " + String.valueOf(polyline.getTag()) +
                    " " + "matching" + " cards.", Toast.LENGTH_SHORT);
        } else {
            toast = Toast.makeText(this, "Cost: " + String.valueOf(polyline.getTag()) +
                    " " + getRouteType(polyline) + " cards.", Toast.LENGTH_SHORT);
        }
        View view = toast.getView();
        DisplayRoute route = (DisplayRoute) mLineRouteMap.get(polyline);
        String color = route.getColor();
        int colorVal = getRouteColor(color);
        view.getBackground().setColorFilter(colorVal, PorterDuff.Mode.SRC_IN);

        if (colorVal == getResources().getColor(R.color.Hopper)
                || colorVal == getResources().getColor(R.color.Coal)
                || colorVal == getResources().getColor(R.color.Gray)) {
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.Passenger));
        }
        toast.show();
    }

    private TrainCardType getRouteType(Polyline polyline){
        DisplayRoute route = (DisplayRoute) mLineRouteMap.get(polyline);
        String color = route.getColor();
        switch (color){
            case "White":
                return TrainCardType.PASSENGER;
            case "Black":
                return TrainCardType.HOPPER;
            case "Blue":
                return TrainCardType.TANKER;
            case "Green":
                return TrainCardType.CABOOSE;
            case "Red":
                return TrainCardType.COAL;
            case "Purple":
                return TrainCardType.BOX;
            case "Orange":
                return TrainCardType.FREIGHT;
            case "Yellow":
                return TrainCardType.REEFER;
            case "Gray":
                return TrainCardType.GRAY;
                default:
                    return TrainCardType.GRAY;
        }
    }
    //refactor
    private String getCity1(Polyline polyline){
        DisplayRoute route = (DisplayRoute) mLineRouteMap.get(polyline);
        return route.getCity1().getCityName();
    }
    private String getCity2(Polyline polyline){
        DisplayRoute route = (DisplayRoute) mLineRouteMap.get(polyline);
        return route.getCity2().getCityName();
    }

    @Override
    public void updateClaimedRoutes(PlayerColors color, List<Route> routes, boolean disableSecond) {
        for (Route route : routes){
            DisplayRoute displayRoute = mRoutes.getRoute(
                    mDisplayConverter.getUIStringFor(route.getCity1()),
                    mDisplayConverter.getUIStringFor(route.getCity2()), route.isSecondRoute());
            Polyline line = (Polyline) mRouteLineMap.get(displayRoute);
            line.setPattern(claimedRoutePattern);
            line.setClickable(false);
            line.setColor(getPlayerColor(color));
            // Also disable second line here if player has claimed this route or there is less than 3 players
            if(disableSecond) {
                DisplayRoute doubleRoute = mRoutes.getRoute(
                        displayRoute.getCity1().getCityName(), displayRoute.getCity2().getCityName(), !route.isSecondRoute());
                if (doubleRoute != null){
                    Polyline doubleLine = (Polyline) mRouteLineMap.get(doubleRoute);
                    doubleLine.setClickable(false);
                }
            }

        }
    }
    private int getPlayerColor(PlayerColors color){
        switch (color){
            case PLAYER_RED:
                return getResources().getColor(R.color.PLAYER_RED);
            case PLAYER_BLUE:
                return getResources().getColor(R.color.PLAYER_BLUE);
            case PLAYER_BLACK:
                return getResources().getColor(R.color.PLAYER_BLACK);
            case PLAYER_GREEN:
                return getResources().getColor(R.color.PLAYER_GREEN);
            case PLAYER_YELLOW:
                return getResources().getColor(R.color.PLAYER_YELLOW);
                default:
                    return getResources().getColor(R.color.Gray);
        }
    }

    public void finishGame() {
	    Intent intent = new Intent(instance, GameOverActivity.class);
	    startActivity(intent);
        Poller.getInstance(this).stopPolling();
    }


    public class DestinationCardsTask extends AsyncTask<Void, Void, Boolean> {
        private String errorString = "";
        private List<DestinationCard> destinationCards;
        private ChooseDestinationCardsFragment fragment = new ChooseDestinationCardsFragment();

        DestinationCardsTask() {
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            ClientFacade clientFacade = new ClientFacade();
            ChooseDestinationCardsPresenter gameListPresenter = new ChooseDestinationCardsPresenter(fragment, clientFacade);
            try {
                destinationCards = gameListPresenter.getDrawableDestinationCards();
            } catch (Exception e) {
                errorString = e.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                Bundle chooseDestinationCardsArgs = new Bundle();
                chooseDestinationCardsArgs.putSerializable("cards", (Serializable) destinationCards);
                fragment.setArguments(chooseDestinationCardsArgs);
                fragment.show(getSupportFragmentManager(), "ChooseDestinationCardsFragment");
            } else {
                Toast.makeText(instance, errorString, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }


}
