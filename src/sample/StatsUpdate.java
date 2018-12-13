package sample;

import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Class StatsUpdate represents class that calculate stats of the weather parameters and display them. Implements Observer and Display interfaces.
 *
 * @author Sara Strzałka
 * @version 1.0
 */

public class StatsUpdate implements Observer, Display {

    /**
     * Represents list of temperatures.
     */
    private ArrayList<Double> temps = new ArrayList<>();
    /**
     * Represents list of humidity.
     */
    private ArrayList<Double> hum = new ArrayList<>();
    /**
     * Represents list of pressures.
     */
    private ArrayList<Double> press = new ArrayList<>();
    /**
     * Represents TextField to display number of measurements.
     */
    private TextField NumberTxt;
    /**
     * Represents TextField to display minimal value of temperature
     */
    private TextField TMinTxt;
    /**
     * Represents TextField to display minimal value of humidity.
     */
    private TextField HMinTxt;
    /**
     * Represents TextField to display minimal value of pressure.
     */
    private TextField PMinTxt;
    /**
     * Represents TextField to display maximal value of temperature.
     */
    private TextField TMaxTxt;
    /**
     * Represents TextField to display maximal value of humidity.
     */
    private TextField HMaxTxt;
    /**
     * Represents TextField to display maximal value of pressure
     */
    private TextField PMaxTxt;
    /**
     * Represents TextField to display standatrd deviation of temperature.
     */
    private TextField TStdTxt;
    /**
     * Represents TextField to display standatrd deviation of humidity.
     */
    private TextField HStdTxt;
    /**
     * Represents TextField to display standatrd deviation of pressure.
     */
    private TextField PStdTxt;
    /**
     * Represents table of double values of parameters stats.
     */
    private double[] data = new double[10];

    /**
     * Creates object with given parameters.
     *
     * @param numberTxt TextField with number of measurements.
     * @param TMinTxt TextField with minimal values of temperature.
     * @param HMinTxt TextField with minimal values of humidity.
     * @param PMinTxt TextField with minimal values of pressure.
     * @param TMaxTxt TextField with maximal values of temperature.
     * @param HMaxTxt TextField with maximal values of humidity.
     * @param PMaxTxt TextField with maximal values of pressure.
     * @param TStdTxt TextField with standard deviation of temperature.
     * @param HStdTxt TextField with standard deviation of humidity.
     * @param PStdTxt TextField with standard deviation of pressure.
     */
    public StatsUpdate(TextField numberTxt, TextField TMinTxt, TextField HMinTxt, TextField PMinTxt, TextField TMaxTxt, TextField HMaxTxt, TextField PMaxTxt, TextField TStdTxt, TextField HStdTxt, TextField PStdTxt) {
        this.NumberTxt = numberTxt;
        this.TMinTxt = TMinTxt;
        this.HMinTxt = HMinTxt;
        this.PMinTxt = PMinTxt;
        this.TMaxTxt = TMaxTxt;
        this.HMaxTxt = HMaxTxt;
        this.PMaxTxt = PMaxTxt;
        this.TStdTxt = TStdTxt;
        this.HStdTxt = HStdTxt;
        this.PStdTxt = PStdTxt;
    }

    /**
     * Assigns elements of arraylist data to TextFields.
     */
    @Override
    public void display() {

        Platform.runLater(() -> {
            calculate();
            NumberTxt.setText(String.valueOf(data[0]));
            TMinTxt.setText(String.valueOf(data[1]));
            HMinTxt.setText(String.valueOf(data[2]));
            PMinTxt.setText(String.valueOf(data[3]));
            TMaxTxt.setText(String.valueOf(data[4]));
            HMaxTxt.setText(String.valueOf(data[5]));
            PMaxTxt.setText(String.valueOf(data[6]));
            TStdTxt.setText(String.valueOf(data[7]));
            HStdTxt.setText(String.valueOf(data[8]));
            PStdTxt.setText(String.valueOf(data[9]));
        });

    }

    /**
     *  Updates arraylists with current weather data and calls method display().
     *
     * @param weather Updated Weather data.
     */
    @Override
    public void updateData(Weather weather) {

        if (weather.getPressure() != 0) {
            temps.add(weather.getTemp());
            hum.add(weather.getHumidity());
            press.add(weather.getPressure());
        }

        display();

    }

    /**
     * Calculates values of weather parameters stats.
     */
    private void calculate() {
        double stdT1 = 0;
        double stdH1 = 0;
        double stdP1 = 0;
        double maxT = temps.get(0);
        double maxH = hum.get(0);
        double maxP = press.get(0);
        double minT = temps.get(0);
        double minH = hum.get(0);
        double minP = press.get(0);
        double sumT = 0;
        double sumH = 0;
        double sumP = 0;
        double meanT;
        double meanH;
        double meanP;
        int a = 0;
        int n = temps.size();

        for (int i = 0; i < n - 1; i++) {
            //wartości minimalne
            if (temps.get(i) < minT) {
                minT = temps.get(i);
            }
            if (hum.get(i) < minH) {
                minH = hum.get(i);
            }
            if (press.get(i) < minP) {
                minP = press.get(i);
            }

            //wartosci  maksymalne
            if (temps.get(i) > maxT) {
                maxT = temps.get(i);
            }
            if (hum.get(i) > maxH) {
                maxH = hum.get(i);
            }
            if (press.get(i) > maxP) {
                maxP = press.get(i);
            }

            //srednia
            sumT += temps.get(i);
            sumH += hum.get(i);
            sumP += press.get(i);
            a++;

        }
        //zle liczy odchylenie
        meanT = sumT / (double) a;
        meanH = sumH / (double) a;
        meanP = sumP / (double) a;
        for (int i = 0; i < n - 1; i++) {

            stdT1 += Math.pow((temps.get(i) - meanT), 2.0);
            stdH1 += Math.pow((hum.get(i) - meanH), 2.0);
            stdP1 += Math.pow((press.get(i) - meanP), 2.0);
        }

        double stdT = Math.sqrt(stdT1 / (a - 1));
        double stdH = Math.sqrt(stdH1 / (a - 1));
        double stdP = Math.sqrt(stdP1 / (a - 1));
        stdT = Math.round(stdT * 100.0) / 100.0;
        stdH = Math.round(stdH * 100.0) / 100.0;
        stdP = Math.round(stdP * 100.0) / 100.0;
        data = new double[]{n, minT, minH, minP, maxT, maxH, maxP, stdT, stdH, stdP};

    }
}
