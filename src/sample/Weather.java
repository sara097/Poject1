package sample;

import java.time.LocalTime;

/**
 * Class Weather represents weather parameters in time.
 *
 * @author Sara Strzałka
 * @version 1.0
 */

public class Weather {

    /**
     * Represents number of measurements.
     */
    private int n;
    /**
     * Represents time od measure.
     */
    private LocalTime time;
    /**
     * Represents temperature.
     */
    private double temp;
    /**
     * Represents pressure
     */
    private double pressure;
    /**
     * Represents humidity.
     */
    private double humidity;


    /**
     * Returns temperature.
     * @return Temperature.
     */
    public double getTemp() {
        return temp;
    }

    /**
     * Sets temperature.
     * @param temp Temperature.
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     * Returns pressure.
     * @return pressure.
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * Sets pressure.
     * @param pressure Pressure.
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * Returns humidity.
     * @return Humidity.
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Sets humidity.
     * @param humidity Humidity.
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * Return number of measurements.
     * @return number of measurements
     */
    public int getN() {
        return n;
    }

    /**
     * Sets number of measurements.
     * @param n number of measurements
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * Sets time.
     * @param time Time.
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Returns time.
     * @return Time.
     */
    public LocalTime getTime(){return time;};

    /**
     * Returns String weather parameters.
     * @return String weather parameters.
     */
    @Override
    public String toString() {
        return n + " - " + temp + " - " + pressure + " - " + humidity;
    }

}
