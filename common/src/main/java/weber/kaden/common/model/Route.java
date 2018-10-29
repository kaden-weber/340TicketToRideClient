package weber.kaden.common.model;

import java.util.Objects;

public class Route {
    private City city1;
    private City city2;
    private Integer score;
    private Integer cost;

    public Route(City city1, City city2, Integer score, Integer cost) {
        this.city1 = city1;
        this.city2 = city2;
        this.score = score;
        this.cost = cost;
    }

    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return city1 == route.city1 &&
                city2 == route.city2 &&
                Objects.equals(score, route.score) &&
                Objects.equals(cost, route.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city1, city2, score, cost);
    }
}
