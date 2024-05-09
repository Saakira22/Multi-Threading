import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TollBoothSimulation {
    private static final int NUM_LANES = 3; 
    private static final int NUM_CARS = 10; 
    private static final Semaphore semaphore = new Semaphore(NUM_LANES);
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < NUM_CARS; i++) {
            Thread carThread = new Thread(new Car(i));
            carThread.start();
        }
    }

    static class Car implements Runnable {
        private final int carId;

        public Car(int carId) {
            this.carId = carId;
        }

        @Override
        public void run() {
            try {
                System.out.println("Car " + carId + " is approaching the toll booth.");

                semaphore.acquire(); // Acquire a permit for a toll booth lane

                System.out.println("Car " + carId + " has entered a toll booth lane.");

                // Simulate time taken for transaction
                Thread.sleep(1000);

                lock.lock(); // Acquire the lock for exclusive access to the toll booth lane
                try {
                    // Critical section: Simulate transaction
                    System.out.println("Car " + carId + " is processing payment at the toll booth.");
                } finally {
                    lock.unlock(); // Release the lock
                }

                semaphore.release(); // Release the toll booth lane permit
                System.out.println("Car " + carId + " has exited the toll booth lane.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

