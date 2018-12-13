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

/**
 * Class Controller represents GUI control methods.
 *
 * @author Sara Strzałka
 * @version 1.0
 */
public class Controller {

    /**
     * Represents list of units user can choose.
     */
    private ObservableList<String> units =
            FXCollections.observableArrayList(
                    "Metric",
                    "Imperial",
                    "Standard"
            );

    /**
     * Represents list of parameters user can choose to display.
     */
    private ObservableList<String> parameters =
            FXCollections.observableArrayList(
                    "Temperature",
                    "Humidity",
                    "Pressure"
            );

    /**
     * Represents WeatherStation object.
     */
    private WeatherStation ws1;
    /**
     * Represents WeatherStation object.
     */
    private WeatherStation ws2;
    /**
     * Represents WeatherStation object.
     */
    private WeatherStation ws3;

    /**
     * Represents list of gathered weather data.
     */
    private final ObservableList<Weather> data = FXCollections.observableArrayList(
            new Weather());

    /**
     * Represents Weather object.
     */
    private Weather weather = new Weather();
    /**
     * Represents Weather object.
     */
    private Weather weather2 = new Weather();

    /**
     * Represents TableView of one city weather parameters.
     */
    @FXML
    private TableView<Weather> OCTable;
    /**
     * Represents TableColumn with one city number of measurements.
     */
    @FXML
    private TableColumn<Weather, String> OCCNumber;
    /**
     *  Represents TableColumn with one city time of measure.
     */
    @FXML
    private TableColumn<Weather, String> OCCtime;
    /**
     *  Represents TableColumn with one city temperature.
     */
    @FXML
    private TableColumn<Weather, String> OCCTemp;
    /**
     *  Represents TableColumn with one city humidity.
     */
    @FXML
    private TableColumn<Weather, String> OCChum;
    /**
     *  Represents TableColumn with one city pressure.
     */
    @FXML
    private TableColumn<Weather, String> OCCpress;
    /**
     * Represents TextField with one city city name.
     */
    @FXML
    private TextField OCCityTxt;
    /**
     * Represents ComboBox with one city units options.
     */
    @FXML
    private ComboBox<String> OCUnits;
    /**
     * Represents TextField with one city interval of measurements.
     */
    @FXML
    private TextField OCTimeS;
    /**
     * Represents TextField with file name to save data.
     */
    @FXML
    private TextField OCfileName;
    /**
     * Represents Text of information about incorrect city name input.
     */
    @FXML
    private Text OCItyField;
    /**
     * Represents Text of information about incorrect interval input.
     */
    @FXML
    private Text OTimeTxt;
    /**
     * Represents ChoiceBox with parameters on chart options.
     */
    @FXML
    private ChoiceBox<String> ChooseParameterBox;
    /**
     * Represents one city chart.
     */
    @FXML
    private ScatterChart<Number, Number> OCChart;
    /**
     * Represents horizontal axis of one city chart
     */
    @FXML
    private NumberAxis xAxis;
    /**
     * Represents vertical axis of one city chart.
     */
    @FXML
    private NumberAxis yAxis;
    /**
     * Represents TextField with number of measurements.
     */
    @FXML
    private TextField NumberTxt;
    /**
     * Represents TextField with standard deviation of temperature.
     */
    @FXML
    private TextField TStdTxt;
    /**
     * Represents TextField with standard deviation of humidity.
     */
    @FXML
    private TextField HStdTxt;
    /**
     * Represents TextField with standard deviation of pressure.
     */
    @FXML
    private TextField PStdTxt;
    /**
     * Represents TextField with maximal value of temperature.
     */
    @FXML
    private TextField TMaxTxt;
    /**
     * Represents TextField with maximal value of humidity.
     */
    @FXML
    private TextField HMaxTxt;
    /**
     * Represents TextField with maximal value of pressure.
     */
    @FXML
    private TextField PMaxTxt;
    /**
     * Represents TextField with minimal value of temperature.
     */
    @FXML
    private TextField TMinTxt;
    /**
     * Represents TextField with minimal value of humidity.
     */
    @FXML
    private TextField HMinTxt;
    /**
     * Represents TextField with minimal value of pressure.
     */
    @FXML
    private TextField PMinTxt;
    /**
     * Represents ComboBox with two city units options.
     */
    @FXML
    private ComboBox<String> TCUnits;
    /**
     * Represents TextField with two city interval of measurements.
     */
    @FXML
    private TextField TCTimeS;
    /**
     * Represents chart of two city temperatures.
     */
    @FXML
    private LineChart<Number, Number> TCChart;
    /**
     * Represents two city chart horizontal axis.
     */
    @FXML
    private NumberAxis xAxis2;
    /**
     * Represents two city chart vertical axis.
     */
    @FXML
    private NumberAxis yAxis2;
    /**
     * Represents TextField with first of two cities name.
     */
    @FXML
    private TextField TC1;
    /**
     * Represents TextField with second of two cities name.
     */
    @FXML
    private TextField TC2;
    /**
     * Represents Text with message about wrong one or two city names.
     */
    @FXML
    private Text TCCTxt;
    /**
     * Represents Text with message about wrong interval in two city part.
     */
    @FXML
    private Text TCTTxt;
    /**
     * Represents horizontal axis of chart of read data.
     */
    @FXML
    private NumberAxis RXAxis;
    /**
     * Represents vertical axis of chart of read data.
     */
    @FXML
    private NumberAxis RYAxis;
    /**
     * Represents chart of read data.
     */
    @FXML
    private ScatterChart<Number, Number> ReadChart;
    /**
     * Represents TextField with file to read name.
     */
    @FXML
    private TextField DataName;
    /**
     * Represents ComboBox with read data chart parameters options to display.
     */
    @FXML
    private ComboBox<String> DataParameters;
    /**
     * Represents Text with message about error during reading file.
     */
    @FXML
    private Text Error;
    /**
     * Represents TableView with read data.
     */
    @FXML
    private TableView<Weather> Table;
    /**
     * Represents Column with number of measurements of read data.
     */
    @FXML
    private TableColumn<?, ?> TNum;
    /**
     * Represents Column with time of read data.
     */
    @FXML
    private TableColumn<?, ?> TTime;
    /**
     * Represents Column with temperature of read data.
     */
    @FXML
    private TableColumn<?, ?> TTemp;
    /**
     * Represents Column with humidity of read data.
     */
    @FXML
    private TableColumn<?, ?> THum;
    /**
     * Represents Column with pressure of read data.
     */
    @FXML
    private TableColumn<?, ?> TPres;
    /**
     * Represents Button that pauses all processes in application in one city part.
     */
    @FXML
    private Button OCPause;
    /**
     * Represents Button that unpauses all processes in application in one city part.
     */
    @FXML
    private Button OCPause1;
    /**
     * Represents Button that pauses all processes in application in two city part.
     */
    @FXML
    private Button TCPause;
    /**
     * Represents Button that unpauses all processes in application in two city part.
     */
    @FXML
    private Button TCPause1;
    /**
     * Represents Button that stops all processes in application in one city part.
     */
    @FXML
    private Button OCStop;
    /**
     * Represents Button that stops all processes in application in two city part.
     */
    @FXML
    private Button TCStop;

    /**
     * Creates an object.
     */
    public Controller() {
    }

    /**
     * Returns way of format text in TextFields where user writes city name.
     *
     * @return TextFormatter
     */
    private TextFormatter format() { //prywatna metoda (może ją użyc tylko metoda z klasy) zwracająca obiekt typu TextFormatter
        //ustawienie formatowania tekstu w polach tekstowych (zeby nie wpisywać niedozwolonych wartości
        Pattern pattern = Pattern.compile("[a-zA-Z\\s\\']*"); //ustawienie wzoru formatowania tekstu

        //[a-zA-Z]
        //ustawienie formatowania tekstu z użyciem interfejsu UnaryOperator
        // Tworze obiekt klasy TextFormatter, w którego konstruktorze używam operatora lambda
        //jesli wyrazenie wpisywane nie pasuje do wzoru formatowania nie pojawia się w polu tekstowym
        return new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null; //wyrazenie lambda zwraca null jesli wpisywany tekst nie pasuje do wzrou formatowania
        }); //metoda zwraca obiekt typu TextFormatter
    }

    /**
     * Returns way of format text in TextFields where user writes interval.
     *
     * @return TextFormatter
     */
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

    /**
     * Initialize application after launching.
     */
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

    /**
     * Method called after StartButton clicked. Starts all processes in one city part of application.
     *
     * @param event Start Button pressed.
     */
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
        OCChart.getData().removeAll(OCChart.getData());

        int interval = Integer.parseInt(OCTimeS.getText()) * 1000;

        String city1 = OCCityTxt.getText();
        String city = formatCity(city1);

        cityCheck(city, OCCityTxt, OCItyField);
        timeCheck(interval, OCTimeS, OTimeTxt);

        String units = "";
        if (!OCUnits.getValue().equals("Standard")) {
            units = OCUnits.getValue();
        }
        String appiD = "0cb8430587fd18e33b1ac06361927f0d";

        ws1 = new WeatherStation(units, city, appiD);
        ws1.setInterval(interval);
        ws1.start();

        data.clear();
        WeatherUpdate wu = new WeatherUpdate(data);
        ws1.addObserver(wu);

        OCCNumber.setCellValueFactory(new PropertyValueFactory<>("n"));
        OCCtime.setCellValueFactory(new PropertyValueFactory<>("time"));
        OCCTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));
        OCChum.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        OCCpress.setCellValueFactory(new PropertyValueFactory<>("pressure"));
        OCTable.setItems(data);

        Chart1Update chart1Update = new Chart1Update(OCChart, ChooseParameterBox, yAxis);
        ws1.addObserver(chart1Update);

        yAxis.setAutoRanging(true);
        yAxis.setTickUnit(1);
        xAxis.setAutoRanging(true);
        xAxis.setTickUnit(1);
        xAxis.setLabel("Measurement number");

        ChooseParameterBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> chart1Update.display());

        StatsUpdate statsUpdate = new StatsUpdate(NumberTxt, TMinTxt, HMinTxt, PMinTxt, TMaxTxt, HMaxTxt, PMaxTxt, TStdTxt, HStdTxt, PStdTxt);
        ws1.addObserver(statsUpdate);

    }

    /**
     * Method called after PauseButton clicked. Pauses all processes in one city part of application.
     *
     * @param event Pause Button clicked.
     */
    @FXML
    void OCPauseClicked(ActionEvent event) {
        ws1.stop();
        OCPause.setDisable(true);
        OCPause1.setDisable(false);
    }

    /**
     * Method called after UnpauseButton clicked. Unpauses all processes in one city part of application.
     *
     * @param event Unpause Button clicked.
     */
    @FXML
    void OCUPClicked(ActionEvent event) {
        ws1.start();
        OCPause1.setDisable(true);
        OCPause.setDisable(false);
    }

    /**
     * Method called after StopButton clicked. Stops all processes in one city part of application.
     *
     * @param event Stop Button clicked.
     */
    @FXML
    void OCStopClicked(ActionEvent event) {
        ws1.interrupt();
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

    /**
     * Method called after StartButton clicked. Starts all processes in two city part of application.
     *
     * @param event Start Button clicked.
     */
    @FXML
    void TCStartClicked(ActionEvent event) {

        TCPause.setDisable(false);
        TCStop.setDisable(false);
        TC1.setStyle("-fx-background-color: white;");
        TC2.setStyle("-fx-background-color: white;");

        int interval = Integer.parseInt(TCTimeS.getText()) * 1000;

        String city1 = TC1.getText();
        String city01 = formatCity(city1);

        cityCheck(city01, TC1, TCCTxt);

        String city2 = TC2.getText();
        String city02 = formatCity(city2);
        cityCheck(city02, TC2, TCCTxt);
        timeCheck(interval, TCTimeS, TCTTxt);

        if (city01.equals(city02)) {
            TC1.setStyle("-fx-background-color: red;");
            TC2.setStyle("-fx-background-color: red;");
            throw new IllegalArgumentException("the same cities");
        }

        String units = "";
        if (!TCUnits.getValue().equals("Standard")) {
            units = TCUnits.getValue();
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
        Chart2Update chart2Update = new Chart2Update(city01, city02, TCChart, weather, weather2, u1, u2);
        chart2Update.display();

        xAxis2.setTickUnit(1);
        yAxis2.setTickUnit(1);
        xAxis2.setAutoRanging(true);
        yAxis2.setAutoRanging(true);
        xAxis2.setLabel("Measurement number");
        yAxis2.setLabel("Temperature");

        ws2.start();
        ws3.start();
    }

    /**
     * Method called after PauseButton clicked. Pauses all processes in two city part of application.
     *
     * @param event Pause Button clicked.
     */
    @FXML
    void TCPauseClicked(ActionEvent event) {
        ws2.stop();
        ws3.stop();
        TCPause.setDisable(true);
        TCPause1.setDisable(false);
    }

    /**
     * Method called after UnpauseButton clicked. Unpauses all processes in two city part of application.
     *
     * @param event Unpause Button clicked.
     */
    @FXML
    void TCUPClicked(ActionEvent event) {
        ws2.start();
        ws3.start();
        TCPause1.setDisable(true);
        TCPause.setDisable(false);
    }

    /**
     * Method called after StopButton clicked. Stops all processes in two city part of application.
     *
     * @param event Stop Button clicked.
     */
    @FXML
    void TCStopClicked(ActionEvent event) {
        ws2.interrupt();
        ws3.interrupt();
        TCPause.setDisable(true);
        TCPause1.setDisable(true);
        TCStop.setDisable(true);

    }

    /**
     * Method called after ReadButton clicked. Reads data from file and displays it.
     *
     * @param event ReadButton clicked.
     */
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
            RXAxis.setLabel("Measurement number");

        } catch (NullPointerException e) {
            Error.setText("Wrong file name");
            DataName.setStyle("-fx-background-color: red;");
        }

    }

    /**
     * Saves gathered data to file.
     *
     * @param name File name
     */
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

    /**
     *Checks if city given by user is correct and reacts.
     *
     * @param toCheck city name user gave
     * @param field TextField where name was given
     * @param text Text with error message.
     * @throws IllegalArgumentException
     */
    private void cityCheck(String toCheck, TextField field, Text text) throws IllegalArgumentException {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Cities[] cities = null;
        boolean exists = false;
        File file = new File("citylist.json");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            cities = gson.fromJson(bufferedReader, Cities[].class);


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

    /**
     * Checks if interval given by user is correct and reacts.
     *
     * @param interval interval given by user
     * @param field TextField where interval was given
     * @param text Text with error message
     */
    private void timeCheck(int interval, TextField field, Text text) {

        if (interval < 2000) {
            field.setStyle("-fx-background-color: red;");
            text.setText("2 small");
            throw new IllegalArgumentException("Interval too small");
        } else {
            field.setStyle("-fx-background-color: #65ff55;");
        }

    }

    /**
     * Formats city name to start with capital letter.
     *
     * @param city1 city name to format
     * @return formated city name.
     */
    private String formatCity(String city1) {
        city1 = city1.toLowerCase();

        while (city1.charAt(city1.length() - 1) == ' ') {
            city1 = city1.substring(0, city1.length() - 1);
        }

        String city = "";
        String[] citySplit1 = city1.split(" ");
        if (citySplit1.length > 1) {

            for (int i = 0; i < citySplit1.length; i++) {

                if (i == citySplit1.length - 1)
                    city = city + citySplit1[i].substring(0, 1).toUpperCase() + citySplit1[i].substring(1);
                else
                    city = city + citySplit1[i].substring(0, 1).toUpperCase() + citySplit1[i].substring(1) + " ";
            }
        } else {
            city = city1.substring(0, 1).toUpperCase() + city1.substring(1);
        }
        return city;
    }

    /**
     * Reads data from file with given in parameter name.
     *
     * @param name file name.
     * @return Table with Weather objects.
     */
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
