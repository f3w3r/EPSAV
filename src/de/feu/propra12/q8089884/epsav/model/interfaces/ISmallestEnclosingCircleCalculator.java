/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

/**
 * Das Interface spezifiziert Methoden, die das Berechnen des kleinsten
 * umfassenden Kreises zur Verfuegung stellen.
 * 
 * @author Felix Wenz
 * 
 */
public interface ISmallestEnclosingCircleCalculator {

    /**
     * Die Methode berechnet die x-Koordinate des Mittelpunkt des kleinsten
     * umfassenden Kreises und gibt diese zurueck.
     * 
     * @return die x-Koordinate des Mittelpunkts des kleinsten umfassenden
     *         Kreises
     */
    public double getSECCenterPointX();

    /**
     * Die Methode berechnet die y-Koordinate des Mittelpunkt des kleinsten
     * umfassenden Kreises und gibt diese zurueck.
     * 
     * @return die y-Koordinate des Mittelpunkts des kleinsten umfassenden
     *         Kreises
     */
    public double getSECCenterPointY();

    /**
     * Die Methode berechnet den Radius des kleinsten umfassenden Kreises und
     * gibt diesen zurueck.
     * 
     * @return den Radius des kleinsten umfassenden Kreises
     */
    public double getSECRadius();

    // TODO Triangulationslinienmethode
}
