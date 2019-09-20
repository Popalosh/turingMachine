import java.io.IOException;
import java.util.ArrayList;

public class Simulation {

    public Simulation() throws IOException {}

    private String inputTape = "src/main/tape2.txt";
    private String inputStates = "src/main/states2.txt";
    private String inputAlphabet = "src/main/alphabet2.txt";
    private String outputFile = "src/main/output2.txt";

    Parser parser = new Parser();
    private Output output = new Output();

    private String tape = parser.parseTape(inputTape);
    private String alphabet = parser.parseAlphabet(inputAlphabet);
    private ArrayList<State> states = parser.parseStates(inputStates,alphabet);

    StringBuilder currentTape = new StringBuilder(tape);
    int currentPosition = 0;
    State currentState = states.get(0);


    void recompile() throws IOException {
        this.tape = parser.parseTape(inputTape);
        this.alphabet = parser.parseAlphabet(inputAlphabet);
        this.states = parser.parseStates(inputStates,alphabet);
    }

    public void setTape(String tape) {
        this.tape = tape;
    }

    public String getTape() {
        return tape;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setStates(ArrayList<State> states) { this.states = states;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public void setInputTape(String input) {
        if(!input.equals("")) {
            inputTape = input;
        }
    }

    public void setInputStates(String input) {
        if(!input.equals("")) {
            this.inputStates = input;
        }
    }

    public void setOutputFile(String input) {
        if(!input.equals("")) {
            this.outputFile = input;
        }
    }

    public void setInputAlphabet(String input) {
        if(!input.equals("")) {
            this.inputAlphabet = input;
        }
    }

    public String getInputAlphabet() {
        return inputAlphabet;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getInputTape() {
        return inputTape;
    }

    public String getInputStates() {
        return inputStates;
    }

    public void main(String mode, Integer number, Integer stepsQuantity) throws IOException {
        if (!(mode.equals("normal")) || mode.equals("proceed") || mode.equals("modified")) {
            output.write("$mode is an incorrect mode" +
                    "correct ones are: normal, modified, proceed" +
                    "please choose one of them", "src/main/log.txt");
            System.exit(1);
        }
        String cTape = tape;

        try {
            switch (mode) {
                case ("normal") : {
                    normal(cTape,false,0,0);
                }
                case ("modified") : {
                    normal(cTape, true, 0, stepsQuantity);
                }
                case ("proceed") : {
                    normal(currentTape.toString(),true,number,stepsQuantity);
                }
            }
        } catch (IllegalArgumentException e) {
            output.write(e.getMessage().toString(), "src/main/log.txt");
            System.exit(1);
        }
    }

    public void normal(String tape, Boolean modified, Integer number, Integer stepsQuantity) throws IOException {
        if (number.equals(0)) {
            currentTape = new StringBuilder(tape);
            currentPosition = 0;
            currentState = states.get(number);
        }

        int counter = 0;

        try {
            while (currentState.getNumber() != -1) {
                if (modified && counter == stepsQuantity) {
                    System.out.print("$stepsQuantity steps of your program have just been executed");
                    output.write(currentTape.toString(), outputFile);
                }

                char currentSymbol = currentTape.charAt(currentPosition);
                int symbolState = alphabet.indexOf(currentSymbol);
                Command currentCommand = currentState.getMotion().get(symbolState);
                int nextState = currentState.getNextStates().get(symbolState);
                Character newSymbol = currentState.getSymbols().get(symbolState);

                if (currentPosition > 0) {
                    StringBuilder temp1 = new StringBuilder(currentTape.substring(0,currentPosition));
                    String temp2 = currentTape.substring(currentPosition + 1);

                    temp1.append(newSymbol);
                    currentTape.append(temp1.toString()).append(temp2);
                }

                if (currentPosition == 0) {
                    currentTape = new StringBuilder(newSymbol + currentTape.substring(1));
                }

                if (currentCommand.equals(Command.Right) && currentPosition == currentTape.length() - 1) {
                    currentTape.append(" ");
                }

                switch (currentCommand) {
                    case Left: {
                        currentPosition--;
                    }

                    case Right: {
                        currentPosition++;
                    }

                    case IllegalCommand: {

                        currentTape = new StringBuilder(tape);
                        throw new IllegalArgumentException("Illegal command has been found" +
                                " at ${currentState.getNumber()} state, symbol:($currentSymbol) " +
                                "maybe you haven't specified this state");
                    }
                }

                if (nextState == -1) {
                    System.out.println("Program is finished");
                    while (currentTape.charAt(currentTape.length() - 1) == ' ') {
                        currentTape = new StringBuilder(currentTape.substring(0,currentTape.length() - 1));
                    }
                    output.write(currentTape.toString(), outputFile);
                    return;
                }

                currentState = states.get(nextState);
                counter++;
            }
        } catch (IllegalArgumentException e) {
            output.write(e.getMessage(), "src/main/log.txt");
            output.write(currentTape.toString(), outputFile);
            return;

        } catch (Exception e) {
            output.write(e.getMessage().toString(), "src/main/log.txt");
            output.write(currentTape.toString(), outputFile);
            return;
        }
    }
}
