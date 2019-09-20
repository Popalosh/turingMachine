import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;

public class State {
    int number;
    ArrayList<Character> symbols;
    ArrayList<Command> motion;
    ArrayList<Integer> nextStates;


    State () {
        this.nextStates = new ArrayList<>();
        this.motion = new ArrayList<>();
        this.symbols = new ArrayList<>();
        this.number = 0;
    }

    State (int number, ArrayList<Character> symbols, ArrayList<Command> motion, ArrayList<Integer> nextStates){
        this.number = number;
        this.symbols = symbols;
        this.motion = motion;
        this.nextStates = nextStates;
    }

    public void setMotion(ArrayList<Command> motion) {
        this.motion = motion;
    }

    public void setNextStates(ArrayList<Integer> nextStates) {
        this.nextStates = nextStates;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSymbols(ArrayList<Character> symbols) {
        this.symbols = symbols;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<Character> getSymbols() {
        return symbols;
    }

    public ArrayList<Command> getMotion() {
        return motion;
    }

    public ArrayList<Integer> getNextStates() {
        return nextStates;
    }
}
