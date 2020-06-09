import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Cafe {
    Staff owner;
    Staff waiter;
    Clock c;
    int orders = 0;
    int waitlist = 0;
    boolean first = true;
    String position;
    Semaphore semCupboard = new Semaphore(1);
    Semaphore semPrep = new Semaphore(2);
    Semaphore semTap = new Semaphore(1);
    List<Customer> listCustomer;

    public Cafe(Clock c) {
        c.start();
        listCustomer = new LinkedList<Customer>();
        Staff owner = new Staff("Owner", this, semCupboard, semPrep, semTap);
        Staff waiter = new Staff("Waiter", this, semCupboard, semPrep, semTap);
        this.owner = owner;
        this.waiter = waiter;
        this.c = c;
        owner.start();
        waiter.start();
    }

    public boolean enter(Customer customer) {    
        c.totalCustomers++;   
        if (waitlist > 10) {
            System.out.println("    ♦ Customer " + customer.id + " left because it's crowded");
            c.customersLeft++;
            return false;
        }
        System.out.println("    ♦ Customer " + customer.id + " joins the queue");
        ((LinkedList<Customer>)listCustomer).offer(customer);
        return true;
    }  

    public void order() {

        synchronized(listCustomer) {
            listCustomer.notify();
            waitlist++;
            if (!first) {
                while (waitlist > 0) {
                    try {
                        listCustomer.wait();
                        break;
                    } catch(InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        }
        Customer customer = (Customer)((LinkedList<?>)listCustomer).poll();
        if (Staff.sposition == "Waiter") {
            waiter.prepare(customer, this);
        }
        else if (Staff.sposition == "Owner") {
            owner.prepare(customer, this);
        }
        c.customersServed++;
        if (!first) {waitlist--;}
            
        if (first) {first = false;}
    
    }
}