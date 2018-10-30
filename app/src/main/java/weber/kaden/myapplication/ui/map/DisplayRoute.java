package weber.kaden.myapplication.ui.map;


public class DisplayRoute {
    private Location city1;
    private Location city2;
    private int length;
    private String color;

    public DisplayRoute(Location city1, Location city2, int length, String color) {
        this.city1 = city1;
        this.city2 = city2;
        this.length = length;
        this.color = color;
    }

    public Location getCity1() {
        return city1;
    }

    public Location getCity2() {
        return city2;
    }

    public int getLength() {
        return length;
    }

    public String getColor() {
        return color;
    }
}
