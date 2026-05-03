package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public abstract class BaseView {
    protected static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    protected static PrintWriter writer = new PrintWriter(System.out, true);

    private BaseView() {
    }

    protected static String readLine() throws IOException {
        return reader.readLine();
    }

    protected static int readInt() throws IOException, NumberFormatException {
        return Integer.parseInt(reader.readLine());
    }

    protected static void print(String message) {
        writer.print(message);
    }

    protected static void println(String message) {
        writer.println(message);
    }

    protected static void printf(String format, Object... args) {
        writer.printf(format, args);
    }
}
