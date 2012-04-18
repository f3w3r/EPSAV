/**
 * Das Paket beinhaltet alle Interfaces, die verschiedene Zugriffsarten auf 
 * eine Punkmenge voneinander abgrenzen, bestimmte Operationen auf diesen nach
 * aussen zugaenglich machen und den generellen Umgang mit diesen naeher 
 * spezifizieren.
 */
package de.feu.propra12.q8089884.epsav.model.interfaces;

import java.io.*;

/**
 * Das Interface spezifiziert Methoden, die eine Punktmenge, die sich in Dateien
 * speichern laesst und Punkte aus einer solchen importieren kann, bereitstellen
 * muss.
 * 
 * @author Felix Wenz
 * 
 */
public interface IFilePersistent {

    /**
     * Die Methode fuegt der Punktmenge Punkte aus einer Datei hinzu.
     * 
     * @param filename
     *            der Dateipfad
     * @throws IOException
     *             wird geworfen, wenn ein Fehler beim Lesen der Datei auftritt
     */
    public void importPointsFromFile(String filename) throws IOException;

    /**
     * Die Methode fuegt der Punktmenge Punkte aus einer Datei hinzu.
     * 
     * @param file
     *            die Datei
     * @throws IOException
     *             wird geworfen, wenn ein Fehler beim Lesen der Datei auftritt
     */
    public void importPointsFromFile(File file) throws IOException;

    /**
     * Die Methode speichert die Punkte der Punktmenge in einer Datei.
     * 
     * @param filename
     *            der Dateipfad
     * @throws IOException
     *             wird geworfen, wenn ein Fehler beim Lesen der Datei auftritt
     */
    public void exportToFile(String filename) throws IOException;

    /**
     * Die Methode speichert die Punkte der Punktmenge in einer Datei.
     * 
     * @param file
     *            die Datei
     * @throws IOException
     *             wird geworfen, wenn ein Fehler beim Lesen der Datei auftritt
     */
    public void exportToFile(File file) throws IOException;

    /**
     * Die Methode liefert wahr, wenn ungepeicherte Aenderungen vorhanden sind.
     * 
     * @return wahr, wenn ungespeicherte Aenderungen vorhanden sind
     */
    public boolean hasUnsavedChanges();
}
