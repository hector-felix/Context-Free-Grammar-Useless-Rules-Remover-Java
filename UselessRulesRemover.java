package CSC471Project2;

/**
 *
 * @author Hector Felix
 */
import java.util.ArrayList;

public class UselessRulesRemover {

    public CFG removeUselessRules(CFG cfg) {

        ArrayList<Character> leftSide = new ArrayList<>();
        ArrayList<Character> rightSide = new ArrayList<>();
        ArrayList<Character> finalTerminals = new ArrayList<>(); // new list of terminals
        boolean addedFlag = true;
        boolean remove;

        for (Character term : cfg.getTerminals()) { // add terminals to right
            rightSide.add(term);
        }
        for (State state : cfg.getStates()) { //add others to left
            leftSide.add(state.getNotTerminal());
        }

        while (addedFlag) {
            addedFlag = false;
            for (State stateList : cfg.getStates()) {
                for (String stateTerminals : stateList.getDerivedList()) {
                    char[] terminalList = stateTerminals.toCharArray();
                    int k = 0;
                    for (char character : terminalList) { // ste to see if character exists in right side already
                        if (rightSide.contains(character)) {
                            k++;
                        }
                    }
                    if (k == stateTerminals.length()) {
                        if (!rightSide.contains(stateList.getNotTerminal())) { //check to see if terminals exist already in right side
                            rightSide.add(stateList.getNotTerminal());
                            int i = leftSide.indexOf(stateList.getNotTerminal());
                            leftSide.remove(i);
                            addedFlag = true;
                        }
                    }
                }
            }
        }
        for (State stateCheck : cfg.getStates()) { // ste if current state is still in leftside. Remove from all state list if so
            ArrayList<String> needToDelete = new ArrayList<>();
            for (String s : stateCheck.getDerivedList()) {//
                char[] term = s.toCharArray();
                remove = false;
                for (char str : term) {
                    if (leftSide.contains(str)) {
                        remove = true;
                    }
                }
                if (remove) {
                    needToDelete.add(s);
                }
            }
            stateCheck.getDerivedList().removeAll(needToDelete);
        }
        ArrayList<State> newStates = new ArrayList<>();
        //add newstates to this AL

        for (char fState : rightSide) {
            for (State ste : cfg.getStates()) {
                if (ste.getNotTerminal() == fState) {
                    newStates.add(ste);
                }
            }
        }
        cfg.setStates(newStates);

        //Removing unreachable
        ArrayList<State> reachable = new ArrayList<>();
        reachable.add(cfg.getStartState());

        for (State currentState : cfg.getStates()) { //when state is reachable, look through derivations to find States listed, and also might be reachable or not
            if (reachable.contains(currentState)) {
                for (String s : currentState.getDerivedList()) {
                    char[] parseSplit = s.toCharArray();
                    for (char currentChar : parseSplit) {
                        if (Character.isUpperCase(currentChar)) { // checking character for Upper Case  state
                            State reachableState = cfg.matchState(currentChar);
                            if (!reachable.contains(reachableState)) {
                                reachable.add(reachableState);
                            }
                        }
                    }
                }
            }
        }
        cfg.setStates(reachable);

        for (State s : cfg.getStates()) {
            for (String term : s.getDerivedList()) {
                char[] found = term.toCharArray();
                for (char i : found) {
                    if (!finalTerminals.contains(i)) {
                        finalTerminals.add(i);
                    }
                }
            }
        }
        cfg.setTerminals(finalTerminals);
        return cfg;
    }
}
