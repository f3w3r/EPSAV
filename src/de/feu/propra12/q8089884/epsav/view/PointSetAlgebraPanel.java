/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die für eine GUI zur Darstellung einer Punktmengenalgebra benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.view;

import java.awt.*;

import javax.swing.JPanel;

import de.feu.propra12.q8089884.epsav.model.interfaces.IROPointSetAlgebra;
import de.feu.propra12.q8089884.epsav.util.Point;

/**
 * Die Klasse stellt die Zeichenflaeche fuer eine Punktmengenalgebra dar.
 * 
 * @author Felix Wenz
 * 
 */
public class PointSetAlgebraPanel extends JPanel implements
        IPointSetAlgebraView {

    /**
     * die darzustellende Punktmenge
     */
    private IROPointSetAlgebra pointSetAlgebra = null;

    /**
     * der aktuell ausgewaehlte Punkt
     */
    private Point selectedPoint = null;

    /**
     * Der Konstruktor fuer eine Punktmengenanzeige; ein Datenmodell wird als
     * Parameter uebergeben.
     * 
     * @param pointSetAlgebra
     *            die darzustellende Punktmengenalgebra
     */
    public PointSetAlgebraPanel(IROPointSetAlgebra pointSetAlgebra) {
        super();
        this.pointSetAlgebra = pointSetAlgebra;

        setLayout(null);
        setSize(700, 450);
        setBounds(80, 45, 850, 450);
        setVisible(true);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawPointSetAlgebra(g2);
    }

    /**
     * Die Methode zeichnet die Punktmengenalgebra in ein Grafikobjekt.
     * 
     * @param g2
     *            das Grafikobjekt
     */
    private void drawPointSetAlgebra(Graphics2D g2) {
        drawPointSet(g2);
        drawConvexHull(g2);
        drawTriangulationLines(g2);
        drawSmallestEnclosingCircle(g2);
    }

    /**
     * Die Methode zeichnet die Elemente der Punktmenge unter Beruecksichtigung
     * des aktuell ausgewaehlten Punktes.
     * 
     * @param g2
     *            das Grafikobjekt
     */
    private void drawPointSet(Graphics2D g2) {
        // alle Punkte zeichnen
        for (Point p : pointSetAlgebra.getAllPoints()) {
            if (p.equals(selectedPoint)) {
                // den ausgewaehlten Punkt groesser und blau darstellen
                g2.setColor(Color.BLUE);
                g2.fillOval(selectedPoint.getxPos() - 5,
                        selectedPoint.getyPos() - 5, 11, 11);
            } else {
                g2.setColor(Color.BLACK);

                g2.fillOval(p.getxPos() - 3, p.getyPos() - 3, 7, 7);
            }
        }
    }

    /**
     * Die Methode zeichnet die konvexe Huelle.
     * 
     * @param g2
     *            das Grafikobjekt
     */
    private void drawConvexHull(Graphics2D g2) {
        // TODO Auto-generated method stub
        // TODO zum Zeichnen wäre eine doppelt verkettete Liste von Punkten am
        // besten/einfachsten
        // TODO wenn gerade ein Punkt bewegt wird, sollen dieser, sowie die zu
        // ihm führenden Kanten, rot dargestellt werden (userMovingPoint)
    }

    /**
     * Die Methode zeichnet die Triangulationslinien.
     * 
     * @param g2
     *            das Grafikobjekt
     */
    private void drawTriangulationLines(Graphics2D g2) {
        // TODO Auto-generated method stub

    }

    /**
     * Die Methode zeichnet den kleinsten umfassenden Kreis.
     * 
     * @param g2
     *            das Grafikobjekt
     */
    private void drawSmallestEnclosingCircle(Graphics2D g2) {
        if (!pointSetAlgebra.isEmpty()) {
            g2.setColor(Color.BLACK);
            int radius = (int) pointSetAlgebra.getSECRadius();
            g2.drawOval((int) pointSetAlgebra.getSECCenterPointX() - radius,
                    (int) pointSetAlgebra.getSECCenterPointY() - radius,
                    radius * 2, radius * 2);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.view.IPointSetView#refresh()
     */
    @Override
    public void refresh() {
        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.view.IPointSetAlgebraView#getPointSetAlgebra
     * ()
     */
    @Override
    public IROPointSetAlgebra getPointSetAlgebra() {
        return pointSetAlgebra;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.view.IPointSetView#setPointSetModel(de
     * .feu.propra12.q8089884.epsav.model.IROPointSet)
     */
    @Override
    public void setPointSetAlgebra(IROPointSetAlgebra pointSetAlgebra) {
        this.pointSetAlgebra = pointSetAlgebra;
    }

    /**
     * Die Methode gibt den aktuell ausgewaehlten Punkt zurueck, falls
     * vorhanden.
     * 
     * @return der ausgewaehlte Punkt
     */
    public Point getSelectedPoint() {
        return selectedPoint;
    }

    /**
     * Die Methode setzt den aktuelle ausgewaehlten Punkt.
     * 
     * @param selectedPoint
     *            der ausgewaehlte Punkt
     */
    public void setSelectedPoint(Point selectedPoint) {
        this.selectedPoint = selectedPoint;
    }
}
