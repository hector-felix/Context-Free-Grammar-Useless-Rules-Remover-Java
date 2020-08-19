package CSC471Project2;

/**
 *
 * @author Hector Felix
 */
import java.io.IOException;

public class Driver {

    public static void main(String[] args) {

        readFile rf = new readFile();
        CFG startingGrammar = null;
        UselessRulesRemover ur = new UselessRulesRemover();
        try {
            startingGrammar = rf.extractCFG("/Users/mac/Desktop/input.txt");
        } catch (IOException e) {
        }

        if (startingGrammar != null) {
            System.out.println(startingGrammar.toString());
            CFG finishedGrammar = ur.removeUselessRules(startingGrammar);
            System.out.println(finishedGrammar.toString());

        } else {
            System.out.println("Starting Grammar was Empty...");
        }
    }
}
