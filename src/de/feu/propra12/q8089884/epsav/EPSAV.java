/**
 * Das Paket beinhaltet alle weiteren zur EPSAV-Anwendung gehoerenden Pakete, 
 * sowie die Startklasse der Anwendung.
 */
package de.feu.propra12.q8089884.epsav;

import de.feu.propra12.q8089884.epsav.controller.SynchronizedPointSetController;
import de.feu.propra12.q8089884.epsav.model.SynchronizedCompletePointSetAlgebra;
import de.feu.propra12.q8089884.epsav.view.EPSAVMainFrame;

/**
 * Die Klasse beinhaltet die Startroutine der EPSAV-Anwendung.
 * 
 * @author Felix Wenz
 * 
 */
public class EPSAV {

    /**
     * Die main-Methode der EPSAV-Anwendung. Verheiratet die einzelnen
     * Anwendungsmodule miteinander und initialisiert die benoetigten
     * Datenstrukturen. Dann wird das Hauptfenster der Anwendung sichtbar
     * gemacht.
     * 
     * @param args
     *            die Argumente zum Programmstart
     */
    public static void main(String[] args) {

        // PointSetAlgebra erzeugen
        SynchronizedCompletePointSetAlgebra pointSetAlgebra = new SynchronizedCompletePointSetAlgebra();

        // Controller erzeugen
        SynchronizedPointSetController pointSetController = new SynchronizedPointSetController(
                pointSetAlgebra);

        // View erzeugen
        EPSAVMainFrame mainFrame = new EPSAVMainFrame(pointSetAlgebra);

        // Beobachtermuster zwischen View und Model etablieren
        pointSetAlgebra.addPointSetChangedListener(mainFrame);

        // Beobachtermuster zwischen Controller und View etablieren
        mainFrame.addPointSetOperationListener(pointSetController);

    }

}
