package sample;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;

/**
 * Class Chart2Update represent class that updates chart with two city data. Implements Display interface.
 *
 * @author Sara Strzałka
 * @version 1.0
 */
public class Chart2Update implements Display {

    /**
     * Represent chart to update.
     */
    private LineChart<Number, Number> chart;
    /**
     * Represent weather of first city.
     */
    private Weather weather;
    /**
     * Represents weather of second city.
     */
    private Weather weather2;
    /**
     * Represents first city name.
     */
    private String city1;
    /**
     * Represents second city name.
     */
    private String city2;
    /**
     * Represents UpdateDataCity1 object.
     */
    private UpdateDataCity1 updateDataCity1;
    /**
     * Represents UpdateDataCity2 object.
     */
    private UpdateDataCity2 updateDataCity2;

    /**
     * Creates object with given parameters.
     *
     * @param city1           First city name.
     * @param city2           Second city name.
     * @param chart           Chart to update.
     * @param weather         Weather of first city.
     * @param weather2        Weather of second city.
     * @param updateDataCity1 UpdateDataCity1 object.
     * @param updateDataCity2 UpdateDataCity2 object.
     */
    public Chart2Update(String city1, String city2, LineChart<Number, Number> chart, Weather weather, Weather weather2, UpdateDataCity1 updateDataCity1, UpdateDataCity2 updateDataCity2) {
        this.chart = chart;
        this.weather = weather;
        this.weather2 = weather2;
        this.updateDataCity1 = updateDataCity1;
        this.updateDataCity2 = updateDataCity2;
        this.city1 = city1;
        this.city2 = city2;
    }

    /**
     * Displays updated in updateDataCity1 and updateDataCity2 data on the chart.
     */
    @Override
    public void display() {
        Platform.runLater(() -> {
            updateDataCity1.updateData(weather);
            updateDataCity2.updateData(weather2);
            chart.setAnimated(false);
            chart.getData().removeAll(chart.getData());
            chart.getData().add(updateDataCity1.getTseries1());
            chart.getData().add(updateDataCity2.getTseries2());
            updateDataCity1.getTseries1().setName(city1);
            updateDataCity2.getTseries2().setName(city2);
        });

    }

}
