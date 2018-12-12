package sample;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

public class UpdateDataCity2 implements Observer {

    private XYChart.Series<Number, Number> Tseries2 = new XYChart.Series<>();

    public XYChart.Series<Number, Number> getTseries2() {
        return Tseries2;
    }

    @Override
    public void updateData(Weather weather) {
        Platform.runLater(() -> {
            if (weather.getPressure() != 0)
                Tseries2.getData().add(new XYChart.Data<>(weather.getN(), weather.getTemp()));

        });
    }
}
