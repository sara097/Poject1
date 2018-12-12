package sample;

import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.util.ArrayList;


public class StatsUpdate implements Observer, Display {

    private ArrayList<Double> temps = new ArrayList<>();
    private ArrayList<Double> hum = new ArrayList<>();
    private ArrayList<Double> press = new ArrayList<>();
    private TextField NumberTxt;
    private TextField TMinTxt;
    private TextField HMinTxt;
    private TextField PMinTxt;
    private TextField TMaxTxt;
    private TextField HMaxTxt;
    private TextField PMaxTxt;
    private TextField TStdTxt;
    private TextField HStdTxt;
    private TextField PStdTxt;
    private double[] data = new double[10];


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

    @Override
    public void updateData(Weather weather) {

        if (weather.getPressure() != 0) {
            temps.add(weather.getTemp());
            hum.add(weather.getHumidity());
            press.add(weather.getPressure());
        }

        display();

    }

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
