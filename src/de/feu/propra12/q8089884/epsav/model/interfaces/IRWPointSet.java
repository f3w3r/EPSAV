/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

import de.feu.propra12.q8089884.epsav.util.Point;
import de.feu.propra12.q8089884.epsav.util.PointSetException;

/**
 * Das Interface spezifiziert die Methoden, die eine Punktmenge für
 * Lese-/Schreibzugriff zur Verfuegung stellen muss.
 * 
 * @author Felix Wenz
 * 
 */
public interface IRWPointSet extends IROPointSet {

    /**
     * Die Methode fuegt der Punktmenge einen Punkt hinzu.
     * 
     * @param p
     *            der Punkt
     */
    public void addPoint(Point p);

    /**
     * Die Methode fuegt der Punktmenge einen Punkt hinzu.
     * 
     * @param xPos
     *            die x-Koordinate des Punktes
     * @param yPos
     *            die y-Koordinate des Punktes
     */
    public void addPoint(int xPos, int yPos);

    /**
     * Die Methode fuegt der Punktmenge mehrere Punkte hinzu.
     * 
     * @param points
     *            das Array von Punkten.
     */
    public void addPoints(Point[] points);

    /**
     * Die Methode entfernt einen Punkt aus der Punktmenge.
     * 
     * @param p
     *            der Punkt
     * @throws PointSetException
     *             wird geworfen, falls der uebergebene Punkt nicht in der
     *             Punktmenge liegt
     */
    public void removePoint(Point p) throws PointSetException;

    /**
     * Die Methode entfernt einen Punkt aus der Punktmenge.
     * 
     * @param xPos
     *            die x-Koordinate des Punktes
     * @param yPos
     *            die y-Koordinate des Punktes
     * @throws PointSetException
     *             wird geworfen, falls der uebergebene Punkt nicht in der
     *             Punktmenge liegt
     */
    public void removePoint(int xPos, int yPos) throws PointSetException;

    /**
     * Die Methode bewegt den uebergebenen Punkt an die Koordinaten des neuen
     * Punkts, falls dieser in der Punktmenge liegt.
     * 
     * @param p
     *            der Punkt
     * @param np
     *            der neue Punkt
     * @throws PointSetException
     *             wird geworfen, falls der uebergebene Punkt nicht in der
     *             Punktmenge liegt
     */
    public void movePoint(Point p, Point np) throws PointSetException;

    /**
     * Die Methode bewegt den uebergebenen Punkt an die neuen Koordinaten, falls
     * dieser in der Punktmenge liegt.
     * 
     * @param p
     *            der Punkt
     * @param xPos
     *            die neue x-Koordinate
     * @param yPos
     *            die neue y-Koordinate
     * @throws PointSetException
     *             wird geworfen, falls der uebergebene Punkt nicht in der
     *             Punktmenge liegt
     */
    public void movePoint(Point p, int xPos, int yPos) throws PointSetException;

    /**
     * Die Methode bewegt den uebergebenen Punkt an die neuen Koordinaten, falls
     * dieser in der Punktmenge liegt.
     * 
     * @param xPos
     *            die x-Koordinate des Punktes
     * @param yPos
     *            die y-Koordinate des Punktes
     * @param newXPos
     *            die neue x-Koordinate
     * @param newYPos
     *            die neue y-Koordinate
     * @throws PointSetException
     *             wird geworfen, falls der uebergebene Punkt nicht in der
     *             Punktmenge liegt
     */
    public void movePoint(int xPos, int yPos, int newXPos, int newYPos)
            throws PointSetException;

    /**
     * Die Methode entfernt alle Punkte aus der Punktmenge.
     */
    public void clear();
}
