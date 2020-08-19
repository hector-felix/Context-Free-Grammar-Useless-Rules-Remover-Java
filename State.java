package CSC471Project2;

/**
 *
 * @author Hector Felix
 */
import java.util.ArrayList;

public class State {

    private char notTerminal;

    private ArrayList<String> derivedList;

    public State(char notTerminal) {
        this.notTerminal = notTerminal;
    }

    public char getNotTerminal() {
        return notTerminal;
    }

    public void setNotTerminal(char notTerminal) {
        this.notTerminal = notTerminal;
    }

    public ArrayList<String> getDerivedList() {
        return derivedList;
    }

    public void setDerviedList(ArrayList<String> derivations) {
        this.derivedList = derivations;
    }

    public void removeDerivation(String derivation) {
        derivedList.remove(derivation);
    }

    public void addDerivation(String derivation) {
        derivedList.add(derivation);
    }

}
