/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die im Allgemeinen zum Arbeiten mit/auf Punktmengen benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.util;

/**
 * Der Aufzaehlungstyp beschreibt die Art der Aenderung an einer Punktmenge.
 * 
 * @author Felix Wenz
 * 
 */
public enum EPointSetChangedMode {

    /**
     * Hinzufuegen eines Punktes
     */
    POINT_ADDED,

    /**
     * Aenderung an einem Punkt
     */
    POINT_MOVED,

    /**
     * Entfernen eines Punktes
     */
    POINT_REMOVED,

    /**
     * Loeschen aller Punkte
     */
    POINTSET_CLEARED;

    // TODO weitere Änderungsarten einfügen
}
