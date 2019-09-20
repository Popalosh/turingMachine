import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Simulation simulation = new Simulation();
        String mode = "";
        int stepsQuantity = 0;
        String inputAlphabet = "";
        String inputTape = "";
        String inputStates = "";
        String input;

        System.out.println("Please, choose mode(normal, modified) or type 'help' for more info: ");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        while (input.equals("help")) {
            modeAndHelp(true);
            modeAndHelp(false);
            input = scanner.nextLine();
            if(!input.equals("normal") && !input.equals("modified") && !input.equals("help")) {
                System.out.println("Unresolved command");
            }
        }

        mode = input;

        if (mode.equals("modified")) {
            System.out.println("Please, specify number of steps you want your machine to execute: ");
            String pattern = "\\d+";
            Pattern pttrn = Pattern.compile(pattern);
            input = scanner.nextLine();
            if (!input.matches(String.valueOf(pttrn))) {
                System.out.println("Please, specify number of steps you want your machine to execute: ");
                input = scanner.nextLine();
            }
            stepsQuantity = Integer.parseInt(input);
            
            System.out.println("Please, specify name of the file where your machine' alphabet is: ");
            inputAlphabet =  scanner.nextLine();
                    System.out.println("Please, specify name of the file where your machine' tape is: ");
            inputTape =  scanner.nextLine();
                    System.out.println("Please, specify name of the file where your machine' states are: ");
            inputStates =  scanner.nextLine();

            simulation.setInputAlphabet(inputAlphabet);
            simulation.setInputTape(inputTape);
            simulation.setInputStates(inputStates);
            simulation.recompile();

            simulation.main(mode,0,stepsQuantity);

            while (mode.equals("modified")) {
                System.out.println("Do you want to proceed execution?\n(Yes/No)");
                input = scanner.nextLine();
                if (input.equals("Yes")) {
                    System.out.println("Please, choose steps' quantity: ");
                    input = scanner.nextLine();
                    stepsQuantity = Integer.parseInt(input);
                    simulation.main("proceed", simulation.currentState.getNumber(),stepsQuantity);
                }
            }
        }
    }
    private static void modeAndHelp(boolean help) {
        if(help) {
            System.out.println("normal - just a normal mode\nmodified - program will be executed in several steps");
        } else {
            System.out.println("Please, choose mode(normal, modified) or type 'help' for more info: ");
        }
        return;
    }
}
