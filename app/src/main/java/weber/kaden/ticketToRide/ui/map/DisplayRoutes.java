package weber.kaden.ticketToRide.ui.map;

import java.util.ArrayList;
import java.util.List;

public class DisplayRoutes {
    private List<DisplayRoute> mRoutes;

    public DisplayRoutes(Locations locations) {
        mRoutes = new ArrayList<>();
        RouteData names = new RouteData();
        while (names.hasNext()){
            String city1 = names.next();
            String city2 = names.next();
            int length = Integer.parseInt(names.next());
            String color = names.next();
            if(color.contains(",")){
                mRoutes.add(new DisplayRoute(locations.getLocation(city1),
                        locations.getLocation(city2), length, getFirstColor(color)));
                DisplayRoute secondRoute = new DisplayRoute(locations.getLocation(city1),
                        locations.getLocation(city2), length, getSecondColor(color));
                secondRoute.setSecondRoute(true);
                mRoutes.add(secondRoute);
            }

        }

    }

//    //add routes TODO: make double routes look better
//    mRoutes = new DisplayRoutes(mLocations);
//        for ( DisplayRoute route : mRoutes.getRoutes()){
//        String routeColor = route.getColor();
//        //check for double route
//        if(routeColor.contains(",")){
//            createDoubleRoute(googleMap, route);
//            route.setColor(getFirstColor(routeColor));
//        }
//        //add regular route
//        addRoute(googleMap, route, route.getCity1().getCoords(), route.getCity2().getCoords());
//    }
//private void addRoute(GoogleMap googleMap, DisplayRoute route, LatLng loc1, LatLng loc2){
//    Polyline line = googleMap.addPolyline(new PolylineOptions().clickable(true)
//            .add(loc1, loc2)
//            .width(ROUTE_WIDTH)
//            .geodesic(true)
//            .pattern(unclaimedRoutePattern)
//            .color(getRouteColor(route.getColor())));
//    line.setTag(route.getLength());
//    mLineRouteMap.put(line, route);
//    mRouteLineMap.put(route, line);
//}
//
    private String getFirstColor(String routeColor) {
        return routeColor.substring(0, routeColor.indexOf(","));
    }
//
//    private void createDoubleRoute(GoogleMap googleMap, DisplayRoute route) {
//        route.setColor(getSecondColor(route.getColor()));
//        double slope = slope(route.getCity1(), route.getCity2());
//        LatLng newCity1 = offsetCoords(route.getCity1().getCoords(), slope);
//        LatLng newCity2 = offsetCoords(route.getCity2().getCoords(), slope);
//        addRoute(googleMap, route, newCity1, newCity2);
//
//    }
//
//    private LatLng offsetCoords(LatLng input, double slope){
//        double perpendicularSlope = -1/slope;
//        double theta = Math.atan(perpendicularSlope);
//        double deltaX = SECOND_ROUTE_OFFSET * Math.cos(theta);
//        double deltaY = SECOND_ROUTE_OFFSET * Math.sin(theta);
//        double newX = input.longitude + deltaX;
//        double newY = input.latitude + deltaY;
//        return new LatLng(newY, newX);
//    }
//    private double slope(Location location1, Location location2){
//        return (location2.getCoords().latitude - location1.getCoords().latitude)
//                / (location2.getCoords().longitude - location1.getCoords().longitude);
//    }
    private String getSecondColor(String routeColor) {
        return routeColor.substring(routeColor.indexOf(" ") + 1);
    }

    public List<DisplayRoute> getRoutes() {
        return mRoutes;
    }

    public DisplayRoute getRoute(String city1, String city2){
        for (DisplayRoute route : mRoutes){
            if(route.getCity1().getCityName().equals(city1) && route.getCity2().getCityName().equals(city2)){
                return route;
            }
        }
        return null;
    }

    public DisplayRoute getDoubleRoute(String city1, String city2) {
        boolean passedOriginalRoute = false;
        for (int i = 0; i < mRoutes.size(); i++){
            if (!passedOriginalRoute) {
                if (mRoutes.get(i).getCity1().getCityName().equals(city1) && mRoutes.get(i).getCity2().getCityName().equals(city2)) {
                    passedOriginalRoute = true;
                }
            } else {
                if (mRoutes.get(i).getCity1().getCityName().equals(city1) && mRoutes.get(i).getCity2().getCityName().equals(city2)) {
                    return mRoutes.get(i);
                }
            }
        }
        return null;
    }
}
