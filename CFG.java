package CSC471Project2;

/**
 *
 * @author Hector Felix
 */
import java.util.ArrayList;

public class CFG {

    private ArrayList<State> states;
    private ArrayList<Character> terminals;
    private State startState;

    public ArrayList<State> getStates() {
        return states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    public ArrayList<Character> getTerminals() {
        return terminals;
    }

    public void setTerminals(ArrayList<Character> terminals) {
        this.terminals = terminals;
    }

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public ArrayList<State> deleteState(State deleteState) {
        states.remove(deleteState);
        return states;
    }

    public State matchState(char stateLetter) {
        for (State state : states) {
            if (state.getNotTerminal() == stateLetter) {
                return state;
            }
        }
        return null;
    }
    
    public void removeEmptyStates() {
		if (!states.isEmpty()) {
			for (int indexOfState = 0; indexOfState < states.size(); indexOfState++) {
				if (states.get(indexOfState).getDerivedList().isEmpty()) {
					states.remove(indexOfState);
				}
			}
		}
		else {
			System.out.println("There are no states in this grammar, so no empty ones were removed.");
		}
	}

    @Override
    public String toString() {
        String s = "";
        s += "V: ";
        for (State state : states) {
            s += state.getNotTerminal();
            if (!(states.indexOf(state) == (states.size() - 1))) {
                s += ", ";
            }
        }
        s += "\n" + "Σ: ";
        for (char terminal : terminals) {
            s += terminal;

            if (!(terminals.indexOf(terminal) == (terminals.size() - 1))) {
                s += ", ";
            }
        }
        s += "\n" + "S: " + startState.getNotTerminal() + "\n" + "R: " + "\n";
        for (State state : states) {
            s += "\t" + state.getNotTerminal() + " - ";
            ArrayList<String> currentString = state.getDerivedList();
            for (String finalString : currentString) {
                s += finalString;

                if (!(currentString.indexOf(finalString) == (currentString.size() - 1))) {
                    s += " | ";
                }
            }
            s += "\n";
        }
        return s;
    }
}
