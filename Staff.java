import java.util.Random;
import java.util.concurrent.Semaphore;

public class Staff extends Thread{
    public static String sposition;
    String position;
    public boolean busy;
    Cafe cafe;
    int preparing = 0;
    Semaphore semPrep;
    Semaphore semCupboard;
    Semaphore semTap;

    public Staff(String ps, Cafe cafe, Semaphore s, Semaphore ss, Semaphore t){
        this.cafe = cafe;
        busy = false;
        this.position = ps;
        this.semCupboard = s;
        this.semPrep = ss;
        this.semTap = t;
    }
    
    public void run() {
        Thread.currentThread().setName(position);
        while (true) {
            sposition = currentThread().getName();
            synchronized(cafe.listCustomer) {
                cafe.position = position;
                while (cafe.waitlist == 0) {
                    try {
                        cafe.listCustomer.wait();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
                if (cafe.c.close1){ break; }
                if (this.position == "Waiter" && Clock.close) { 
                    System.out.println("• WAITER LEFT");
                    break; 
                }
            }
        }
        
    }

    void prepare(final Customer customer, Cafe cafe) {
        try {
            semPrep.acquire();
        } catch (InterruptedException ie) {}
        
        if (customer.orderChoice == "juice") {
            obtainFromCupboard(this, customer);
            useTap(this, customer);
        }
        else if (customer.orderChoice == "cappuccino") {
            obtainFromCupboard(this, customer);
            obtainIngredients(this, customer);
            System.out.println("        Order " + customer.id + ": " + this.position + " is mixing ingredients");
        }
        System.out.println("        Order " + customer.id + ": " + this.position + " is handling order to " + customer.id);

        synchronized(cafe.listCustomer) {
            cafe.listCustomer.notify();
        }
        semPrep.release();
    }    

    public void obtainFromCupboard(Staff staff, Customer customer) {
        try {
            semCupboard.acquire();
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        String cupOrGlass;
        if (customer.orderChoice == "juice") {
            cupOrGlass = "glass";
        } else {
            cupOrGlass = "cup";
        }
        System.out.println("        Order " + customer.id + ": " + staff.position + " is obtaining " + cupOrGlass + " from cupboard");
        try{
            Thread.sleep(new Random().nextInt(2)*1000);
        } catch(Exception e) {}
        
        System.out.println("        Order " + customer.id + ": " + staff.position + " left cupboard");
        
        semCupboard.release();
    }

    public void obtainIngredients(Staff staff, Customer customer) {
        try {
            semCupboard.acquire();
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println("        Order " + customer.id + ": " + staff.position + " is obtaining cappuccino ingredients from cupboard");
        try{
            Thread.sleep(new Random().nextInt(3)*1000);
            } catch(Exception e){}
        
        System.out.println("        Order " + customer.id + ": " + staff.position + " left cupboard");
        semCupboard.release();
    }

    public void useTap(Staff staff, Customer customer) {
            try {
                semTap.acquire();
            } catch (InterruptedException ie){}
            System.out.println("        Order " + customer.id + ": " + staff.position + " is filling out the glass with juice");
            try{
                Thread.sleep(new Random().nextInt(3)*1000);
            } catch(Exception e){}
            System.out.println("        Order " + customer.id + ": " + staff.position + " left the juice tap");
            semTap.release();
    }
}