/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die im Allgemeinen zum Arbeiten mit/auf Punktmengen benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.util;

/**
 * Die Klasse stellt eine Ausnahme dar, die im Zusammenhang mit Operationen auf
 * Punktmengen auftreten kann.
 * 
 * @author Felix Wenz
 * 
 */
public class PointSetException extends Exception {

    /**
     * Der Standardkonstruktor fuer eine Punktmengenausnahme.
     */
    public PointSetException() {
        super();
    }

    /**
     * Konstruktor fuer eine Punktmengenausnahme die Informationen zum Grund des
     * Auftretens der Ausnahme uebergibt.
     * 
     * @param s
     */
    public PointSetException(String s) {
        super(s);
    }
}
