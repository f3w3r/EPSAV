/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

/**
 * Das Interface spezifiziert die Methoden, die eine Punktmengenalgebra für nur
 * lesenden Zugriff zur Verfuegung stellen muss.
 * 
 * @author Felix Wenz
 * 
 */
public interface IROPointSetAlgebra extends IROPointSet, IConvexHullCalculator,
        ISmallestEnclosingCircleCalculator {

    // TODO der Zugriff auf die berechneten Körper sollte innerhlab eines
    // synchronized Blocks passieren, damit alle Berechnungen auf derselben
    // Punktmenge passieren!!!
}
