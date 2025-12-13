package group.habooba.core.base;

/**
 * Primitive singleton logger class
 */
public class Logger {
    public static Logger instance;

    public static Logger getInstance() {
        if(instance == null)
            instance = new Logger();
        return instance;
    }

    private Logger() {

    }

    public static void log(String message) {
        System.out.println(message);
    }
}
