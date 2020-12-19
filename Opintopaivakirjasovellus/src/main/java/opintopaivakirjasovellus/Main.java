
package opintopaivakirjasovellus;

import opintopaivakirjasovellus.ui.OpintopaivakirjasovellusGUI;

public class Main {
    /**
    * Sovelluksen main metodi.
    * @param args käynnistyksessä annettavat parametrit
    */
    public static void main(String[] args) {
        try {
            OpintopaivakirjasovellusGUI.main(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
