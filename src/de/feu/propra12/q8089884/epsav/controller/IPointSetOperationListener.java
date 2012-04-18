/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die zur Steuerung einer Punktmenge benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.controller;

import de.feu.propra12.q8089884.epsav.util.PointSetOperationEvent;

/**
 * Das Interface spezifiziert Methoden, die ein Beobachter von
 * PointSetOperationEvents bereitstellen muss
 * 
 * @author Felix Wenz
 * 
 */
public interface IPointSetOperationListener {

    /**
     * Die Methode informiert den Beobachter ueber ein PointSetOperationEvent,
     * das sich ereignet hat.
     */
    public void firePointSetOperationEvent(PointSetOperationEvent e);
}
