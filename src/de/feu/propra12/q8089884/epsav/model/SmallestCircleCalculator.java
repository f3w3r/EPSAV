/**
 * 
 */
package de.feu.propra12.q8089884.epsav.model;

import java.io.IOException;

import de.feu.propra12.interfaces.ISmallestCircleCalculator;
import de.feu.propra12.q8089884.epsav.util.Point;

/**
 * Die Klasse implementiert das ProPra-Testinterface. Gleichzeitig stellt es
 * eine Wrapper-Klasse fuer eine PointSetAlgebra dar. Die Berechnungen werden
 * komplett im Delegate durchgefuehrt und nach aussen durchgereicht.
 * 
 * @author Felix Wenz
 * 
 */
public class SmallestCircleCalculator implements ISmallestCircleCalculator {

    /**
     * die PointSetAlgebra (Delegate)
     */
    private SynchronizedCompletePointSetAlgebra psa = new SynchronizedCompletePointSetAlgebra();

    @Override
    public void addPoint(int x, int y) {
        psa.addPoint(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.interfaces.ISmallestCircleCalculator#addPointsFromArray
     * (int[][])
     */
    @Override
    public void addPointsFromArray(int[][] pointArray) {
        // alle Punkte in Koordinatenarray transformieren
        for (int i = 0; i < pointArray.length; i++) {
            psa.addPoint(pointArray[i][0], pointArray[i][1]);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.interfaces.ISmallestCircleCalculator#addPointsFromFile
     * (java.lang.String)
     */
    @Override
    public void addPointsFromFile(String fileName) throws IOException {
        psa.importPointsFromFile(fileName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.interfaces.ISmallestCircleCalculator#clear()
     */
    @Override
    public void clear() {
        psa.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.interfaces.ISmallestCircleCalculator#getCenterX()
     */
    @Override
    public double getCenterX() {
        return psa.getSECCenterPointX();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.interfaces.ISmallestCircleCalculator#getCenterY()
     */
    @Override
    public double getCenterY() {
        return psa.getSECCenterPointY();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.interfaces.ISmallestCircleCalculator#getConvexHull()
     */
    @Override
    public int[][] getConvexHull() {
        Point[] points = psa.getAllPoints();
        int[][] result = new int[points.length][2];
        // alle Punkte in Koordinatenarray transformieren
        for (int i = 0; i < points.length; i++) {
            result[i][0] = points[i].getxPos();
            result[i][1] = points[i].getyPos();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.interfaces.ISmallestCircleCalculator#getEmail()
     */
    @Override
    public String getEmail() {
        return psa.getEmail();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.interfaces.ISmallestCircleCalculator#getMatrNr()
     */
    @Override
    public String getMatrNr() {
        return psa.getMatrNr();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.interfaces.ISmallestCircleCalculator#getName()
     */
    @Override
    public String getName() {
        return psa.getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.interfaces.ISmallestCircleCalculator#getRadius()
     */
    @Override
    public double getRadius() {
        return psa.getSECRadius();
    }

}
