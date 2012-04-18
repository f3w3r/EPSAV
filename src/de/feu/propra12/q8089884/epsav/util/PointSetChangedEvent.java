/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die im Allgemeinen zum Arbeiten mit/auf Punktmengen benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.util;

import java.util.EventObject;

/**
 * Die Klasse repraesentiert ein Ereignis, das auftritt, sobald sich eine
 * Punktmenge geaendert hat. Es enthaelt Informationen ueber die Art der
 * Aenderung.
 * 
 * @author Felix Wenz
 * 
 */
public class PointSetChangedEvent extends EventObject {

    /**
     * die Art der Aenderung
     */
    private final EPointSetChangedMode changeMode;

    /**
     * Der Konstruktor fuer ein Aenderungsereignis einer Punktmenge. Uebergibt
     * eine Referenz auf die geaenderte Punktmenge und zeigt die Art der
     * Aenderung an.
     * 
     * @param source
     *            Referenz auf die geaenderte Punktmenge
     * @param changeMode
     *            die Art der Aenderung
     */
    public PointSetChangedEvent(Object source, EPointSetChangedMode changeMode) {
        super(source);
        this.changeMode = changeMode;
    }

    /**
     * Die Methode gibt die Art der Aenderung zurueck.
     * 
     * @return die Art der Aenderung
     */
    public EPointSetChangedMode getChangeMode() {
        return changeMode;
    }
}
