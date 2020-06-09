import java.util.concurrent.TimeUnit;

public class CustomerGenerator extends Thread{
    Cafe cafe;
    Table table;
    int id = 1;
    int lim = 0;
    
    public CustomerGenerator(Cafe cafe, Table table) {
        this.cafe = cafe;
        this.table = table;
    }

    public void run() {
        while (Clock.close) {
            System.out.print("");
            if (!Clock.close)
                break;
        }
        while (!Clock.close) {
            if (lim >= 50)
                break;
            Customer customer = new Customer(id, cafe, table);
            Thread threadCustomer = new Thread(customer);
            threadCustomer.start();
            try {
                TimeUnit.SECONDS.sleep((long) (Math.random()*1.3));
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            lim++;
            id++;
        }
        if (Clock.close) {
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException ie) {
                ie.printStackTrace(); 
            }        
        }
        return;
    }
}