package escape.component;

import escape.gamedef.GameObserver;

public class MyObserver implements GameObserver {
    String lastMessage;
    Throwable lastCause;

    /**
     * Receive a message from the game
     * @param message the message
     */
    public void notify(String message) {
        this.lastMessage = message;
        this.lastCause = null;
        System.out.println(this);
    }


    /**
     * Receive a message with the cause
     * @param message the message
     * @param cause usually the exception that caused the state indicated
     * 	by the message
     */
    public void notify(String message, Throwable cause) {
        this.lastMessage = message;
        this.lastCause = cause;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "MyObserver:" + "\n" +
                "lastMessage: " + this.lastMessage + "\n" +
                "lastCause: " + this.lastCause + "\n";
    }
}
