package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

/**
 * A proxy class that measures the execution time of methods on a target object.
 * This class implements the InvocationHandler interface and uses Java reflection to invoke methods.
 *
 * @version 1.0
 * @since 2024-06-15
 * @see java.lang.reflect.InvocationHandler
 * @see java.lang.reflect.Method
 * @see java.lang.reflect.Proxy
 */
public class TimingProxy implements InvocationHandler {
    private final Object target;
    private ArrayList<Long> meanTimes;
    private int numberOfRepetitions;

    /**
     * Constructs a TimingProxy for the given target object.
     *
     * @param target the target object whose methods are to be proxied
     */
    public TimingProxy(Object target) {
        this.target = target;
        this.meanTimes = new ArrayList<>();
        this.numberOfRepetitions = 0;
    }

    /**
     * Intercepts method calls on the proxy instance, measures the execution time,
     * and delegates the call to the target object.
     *
     * @param proxy the proxy instance
     * @param method the method being called
     * @param args the arguments for the method call
     * @return the result of the method call on the target object
     * @throws Throwable if the method invocation on the target object throws an exception
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long endTime = System.nanoTime();
        this.meanTimes.add(endTime - startTime);
        this.numberOfRepetitions++;
        System.out.println(method.getName() + " executed in " + (endTime - startTime) + " nanoseconds");
        return result;
    }

    /**
     * Creates a proxy instance for the specified target object that implements the given interface.
     *
     * @param <T> the type of the target object
     * @param target the target object to be proxied
     * @param interfaceType the interface that the proxy instance should implement
     * @return a proxy instance that implements the specified interface and measures method execution times
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new TimingProxy(target)
        );
    }

    /**
     * Returns the mean execution time of the proxied methods.
     *
     * @return the mean execution time in nanoseconds
     */
    public float getMeanTimes() {
        float mean = 0;
        for (long time : meanTimes) {
            mean += time;
        }
        return mean / this.numberOfRepetitions;
    }
}
