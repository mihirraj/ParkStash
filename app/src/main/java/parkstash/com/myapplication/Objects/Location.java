package parkstash.com.myapplication.Objects;

/**
 * Created by mihir on 4/5/2018.
 */

public class Location {
    private double latitude,longitude;
    private String cost;

    public Location(double latitude, double longitude, String cost) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cost = cost;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
