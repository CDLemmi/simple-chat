package debug;

public class Log {

    public static final boolean DEBUG = false;

    public static void LogI(String msg) {
        if(!DEBUG) {
            System.out.print("[LOG:INFO] ");
            System.out.println(msg);
        }
    }

    public static void LogW(String msg) {
        if (!DEBUG) {
            System.out.print("[LOG:INFO] ");
            System.out.println(msg);
        }
    }




}
