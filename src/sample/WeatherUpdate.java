package sample;

import javafx.collections.ObservableList;

/**
 * Class WeatherUpdate represents updates of the Weather. Implements Observer interface.
 *
 * @author Sara Strzałka
 * @version 1.0
 */

public class WeatherUpdate implements Observer {

    /**
     * Represents list of gathered Weather objects.
     */
    private ObservableList<Weather> data;

    /**
     * Creates an object with given parameter.
     *
     * @param data list of gathered Weather objects.
     */
    public WeatherUpdate(ObservableList<Weather> data) {
        this.data = data;
    }

    /**
     * Adds Weather object to the list and prints Weather parameters.
     *
     * @param weather Updated Weather data.
     */
    @Override
    public void updateData(Weather weather) {
        System.out.println(weather);
        data.add(weather);
    }
}
