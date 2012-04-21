/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die im Allgemeinen zum Arbeiten mit/auf Punktmengen benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.util;

/**
 * Der Aufzaehlungstyp beschreibt die Operation, die auf einer Punktmenge
 * ausgefuehrt werden soll.
 * 
 * @author Felix Wenz
 * 
 */
public enum EPointSetOperation {

    /**
     * Punkt hinzufuegen
     */
    ADD_POINT,

    /**
     * Punkt bewegen
     */
    MOVE_POINT,

    /**
     * Punkt entfernen
     */
    REMOVE_POINT,

    /**
     * Punktmenge leeren
     */
    CLEAR_POINTSET,

    /**
     * Punkte aus Datei importieren
     */
    IMPORT_FROM_FILE,

    /**
     * Punktmenge in Datei exportieren
     */
    EXPORT_TO_FILE,

    /**
     * letzte Aenderung rueckgaengig machen
     */
    UNDO,

    /**
     * zuletzt rueckgaengig gemachte Aenderung wiederholen
     */
    REDO,

    /**
     * Operationsquelle anmelden
     */
    OPERATION_SOURCE_INIT,

    /**
     * Operationsquelle abmelden
     */
    OPERATION_SOURCE_QUIT;

}
