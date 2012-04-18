/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die im Allgemeinen zum Arbeiten mit/auf Punktmengen benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.util;

import java.util.EventObject;

/**
 * Die Klasse repraesentiert ein Ereignis, das auftritt, sobald eine Operation
 * auf einer Punktmenge ausgefuehrt werden soll. Es enthaelt Informationen ueber
 * die Art der Operation.
 * 
 * @author Felix Wenz
 * 
 */
public class PointSetOperationEvent extends EventObject {

    /**
     * die Operation
     */
    private final EPointSetOperation operation;

    /**
     * die Argumente der Operation
     */
    private final Object[] args;

    /**
     * Der Konstruktor fuer ein Operationsereignis auf einer Punktmenge.
     * Uebergibt eine Referenz auf die Quelle der Operation und spezifiziert die
     * Operation und deren Argumente.
     * 
     * @param source
     *            Referenz auf die geaenderte Punktmenge
     * @param operation
     *            die Art der Aenderung
     */
    public PointSetOperationEvent(Object source, EPointSetOperation operation,
            Object[] args) {
        super(source);
        this.operation = operation;
        this.args = args;
    }

    /**
     * Die Methode gibt die Operation zurueck.
     * 
     * @return die Operation
     */
    public EPointSetOperation getOperation() {
        return operation;
    }

    /**
     * Die Methode gibt die Argumente der Operation zurueck.
     * 
     * @return die Argumente
     */
    public Object[] getArgs() {
        return args;
    }
}
