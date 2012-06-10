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
     * zeigt an, ob der Benutzer gerade einen Punkt verschiebt
     */
    private boolean userMovingPoint = false;

    /**
     * der Abstand zwischen ausgewahltem Punkt und Mauszeiger auf der x-Achse
     * --> P.x - Cursor.x
     */
    private int dX = 0;

    /**
     * der Abstand zwischen ausgewahltem Punkt und Mauszeiger auf der x-Achse
     * --> P.y - Cursor.y
     */
    private int dY = 0;

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
        Point[] convexHull = pointSetAlgebra.getConvexHull();
        // Punkte des Polygons verbinden
        if (convexHull != null)
            for (int i = 0; i < convexHull.length - 1; i++) {
                // Linien zum gerade ausgewaehlten Punkt sollen blau gezeichnet
                // werden, wenn dieser gerade bewegt wird
                if (isUserMovingPoint()
                        && ((convexHull[i] == selectedPoint) || (convexHull[i + 1] == selectedPoint)))
                    g2.setColor(Color.BLUE);
                else
                    g2.setColor(Color.BLACK);
                g2.drawLine(convexHull[i].getxPos(), convexHull[i].getyPos(),
                        convexHull[i + 1].getxPos(),
                        convexHull[i + 1].getyPos());

                // Polygon schliessen
                if (convexHull.length > 2) {
                    if (isUserMovingPoint()
                            && ((convexHull[convexHull.length - 1] == selectedPoint) || (convexHull[0] == selectedPoint)))
                        g2.setColor(Color.BLUE);
                    else
                        g2.setColor(Color.BLACK);
                    g2.drawLine(convexHull[convexHull.length - 1].getxPos(),
                            convexHull[convexHull.length - 1].getyPos(),
                            convexHull[0].getxPos(), convexHull[0].getyPos());
                }
            }
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
     * Die Methode setzt den aktuell ausgewaehlten Punkt.
     * 
     * @param selectedPoint
     *            der ausgewaehlte Punkt
     */
    public void setSelectedPoint(Point selectedPoint) {
        this.selectedPoint = selectedPoint;
    }

    /**
     * Die Methode gibt den Abstand zwischen dem ausgewahlten Punkt und dem
     * Mauszeiger auf der x-Achse zurueck.
     * 
     * @return der Abstand zwischen ausgewaehltem Punkt und Mauszeiger auf der
     *         x-Achse
     */
    public int getdX() {
        return dX;
    }

    /**
     * Die Methode setzt den Abstand zwischen dem ausgewahlten Punkt und dem
     * Mauszeiger auf der x-Achse.
     * 
     * @param dX
     *            der Abstand
     */
    public void setdX(int dX) {
        this.dX = dX;
    }

    /**
     * Die Methode gibt den Abstand zwischen dem ausgewahlten Punkt und dem
     * Mauszeiger auf der y-Achse zurueck.
     * 
     * @return der Abstand zwischen ausgewaehltem Punkt und Mauszeiger auf der
     *         y-Achse
     */
    public int getdY() {
        return dY;
    }

    /**
     * Die Methode setzt den Abstand zwischen dem ausgewahlten Punkt und dem
     * Mauszeiger auf der y-Achse.
     * 
     * @param dY
     *            der Abstand
     */
    public void setdY(int dY) {
        this.dY = dY;
    }

    /**
     * Die Methode gibt an, ob gerade ein Punkt bewegt wird.
     * 
     * @return wahr, wenn gerade ein Punkt bewegt wird
     */
    public boolean isUserMovingPoint() {
        return userMovingPoint;
    }

    /**
     * Die Methode legt fest, ob gerade ein Punkt bewegt wird.
     * 
     * @param userMovingPoint
     *            wahr, wenn angezeigt werden soll, dass gerade ein Punkt bewegt
     *            wird
     */
    public void setUserMovingPoint(boolean userMovingPoint) {
        this.userMovingPoint = userMovingPoint;
    }
}
