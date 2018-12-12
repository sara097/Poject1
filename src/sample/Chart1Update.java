package sample;

import javafx.application.Platform;
import javafx.scene.chart.Axis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;

public class Chart1Update implements Observer, Display {

    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> Tseries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> Hseries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> Pseries = new XYChart.Series<>();
    private Axis yAxis;
    private ChoiceBox parameter;

    public Chart1Update(ScatterChart<Number, Number> chart, ChoiceBox parameter, Axis yAxis) {
        this.chart = chart;
        this.parameter = parameter;
        this.yAxis = yAxis;
    }

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
