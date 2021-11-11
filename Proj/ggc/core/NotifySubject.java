package ggc.core;

public interface NotifySubject {

    public void addObserver(NotifyObserver obs);
    public void removeObserver(NotifyObserver obs);
    public void notificateObserver(String notificacion, Product p);
    
}