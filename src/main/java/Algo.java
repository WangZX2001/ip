public class Algo {
    static final String BOT_NAME = "ALGO";
    static final String LINE = "________________________________________________________";
    static final String LOGO =
            "     ___    _      ____   ___  \n" +
                    "    / _ \\  | |    / ___| / _ \\ \n" +
                    "   / /_\\ \\ | |   | |  _ | | | |\n" +
                    "   |  _  | | |___| |_| || |_| |\n" +
                    "   |_| |_| |_____\\____/  \\___/ \n";

    public static void print_simple_greeting() {
        System.out.println(LINE);
        System.out.println(LOGO);
        System.out.println(" Hello! I'm " + BOT_NAME);
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);

    }

    public static void main(String[] args) {
        print_simple_greeting();
    }


}
