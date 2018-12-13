package sample;

import javafx.application.Platform;
import javafx.scene.chart.Axis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;

/**
 * Class Chart1Update represent class that updates chart with one city data. Implements Observer and Display interfaces.
 *
 * @author Sara Strzałka
 * @version 1.0
 */

public class Chart1Update implements Observer, Display {

    /**
     * Represent chart to update.
     */
    private ScatterChart<Number, Number> chart;
    /**
     * Represents temperature and number of measurements data series.
     */
    private XYChart.Series<Number, Number> Tseries = new XYChart.Series<>();
    /**
     * Represents humidity and number of measurements data series.
     */
    private XYChart.Series<Number, Number> Hseries = new XYChart.Series<>();
    /**
     * Represents pressure and number of measurements data series.
     */
    private XYChart.Series<Number, Number> Pseries = new XYChart.Series<>();
    /**
     * Represents Y(vertical) axis of a chart.
     */
    private Axis yAxis;
    /**
     * Represents ChoiceBox with the parameter to display on chart.
     */
    private ChoiceBox parameter;

    /**
     * Creates object with given parameters.
     *
     * @param chart Chart.
     * @param parameter Parameter to display.
     * @param yAxis Vertical axis of the chart.
     */
    public Chart1Update(ScatterChart<Number, Number> chart, ChoiceBox parameter, Axis yAxis) {
        this.chart = chart;
        this.parameter = parameter;
        this.yAxis = yAxis;
    }

    /**
     * Displays chosen in ChoiceBox series of data on the chart and change vertical axis label.
     */
    @Override
    public void display() {
        Tseries.setName("Temperature");
        Hseries.setName("Humidity");
        Pseries.setName("Pressure");
        chart.setAnimated(false);
        chart.getData().removeAll(chart.getData());
        if (parameter.getValue() == "Temperature") {
            chart.getData().addAll(Tseries);
            yAxis.setLabel("Temperature");
        } else if (parameter.getValue() == "Humidity") {
            chart.getData().addAll(Hseries);
            yAxis.setLabel("Humidity");
        } else {
            chart.getData().addAll(Pseries);
            yAxis.setLabel("Pressure");
        }

    }

    /**
     * Updates data series with received from Observable data and calls the method display().
     * @param weather Updated Weather data.
     */
    @Override
    public void updateData(Weather weather) {

        Platform.runLater(() -> {
            if (weather.getPressure() != 0) {
                Tseries.getData().add(new XYChart.Data<>(weather.getN(), weather.getTemp()));
                Hseries.getData().add(new XYChart.Data<>(weather.getN(), weather.getHumidity()));
                Pseries.getData().add(new XYChart.Data<>(weather.getN(), weather.getPressure()));
            }
        });

        display();

    }
}
