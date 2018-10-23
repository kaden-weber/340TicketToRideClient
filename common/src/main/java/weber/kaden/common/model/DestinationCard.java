package weber.kaden.common.model;

public class DestinationCard {
    private City startCity;
    private City endCity;
    private Integer points;

    public DestinationCard(City startCity, City endCity, Integer points) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.points = points;
    }

    public City getStartCity() {
        return startCity;
    }

    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }

    public City getEndCity() {
        return endCity;
    }

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
