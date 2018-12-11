package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.text.Text;

import java.io.*;
import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Controller {

    private ObservableList<String> units =
            FXCollections.observableArrayList(
                    "Metric",
                    "Imperial",
                    "Standard"
            );

    private ObservableList<String> parameters =
            FXCollections.observableArrayList(
                    "Temperature",
                    "Humidity",
                    "Pressure"
            );

    private WeatherStation ws1;
    private WeatherStation ws2;
    private WeatherStation ws3;

    private final ObservableList<Weather> data = FXCollections.observableArrayList(
            new Weather());

    private Weather weather = new Weather();
    private Weather weather2 = new Weather();

    @FXML
    private TableView<Weather> OCTable;

    @FXML
    private NumberAxis RXAxis;

    @FXML
    private NumberAxis RYAxis;

    @FXML
    private TableColumn<Weather, String> OCCNumber;

    @FXML
    private TableColumn<Weather, String> OCCtime;

    @FXML
    private TableColumn<Weather, String> OCCTemp;

    @FXML
    private TableColumn<Weather, String> OCChum;

    @FXML
    private TableColumn<Weather, String> OCCpress;

    @FXML
    private TextField OCCityTxt;


    @FXML
    private ComboBox<String> OCUnits;


    @FXML
    private TextField OCTimeS;


    @FXML
    private Text OCItyField;

    @FXML
    private Text OTimeTxt;

    @FXML
    private ChoiceBox<String> ChooseParameterBox;

    @FXML
    private ScatterChart<Number, Number> OCChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TextField NumberTxt;

    @FXML
    private TextField TStdTxt;

    @FXML
    private TextField HStdTxt;

    @FXML
    private TextField PStdTxt;

    @FXML
    private TextField TMaxTxt;

    @FXML
    private TextField HMaxTxt;

    @FXML
    private TextField PMaxTxt;

    @FXML
    private TextField TMinTxt;

    @FXML
    private TextField HMinTxt;

    @FXML
    private TextField PMinTxt;

    @FXML
    private ComboBox<String> TCUnits;

    @FXML
    private TextField TCTimeS;

    @FXML
    private LineChart<Number, Number> TCChart;

    @FXML
    private NumberAxis xAxis2;

    @FXML
    private NumberAxis yAxis2;

    @FXML
    private ScatterChart<Number, Number> ReadChart;

    @FXML
    private TextField DataName;

    @FXML
    private ComboBox<String> DataParameters;

    @FXML
    private Text Error;

    @FXML
    private TextField TC1;

    @FXML
    private TextField TC2;

    @FXML
    private TextField OCfileName;

    @FXML
    private Text TCCTxt;

    @FXML
    private Text TCTTxt;

    @FXML
    private TableView<Weather> Table;

    @FXML
    private TableColumn<?, ?> TNum;

    @FXML
    private TableColumn<?, ?> TTime;

    @FXML
    private TableColumn<?, ?> TTemp;

    @FXML
    private TableColumn<?, ?> THum;

    @FXML
    private TableColumn<?, ?> TPres;

    @FXML
    private Button OCPause;

    @FXML
    private Button OCPause1;

    @FXML
    private Button TCPause;

    @FXML
    private Button TCPause1;

    @FXML
    private Button OCStop;

    @FXML
    private Button TCStop;


    public Controller() {
    }

    //formatowanie tekstu
    private TextFormatter format() { //prywatna metoda (może ją użyc tylko metoda z klasy) zwracająca obiekt typu TextFormatter
        //ustawienie formatowania tekstu w polach tekstowych (zeby nie wpisywać niedozwolonych wartości
        Pattern pattern = Pattern.compile("[a-zA-Z]*"); //ustawienie wzoru formatowania tekstu

        //ustawienie formatowania tekstu z użyciem interfejsu UnaryOperator
        // Tworze obiekt klasy TextFormatter, w którego konstruktorze używam operatora lambda
        //jesli wyrazenie wpisywane nie pasuje do wzoru formatowania nie pojawia się w polu tekstowym
        return new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null; //wyrazenie lambda zwraca null jesli wpisywany tekst nie pasuje do wzrou formatowania
        }); //metoda zwraca obiekt typu TextFormatter
    }

    private TextFormatter format2() { //prywatna metoda (może ją użyc tylko metoda z klasy) zwracająca obiekt typu TextFormatter
        //ustawienie formatowania tekstu w polach tekstowych (zeby nie wpisywać niedozwolonych wartości
        Pattern pattern = Pattern.compile("\\d{0,10}([\\.]\\d{0,2})?"); //ustawienie wzoru formatowania tekstu

        //ustawienie formatowania tekstu z użyciem interfejsu UnaryOperator
        // Tworze obiekt klasy TextFormatter, w którego konstruktorze używam operatora lambda
        //jesli wyrazenie wpisywane nie pasuje do wzoru formatowania nie pojawia się w polu tekstowym
        TextFormatter formatter2 = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null; //wyrazenie lambda zwraca null jesli wpisywany tekst nie pasuje do wzrou formatowania
        });
        return formatter2; //metoda zwraca obiekt typu TextFormatter
    }


    public void initialize() {
        //przygotowanie aplikacji
        OCUnits.setItems(units);
        TCUnits.setItems(units);
        ChooseParameterBox.setItems(parameters);
        ChooseParameterBox.setValue("Temperature");
        DataParameters.setItems(parameters);
        DataParameters.setValue("Temperature");

        OCCityTxt.setTextFormatter(format());
        TC1.setTextFormatter(format());
        TC2.setTextFormatter(format());
        OCTimeS.setTextFormatter(format2());
        TCTimeS.setTextFormatter(format2());
        OCPause.setDisable(true);
        OCPause1.setDisable(true);
        TCPause.setDisable(true);
        TCPause1.setDisable(true);
        OCStop.setDisable(true);
        TCStop.setDisable(true);
        OCCityTxt.setText("Wroclaw");
        OCTimeS.setText("5");
        TC1.setText("Wroclaw");
        TC2.setText("Oslo");
        TCTimeS.setText("5");
        OCUnits.setValue("Metric");
        TCUnits.setValue("Metric");

    }

    //one city


    @FXML
    void OCStartClicked(ActionEvent event) {

        OCStop.setDisable(false);
        OCPause.setDisable(false);
        OCCityTxt.setStyle("-fx-background-color: white;");
        OCTimeS.setStyle("-fx-background-color: white;");
        OCItyField.setText(" ");
        OTimeTxt.setText(" ");
        OCfileName.setStyle("-fx-background-color: white;");
        OCfileName.clear();
        int interval = Integer.parseInt(OCTimeS.getText()) * 1000;

        //wielkosc liter nie ma znaczenia ale pod warunkiem ze to miasto ma jeden wyraz
        String city1 = OCCityTxt.getText();
        city1 = city1.toLowerCase();
        String city = city1.substring(0, 1).toUpperCase() + city1.substring(1);

        cityCheck(city, OCCityTxt, OCItyField);
        timeCheck(interval, OCTimeS, OTimeTxt);


        String units = "";
        if(!OCUnits.getValue().equals("Standard")){
            units=OCUnits.getValue();
        }
        String appiD = "0cb8430587fd18e33b1ac06361927f0d";

        XYChart.Series<Number, Number> tTSeries = new XYChart.Series<>();
        XYChart.Series<Number, Number> tHSeries = new XYChart.Series<>();
        XYChart.Series<Number, Number> tPSeries = new XYChart.Series<>();
        OCChart.getData().removeAll(OCChart.getData());

        ws1 = new WeatherStation(units, city, appiD);
        ws1.setInterval(interval);

        ws1.start();

        Chart1Update chart1Update = new Chart1Update(OCChart, weather, ChooseParameterBox, yAxis);
        ws1.addObserver(chart1Update);

        ChooseParameterBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> chart1Update.display());


        data.clear();
        WeatherUpdate wu = new WeatherUpdate(data);
        ws1.addObserver(wu);

        OCCNumber.setCellValueFactory(new PropertyValueFactory<>("n"));
        OCCtime.setCellValueFactory(new PropertyValueFactory<>("time"));
        OCCTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));
        OCChum.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        OCCpress.setCellValueFactory(new PropertyValueFactory<>("pressure"));
        OCTable.setItems(data);

        yAxis.setAutoRanging(true);
        yAxis.setTickUnit(1);
        xAxis.setAutoRanging(true);
        xAxis.setTickUnit(1);
        xAxis.setLabel("Number of measures");

         StatsUpdate2 statsUpdate=new StatsUpdate2(weather, NumberTxt, TMinTxt, HMinTxt, PMinTxt, TMaxTxt, HMaxTxt, PMaxTxt, TStdTxt, HStdTxt, PStdTxt);
        ws1.addObserver(statsUpdate);

    }


    @FXML
    void OCPauseClicked(ActionEvent event) {
        ws1.interrupt();
        OCPause.setDisable(true);
        OCPause1.setDisable(false);

    }

    @FXML
    void OCUPClicked(ActionEvent event) {

        ws1.start();
        OCPause1.setDisable(true);
        OCPause.setDisable(false);
    }

    @FXML
    void OCStopClicked(ActionEvent event) {

        ws1.interrupt();
        ws1.stop();
        if (OCfileName.getText().equals("")) {
            OCfileName.setStyle("-fx-background-color: #ff3d44;");
        } else {
            saveToFile(OCfileName.getText() + ".json");
        }

        OCCityTxt.setEditable(true);
        OCTimeS.setEditable(true);
        OCPause1.setDisable(true);
        OCPause.setDisable(true);
    }

    private void saveToFile(String name) {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();


        File file = new File(name);
        try (FileWriter fileWriter = new FileWriter(file)) {
            gson.toJson(data, fileWriter);
            OCfileName.setStyle("-fx-background-color: #61c441;");
        } catch (IOException e) {
            System.out.println("błąd wejścia wyjscia");
            OCfileName.setStyle("-fx-background-color: red;");
        }


    }

    private void cityCheck(String toCheck, TextField field, Text text) throws IllegalArgumentException {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        CIties[] cities = null;
        boolean exists = false;
        File file = new File("city.list.json");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            cities = gson.fromJson(bufferedReader, CIties[].class);

        } catch (FileNotFoundException e) {
            field.setStyle(("-fx-background-color: red;"));
            e.printStackTrace();
        } catch (IOException e) {
            field.setStyle(("-fx-background-color: red;"));
            e.printStackTrace();
        }


        for (int i = 0; i < cities.length; i++) {
            if (cities[i].getName().equals(toCheck)) {
                exists = true;
                break;
            }
        }

        if (exists) {
            text.setText("");
            field.setStyle(("-fx-background-color: #5df552;"));
        } else {

            text.setText("Wrong city");
            field.setStyle(("-fx-background-color: red;"));
            throw new IllegalArgumentException("There is no such city in a list");
        }

    }


    private void timeCheck(int interval, TextField field, Text text) {

        if (interval < 2000) {
            field.setStyle("-fx-background-color: red;");
            text.setText("2 small");
            throw new IllegalArgumentException("To small");
        } else {
            field.setStyle("-fx-background-color: #65ff55;");
        }

    }

    //Two cities

    @FXML
    void TCStartClicked(ActionEvent event) {

        if (ws2 != null && ws3 != null && ws2.isRunning && ws3.isRunning) {
            ws2.stop();
            ws3.stop();
        }

        TCPause.setDisable(false);
        TCStop.setDisable(false);

        if(TCChart.getData().size()>0) {
            TCChart.getData().removeAll(TCChart.getData());
        }


        TC1.setStyle("-fx-background-color: white;");
        TC2.setStyle("-fx-background-color: white;");

        int interval = Integer.parseInt(TCTimeS.getText()) * 1000;

        //wielkosc liter nie ma znaczenia ale pod warunkiem ze to miasto ma jeden wyraz
        String city1 = TC1.getText();
        city1 = city1.toLowerCase();
        String city01 = city1.substring(0, 1).toUpperCase() + city1.substring(1);
        cityCheck(city01, TC1, TCCTxt);

        String city2 = TC2.getText();
        city2 = city2.toLowerCase();
        String city02 = city2.substring(0, 1).toUpperCase() + city2.substring(1);
        cityCheck(city02, TC2, TCCTxt);
        timeCheck(interval, TCTimeS, TCTTxt);

        if (city01.equals(city02)) {
            TC1.setStyle("-fx-background-color: red;");
            TC2.setStyle("-fx-background-color: red;");
            throw new IllegalArgumentException("the same cities");
        }
        String units = "";
        if(!TCUnits.getValue().equals("Standard")){
            units=TCUnits.getValue();
        }

        String appiD = "0cb8430587fd18e33b1ac06361927f0d";

        ws2 = new WeatherStation(units, city01, appiD);
        ws2.setInterval(interval);
        ws3 = new WeatherStation(units, city02, appiD);
        ws3.setInterval(interval);

        UpdateDataCity1 u1 = new UpdateDataCity1();
        UpdateDataCity2 u2 = new UpdateDataCity2();
        ws2.addObserver(u1);
        ws3.addObserver(u2);
        Chart2Update chart2Update = new Chart2Update(city01, city02,TCChart, weather, weather2, u1, u2);
        chart2Update.display();

        ws2.start();
        ws3.start();

        xAxis2.setTickUnit(1);
        yAxis2.setTickUnit(1);
        xAxis2.setAutoRanging(true);
        yAxis2.setAutoRanging(true);
        xAxis2.setLabel("Number of measures");
        yAxis2.setLabel("Temperature");
    }

    @FXML
    void TCPauseClicked(ActionEvent event) {
        ws2.interrupt();
        ws3.interrupt();
        TCPause.setDisable(true);
        TCPause1.setDisable(false);
    }


    @FXML
    void TCUPClicked(ActionEvent event) {
        ws2.start();
        ws3.start();
        TCPause1.setDisable(true);
        TCPause.setDisable(false);
    }

    @FXML
    void TCStopClicked(ActionEvent event) {
        ws2.interrupt();
        ws3.interrupt();
        ws2.stop();
        ws3.stop();
        TCPause.setDisable(true);
        TCPause1.setDisable(true);
        TCStop.setDisable(true);

    }

    //read data
    @FXML
    void ReadClicked(ActionEvent event) {

        Error.setText(" ");
        DataName.setStyle("-fx-background-color: white;");
        ReadChart.getData().removeAll(ReadChart.getData());
        Table.getItems().removeAll(Table.getItems());

        ObservableList<Weather> readDatas = FXCollections.observableArrayList();
        try {
            Weather[] weathers = readData(DataName.getText() + ".json");
            readDatas.addAll(weathers);


            XYChart.Series<Number, Number> tT = new XYChart.Series<>();
            XYChart.Series<Number, Number> tH = new XYChart.Series<>();
            XYChart.Series<Number, Number> tP = new XYChart.Series<>();

            for (int i = 0; i < readDatas.size(); i++) {

                tT.getData().add(new XYChart.Data<Number, Number>(readDatas.get(i).getN(), readDatas.get(i).getTemp()));
                tH.getData().add(new XYChart.Data<Number, Number>(readDatas.get(i).getN(), readDatas.get(i).getHumidity()));
                tP.getData().add(new XYChart.Data<Number, Number>(readDatas.get(i).getN(), readDatas.get(i).getPressure()));
            }

            TNum.setCellValueFactory(new PropertyValueFactory<>("n"));
            TTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            TTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));
            THum.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            TPres.setCellValueFactory(new PropertyValueFactory<>("pressure"));
            Table.setItems(readDatas);

            if (DataParameters.getValue().equals("Temperature")) {
                ReadChart.getData().addAll(tT);
                RYAxis.setLabel("Temperature");
            } else if (DataParameters.getValue().equals("Humidity")) {
                ReadChart.getData().addAll(tH);
                RYAxis.setLabel("Humidity");
            } else {
                ReadChart.getData().addAll(tP);
                RYAxis.setLabel("Pressure");
            }

            tT.setName("Temperature");
            tP.setName("Pressure");
            tH.setName("Humidity");
            DataParameters.getSelectionModel()
                    .selectedItemProperty()
                    .addListener((observableValue, s, t1) -> {
                        ReadChart.getData().removeAll(ReadChart.getData());
                        if (DataParameters.getValue() == "Temperature") {
                            ReadChart.getData().addAll(tT);
                            RYAxis.setLabel("Temperature");
                        } else if (DataParameters.getValue() == "Humidity") {
                            RYAxis.setLabel("Humidity");
                            ReadChart.getData().addAll(tH);
                        } else {
                            ReadChart.getData().addAll(tP);
                            RYAxis.setLabel("Pressure");
                        }
                    });


            RYAxis.setAutoRanging(true);
            RYAxis.setTickUnit(1);
            RXAxis.setAutoRanging(true);
            RXAxis.setTickUnit(1);
            RXAxis.setLabel("Number of measures");

        } catch (NullPointerException e) {
            Error.setText("Wrong file name");
            DataName.setStyle("-fx-background-color: red;");
        }

    }

    private Weather[] readData(String name) {
        //zeby sprawdzic poprawnosc odczytuje plik
        Weather[] weathers = null;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();


        File file = new File(name);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            weathers = gson.fromJson(bufferedReader, Weather[].class);
        } catch (IOException e) {
            DataName.setStyle("-fx-background-color: red;");
        }
        DataName.setStyle("-fx-background-color: white;");

        System.out.println(Arrays.toString(weathers));
        return weathers;
    }

}
