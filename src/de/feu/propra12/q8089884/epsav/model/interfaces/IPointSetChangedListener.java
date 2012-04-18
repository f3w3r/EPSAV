/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

import de.feu.propra12.q8089884.epsav.util.PointSetChangedEvent;

/**
 * Das Interface spezifiziert Methoden, die ein Beobachter von
 * PointSetChangedEvents bereitstellen muss
 * 
 * @author Felix Wenz
 * 
 */
public interface IPointSetChangedListener {

    /**
     * Die Methode informiert den Beobachter ueber ein PointSetChangedEvent, das
     * sich ereignet hat.
     */
    public void firePointSetChangedEvent(PointSetChangedEvent e);
}
