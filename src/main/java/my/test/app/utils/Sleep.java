package my.test.app.utils;

public class Sleep {

    static public void sec(int sleepSec) {
        millis(sleepSec * 1000);
    }

    static public void millis(int sleepMillis) {
        try {
            Thread.sleep(sleepMillis);
        } catch (Throwable e) {/* Ignore*/}
    }
}
