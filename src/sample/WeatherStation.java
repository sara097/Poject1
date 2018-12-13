package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;


public class WeatherStation implements Runnable, Observable {

    //Atrybuty klasy
    private String result;
    private Weather weather = new Weather();

    private String units;
    private String appid;
    private String city;

    //te do runnable
    private Thread whtr;
    protected volatile boolean isRunning = false;
    private int interval = 10000;
    private int n = 0;

    private ArrayList<Observer> weatherUpdates = new ArrayList<>();


    public WeatherStation(String units, String city, String appid) { //Konstruktor kasy
        this.units = units;
        this.appid = appid;
        this.city = city;
    }

    public WeatherStation() { //Konstruktor kasy nie przyjmujący żadnych parametrów
        this.units = "metric";
        this.appid = "0cb8430587fd18e33b1ac06361927f0d";
        this.city = "Wroclaw";
    }

    //Gettery i settery

    public void setInterval(int interval) {
        this.interval = interval;
    }

    //Metoda prywatna klasy (niedostępna dla użytkownika) zwracająca wartość String
    //metoda pobiera ze strony OpenWeather pogodę i zwraca otrzymany ciąg znaków
    private String getWeather() {
        try {

            StringBuilder adr = new StringBuilder();
            adr.append("http://api.openweathermap.org/data/2.5/weather?q=");
            adr.append(city);
            adr.append("&units=");
            adr.append(units);
            adr.append("&APPID=");
            adr.append(appid);

            //utworzenie stringBuffera
            StringBuffer response = new StringBuffer();
            String url = adr.toString(); //utworzenie adresu url z użyciem metody pobierzMiasto
            URL obj = new URL(url); //utworzenie obiektu typu URL, którego parametrem jest String z andresem

            HttpURLConnection connection = (HttpURLConnection) obj.openConnection(); //nawiązywanie połączenia
            connection.setRequestMethod("GET"); //utawianie metody GET w celu pobrania danych
            int responseCode = connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); //utworzenie BufferReadera
            String inputLine; //zmienna do sprawdzanie czy istnieje kolejna linia

            while ((inputLine = in.readLine()) != null) { //pętla while, która jesli istnieje kolejna linia dodanie do zmiennej response jej zawartość
                response.append(inputLine);
            }
            in.close(); //zamknięcie BufferReadera
            result = response.toString(); //zapisanie do atrybutu klasy otrzymanej odpowiedzi od strony

            //obsługa wyjątków
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("nie ma takiego miasta");
        } finally {
            return result;
        }

    }

    //publiczna metoda typu void służąca do przypisania wartosci pogody atrybutom klasy
    private void mapWeather() {
        //utworzenie GsonBuildera
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String pogoda = getWeather(); //utworzenie Stringa z rezultatem ktory zwraca wywołana metoda getWeather
        Map m = gson.fromJson(pogoda, Map.class); //utworzzenie mapy obiektu
        String output = m.get("main").toString(); //utworzenie Stringa z interesującą nas wartością atrybutu obiektu
        output = output.replaceAll("[^\\d.\\s]", ""); //usunięcie z atrubutu wszystkiego co nie jest liczbą, kropką lub spacją
        String[] parameters = output.split(" "); //rozdzielenie Stringa na tablice Stringów , gdzie każdy rodzielony był spacją

        //przypisanie wartości do atrubutów klasy
        weather = new Weather();
        weather.setTemp(Double.parseDouble(parameters[0]));
        weather.setPressure(Double.parseDouble(parameters[1]));
        weather.setHumidity(Double.parseDouble(parameters[2]));
        weather.setTime(LocalTime.now());
        weather.setN(n);
        n++;
    }

    public void start() {
        whtr = new Thread(this, " Clock thread");
        whtr.start();

    }

    public void stop() {
        isRunning = false;
    }

    public void interrupt() {

        isRunning = false;
        whtr.interrupt();
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {

            try {
                mapWeather();
                updateObservers(weather);
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Failed to complete operation");
            }
        }

    }

    @Override
    public void addObserver(Observer observer) {
        if (!weatherUpdates.contains(observer))
            weatherUpdates.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) throws NoSuchElementException {
        if (weatherUpdates.contains(observer)) {
            weatherUpdates.remove(observer);
        } else {
            throw new IllegalArgumentException("No such observer");
        }

    }

    @Override
    public void updateObservers(Weather forecast) {

        Platform.runLater(() -> {
            for (Observer observers : weatherUpdates) {
                observers.updateData(forecast);
            }
        });

    }
}
