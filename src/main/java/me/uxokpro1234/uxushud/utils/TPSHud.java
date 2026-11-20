package me.uxokpro1234.uxushud.utils;

public class TPSHud {

    private static long lastTick = -1;
    private static long lastUpdate = -1;
    public static double tps = -1;

    public static void reset() {
        lastTick = -1;
        lastUpdate = -1;
        tps = -1;
    }

    public static int updateTime(long ticks) {
        if (lastTick < 0) {
            lastTick = ticks;
            lastUpdate = System.nanoTime();
            return 0;
        }

        long time = System.nanoTime();
        double elapsedMilli = (time - lastUpdate) / 1_000_000d;
        int passedTicks = (int) (ticks - lastTick);
        if (passedTicks > 0) {
            double mspt = elapsedMilli / passedTicks;
            tps = round(Math.min(1000 / mspt, 20), 2);
        }

        lastTick = ticks;
        lastUpdate = time;
        return passedTicks;
    }

    // ARGB color for the TPS text
    public static int getColor() {
        if (tps < 0) return 0xFFAAAAAA;
        if (tps >= 18) return 0xFF55FF55;
        if (tps >= 15) return 0xFFFFFF55;
        return 0xFFFF5555;
    }

    private static double round(double value, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(value * scale) / scale;
    }
}
