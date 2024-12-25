package debug;

public class Log {

    public static int DEBUG_LEVEL = 0;

    public static void LogI(String msg) {
        if(DEBUG_LEVEL >= 2) {
            System.out.print("[LOG:INFO] ");
            System.out.println(msg);
        }
    }

    public static void LogW(String msg) {
        if (DEBUG_LEVEL >= 1) {
            System.out.print("[LOG:ERROR] ");
            System.out.println(msg);
        }
    }




    public static void applyArgs(String[] args) {
        for (String s : args) {
            if (s.startsWith("--debug=")) {
                try {
                    int debugLevel = Integer.parseInt(String.valueOf(s.charAt(8)));
                    if (debugLevel < 0 || debugLevel > 2) {
                        System.out.println("WARNING: debug level must be 0, 1 or 2");
                        System.exit(1);
                    }
                    DEBUG_LEVEL = debugLevel;
                    LogI("set debug level to " + DEBUG_LEVEL);
                } catch (NumberFormatException e) {
                    System.out.println("WARNING: debug level must be 0, 1 or 2");
                    System.exit(1);
                }
            }
        }
    }

}
