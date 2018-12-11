package sample;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;


public class UpdateDataCity1 implements Observer {

    private XYChart.Series<Number, Number> Tseries1 = new XYChart.Series<>();

    public XYChart.Series<Number, Number> getTseries1() {
        return Tseries1;
    }

    public void setTseries1(XYChart.Series<Number, Number> tseries1) {
        Tseries1 = tseries1;

    }

    @Override
    public void updateData(Weather weather) {

        Platform.runLater(() -> {
            if (weather.getPressure() != 0)
                Tseries1.getData().add(new XYChart.Data<>(weather.getN(), weather.getTemp()));
        });

    }
}
