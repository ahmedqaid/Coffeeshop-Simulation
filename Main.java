public class Main {
    public static void main(final String[] args) {
        Clock c = new Clock();
        Table table = new Table();
        
        final Cafe cafe = new Cafe(c);
        CustomerGenerator generator = new CustomerGenerator(cafe, table);
        generator.start();
    }
}