/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die zur Darstellung einer Punktmenge im zweidimensionalen Raum benoetigt 
 * werden.
 */
package de.feu.propra12.q8089884.epsav.model;

import java.io.*;
import java.util.*;

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
        Point[] points = pointSet.toArray(new Point[0]);

        // Konturpolygon nur "berechnen", falls mehr als 3 Punkte in der Menge
        // enthalten sind
        if (points.length > 3) {

            // Konturabschnitte in vorsortierter Datenstruktur speichern, um
            // doppelte Punkte zu vermeiden

            // Konturabschnitt Westen-Norden-Osten
            TreeSet<Point> wno = new TreeSet<Point>();
            // Konturabschnitt Westen-Sueden-Osten
            TreeSet<Point> wso = new TreeSet<Point>();

            // Index des letzten Elements der Punktmenge
            int iLast = points.length - 1;

            // Punkt mit der temporaer groessten y-Koordinate von links kommend
            Point leftMaxY = points[0];
            // Punkt mit der temporaer kleinsten y-Koordinate von links kommend
            Point leftMinY = points[0];
            // Punkt mit der temporaer groessten y-Koordinate von rechts kommend
            Point rightMaxY = points[iLast];
            // Punkt mit der temporaer kleinsten y-Koordinate von rechts kommend
            Point rightMinY = points[iLast];

            // Punkte W und O entsprechend einfuegen
            wno.add(leftMinY);
            wso.add(rightMaxY);

            // TODO evtl. Performanz verbessern durch Einsatz einer
            // while-Schleife mit Abbruchbedingung "Faeden ueberlappen"...

            // "Faden" von beiden Seiten an die Punktmenge "pusten"; neue
            // max-/min-Y Punkte in Konturlinien einfuegen
            for (int i = 1; i < iLast; i++) {

                if (points[i].getyPos() < leftMinY.getyPos()) {
                    leftMinY = points[i];
                    wno.add(leftMinY);
                }
                if (points[iLast - i].getyPos() < rightMinY.getyPos()) {
                    rightMinY = points[iLast - i];
                    wno.add(rightMinY);
                }
                if (points[i].getyPos() > leftMaxY.getyPos()) {
                    leftMaxY = points[i];
                    wso.add(leftMaxY);
                }
                if (points[iLast - i].getyPos() > rightMaxY.getyPos()) {
                    rightMaxY = points[iLast - i];
                    wso.add(rightMaxY);
                }
            }

            // oberen und unteren Teil des Konturpolygons aneinander haengen;
            // kein Punkt darf doppelt vorkommen
            // TODO ...eigentlich sollte das Konturpolygon auch doppelte Punkte
            // enthalten dürfen...;-)
            LinkedList<Point> result = new LinkedList<Point>();
            for (Iterator<Point> iterator = wno.iterator(); iterator.hasNext();) {
                Point wnoNext = iterator.next();
                if (!result.contains(wnoNext))
                    result.add(wnoNext);
            }
            for (Iterator<Point> iterator = wso.descendingIterator(); iterator
                    .hasNext();) {
                Point wsoNext = iterator.next();
                if (!result.contains(wsoNext))
                    result.add(wsoNext);
            }

            return result.toArray(new Point[0]);

        } else
            return points;
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
        Point[] contourPolygon = getContourPolygon();

        // konvexe Huelle nur "berechnen", falls mehr als 3 Punkte das
        // Konturpolygon bilden
        if (contourPolygon.length > 3) {

            // Indizes der Extrempunkte feststellen
            int iWest = 0;
            int iNorth = 0;
            int iEast = 0;
            int iSouth = 0;

            for (int i = 1; i < contourPolygon.length; i++) {
                if (contourPolygon[i].getyPos() <= contourPolygon[iNorth]
                        .getyPos())
                    iNorth = i;
                if (contourPolygon[i].getyPos() >= contourPolygon[iSouth]
                        .getyPos())
                    iSouth = i;
                if (contourPolygon[i].getxPos() >= contourPolygon[iEast]
                        .getxPos())
                    iEast = i;
            }

            // "Dellen" aus den Polygonabschnitten entfernen
            eliminateCornersFromPolygonIntercept(contourPolygon, iWest, iNorth);
            eliminateCornersFromPolygonIntercept(contourPolygon, iNorth, iEast);
            eliminateCornersFromPolygonIntercept(contourPolygon, iEast, iSouth);
            eliminateCornersFromPolygonIntercept(contourPolygon, iSouth, iWest);

            // verbliebene Punkte in Liste erfassen und als Array zurueckgeben
            LinkedList<Point> result = new LinkedList<Point>();
            for (int i = 0; i < contourPolygon.length; i++) {
                if (contourPolygon[i] != null)
                    result.add(contourPolygon[i]);
            }

            return result.toArray(new Point[0]);

        } else
            return contourPolygon;
    }

    /**
     * Die Methode entfernt Punkte, die eine Delle auf dem angegebenen
     * Polygonabschnitt darstellen.
     * 
     * @param polygon
     *            das Polygon
     * @param iFirst
     *            Startpunkt des Polygonabschnitts
     * @param iLast
     *            Endpunkt des Polygonabschnitts
     */
    private void eliminateCornersFromPolygonIntercept(Point[] polygon,
            int iFirst, int iLast) {

        int pLength = polygon.length;
        int diff = (iLast - iFirst + pLength) % pLength;

        // es koennen nur Ecken entfernt werden, wenn mehr als drei Punkte den
        // Abschnitt bilden
        if (diff >= 2) {
            // laufe vom zweiten Punkt des Polygonabschnitts bis zum vorletzten
            // Spezialfall Arrayüberlauf --> wird hier stets mittels MODULO
            // abfangen
            for (int i = 1; i < diff; i++) {

                int pos = (iFirst + i) % pLength;
                // ersten existierenden Vorgaenger und Nachfolger merken
                int predecessor = getPredecessorInRingArray(polygon, pos);
                int successor = getSuccessorInRingArray(polygon, pos);

                // mit direktem Vorgeaenger und Nachfolger auf lokale Delle
                // und Kolinearitaet pruefen
                long determinant = determinantABC(polygon[predecessor],
                        polygon[successor], polygon[pos]);

                // bei Kolinearitaet Punkt entfernen
                if (determinant == 0)
                    polygon[pos] = null;

                // bei Delle
                else if (determinant > 0) {
                    // gehe zurueck bis zum ersten Punkt P(j) für den der
                    // Nachfolger des Dellenpunktes P(i) rechts der Geraden
                    // durch P(j-1) und P(j) liegt
                    for (int j = predecessor; j >= iFirst; j--) {
                        if (polygon[j] != null
                                && determinantABC(
                                        polygon[getPredecessorInRingArray(
                                                polygon, j)], polygon[j],
                                        polygon[successor]) > 0) {
                            // entferne alle Punkte zwischen P(j) und dem
                            // Nachfolger von P(i)
                            for (int k = 1; k < (successor - j + pLength)
                                    % pLength; k++) {
                                polygon[(j + k) % pLength] = null;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Die Methode bildet ein Array auf einen Ring ab und gibt zu einer Position
     * die Position des Vorgaengers im Ring zurueck. Gibt es keine anderen
     * Elemente mehr im Ring wird -1 zurueckgegeben.
     * 
     * @param ringArray
     *            das Ringarray
     * @param pos
     *            die Position
     * @return die Position des Vorgaengers
     */
    private int getPredecessorInRingArray(Object[] ringArray, int pos) {
        int result = -1;
        int l = ringArray.length;
        for (int i = 1; i < l; i++) {
            if (ringArray[(pos - i + l) % l] != null) {
                result = (pos - i + l) % l;
                break;
            }
        }
        return result;
    }

    /**
     * Die Methode bildet ein Array auf einen Ring ab und gibt zu einer Position
     * die Position des Nachfolgers im Ring zurueck. Gibt es keine anderen
     * Elemente mehr im Ring wird -1 zurueckgegeben.
     * 
     * @param ringArray
     *            das Ringarray
     * @param pos
     *            die Position
     * @return die Position des Nachfolgers
     */
    private int getSuccessorInRingArray(Object[] ringArray, int pos) {
        int result = -1;
        int l = ringArray.length;
        for (int i = 1; i < l; i++) {
            if (ringArray[(pos + i) % l] != null) {
                result = (pos + i) % l;
                break;
            }
        }
        return result;
    }

    /**
     * Die Methode berechnet die Determinante der Punkte A, B und C. Der
     * Rückgabewert ist < 0 wenn C links, > 0 wenn C rechts und = 0 wenn C auf
     * der Geraden von A nach B liegt. Die Orientierung ist hierbei x von links
     * nach rechts aufsteigend und y von oben nach unten aufsteigend.
     * 
     * @param a
     *            der Punkt A
     * @param b
     *            der Punkt B
     * @param c
     *            der Punkt C
     * @return < 0 wenn C links, > 0 wenn C rechts und = 0 wenn C auf der
     *         Geraden von A nach B liegt
     */
    private long determinantABC(Point a, Point b, Point c) {
        long result = ((long) (c.getxPos() - a.getxPos()) * (long) (c.getyPos() + a
                .getyPos()))
                + ((long) (b.getxPos() - c.getxPos()) * (long) (b.getyPos() + c
                        .getyPos()))
                + ((long) (a.getxPos() - b.getxPos()) * (long) (a.getyPos() + b
                        .getyPos()));
        return result;
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
        unsaved = true;
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
        // Falls der Punkt Teil der Punktmenge ist wird diser zuerst aus der
        // Punktmenge entfernt, dann an die neuen Koordinaten bewegt und
        // anschließend wieder in die Punktmenge eingefuegt. Dies geschieht um
        // die Integritaet der vorsortierten Datenstruktur zu gewaehleisten.
        if (contains(p) && !contains(np)) {
            removePoint(p);
            p.moveTo(np.getxPos(), np.getyPos());
            addPoint(p);
            unsaved = true;
            fireChangedEvent(new PointSetChangedEvent(this,
                    EPointSetChangedMode.POINT_MOVED));
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
        if (p != null && pointSet.remove(p)) {
            unsaved = true;
            fireChangedEvent(new PointSetChangedEvent(this,
                    EPointSetChangedMode.POINT_REMOVED));
        } else
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
        unsaved = false;
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
     * #importPointsFromFile (java.io.File)
     */
    @Override
    public synchronized void importPointsFromFile(File file) throws IOException {
        try {
            // Datei mittels BufferedReader zeilenweise einlesen
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "Cp1252"));
            String line = reader.readLine();
            while (line != null) {
                // pruefen ob Zeile gueltiges Punktformat hat
                try {
                    // Punkt parsen und einfuegen
                    Point p = Point.parsePoint(line);
                    addPoint(p);
                } catch (PointFormatException e) {
                    System.out.println("Die Zeile '" + line
                            + "' weist kein gueltiges Punktformat auf!");
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Lesen der Datei!");
        }
        unsaved = false;
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
     * #exportToFile(java .io.File)
     */
    @Override
    public synchronized void exportToFile(File file) throws IOException {
        try {
            // Datei mittels BufferedWriter schreiben
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "Cp1252"));
            writer.write(toString());
            writer.flush();
        } catch (Exception e) {
            System.out.println("Fehler beim Schreiben der Datei!");
        }
        unsaved = false;
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
