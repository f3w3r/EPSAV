/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

/**
 * Das Interface spezifiziert Metoden fuer einen Bereitsteller von
 * Metainformationen das Modul betreffend.
 * 
 * @author Felix Wenz
 * 
 */
public interface IModuleMetaInformationProvider {

    /**
     * Die Methode gibt die Emailadresse des bearbeitenden Studenten zurueck.
     * 
     * @return die Emailadresse
     */
    public String getEmail();

    /**
     * Die Methode gibt die Matrikelnummer des bearbeitenden Studenten zurueck.
     * 
     * @return die Matrikelnummer
     */
    public String getMatrNr();

    /**
     * Die Methode gibt den Namen des bearbeitenden Studenten zurueck.
     * 
     * @return den Namen
     */
    public String getName();

}
