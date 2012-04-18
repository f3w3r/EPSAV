/**
 * Das Paket beinhaltet alle Testklassen fuer die einzelnen Teile der EPSAV-Anwendung.
 */
package de.feu.propra12.q8089884.epsav.test;

import de.feu.propra12.q8089884.epsav.model.SynchronizedCompletePointSetAlgebra;
import de.feu.propra12.q8089884.epsav.util.Point;
import de.feu.propra12.q8089884.epsav.util.PointSetException;

/**
 * Die Klasse regelt das Testen des Datenmodells. Dazu instanziiert sie die
 * Modellklasse und ruft verschiedene Methoden zur Interaktion mit dieser auf.
 * 
 * @author Felix Wenz
 * 
 */
public class ModelTest {

    public static void main(String[] args) {

        // PointSetAlgebra erzeugen
        SynchronizedCompletePointSetAlgebra psa = new SynchronizedCompletePointSetAlgebra();

        // METAINFO-Test
        System.out.println(psa.getName());
        System.out.println(psa.getMatrNr());
        System.out.println(psa.getEmail());
        System.out.println();

        // ADD-Test
        // Punkte auf verschiedene Weisen direkt hinzufügen; auch doppelte
        // Punkte überprüfen; Punktmenge ausgeben
        psa.addPoint(new Point(10, 100));
        psa.addPoint(10, 100);
        Point[] points = { new Point(-5, 200), new Point(1, 1),
                new Point(23, 5), new Point(10, 150) };
        psa.addPoints(points);
        System.out.println("Add-Test:\n" + psa);

        // CONTAINS-Test
        // vorhandene und nicht vorhandene Punkte auf unterschiedliche Arten
        // prüfen
        System.out.println("Contains-Test:");
        System.out.println(psa.contains(new Point(10, 100)));
        System.out.println(psa.contains(10, 100));
        System.out.println(psa.contains(new Point(0, 0)));
        System.out.println(psa.contains(0, 0));
        System.out.println();

        // GETNEARESTPOINTWITHIN-Test
        // Testet die Methode zum finden des naechten Punktes innerhalb eines
        // Radius um eine bestimmte Position
        System.out.println("GetNearestPointWithin-Test:");
        // Test auf direkten Treffer
        System.out.println("direkter Treffer -->");
        System.out.println(psa.getNearestPointWithinRange(10, 100, 0));
        // Test auf Treffer innerhalb Radius
        System.out.println("Treffer innerhalb Radius -->");
        System.out.println(psa.getNearestPointWithinRange(28, 10, 10));
        // Test auf mehrere Treffer innerhalb Radius
        System.out.println("mehrere Treffer innerhalb Radius -->");
        System.out.println(psa.getNearestPointWithinRange(10, 130, 35));
        // Test auf kein Treffer
        System.out.println("kein Treffer -->");
        System.out.println(psa.getNearestPointWithinRange(400, 400, 1));
        System.out.println();

        // MOVE-Test
        // vorhandenen und nicht vorhandene Punkte auf unterschiedliche Arten
        // bewegen; Punktmenge ausgeben
        System.out.println("Move-Test:");
        try {
            psa.movePoint(new Point(1, 1), 2, 2);
        } catch (PointSetException e) {
            System.out.println("move-Test 1 gescheitert");
            System.out.println(e);
        }
        try {
            psa.movePoint(new Point(1, 1), 2, 2);
        } catch (PointSetException e) {
            System.out.println("move-Test 2 gescheitert");
            System.out.println(e);
        }
        try {
            psa.movePoint(23, 5, 5, 23);
        } catch (PointSetException e) {
            System.out.println("move-Test 3 gescheitert");
            System.out.println(e);
        }
        try {
            psa.movePoint(23, 5, 5, 23);
        } catch (PointSetException e) {
            System.out.println("move-Test 4 gescheitert");
            System.out.println(e);
        }
        System.out.println("Aktualisierte Punktmenge:\n" + psa);

        // REMOVE-Test
        // vorhandene und nicht vorhandene Punkte auf unterschiedliche Arten aus
        // der Punktmenge entfernen; Punktmenge ausgeben
        System.out.println("Remove-Test:");
        try {
            psa.removePoint(new Point(2, 2));
        } catch (PointSetException e) {
            System.out.println("remove-Test 1 gescheitert");
            System.out.println(e);
        }
        try {
            psa.removePoint(new Point(2, 2));
        } catch (PointSetException e) {
            System.out.println("remove-Test 2 gescheitert");
            System.out.println(e);
        }
        try {
            psa.removePoint(5, 23);
        } catch (PointSetException e) {
            System.out.println("remove-Test 3 gescheitert");
            System.out.println(e);
        }
        try {
            psa.removePoint(5, 23);
        } catch (PointSetException e) {
            System.out.println("remove-Test 4 gescheitert");
            System.out.println(e);
        }
        System.out.println("Aktualisierte Punktmenge:\n" + psa);

        // GETALLPOINTS-Test
        // Punkte der Punktmenge als Array speichern und ausgeben
        System.out.println("getAllPoints-Test:");
        points = psa.getAllPoints();
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i]);
        }
        // Punkt im Array manipulieren und mittels Ausgabe prüfen, ob das auch
        // die Punktmenge verändert...was tatsächlich der Fall ist.
        points[0].moveTo(1, 1);
        System.out.println("Array nach Manipulation:");
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i]);
        }
        System.out.println("Aktuelle Punktmenge:\n" + psa);

        // CLEAR-Test
        // Punktmenge leeren
        psa.clear();
        System.out.println("Clear-Test:\n" + psa);

        // TODO UNDO

        // TODO REDO

        // TODO Punkte aus Datei einlesen

        // TODO Punkte in Datei speichern

        // TODO Operationen auf Punktmengen testen

    }
}
