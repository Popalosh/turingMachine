import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    static void assertFileContent(String name, String expectedContent) throws Exception {
        File file = new File("output\\" + name);
        List<String> lines = Files.readAllLines(file.toPath());
        StringBuilder content = new StringBuilder();
        int counter = 1;
        for (String line : lines) {
            if (counter < lines.size()) {
                content.append(line).append("\n");
                counter++;
            } else {
                content.append(line);
            }
        }
        assertEquals(expectedContent, content.toString());
    }

    static void assertFileExist(String name) {
        File file = new File("output\\" + name);
        assertTrue(file.exists());
    }

    @Test
    @Tag("Functionality")
    void testSimulation() throws Exception {
        State state0 = null;
        Simulation simulation = new Simulation();
        simulation.setAlphabet("01_");
        simulation.setTape("001001100_");

        ArrayList<Character> symbols = new ArrayList<Character>();
        symbols.add('1');
        symbols.add('1');
        symbols.add('_');

        ArrayList<Command> motion = new ArrayList<Command>();
        motion.add(Command.Right);
        motion.add(Command.Right);
        motion.add(Command.Right);

        ArrayList<Integer> nextStates = new ArrayList<>();
        nextStates.add(0, 0);
        nextStates.add(1,0);
        nextStates.add(2,-1);
        state0 = new State(0, symbols, motion, nextStates);

        ArrayList<State> states = new ArrayList<>();
        states.add(state0);
        simulation.setStates(states);

        simulation.main("normal",0,0);
        assertFileContent("output.txt","111111111_");
    }

    @Test
    @Tag("Input")
    void secondTestTmSimsimulation() throws Exception {
        Simulation simulation = new Simulation();


        simulation.main("normal",0, 0);
        assertFileContent("output.txt", "112200120_");

    }

    @Test
    @Tag("modeTest")
    void thirdTestTMSimsimulation() throws Exception {
        Simulation simulation = new Simulation();

        simulation.setAlphabet(simulation.parser.parseAlphabet("alphabet2.txt"));
        simulation.setStates(simulation.parser.parseStates("states2.txt", simulation.getAlphabet()) );
        simulation.setTape(simulation.parser.parseTape("tape2.txt"));
        simulation.setOutputFile("output2.txt");

        simulation.main("modified",0, 5);
        assertFileContent("output2.txt", "polyt56789");
    }
}