package weber.kaden.ticketToRide.ui.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Locations {
    private List<Location> locations;

    public Locations(){
        //consider using a resource file?
        locations = new ArrayList<>();
        locations.add(new Location(new LatLng(33.7490,-84.3880), "Atlanta"));
        locations.add(new Location(new LatLng(42.3601,-71.0589), "Boston"));
        locations.add(new Location(new LatLng(51.0486,-114.0708), "Calgary"));
        locations.add(new Location(new LatLng(32.7846,-79.9409), "Charleston"));
        locations.add(new Location(new LatLng(41.87,-87.62), "Chicago"));
        locations.add(new Location(new LatLng(32.7767,-96.7970), "Dallas"));
        locations.add(new Location(new LatLng(39.7392,-104.9903), "Denver"));
        locations.add(new Location(new LatLng(46.7867,-92.1005), "Duluth"));
        locations.add(new Location(new LatLng(31.7619,-106.4850), "El Paso"));
        locations.add(new Location(new LatLng(46.5891,-112.0391), "Helena"));
        locations.add(new Location(new LatLng(29.7604,-95.3698), "Houston"));
        locations.add(new Location(new LatLng(39.0997,-94.5786), "Kansas City"));
        locations.add(new Location(new LatLng(36.1699,-115.1398), "Las Vegas"));
        locations.add(new Location(new LatLng(34.7465,-92.2896), "Little Rock"));
        locations.add(new Location(new LatLng(34.0522,-118.2437), "Los Angeles"));
        locations.add(new Location(new LatLng(25.7617,-80.1918), "Miami"));
        locations.add(new Location(new LatLng(45.5017,-73.5673), "Montreal"));
        locations.add(new Location(new LatLng(36.1627,-86.7816), "Nashville"));
        locations.add(new Location(new LatLng(29.9511,-90.0715), "New Orleans"));
        locations.add(new Location(new LatLng(40.7128,-74.0060), "New York"));
        locations.add(new Location(new LatLng(35.4676,-97.5164), "Oklahoma City"));
        locations.add(new Location(new LatLng(41.2565,-95.9345), "Omaha"));
        locations.add(new Location(new LatLng(33.4484,-112.0740), "Phoenix"));
        locations.add(new Location(new LatLng(40.4406,-79.9959), "Pittsburgh"));
        locations.add(new Location(new LatLng(45.5122,-122.6587), "Portland"));
        locations.add(new Location(new LatLng(35.7796,-78.6382), "Raleigh"));
        locations.add(new Location(new LatLng(38.6270,-90.1994), "Saint Louis"));
        locations.add(new Location(new LatLng(40.7608,-111.8910), "Salt Lake City"));
        locations.add(new Location(new LatLng(37.7749,-122.4194), "San Francisco"));
        locations.add(new Location(new LatLng(35.6870,-105.9378), "Santa Fe"));
        locations.add(new Location(new LatLng(46.4977,-84.3476), "Sault St. Marie"));
        locations.add(new Location(new LatLng(47.6062,-122.3321), "Seattle"));
        locations.add(new Location(new LatLng(43.6532,-79.3832), "Toronto"));
        locations.add(new Location(new LatLng(49.2827,-123.1207), "Vancouver"));
        locations.add(new Location(new LatLng(38.9072,-77.0369), "Washington"));
        locations.add(new Location(new LatLng(49.8951,-97.1384), "Winnipeg"));

    }

    public LatLng coords(String city){
        for (Location location: locations) {
            if (location.getCityName().equals(city)){
                return location.getCoords();
            }
        }
        return new LatLng(0,0);
    }

    Location getLocation(String city){
        for(Location location: locations){
            if(location.getCityName().equals(city)){
                return location;
            }
        }
        return new Location(new LatLng(0,0), "null");
    }

    public List<Location> getLocations(){
        return locations;
    }


}
