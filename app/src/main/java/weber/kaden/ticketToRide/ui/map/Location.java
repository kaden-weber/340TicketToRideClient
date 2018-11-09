package weber.kaden.ticketToRide.ui.map;

import com.google.android.gms.maps.model.LatLng;

public class Location {

    private LatLng coords;
    private String city;

    public Location(LatLng coords, String city){
        this.coords = coords;
        this.city = city;
    }

    public LatLng getCoords() {
        return coords;
    }

    public String getCity() {
        return city;
    }
}
