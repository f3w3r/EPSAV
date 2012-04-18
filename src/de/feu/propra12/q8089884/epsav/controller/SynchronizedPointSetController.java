/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die zur Steuerung einer Punktmenge benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.controller;

import java.util.LinkedList;

import de.feu.propra12.q8089884.epsav.model.SynchronizedCompletePointSetAlgebra;
import de.feu.propra12.q8089884.epsav.model.interfaces.IRWPointSet;
import de.feu.propra12.q8089884.epsav.util.*;

/**
 * Die Klasse repraesentiert den Controller fuer eine Punktmenge. Dieser
 * koordiniert Aenderungsoperationen aus unterschiedlichen Quellen und reagiert
 * auf auftretende Fehler/Ausnahmen.
 * 
 * @author Felix Wenz
 * 
 */
public class SynchronizedPointSetController implements
        IPointSetOperationListener {

    /**
     * die Liste der Operationsereignisquellen, die der Controller beobachtet
     */
    private LinkedList<IPointSetOperationSource> operationsSources = new LinkedList<IPointSetOperationSource>();

    /**
     * die vom Controller zu verwaltende Punktmenge
     */
    private SynchronizedCompletePointSetAlgebra pointSetAlgebra;

    /**
     * Der Konstruktor fuer einen Punktmengencontroller. Die zu verwaltende
     * Punktmengenalgebra wird als Parameter uebergeben.
     * 
     * @param pointSetAlgebra
     *            die Punktmengenalgebra
     */
    public SynchronizedPointSetController(
            SynchronizedCompletePointSetAlgebra pointSetAlgebra) {
        this.pointSetAlgebra = pointSetAlgebra;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.controller.IPointSetOperationListener#
     * firePointSetOperationEvent
     * (de.feu.propra12.q8089884.epsav.util.PointSetOperationEvent)
     */
    @Override
    public void firePointSetOperationEvent(PointSetOperationEvent e) {
        Object[] args = e.getArgs();
        try {
            switch (e.getOperation()) {
            case ADD_POINT:
                pointSetAlgebra.addPoint((Point) args[0]);
                break;
            case MOVE_POINT:
                pointSetAlgebra.movePoint((Point) args[0], (Point) args[1]);
                break;
            case REMOVE_POINT:
                pointSetAlgebra.removePoint((Point) args[0]);
                break;
            case CLEAR_POINTSET:
                pointSetAlgebra.clear();
                break;
            case IMPORT_FROM_FILE:
                pointSetAlgebra.importPointsFromFile((String) args[0]);
                break;
            case EXPORT_FROM_FILE:
                pointSetAlgebra.exportToFile((String) args[0]);
                break;
            case UNDO:
                pointSetAlgebra.undo();
                break;
            case REDO:
                pointSetAlgebra.redo();
                break;
            case OPERATION_SOURCE_INIT:
                operationsSources.add((IPointSetOperationSource) e.getSource());
                break;
            case OPERATION_SOURCE_QUIT:
                operationsSources.remove((IPointSetOperationSource) e
                        .getSource());
                // Anwendung beenden, falls keine Quellen fuer
                // Operationsereignisse mehr vorhanden sind
                if (operationsSources.isEmpty())
                    System.exit(0);
                break;
            default:
                throw new PointSetException("Ungültige Operation!");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Die Methode gibt die verwaltete Punktmengenalgebra zurueck.
     * 
     * @return die Punktmengenalgebra
     */
    public synchronized IRWPointSet getPointSetAlgebra() {
        return pointSetAlgebra;
    }

    /**
     * Die Methode legt die zu verwaltende Punktmengenalgebra fest.
     * 
     * @param pointSetAlgebra
     *            die Punktmengenalgebra
     */
    public synchronized void setPointSetAlgebra(
            SynchronizedCompletePointSetAlgebra pointSetAlgebra) {
        this.pointSetAlgebra = pointSetAlgebra;
    }

}
