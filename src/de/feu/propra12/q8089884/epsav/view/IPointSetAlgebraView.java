/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die für eine GUI zur Darstellung einer Punktmengenalgebra benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.view;

import de.feu.propra12.q8089884.epsav.model.interfaces.IROPointSetAlgebra;

/**
 * Das Interface spezifiziert Methoden, die ein View fuer Punktmengenalgebren
 * bereitstellen muss.
 * 
 * @author Felix Wenz
 * 
 */
public interface IPointSetAlgebraView {

    /**
     * Die Methode uebergibt eine Punktemengenalgebra, die lesenden Zugriff auf
     * ein Punktmengendatenmodell gewaehrt.
     * 
     * @param pointSetAlgebra
     *            die Punktmengenalgebra
     */
    public void setPointSetAlgebra(IROPointSetAlgebra pointSetAlgebra);

    /**
     * Die Methode gibt die Punktmengenalgebra, die lesenden Zugriff auf ein
     * Punktmengendatenmodell gewaehrt, zurueck.
     * 
     * @return die Punktmengenalgebra
     */
    public IROPointSetAlgebra getPointSetAlgebra();

    /**
     * Die Methode erneuert die Darstellung der Punktmengenalgebra.
     */
    public void refresh();
}
