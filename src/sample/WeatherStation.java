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
    private String result; //zmienna, ktora jest rezultatem metody getWeather, nie ma settera bo nie chcę, żeby użytkownik mógł ją zmienić
    private Weather forecast = new Weather();

    private String units;
    private String appid;
    private String city;

    //te do runnable
    private Thread whtr;
    protected volatile boolean isRunning = false;  //volatile - zmiana od razu zostanie zapisana w pamieci glownej i chache zostaje uaktualniony //protected - dostep maja takze klasy dziedzieczace
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


    public void setResult(String result) {
        this.result = result;
    }

    public Thread getWhtr() {
        return whtr;
    }

    public void setWhtr(Thread whtr) {
        this.whtr = whtr;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getResult() {
        return result;
    }

    public Weather getForecast() {
        return forecast;
    }

    public void setForecast(Weather forecast) {
        this.forecast = forecast;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String miasto) {
        this.city = miasto;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
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
        forecast = new Weather();
        forecast.setTemp(Double.parseDouble(parameters[0]));
        forecast.setPressure(Double.parseDouble(parameters[1]));
        forecast.setHumidity(Double.parseDouble(parameters[2]));
        forecast.setTime(LocalTime.now());
        forecast.setN(n);
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

                updateObservers(forecast);
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
        //this.forecast=forecast;

        Platform.runLater(() -> {
            for (Observer observers : weatherUpdates) {
                observers.updateData(forecast);
            }

        });

    }
}
