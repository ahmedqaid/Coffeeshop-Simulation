import java.util.Date;

public class Clock extends Thread {
    public static boolean close = true;
    boolean close1 = false;
    int totalCustomers = 0;
    int customersServed = 0;
    int customersLeft = 0;

    public void run() {
        System.out.println("• The Cafe Has Opened At: "+new Date());
        close = false;
        
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("• The Cafe is closing in 10 seconds " + new Date());
        close = true;
        
        try {
            Thread.sleep(9000);
            close1 = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("• The Cafe Has Closed At: "+new Date());
        System.out.println("• Total Number of Customers: " + totalCustomers);
        System.out.println("• Total Number of Customers Served: " + customersServed);
        System.out.println("• Total Number of Customers Left: " + customersLeft);
    }
}