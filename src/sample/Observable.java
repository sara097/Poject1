package sample;

public interface Observable {

    void addObserver(Observer o);

    void removeObserver(Observer o);

    void updateObservers(Weather weather);


}
