import java.util.Random;

public class Customer extends Thread{
    Cafe cafe;
    Table table;
    int id;
    int entranceTime;
    String orderChoice;
    String[] AvailableOrders = { "juice", "cappuccino" };

    public Customer(final int id, final Cafe c, final Table table) {
        orderChoice = AvailableOrders[new Random().nextInt(2)];
        this.id = id;
        this.table = table;
        cafe = c;
    }

    public void run() {
        int n = 8;
        if (cafe.enter(this)) {
            cafe.order();
            table.sit(this);
            if (Clock.close)
                n = 6;
            try{
                Thread.sleep(new Random().nextInt(n)*1000);
            }catch(Exception e){}
            table.leave(this);
        }
    }
}