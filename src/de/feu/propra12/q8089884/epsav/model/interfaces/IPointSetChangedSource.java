/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

/**
 * Das Interface spezifiziert Methoden, die ein Ausloeser von
 * PointSetChangedEvents bereitstellen muss.
 * 
 * @author Felix Wenz
 * 
 */
public interface IPointSetChangedSource {

    /**
     * Die Methode fuegt einen Beobachter zur Liste der PointSetChangedListener
     * hinzu.
     * 
     * @param l
     *            der Beobachter
     */
    public void addPointSetChangedListener(IPointSetChangedListener l);

    /**
     * Die Methode entfernt einen Beobachter von der Liste der
     * PointSetChangedListener.
     * 
     * @param l
     *            der Beobachter
     */
    public void removePointSetChangedListener(IPointSetChangedListener l);
}
