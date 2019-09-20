import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class Parser {
    Parser() {}

    ArrayList<State> parseStates(String inputFile, String alphabet) throws IOException {
        ArrayList<State> states = new ArrayList<State>();
        File file = new File(inputFile);
        List<String> lines = Files.readAllLines(file.toPath());
        String pattern = "\\d+\\s->\\s(.\\s=\\s(.[<\\.>]\\d+\\s)|(.+))";
        Pattern ptrn = Pattern.compile(pattern);

        if (lines.isEmpty()) {
            return new ArrayList<State>();
        }

        for (String line : lines) {
            Matcher matcher = ptrn.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(
                        "Please write states in a correct format. E.g.: 8 -> a=h>8 b=nonSpec c=s>1 x=_.0 " +
                                "incorrect line format has been found at ${line[0]} state"
                );
            }
            System.exit(1);
        }

        for (String line : lines) {
            String[] parsedLine = line.split("( -> )|(\\s)");
            Integer number = Integer.parseInt(parsedLine[0]);
            ArrayList<Command> motion = new ArrayList<>();
            ArrayList<Character> symbols = new ArrayList<>();
            ArrayList nextStates = new ArrayList<Integer>();

            for (int i = 1; i < parsedLine.length; i++) {
                String part = parsedLine[i];
                char tapeSymbol = part.charAt(0);
                int index = alphabet.indexOf(tapeSymbol);

                if (part.contains("nonSpec")) {
                    symbols.add(index,' ');
                    motion.add(index,Command.IllegalCommand);
                    nextStates.add(index, -1);
                    continue;
                }

                char symbol = part.charAt(2);
                Command command;
                switch (part.charAt(3)) {
                    case ('<'): {
                        command = Command.Left;
                    }
                    case ('>'): {
                        command = Command.Right;
                    }
                    case ('.'): {
                        command = Command.NoMove;
                    }
                    default: {
                        command = Command.IllegalCommand;
                    }
                }
                int next = Integer.parseInt(part.substring(4));

                symbols.add(index,symbol);
                motion.add(index,command);
                nextStates.add(index,next);
            }
            State state = new State(number,symbols,motion,nextStates);
            states.add(number,state);
        }
        return states;
    }

    String parseTape(String inputName) throws IOException {
        return getContent(inputName);
    }

    String parseAlphabet(String inputName) throws IOException {
        return getContent(inputName);
    }

    private String getContent(String inputName) throws IOException {
        File file = new File(inputName);
        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(file.toPath());
        StringBuilder string = new StringBuilder();
        if(lines.isEmpty()) {
            return "";
        } else {
            for (String line : lines) {
                if (lines.indexOf(line) != (lines.size() - 1)){
                    string.append(line + "\n");
                }
            }
            return string.toString();
        }
    }
}
