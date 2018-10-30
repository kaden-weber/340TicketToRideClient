package weber.kaden.myapplication.ui.map;

import java.util.ArrayList;
import java.util.List;

public class DisplayRoutes {
//    private List<List<Location>> endpointsList;
    private List<DisplayRoute> mRoutes;

    public DisplayRoutes(Locations locations) {
//        endpointsList = new ArrayList<>();
        mRoutes = new ArrayList<>();
        RouteData names = new RouteData();
        while (names.hasNext()){
            mRoutes.add(new DisplayRoute(locations.getLocation(names.next()),
                    locations.getLocation(names.next()), 3, names.next()));
//            List<Location> endpoints= new ArrayList<>();
//            endpoints.add(locations.getLocation(names.next()));
//            endpoints.add(locations.getLocation(names.next()));
//            endpointsList.add(endpoints);
        }

    }

//    public List<List<Location>> getRoutes() {
//        return endpointsList;
//    }


    public List<DisplayRoute> getRoutes() {
        return mRoutes;
    }
}
