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

    private String getFirstColor(String routeColor) {
        return routeColor.substring(0, routeColor.indexOf(","));
    }

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
