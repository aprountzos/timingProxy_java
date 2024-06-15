package test;

/**
 * A class that implements the NumberPrinter interface and prints numbers from 1 to 10.
 *
 * @version 1.0
 * @since 2024-06-15
 * @see test.NumberPrinter
 */
public class NumberPrinterImpl implements NumberPrinter {
    @Override
    public void printNumbers() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
        }
    }
}
