/**
 * Das Paket beinhaltet alle Testklassen fuer die einzelnen Teile der EPSAV-Anwendung.
 */
package de.feu.propra12.q8089884.epsav.test;

import de.feu.propra12.q8089884.epsav.controller.SynchronizedPointSetController;
import de.feu.propra12.q8089884.epsav.model.SynchronizedCompletePointSetAlgebra;
import de.feu.propra12.q8089884.epsav.util.Point;
import de.feu.propra12.q8089884.epsav.view.EPSAVMainFrame;

/**
 * Die Klasse regelt das Testen der GUI. Dazu instanziiert sie die verschiedenen
 * GUI-Elemente und ruft verschiedene Methoden zur Interaktion mit diesen auf.
 * 
 * @author Felix Wenz
 * 
 */
public class GUITest {
    public static void main(String[] args) {

        // PointSetAlgebra erzeugen und mit Punkten betanken
        SynchronizedCompletePointSetAlgebra psa = new SynchronizedCompletePointSetAlgebra();
        Point[] points = { new Point(15, 200), new Point(23, 5),
                new Point(180, 300), new Point(110, 190), new Point(300, 100),
                new Point(305, 100) };
        psa.addPoints(points);

        // Controller instanziieren
        SynchronizedPointSetController psc = new SynchronizedPointSetController(
                psa);

        // View instanziieren
        EPSAVMainFrame mf = new EPSAVMainFrame(psa);

        // Beobachtermuster zwischen View und Model etablieren
        psa.addPointSetChangedListener(mf);

        // Beobachtermuster zwischen Controller und View etablieren
        mf.addPointSetOperationListener(psc);

        // Punktmengenpanel anzeigen
        mf.showPointSetAlgebraPanel();

    }
}
