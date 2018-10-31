package weber.kaden.myapplication.ui.map;

import java.util.ArrayList;
import java.util.List;

public class DisplayRoutes {
    private List<DisplayRoute> mRoutes;

    public DisplayRoutes(Locations locations) {
        mRoutes = new ArrayList<>();
        RouteData names = new RouteData();
        while (names.hasNext()){
            mRoutes.add(new DisplayRoute(locations.getLocation(names.next()),
                    locations.getLocation(names.next()), Integer.parseInt(names.next()), names.next()));
        }

    }


    public List<DisplayRoute> getRoutes() {
        return mRoutes;
    }
}
