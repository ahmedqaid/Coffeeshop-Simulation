public class Table {
    private int customersIn;
    private int custemersLimit;

    public Table() {
        customersIn = 0;
        custemersLimit = 10;
    }

    synchronized public void sit(Customer customer) {
        if (customersIn < custemersLimit) {
            customersIn++;
            System.out.println("▼ Customer " + customer.id + " finds a seat [" + customersIn + "/" + custemersLimit + "]");
        }
        else {
            System.out.println("Table has reached its limit (10)");
        }
    }

    synchronized public void leave(Customer customer) {
        customersIn--;
        System.out.println("▲ Customer " + customer.id + " Left [" + customersIn + "/" + custemersLimit + "]");
    }
}