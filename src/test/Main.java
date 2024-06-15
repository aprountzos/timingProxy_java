package test;

import proxy.TimingProxy;
import java.lang.reflect.Method;

/**
 * Main class to demonstrate the use of TimingProxy with the NumberPrinter implementation.
 * This class creates an instance of NumberPrinter, wraps it with a TimingProxy,
 * and measures the execution time of its method.
 *
 * @version 1.0
 * @since 2024-06-15
 * @see proxy.TimingProxy
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Create a concrete instance of test.NumberPrinter
            NumberPrinter printer = new NumberPrinterImpl();
            // Create a proxy.TimingProxy instance for the test.NumberPrinter
            TimingProxy proxy = new TimingProxy(printer);
            // Get the method to be invoked
            Method method = NumberPrinter.class.getMethod("printNumbers");
            // Invoke the method on the proxy instance
            proxy.invoke(printer, method, null);
            // Get the mean execution time
            System.out.println("Mean execution time: " + proxy.getMeanTimes() + " nanoseconds");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
