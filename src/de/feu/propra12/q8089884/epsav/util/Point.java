/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die im Allgemeinen zum Arbeiten mit/auf Punktmengen benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.util;

/**
 * Die Klasse repraesentiert einen Punkt im zweidimensionalen Raum.
 * 
 * @author Felix Wenz
 * 
 */
public class Point implements Comparable<Point> {

    /**
     * Die x-Koordinate des Punktes.
     */
    private int xPos;

    /**
     * Die y-Koordinate des Punktes.
     */
    private int yPos;

    /**
     * Der Konstruktor für einen Punkt im zweidimensionalen Raum.
     * 
     * @param xPos
     *            die x-Koordinate
     * @param yPos
     *            die y-Koordinate
     */
    public Point(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Die Methode bewegt den Punkt an die uebergebenen Koordinaten.
     * 
     * @param xPos
     *            die x-Koordinate
     * @param yPos
     *            die y-Koordinate
     */
    @SuppressWarnings("hiding")
    public synchronized void moveTo(int xPos, int yPos) {
        setxPos(xPos);
        setyPos(yPos);
    }

    /**
     * Die Methode gibt die x-Koordinate des Punktes zurueck.
     * 
     * @return die x-Koordinate
     */
    public synchronized int getxPos() {
        return xPos;
    }

    /**
     * Die Methode setzt die x-Koordinate des Punktes neu.
     * 
     * @param xPos
     *            die zu setzende x-Koordinate
     */
    public synchronized void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Die Methode gibt die y-Koordinate des Punktes zurueck.
     * 
     * @return die y-Koordinate
     */
    public synchronized int getyPos() {
        return yPos;
    }

    /**
     * Die Methode setzt die x-Koordinate des Punktes neu.
     * 
     * @param xPos
     *            die zu setzende x-Koordinate
     */
    public synchronized void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return xPos + " " + yPos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + xPos;
        result = prime * result + yPos;
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        if (xPos != other.xPos)
            return false;
        if (yPos != other.yPos)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Point o) {
        // primary order on xPos
        if (xPos < o.xPos)
            return -1;
        else if (xPos > o.xPos)
            return 1;
        // secondary order on yPos
        else {
            if (yPos < o.yPos)
                return -1;
            if (yPos > o.yPos)
                return 1;
            else
                return 0;
        }
    }

    /**
     * Die Methode parst einen uebergebenen String in einen Punkt.
     * 
     * @param s
     *            der zu parsende String
     * @return der Punkt
     * @throws PointFormatException
     *             wird geworfen, wenn der String keine guetige
     *             Punktformatierung aufweist
     */
    public static Point parsePoint(String s) throws PointFormatException {
        Point result = null;
        String[] point = s.split(" ");
        try {
            result = new Point(Integer.parseInt(point[0]),
                    Integer.parseInt(point[1]));
        } catch (NumberFormatException e) {
            throw new PointFormatException();
        }
        return result;
    }
}
