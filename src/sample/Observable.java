package sample;

/**
 * Observable methods
 *
 * @author Sara Strzałka
 * @version 1.0
 */

public interface Observable {

    /**
     * Adds single observer to the list.
     * @param o Observer
     */
    void addObserver(Observer o);

    /**
     * Removes single onserver from the list.
     * @param o Observer
     */
    void removeObserver(Observer o);

    /**
     * Sends data to observers from the list.
     * @param weather Current weather.
     */
    void updateObservers(Weather weather);


}
