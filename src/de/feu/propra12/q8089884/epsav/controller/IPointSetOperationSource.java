/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die zur Steuerung einer Punktmenge benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.controller;

/**
 * Das Interface spezifiziert Methoden, die ein Ausloeser von
 * PointSetOperationEvents bereitstellen muss.
 * 
 * @author Felix Wenz
 * 
 */
public interface IPointSetOperationSource {
    /**
     * Die Methode fuegt einen Beobachter zur Liste der
     * PointSetOperationListener hinzu.
     * 
     * @param l
     *            der Beobachter
     */
    public void addPointSetOperationListener(IPointSetOperationListener l);

    /**
     * Die Methode entfernt einen Beobachter von der Liste der
     * PointSetOperationListener.
     * 
     * @param l
     *            der Beobachter
     */
    public void removePointSetOperationListener(IPointSetOperationListener l);
}
