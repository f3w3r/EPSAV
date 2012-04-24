/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die zur Darstellung einer Punktmenge im zweidimensionalen Raum benoetigt 
 * werden.
 */
package de.feu.propra12.q8089884.epsav.model;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeSet;

import de.feu.propra12.q8089884.epsav.model.interfaces.*;
import de.feu.propra12.q8089884.epsav.util.*;

/**
 * Die Klasse stellt eine erweiterbare synchronisierte Punktmengenalgebra dar.
 * Eine Punktmenge kann verwaltet werden; folgene Operationen sind auf dieser
 * Punktmenge moeglich:
 * 
 * 1. Konturpolygon berechnen
 * 
 * 2. konvexe Huelle (KH) berechnen
 * 
 * 3. kleinsten umfassenden Kreis (KUK) berechnen
 * 
 * 4. Triangulationslinien anzeigen
 * 
 * 
 * Auch sind folgende Funktionen fuer das Arbeiten mit der Punktmenge vorhanden:
 * 
 * - Speichern/Laden aus Datei
 * 
 * - informieren von Beobachtern des Datenmodells
 * 
 * 
 * @author Felix Wenz
 * 
 */
public class SynchronizedCompletePointSetAlgebra implements IRWPointSetAlgebra,
        IPointSetChangedSource, IFilePersistent, IUndoableRedoable,
        IModuleMetaInformationProvider {

    /**
     * Die Emailadresse des Programmierers
     */
    private final String EMAIL = "felix.wenz@web.de";

    /**
     * Die Matrikelnummer des Programmierers
     */
    private final String MATRNR = "8089884";

    /**
     * Der Name des Programmierers
     */
    private final String NAME = "Felix Wenz";

    // TODO effizientere Datenstruktur für Punktmenge (evtl. selbst anpassen)
    /**
     * die Punktmenge
     */
    private TreeSet<Point> pointSet = new TreeSet<Point>();

    /**
     * die Liste der Aenderungsbeobachter
     */
    private LinkedList<IPointSetChangedListener> listeners = new LinkedList<IPointSetChangedListener>();

    /**
     * wahr, falls seit der letzten Aenderung nicht gespeichert wurde
     */
    private boolean unsaved = false;

    /**
     * Der Standardkonstruktor fuer eine Punktmengenalgebra. Erzeugt eine leere
     * Punktmenge.
     */
    public SynchronizedCompletePointSetAlgebra() {

    }

    /**
     * Konstruktor, der die uebergebenen Punkte in die Punktmenge einfuegt.
     * 
     * @param points
     *            die Punkte
     */
    public SynchronizedCompletePointSetAlgebra(Point[] points) {
        addPoints(points);
    }

    /**
     * Konstruktor, der Punkte aus der uebergebenen Datei in die Punktmenge
     * importiert, falls die Datei lesbar ist.
     * 
     * @param filename
     *            der Name der Datei
     * @throws IOException
     *             wird geworfen, falls Fehler beim Lesen der Datei auftreten
     */
    public SynchronizedCompletePointSetAlgebra(String filename)
            throws IOException {
        importPointsFromFile(filename);
    }

    /**
     * Konstruktor, der Punkte aus der uebergebenen Datei in die Punktmenge
     * importiert, falls die Datei lesbar ist.
     * 
     * @param file
     *            die Datei
     * @throws IOException
     *             wird geworfen, falls Fehler beim Lesen der Datei auftreten
     */
    public SynchronizedCompletePointSetAlgebra(File file) throws IOException {
        importPointsFromFile(file);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IROPointSet#getPoints()
     */
    @Override
    public synchronized Point[] getAllPoints() {
        // erzeuge ein Array, das Kopien der Punkte in der Punktmenge beinhaltet
        // und gebe dieses zurueck
        Point[] origin = pointSet.toArray(new Point[0]);
        Point[] result = new Point[origin.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Point(origin[i].getxPos(), origin[i].getyPos());
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IROPointSet#contains(
     * de.feu.propra12.q8089884.epsav.util.Point)
     */
    @Override
    public synchronized boolean contains(Point p) {
        return pointSet.contains(p);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IROPointSet#contains(int,
     * int)
     */
    @Override
    public synchronized boolean contains(int xPos, int yPos) {
        return pointSet.contains(new Point(xPos, yPos));
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.model.interfaces.IROPointSet#
     * getNearestPointWithinRange (int, int, int)
     */
    @Override
    public synchronized Point getNearestPointWithinRange(int xPos, int yPos,
            int range) {
        Point tempPoint = new Point(xPos, yPos);
        // auf direkten Treffer reagieren
        if (contains(tempPoint))
            return tempPoint;
        // Schleife fuer den Radius
        for (int i = 1; i <= range; i++) {
            // Schleife fuer horizontale Perimeter
            for (int j = -i; j <= i; j++) {
                // alle um -1 auf der y-Achse verschobenen Punkte pruefen
                tempPoint.moveTo(xPos + j, yPos - i);
                if (contains(tempPoint))
                    return tempPoint;
                // alle um +1 auf der y-Achse verschobenen Punkte pruefen
                tempPoint.moveTo(xPos + j, yPos + i);
                if (contains(tempPoint))
                    return tempPoint;
            }
            // Schleife fuer vertikale Perimeter
            for (int j = -i + 1; j < i; j++) {
                // alle um -1 auf der x-Achse verschobenen (inneren) Punkte
                // pruefen
                tempPoint.moveTo(xPos - i, yPos + j);
                if (contains(tempPoint))
                    return tempPoint;
                // alle um +1 auf der x-Achse verschobenen (inneren) Punkte
                // pruefen
                tempPoint.moveTo(xPos + i, yPos + j);
                if (contains(tempPoint))
                    return tempPoint;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IROPointSet#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IConvexHullCalculator
     * #getContourPolygon()
     */
    @Override
    public synchronized Point[] getContourPolygon() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IConvexHullCalculator
     * #getConvexHull()
     */
    @Override
    public synchronized Point[] getConvexHull() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.model.interfaces.
     * ISmallestEnclosingCircleCalculator#getCenterPointX()
     */
    @Override
    public synchronized double getSECCenterPointX() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.model.interfaces.
     * ISmallestEnclosingCircleCalculator#getCenterPointY()
     */
    @Override
    public synchronized double getSECCenterPointY() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.model.interfaces.
     * ISmallestEnclosingCircleCalculator#getRadius()
     */
    @Override
    public synchronized double getSECRadius() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet#addPoint(
     * de.feu.propra12.q8089884.epsav.util.Point)
     */
    @Override
    public synchronized void addPoint(Point p) {
        pointSet.add(p);
        fireChangedEvent(new PointSetChangedEvent(this,
                EPointSetChangedMode.POINT_ADDED));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet#addPoint(int,
     * int)
     */
    @Override
    public synchronized void addPoint(int xPos, int yPos) {
        addPoint(new Point(xPos, yPos));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet#addPoints
     * (de.feu.propra12. q8089884.epsav.util.Point[])
     */
    @Override
    public synchronized void addPoints(Point[] points) {
        // nur Punkte hinzufuegen und Beobachter informieren, falls Punkte
        // vorhanden sind
        if (points.length > 0) {
            for (Point point : points) {
                pointSet.add(point);
            }
            fireChangedEvent(new PointSetChangedEvent(this,
                    EPointSetChangedMode.POINT_ADDED));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet#movePoint
     * (de.feu.propra12.q8089884.epsav.util.Point,
     * de.feu.propra12.q8089884.epsav.util.Point)
     */
    @Override
    public void movePoint(Point p, Point np) throws PointSetException {
        // falls der Punkt Teil der Punktmenge ist wird dieser geloescht und ein
        // Punkt mit den neuen Koordinaten zur Punktmenge hinzugefuegt
        if (contains(p) && !contains(np)) {
            removePoint(p);
            addPoint(np);
            fireChangedEvent(new PointSetChangedEvent(this,
                    EPointSetChangedMode.POINT_MOVED));
            // ein direktes Bewegen des Punktes ist nicht moeglich, da die
            // Datenstruktur der Punktmenge vorsortiert speichert und diese
            // Bedingung sonst verletzt wuerde!
        } else
            throw new PointSetException(
                    "Der übergebene Punkt liegt nicht in der Punktmenge!");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet#movePoint
     * (de.feu.propra12. q8089884.epsav.util.Point, int, int)
     */
    @Override
    public synchronized void movePoint(Point p, int xPos, int yPos)
            throws PointSetException {
        movePoint(p, new Point(xPos, yPos));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet#movePoint
     * (int, int, int, int)
     */
    @Override
    public synchronized void movePoint(int xPos, int yPos, int newXPos,
            int newYPos) throws PointSetException {
        movePoint(new Point(xPos, yPos), newXPos, newYPos);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet#removePoint
     * (de.feu.propra12 .q8089884.epsav.util.Point)
     */
    @Override
    public synchronized void removePoint(Point p) throws PointSetException {
        if (p != null && pointSet.remove(p))
            fireChangedEvent(new PointSetChangedEvent(this,
                    EPointSetChangedMode.POINT_REMOVED));
        else
            throw new PointSetException(
                    "Der übergebene Punkt liegt nicht in der Punktmenge!");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet#removePoint
     * (int, int)
     */
    @Override
    public synchronized void removePoint(int xPos, int yPos)
            throws PointSetException {
        removePoint(new Point(xPos, yPos));
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet#clear()
     */
    @Override
    public synchronized void clear() {
        pointSet.clear();
        fireChangedEvent(new PointSetChangedEvent(this,
                EPointSetChangedMode.POINTSET_CLEARED));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IUndoableRedoable#undo()
     */
    @Override
    public synchronized void undo() throws IllegalStateException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IUndoableRedoable#redo()
     */
    @Override
    public synchronized void redo() throws IllegalStateException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.model.interfaces.IFilePersistent#
     * hasUnsavedChanges()
     */
    @Override
    public boolean hasUnsavedChanges() {
        return unsaved;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IFilePersistentPointSet
     * #importPointsFromFile (java.lang.String)
     */
    @Override
    public synchronized void importPointsFromFile(String filename)
            throws IOException {
        importPointsFromFile(new File(filename));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IFilePersistentPointSet
     * #importPointsFromFile (java.io.File)
     */
    @Override
    public synchronized void importPointsFromFile(File file) throws IOException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IFilePersistentPointSet
     * #exportToFile(java .lang.String)
     */
    @Override
    public synchronized void exportToFile(String filename) throws IOException {
        exportToFile(new File(filename));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IFilePersistentPointSet
     * #exportToFile(java .io.File)
     */
    @Override
    public synchronized void exportToFile(File file) throws IOException {
        // TODO Auto-generated method stub
        unsaved = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public synchronized String toString() {
        // TODO Stringkonkatenation mittels BufferedWriter umsetzen
        String result = "";
        for (Point p : pointSet) {
            result += p + "\n";
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.model.interfaces.
     * IModuleMetaInformationProvider#getEmail()
     */
    @Override
    public String getEmail() {
        return EMAIL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.model.interfaces.
     * IModuleMetaInformationProvider#getMatrNr()
     */
    @Override
    public String getMatrNr() {
        return MATRNR;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.model.interfaces.
     * IModuleMetaInformationProvider#getName()
     */
    @Override
    public String getName() {
        return NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IPointSetChangeEventSource
     * # addPointSetChangeEventListener
     * (de.feu.propra12.q8089884.epsav.model.interfaces
     * .IPointSetChangeEventListener)
     */
    @Override
    public synchronized void addPointSetChangedListener(
            IPointSetChangedListener l) {
        listeners.add(l);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IPointSetChangeEventSource
     * # removePointSetChangeEventListener
     * (de.feu.propra12.q8089884.epsav.model.
     * interfaces.IPointSetChangeEventListener)
     */
    @Override
    public synchronized void removePointSetChangedListener(
            IPointSetChangedListener l) {
        listeners.remove(l);
    }

    /**
     * Die Methode informiert alle Beobachter ueber das Aenderungsereignis.
     * 
     * @param e
     *            das Aenderungsereignis
     */
    private synchronized void fireChangedEvent(PointSetChangedEvent e) {
        for (IPointSetChangedListener l : listeners) {
            l.firePointSetChangedEvent(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IUndoableRedoable#isUndoable
     * ()
     */
    @Override
    public boolean isUndoable() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.model.interfaces.IUndoableRedoable#isRedoable
     * ()
     */
    @Override
    public boolean isRedoable() {
        // TODO Auto-generated method stub
        return false;
    }
}
