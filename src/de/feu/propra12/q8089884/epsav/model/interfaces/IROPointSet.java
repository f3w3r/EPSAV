/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

import de.feu.propra12.q8089884.epsav.util.Point;

/**
 * Das Interface spezifiziert die Methoden, die eine Punktmenge für nur lesenden
 * Zugriff zur Verfuegung stellen muss.
 * 
 * @author Felix Wenz
 * 
 */
public interface IROPointSet {

    /**
     * Die Methode gibt alle Punkte der Punktmenge als Array von Punkten
     * zurueck. Die Elemente dieses Arrays sind Kopien der Punkte in der
     * Punktmenge; Aenderungen an den Punkten des Arrays haben keinen Einfluss
     * auf die Punktmenge selbst.
     * 
     * @return Punktearray mit allen Punkten der Punktmenge
     */
    public Point[] getAllPoints();

    /**
     * Die Methode prueft, ob ein Punkt in der Punktmenge liegt.
     * 
     * @param p
     *            der zu prüfende Punkt
     * @return wahr, falls der Punkt in der Punktmenge liegt
     */
    public boolean contains(Point p);

    /**
     * Die Methode prueft, ob ein Punkt in der Punktmenge liegt.
     * 
     * @param xPos
     *            x-Koordinate des Punkts
     * @param yPos
     *            y-Koordinate des Punkts
     * @return wahr, falls der Punkt in der Punktmenge liegt
     */
    public boolean contains(int xPos, int yPos);

    /**
     * Die Methode gibt den der uebergebenen Position naechtsgelegenen Punkt
     * innerhalb des angegebenen Suchradius zurueck.
     * 
     * @param xPos
     *            die x-Koordinate
     * @param yPos
     *            die y-Koordinate
     * @param range
     *            der Suchradius (in Pixel)
     * @return den Punkt; null, wenn kein sich Punkt innerhalb der angegebenen
     *         Reichweite befindet
     */
    public Point getNearestPointWithinRange(int xPos, int yPos, int range);

    /**
     * Die Methode gibt an, ob die Punktmenge leer ist oder nicht.
     * 
     * @return wahr, falls die Punktmenge leer ist
     */
    public boolean isEmpty();
}
