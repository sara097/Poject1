package sample;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

/**
 * Class UpdateDataCity2 represents class that updates data series needed to make a chart. Implements Observer.
 *
 * @author Sara Strzałka
 * @version 1.0
 */
public class UpdateDataCity2 implements Observer {

    /**
     * Represent series of data with temperature and number od measurements.
     */
    private XYChart.Series<Number, Number> Tseries2 = new XYChart.Series<>();

    /**
     * Returns series of data.
     *
     * @return Temperature and number of measurements series of data.
     */
    public XYChart.Series<Number, Number> getTseries2() {
        return Tseries2;
    }

    /**
     * Updates temperature and number of measurements series of data with current weather temperature and number of measurements.
     *
     * @param weather Updated Weather data.
     */
    @Override
    public void updateData(Weather weather) {
        Platform.runLater(() -> {
            if (weather.getPressure() != 0)
                Tseries2.getData().add(new XYChart.Data<>(weather.getN(), weather.getTemp()));
        });
    }
}
