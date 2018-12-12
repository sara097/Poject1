package sample;

import javafx.scene.chart.LineChart;

public class Chart2Update implements Display {

    private LineChart<Number, Number> chart;
    private Weather weather;
    private Weather weather2;
    private String city1;
    private String city2;

    private UpdateDataCity1 updateDataCity1;
    private UpdateDataCity2 updateDataCity2;

    public Chart2Update(String city1, String city2, LineChart<Number, Number> chart, Weather weather, Weather weather2, UpdateDataCity1 updateDataCity1, UpdateDataCity2 updateDataCity2) {
        this.chart = chart;
        this.weather = weather;
        this.weather2 = weather2;
        this.updateDataCity1 = updateDataCity1;
        this.updateDataCity2 = updateDataCity2;
        this.city1 = city1;
        this.city2 = city2;
    }

    @Override
    public void display() {
        updateDataCity1.updateData(weather);
        updateDataCity2.updateData(weather2);
        chart.setAnimated(false);
        chart.getData().removeAll(chart.getData());
        chart.getData().add(updateDataCity1.getTseries1());
        chart.getData().add(updateDataCity2.getTseries2());
        updateDataCity1.getTseries1().setName(city1);
        updateDataCity2.getTseries2().setName(city2);

    }

}
