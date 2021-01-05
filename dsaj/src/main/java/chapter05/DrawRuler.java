package chapter05;

public class DrawRuler {
    public static void drawRuler(int nInches, int majorLength) {
        drawLine(majorLength, 0);
        for (int i = 1; i <= nInches; i++) {
            drawInterval(majorLength-1);
            drawLine(majorLength, i);
        }
    }

    public static void drawInterval(int centralLength) {
        if (centralLength >0) {
            drawInterval(centralLength-1);
            drawLine(centralLength);
            drawInterval(centralLength-1);
        }
    }

    public static void drawLine(int tickLength, int tickLabel) {
        for (int i = 0; i < tickLength; i++) {
            System.out.print('-');
        }
        if (tickLabel >= 0) {
            System.out.print("  " + tickLabel);
        }
        System.out.print("\n");
    }

    public static void drawLine(int tickLength) {
        drawLine(tickLength, -1);
    }

    public static void main(String[] args) {
        drawRuler(2, 5);
    }
}
