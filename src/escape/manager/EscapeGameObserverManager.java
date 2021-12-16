package escape.manager;

import escape.component.MyObserver;
import escape.gamedef.GameObserver;
import java.util.ArrayList;

public class EscapeGameObserverManager {
    private final ArrayList<GameObserver> observers;

    public EscapeGameObserverManager() {
        this.observers = new ArrayList<>();
    }

    /**
     * Add an observer to the list of managed observers
     * @param observer the observer to add
     */
    public void addObserver(GameObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from the list of managed observers
     * @param observer the observer to rmeove
     * @return the observer that was removed or null if it was not registered
     */
    public GameObserver removeObserver(GameObserver observer) {
        boolean existed = this.observers.remove(observer);
        if(existed) {
            return observer;
        }
        return null;
    }

    /**
     * Notify all observers
     * @param message the message
     */
    public void notify(String message) {
        for(GameObserver o : this.observers) {
            o.notify(message);
        }
    }


    /**
     * Notify all observers with a cause
     * @param message the message
     * @param cause usually the exception that caused the state indicated
     * 	by the message
     */
    public void notify(String message, Throwable cause) {
        for(GameObserver o : this.observers) {
            o.notify(message, cause);
        }
    }
}
