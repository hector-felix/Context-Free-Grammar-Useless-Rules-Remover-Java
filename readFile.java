package CSC471Project2;

/**
 *
 * @author Hector Felix
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class readFile {

    private CFG cleanCFG;

    public readFile() {
    }

    public CFG extractCFG(String filePath) throws IOException {
        //Create new Grammar and set up file to be read
        cleanCFG = new CFG();
        boolean readyForRules = false;

        File file = new File(filePath);
        BufferedReader br;
        if (file.exists()) {
            br = new BufferedReader(new FileReader(file));
        } else {
            throw new IOException("File not found...");
        }

        String currentLine = br.readLine();
        while (currentLine != null && !currentLine.isEmpty() && !(currentLine.trim().equals(""))) {
            if (!readyForRules) {
                if (currentLine.startsWith("V:")) {  //Reading List of States and match correctly //Create a new state object every time new state is read
                    String[] separateStates = (currentLine.split(":")[1]).trim().split(",");
                    ArrayList<State> addStates = new ArrayList<>();
                    for (String state : separateStates) {
                        state = state.trim();
                        //Important to create NEW state because we will need later
                        State newState = new State(state.charAt(0));
                        addStates.add(newState);
                    }
                    cleanCFG.setStates(addStates);
                } else if (currentLine.startsWith("Σ:")) { //Reading List of Terminals
                    String[] separateTerminals = currentLine.split(":")[1].trim().split(",");
                    ArrayList<Character> addTerminals = new ArrayList<>();
                    for (String terminal : separateTerminals) {
                        terminal = terminal.trim();
                        addTerminals.add(terminal.charAt(0));
                    }
                    cleanCFG.setTerminals(addTerminals);
                } else if (currentLine.startsWith("S:")) { ////Reading Start State
                    cleanCFG.setStartState(cleanCFG.matchState(currentLine.split(":")[1].trim().trim().charAt(0)));
                } else if (currentLine.startsWith("R:")) { //R denotes parsing is ready to begin reading rules
                    readyForRules = true;
                }
            } else if (readyForRules) {
                State currentState = cleanCFG.matchState(currentLine.trim().charAt(0));
                ArrayList<String> addDerivations = new ArrayList<>();
                String[] rawDerivations = currentLine.trim().split("[-]")[1].trim().split("[\\|]");
                for (String derivation : rawDerivations) {
                    derivation = derivation.trim();
                    addDerivations.add(derivation);
                }
                currentState.setDerviedList(addDerivations); //match derived rules to correct state
            }
            currentLine = br.readLine();
        }
        br.close();
        return cleanCFG;
    }
}
