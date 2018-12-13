package sample;

/**
 * Observer methods
 *
 * @author Sara Strzałka
 * @version 1.0
 */

public interface Observer {

    /**
     * Gets updated data from Observable.
     * @param weather Updated Weather data.
     */
    void updateData(Weather weather);

}
