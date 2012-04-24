/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die im Allgemeinen zum Arbeiten mit/auf Punktmengen benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.util;

/**
 * Die Klasse stellt eine Ausnahme dar, die auftritt wenn ein uebergebenes
 * Argument keine gueltige Punktformatierung aufweist.
 * 
 * @author Felix Wenz
 * 
 */
public class PointFormatException extends IllegalArgumentException {

    /**
     * Der Standardkonstruktor fuer eine Punktformatausnahme.
     */
    public PointFormatException() {
        super();
    }

    /**
     * Konstruktor fuer eine Punktformatausnahme die Informationen zum Grund des
     * Auftretens der Ausnahme uebergibt.
     * 
     * @param s
     */
    public PointFormatException(String s) {
        super(s);
    }
}
