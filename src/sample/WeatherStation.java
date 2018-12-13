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

/**
 * Class WeatherStation represents Observable that updates Observers.
 *
 * @author Sara Strzałka
 * @version 1.0
 */
public class WeatherStation implements Runnable, Observable {

    /**
     * Represents result received from OpenWeatherAPI
     */
    private String result;
    /**
     * Represents weather.
     */
    private Weather weather = new Weather();
    /**
     * Represents units.
     */
    private String units;
    /**
     * Represents AppiKey.
     */
    private String appid;
    /**
     * Represents city name.
     */
    private String city;
    /**
     * Represents Therad.
     */
    private Thread whtr;
    /**
     * Represents state of Thread.
     */
    protected volatile boolean isRunning = false;
    /**
     * Represents the interval of Thread.
     */
    private int interval = 10000;
    /**
     * Represents number of measurements.
     */
    private int n = 0;
    /**
     * Represents list of Observers.
     */
    private ArrayList<Observer> weatherUpdates = new ArrayList<>();

    /**
     * Creates object with given parameters.
     * @param units Units.
     * @param city City name.
     * @param appid AppiKey.
     */
    public WeatherStation(String units, String city, String appid) { //Konstruktor kasy
        this.units = units;
        this.appid = appid;
        this.city = city;
    }

    /**
     * Sets interval of Thread.
     *
     * @param interval Interval.
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * Gets data of OpenWeatherAPI and returns String with result.
     *
     * @return
     */
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

    /**
     * Maps received from OpenWeatherAPI result and split it into weather parameters, then sets this values to Weather object attributes.
     */
    private void mapWeather() {
        //utworzenie GsonBuildera
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String pogoda = getWeather(); //utworzenie Stringa z rezultatem ktory zwraca wywołana metoda getWeather
        Map m = gson.fromJson(pogoda, Map.class); //utworzzenie mapy obiektu
        String output = m.get("main").toString(); //utworzenie Stringa z interesującą nas wartością atrybutu obiektu
        output = output.replaceAll("[^\\d.\\s\\-]", ""); //usunięcie z atrubutu wszystkiego co nie jest liczbą, kropką lub spacją
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

    /**
     * Creates reference and starts Thread.
     */
    public void start() {
        whtr = new Thread(this, " Clock thread");
        whtr.start();

    }

    /**
     * Stops Thread.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Interrupts Thread.
     */
    public void interrupt() {

        isRunning = false;
        whtr.interrupt();
    }

    /**
     * Maps weather when the Thread is started.
     */
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

    /**
     * Adds observer to the list of Observers if the list does not contains this observer.
     * @param observer Observer.
     */
    @Override
    public void addObserver(Observer observer) {
        if (!weatherUpdates.contains(observer))
            weatherUpdates.add(observer);
    }

    /**
     * Removes Observer from list of Observers, when it contains this Observer.
     * @param observer Observer
     * @throws NoSuchElementException if there is no such observer in the list of Observers.
     */
    @Override
    public void removeObserver(Observer observer) throws NoSuchElementException {
        if (weatherUpdates.contains(observer)) {
            weatherUpdates.remove(observer);
        } else {
            throw new IllegalArgumentException("No such observer");
        }
    }

    /**
     * Updates Observers with current weather parameters.
     *
     * @param forecast Current weather.
     */
    @Override
    public void updateObservers(Weather forecast) {

        Platform.runLater(() -> {
            for (Observer observers : weatherUpdates) {
                observers.updateData(forecast);
            }
        });

    }
}
