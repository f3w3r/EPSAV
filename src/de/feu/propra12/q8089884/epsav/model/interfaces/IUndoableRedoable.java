/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

/**
 * Das Interface spezifiziert die Methoden, die eine Klasse, die eine
 * Aenderungshistorie verwalten soll, bereitstellen muss.
 * 
 * @author Felix Wenz
 * 
 */
public interface IUndoableRedoable {

    /**
     * Die Methode macht die letzte Aenderung innerhalb des Objekts
     * rueckgaengig, falls bereits eine solche stattgefunden hat.
     * 
     * @throws IllegalStateException
     *             wird geworfen, falls noch keine Aenderung stattgefunden hat
     */
    public void undo() throws IllegalStateException;

    /**
     * Die Methode wiederholt die zuletzt widerrufene Aenderung, falls bereits
     * eine solche rueckgaengig gemacht wurde.
     * 
     * @throws IllegalStateException
     *             wird geworfen, falls die letzte Aktion nicht rueckgaengig
     *             gemacht wurde
     */
    public void redo() throws IllegalStateException;

    /**
     * Die Methode gibt wahr zurueck, wenn eine geschehene Aenderung wieder
     * rueckgaengig gemacht werden kann.
     * 
     * @return wahr, wenn Aenderung wieder rueckgaengig gemacht werden kann
     */
    public boolean isUndoable();

    /**
     * Die Methode gibt wahr zurueck, wenn eine rueckgaengig gemachte Aenderung
     * wiederholt werden kann.
     * 
     * @return wahr, wenn eine rueckgaengig gemachte Aenderung wiederholt werden
     *         kann
     */
    public boolean isRedoable();
}
