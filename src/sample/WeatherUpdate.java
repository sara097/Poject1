package sample;

import javafx.collections.ObservableList;

public class WeatherUpdate implements Observer {

    private ObservableList<Weather> data;

    public ObservableList<Weather> getData() {
        return data;
    }

    public WeatherUpdate(ObservableList<Weather> data) {
        this.data = data;
    }

    @Override
    public void updateData(Weather weather) {

        System.out.println(weather);
        data.add(weather);


    }
}
