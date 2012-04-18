/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

import de.feu.propra12.q8089884.epsav.util.Point;

/**
 * Das Interface spezifiziert Methoden, die das Berechnen der konvexen Huelle
 * zur Verfuegung stellen.
 * 
 * @author Felix Wenz
 * 
 */
public interface IConvexHullCalculator {

    /**
     * Die Methode berechnet das Konturpolygon einer Punktmenge und gibt dessen
     * Punkte zurueck.
     * 
     * @return die Punkte des Konturpolygons
     */
    public Point[] getContourPolygon();

    /**
     * Die Methode berechnet die konvexe Huelle und gibt deren Punkte zurueck.
     * 
     * @return die Punkte der konvexen Huelle
     */
    public Point[] getConvexHull();
}
