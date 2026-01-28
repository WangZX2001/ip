import java.util.Scanner;

public class Algo {
    static final String BOT_NAME = "ALGO";
    static final String LINE = "________________________________________________________";
    static final String LOGO =
            "  ___    _      ____   ___  \n" +
                    " / _ \\  | |    / ___| / _ \\ \n" +
                    "/ /_\\ \\ | |   | |  _ | | | |\n" +
                    "|  _  | | |___| |_| || |_| |\n" +
                    "|_| |_| |_____\\____/  \\___/\n";


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        printGreeting();
        String input = in.nextLine();

        while(!input.equalsIgnoreCase("bye")) {
            printDialogueBox(input);
            input = in.nextLine();
        }

        printDialogueBox("Bye. Hope to see you again soon!");
    }

    private static void printGreeting() {
        System.out.println(LINE);
        System.out.println(LOGO);
        System.out.println("Hello! I'm "+ BOT_NAME + "\nWhat can I do for you?");
        System.out.println(LINE);
        System.out.println();
    }
    private static void printDialogueBox(String text) {
        System.out.println(LINE);
        System.out.println(text);
        System.out.println(LINE);
        System.out.println();
    }
}
