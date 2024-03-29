/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die für eine GUI zur Darstellung einer Punktmengenalgebra benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.view;

import java.awt.event.*;
import java.io.File;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.feu.propra12.q8089884.epsav.controller.IPointSetOperationListener;
import de.feu.propra12.q8089884.epsav.controller.IPointSetOperationSource;
import de.feu.propra12.q8089884.epsav.model.interfaces.*;
import de.feu.propra12.q8089884.epsav.util.*;

/**
 * Die Klasse stellt das Hauptfenster der EPSAV-Anwendung dar. Ueber ein Menü
 * sind die weiteren Funktionalitäten zugänglich.
 * 
 * @author Felix Wenz
 * 
 */
public class EPSAVMainFrame extends JFrame implements IPointSetOperationSource,
        IPointSetAlgebraView, IPointSetChangedListener, ActionListener,
        MouseListener, MouseMotionListener, KeyListener {

    /**
     * die darzustellende Punktmenge
     */
    private IROPointSetAlgebra pointSetAlgebra = null;

    /**
     * die Liste der Operationsbeobachter
     */
    private LinkedList<IPointSetOperationListener> operationListeners = new LinkedList<IPointSetOperationListener>();

    /**
     * die Datei, in die zuletzt geschrieben wurde
     */
    private File fileLeastSavedTo = null;

    /*----------------------------------------------------------------*/
    /* GUI-Elemente */
    /*----------------------------------------------------------------*/

    private final String VERSION = "1.0.0 beta";

    /**
     * Begruessungsanzeige
     */
    private WelcomePanel pWelcome;

    /**
     * Zeichenflaeche fuer die Punktmenge
     */
    private PointSetAlgebraPanel pPointSetAlgebra;

    /**
     * Menueleiste
     */
    private JMenuBar mb;

    /**
     * Datei-Menue
     */
    private JMenu mFile;

    /**
     * Datei-Menueeintrag: Neu
     */
    private JMenuItem miNew;

    /**
     * Datei-Menueeintrag: Oeffnen
     */
    private JMenuItem miOpen;

    /**
     * Datei-Menueeintrag: Speichern
     */
    private JMenuItem miSave;

    /**
     * Datei-Menueeintrag: Speichern unter
     */
    private JMenuItem miSaveAs;

    /**
     * Datei-Menueeintrag: Beenden
     */
    private JMenuItem miQuit;

    /**
     * Bearbeiten-Menue
     */
    private JMenu mEdit;

    /**
     * Bearbeiten-Menueeintrag: Rueckgaengig
     */
    private JMenuItem miUndo;

    /**
     * Bearbeiten-Menueeintrag: Wiederherstellen
     */
    private JMenuItem miRedo;

    /**
     * Bearbeiten-Untermenue -- ZufaelligePunkte
     */
    private JMenu mRandomPoints;

    /**
     * ZufaelligePunkte-Menueeintrag: 10
     */
    private JMenuItem mi10;

    /**
     * ZufaelligePunkte-Menueeintrag: 50
     */
    private JMenuItem mi50;

    /**
     * ZufaelligePunkte-Menueeintrag: 100
     */
    private JMenuItem mi100;

    /**
     * ZufaelligePunkte-Menueeintrag: 500
     */
    private JMenuItem mi500;

    /**
     * ZufaelligePunkte-Menueeintrag: 1000
     */
    private JMenuItem mi1000;

    /**
     * Hilfe-Menue
     */
    private JMenu mHelp;

    /**
     * Datei-Menueeintrag: Info
     */
    private JMenuItem miInfo;

    /**
     * Öffentlicher Konstruktor des Hauptfensters.
     */
    public EPSAVMainFrame(IRWPointSetAlgebra pointSetAlgebra) {

        super("EPSAV - Expandable Point Set Algebra Viewer");

        // Punktmengenalgebra uebergeben
        this.pointSetAlgebra = pointSetAlgebra;

        // Schliessmechanismus --> Beenden der Anwendung
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });

        // TODO Layout-Manager auswählen und Größe automatisch an PointSetPanel
        // anpassen
        // anpassen
        // Hauptfenster anpassen
        setLayout(null);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Menue initialisieren
        initMenu();

        // Zeichenflaeche fuer Punktmengenalgebra initialisieren
        pPointSetAlgebra = new PointSetAlgebraPanel(pointSetAlgebra);
        pPointSetAlgebra.addMouseListener(this);
        pPointSetAlgebra.addMouseMotionListener(this);
        pPointSetAlgebra.addKeyListener(this);

        // Begruessungsanzeige initialisieren und anzeigen
        pWelcome = new WelcomePanel("EPSAV",
                "Expandable Point Set Algebra Viewer", VERSION);
        add(pWelcome);
        pWelcome.setVisible(true);

        // Hauptfenster sichtbar machen
        setVisible(true);
    }

    /**
     * Die Methode dient dem Initialisieren der Menuestruktur.
     */
    private void initMenu() {

        mb = new JMenuBar();

        // Menue Datei
        mFile = new JMenu("Datei");
        mFile.setMnemonic(KeyEvent.VK_D);

        // --- Datei-Neu
        miNew = new JMenuItem("Neu");
        miNew.setMnemonic(KeyEvent.VK_N);
        miNew.addActionListener(this);
        mFile.add(miNew);

        // Menueeintraege zum Oeffnen und Speichern nur anzeigen, falls das
        // Punktmengenalgebramodell die entsprechenden Operationen unterstuetzt,
        // bzw. das zugehoerige Interface implementiert.
        if (pointSetAlgebra instanceof IFilePersistent) {
            // --- Datei-Oeffnen
            miOpen = new JMenuItem("Öffnen");
            miOpen.setMnemonic(KeyEvent.VK_O);
            miOpen.addActionListener(this);
            mFile.add(miOpen);

            // --- Datei-Speichern
            miSave = new JMenuItem("Speichern");
            miSave.setMnemonic(KeyEvent.VK_S);
            miSave.addActionListener(this);
            miSave.setEnabled(false);
            mFile.add(miSave);

            // --- Datei-SpeichernUnter
            miSaveAs = new JMenuItem("Speichern unter...");
            miSaveAs.setMnemonic(KeyEvent.VK_U);
            miSaveAs.addActionListener(this);
            miSaveAs.setEnabled(false);
            mFile.add(miSaveAs);

            mFile.addSeparator();

        }

        // --- Datei-Beenden
        miQuit = new JMenuItem("Beenden");
        miQuit.setMnemonic(KeyEvent.VK_Q);
        miQuit.addActionListener(this);
        mFile.add(miQuit);

        mb.add(mFile);

        // Menue Bearbeiten
        mEdit = new JMenu("Bearbeiten");
        mEdit.setMnemonic(KeyEvent.VK_E);

        // Menueeintraege zum Rueckgaengigmachen und Wiederherstellen nur
        // anzeigen, falls das Punktmengenalgebramodell die entsprechenden
        // Operationen unterstuetzt, bzw. das zugehoerige Interface
        // implementiert.
        if (pointSetAlgebra instanceof IUndoableRedoable) {
            // --- Bearbeiten-Rueckgaengig
            miUndo = new JMenuItem("Rückgängig");
            miUndo.setMnemonic(KeyEvent.VK_Z);
            miUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                    ActionEvent.CTRL_MASK));
            miUndo.addActionListener(this);
            miUndo.setEnabled(false);
            mEdit.add(miUndo);

            // --- Bearbeiten-Wiederherstellen
            miRedo = new JMenuItem("Wiederherstellen");
            miRedo.setMnemonic(KeyEvent.VK_T);
            miRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                    ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
            miRedo.addActionListener(this);
            miRedo.setEnabled(false);
            mEdit.add(miRedo);

        }

        mEdit.addSeparator();

        // Bearbeiten-Untermenue ZufaelligePunkte
        mRandomPoints = new JMenu("Zufällige Punkte erzeugen");
        mRandomPoints.setMnemonic(KeyEvent.VK_R);

        // --- Bearbeiten --- ZufaelligePunkte-10
        mi10 = new JMenuItem("10");
        mi10.addActionListener(this);
        mRandomPoints.add(mi10);

        // --- Bearbeiten --- ZufaelligePunkte-50
        mi50 = new JMenuItem("50");
        mi50.addActionListener(this);
        mRandomPoints.add(mi50);

        // --- Bearbeiten --- ZufaelligePunkte-100
        mi100 = new JMenuItem("100");
        mi100.addActionListener(this);
        mRandomPoints.add(mi100);

        // --- Bearbeiten --- ZufaelligePunkte-500
        mi500 = new JMenuItem("500");
        mi500.addActionListener(this);
        mRandomPoints.add(mi500);

        // --- Bearbeiten --- ZufaelligePunkte-1000
        mi1000 = new JMenuItem("1000");
        mi1000.addActionListener(this);
        mRandomPoints.add(mi1000);

        mEdit.add(mRandomPoints);

        mb.add(mEdit);

        // Menue Hilfe
        mHelp = new JMenu("Hilfe");
        mHelp.setMnemonic(KeyEvent.VK_H);

        // --- Hilfe-Info
        miInfo = new JMenuItem("Info");
        miInfo.setMnemonic(KeyEvent.VK_I);
        miInfo.addActionListener(this);
        mHelp.add(miInfo);

        mb.add(mHelp);

        // Menueleiste anzeigen
        setJMenuBar(mb);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();

        // Ereignisquelle prüfen und entsprechend reagieren
        if (eventSource == miQuit)
            quit();
        if (eventSource == miNew)
            newPointSet();
        if (eventSource == miOpen)
            open();
        if (eventSource == miSave)
            save();
        if (eventSource == miSaveAs)
            saveAs();
        if (eventSource == miUndo)
            undo();
        if (eventSource == miRedo)
            redo();
        if (eventSource == mi10)
            addRandomPoints(10);
        if (eventSource == mi50)
            addRandomPoints(50);
        if (eventSource == mi100)
            addRandomPoints(100);
        if (eventSource == mi500)
            addRandomPoints(500);
        if (eventSource == mi1000)
            addRandomPoints(1000);
        if (eventSource == miInfo)
            info();

    }

    /**
     * Die Methode schickt dem Controller ein Kommando zum Leeren der
     * Punktmenge; falls ungespeicherte Aenderungen vorliegen wird der Benutzer
     * gefragt, ob diese gespechert werden sollen.
     */
    private void newPointSet() {
        // auf ungespeicherte Aenderungen pruefen
        askForUnsavedChanges();
        // Punktmenge leeren
        fireOperationEvent(new PointSetOperationEvent(this,
                EPointSetOperation.CLEAR_POINTSET, null));
        fileLeastSavedTo = null;
        showPointSetAlgebraPanel();
    }

    /**
     * Die Methode zeigt einen Oeffnen-Dialog an und fragt, ob die Punkte in der
     * Datei der Punktmenge hinzugefuegt werden sollen oder ob eine neue
     * Punktmenge aus dieser generiert werden soll. Falls ungespeicherte
     * Aenderungen im letzten Fall vorhanden sind, wird gefragt, ob diese vorher
     * gespeichert werden sollen.
     */
    private void open() {
        // bei nicht leerer Punktmenge fragen, ob die Punkte in der Datei
        // der Punktmenge hinzugefuegt werden sollen
        if (!pointSetAlgebra.isEmpty()) {
            int addPointsToSet = JOptionPane
                    .showConfirmDialog(
                            this,
                            "Sollen die Punkte aus der Datei der bestehenden Punktmenge hinzugefuegt werden?",
                            "Punktmenge erweitern?",
                            JOptionPane.YES_NO_CANCEL_OPTION);
            // bei "Abbrechen" oeffnen-Methode verlassen
            if (addPointsToSet == JOptionPane.CANCEL_OPTION)
                return;

            // falls Punkte nicht hinzugefuegt werden sollen: neue Punktmenge
            // anlegen
            else if (addPointsToSet == JOptionPane.NO_OPTION)
                newPointSet();
        }

        // Dialog zur Dateiauswahl anzeigen
        JFileChooser fileChooser = new JFileChooser();
        // Filter für points-Dateien verwenden
        FileFilter fileFilter = new FileNameExtensionFilter("Points-Dateien",
                "points");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.showOpenDialog(this);
        File f = fileChooser.getSelectedFile();
        // leere Auswahl abfangen
        if (f != null) {
            Object[] args = { f };
            fireOperationEvent(new PointSetOperationEvent(this,
                    EPointSetOperation.IMPORT_FROM_FILE, args));
            // Setzen der zuletzt genutzten Datei
            fileLeastSavedTo = f;
            showPointSetAlgebraPanel();
        }
    }

    /**
     * Die Methode speichert in der zuletzt beschriebenen Datei. Falls keine
     * solche vorhanden ist wird SpeichernUnter aufgerufen.
     */
    private void save() {
        if ((pointSetAlgebra instanceof IFilePersistent)
                && (fileLeastSavedTo != null)) {
            Object[] args = { fileLeastSavedTo };
            fireOperationEvent(new PointSetOperationEvent(this,
                    EPointSetOperation.EXPORT_TO_FILE, args));
        } else
            saveAs();
    }

    /**
     * Methode zeigt einen Speichern-Dialog an und speichert die Punktmenge
     * entsprechend in der ausgewahlten Datei.
     */
    private void saveAs() {
        // Dialog zur Dateiauswahl anzeigen
        JFileChooser fileChooser = new JFileChooser();
        // Filter für points-Dateien verwenden
        FileFilter fileFilter = new FileNameExtensionFilter("Points-Dateien",
                "points");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.showSaveDialog(this);
        File f = fileChooser.getSelectedFile();
        // leere Auswahl abfangen
        if (f != null) {
            Object[] args = { f };
            fireOperationEvent(new PointSetOperationEvent(this,
                    EPointSetOperation.EXPORT_TO_FILE, args));
            // Setzen der zuletzt genutzten Datei
            fileLeastSavedTo = f;
        }
    }

    /**
     * Methode zum Beenden der Anwendung. Bei ungespeicherten Aenderungen wird
     * der Benutzer gefragt, ob er diese speichern moechte.
     */
    private void quit() {
        askForUnsavedChanges();
        signOffAsOperationSource();
    }

    /**
     * Methode zum Rueckgaengigmachen der letzten Aenderung, falls eine solche
     * vorhanden ist.
     */
    private void undo() {
        if ((pointSetAlgebra instanceof IUndoableRedoable)
                && ((IUndoableRedoable) pointSetAlgebra).isUndoable())
            fireOperationEvent(new PointSetOperationEvent(this,
                    EPointSetOperation.UNDO, null));
    }

    /**
     * Methode zum Wiederholen der zuletzt rueckgaengig gemachten Aenderung,
     * falls eine solche vorhanden ist.
     */
    private void redo() {
        if ((pointSetAlgebra instanceof IUndoableRedoable)
                && ((IUndoableRedoable) pointSetAlgebra).isRedoable())
            fireOperationEvent(new PointSetOperationEvent(this,
                    EPointSetOperation.REDO, null));
    }

    /**
     * Methode zum Erzeugen einer gegebenen Anzahl von zufaelligen Punkten.
     * 
     * @param numberOfPoints
     *            die Anzahl der Punkte
     */
    private void addRandomPoints(int numberOfPoints) {
        // bei nicht leerer Punktmenge fragen, ob die Punkte in der Datei
        // der Punktmenge hinzugefuegt werden sollen
        if (!pointSetAlgebra.isEmpty()) {
            int addPointsToSet = JOptionPane
                    .showConfirmDialog(
                            this,
                            "Sollen die zufälligen Punkte der bestehenden Punktmenge hinzugefuegt werden?",
                            "Punktmenge erweitern?",
                            JOptionPane.YES_NO_CANCEL_OPTION);
            // bei "Abbrechen" zufaelligePunkteHinzufuegen-Methode verlassen
            if (addPointsToSet == JOptionPane.CANCEL_OPTION)
                return;

            // falls Punkte nicht hinzugefuegt werden sollen: neue Punktmenge
            // anlegen
            else if (addPointsToSet == JOptionPane.NO_OPTION)
                newPointSet();
        }
        Object[] args = { numberOfPoints, 10, pPointSetAlgebra.getWidth() - 10,
                10, pPointSetAlgebra.getHeight() - 10 };
        fireOperationEvent(new PointSetOperationEvent(this,
                EPointSetOperation.ADD_RANDOM_POINTS, args));
        showPointSetAlgebraPanel();
    }

    /**
     * Die Methode zeigt Informationen zur Anwendung in einem Nachrichtendialog
     * an.
     */
    private void info() {
        String message = "                    EPSAV\n                        -"
                + "\nExpandable Point Set Algebra Viewer\n\n\nVersion:   "
                + VERSION + "\n\nAutor:      Felix Wenz\n\n               2012";
        JOptionPane.showMessageDialog(this, message, "Info",
                JOptionPane.INFORMATION_MESSAGE, null);
    }

    /**
     * Die Methode meldet diesen View beim Controller als
     * Operationsereignisquelle ab.
     */
    private void signOffAsOperationSource() {
        fireOperationEvent(new PointSetOperationEvent(this,
                EPointSetOperation.OPERATION_SOURCE_QUIT, null));
        // Hauptfenster samt Inhalt(en) aus der Anwendung entfernen
        this.dispose();
    }

    /**
     * Die Methode zeigt dem Benutzer bei ungespeicherten Aenderungen einen
     * Dialog an und sollte aufgerufen werden, bevor ungespeicherte Aenderungen
     * durch eine Operation verloren gehen wuerden.
     */
    private void askForUnsavedChanges() {
        // pruefen, ob Speichern des aktuellen Datenmodells moeglich ist und ob
        // nicht gespeicherte Aenderungen vorliegen
        if ((pointSetAlgebra instanceof IFilePersistent)
                && ((IFilePersistent) pointSetAlgebra).hasUnsavedChanges()) {
            int answer = JOptionPane
                    .showConfirmDialog(
                            this,
                            "Sollen die Änderungen an der bestehenden Punktmenge gespeichert werden?",
                            "Punktmenge speichern?", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
            // bei "Ja" speichern
            if (answer == JOptionPane.YES_OPTION)
                save();
        }
    }

    /**
     * Die Methode zeigt die Zeichenflaeche fuer die Punktmengenalgebra an.
     */
    public void showPointSetAlgebraPanel() {
        // TODO sollte eigentlich private sein...
        remove(pWelcome);
        add(pPointSetAlgebra);
        pPointSetAlgebra.refresh();
    }

    /**
     * Die Methode informiert alle Beobachter ueber das Operationsereignis.
     * 
     * @param e
     *            das Operationsereignis
     */
    private void fireOperationEvent(PointSetOperationEvent e) {
        for (IPointSetOperationListener l : operationListeners) {
            l.firePointSetOperationEvent(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.controller.IPointSetOperationSource#
     * addPointSetOperationListener
     * (de.feu.propra12.q8089884.epsav.controller.IPointSetOperationListener)
     */
    @Override
    public void addPointSetOperationListener(IPointSetOperationListener l) {
        operationListeners.add(l);
        // beim neuen Beobachter als Quelle von Operationsereignissen anmelden
        l.firePointSetOperationEvent(new PointSetOperationEvent(this,
                EPointSetOperation.OPERATION_SOURCE_INIT, null));
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.epsav.controller.IPointSetOperationSource#
     * removePointSetOperationListener
     * (de.feu.propra12.q8089884.epsav.controller.IPointSetOperationListener)
     */
    @Override
    public void removePointSetOperationListener(IPointSetOperationListener l) {
        operationListeners.remove(l);
        // beim gerade entfernten Beobachter als Quelle von
        // Operationsereignissen abmelden
        l.firePointSetOperationEvent(new PointSetOperationEvent(this,
                EPointSetOperation.OPERATION_SOURCE_QUIT, null));
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.feu.propra12.q8089884.model.IPointSetChangeEventListener#
     * firePointSetChangeEvent
     * (de.feu.propra12.q8089884.model.PointSetChangeEvent)
     */
    @Override
    public void firePointSetChangedEvent(PointSetChangedEvent e) {
        refresh();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.feu.propra12.q8089884.epsav.view.IPointSetAlgebraView#setPointSetAlgebra
     * (de.feu.propra12.q8089884.epsav.model.interfaces.IROPointSetAlgebra)
     */
    @Override
    public void setPointSetAlgebra(IROPointSetAlgebra pointSetAlgebra) {
        this.pointSetAlgebra = pointSetAlgebra;
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
     * @see de.feu.propra12.q8089884.epsav.view.IPointSetAlgebraView#refresh()
     */
    @Override
    public void refresh() {
        // TODO evtl.die Art der Aenderung anzeigen (oder auf die Konsole
        // ausgeben)?!

        // Menueeintraege en-/disablen

        // save-Menueeintrag
        if ((pointSetAlgebra instanceof IFilePersistent)
                && ((IFilePersistent) pointSetAlgebra).hasUnsavedChanges()
                && (fileLeastSavedTo != null))
            miSave.setEnabled(true);
        else
            miSave.setEnabled(false);

        // saveAs-Menueeintrag
        if ((pointSetAlgebra instanceof IFilePersistent)
                && !pointSetAlgebra.isEmpty())
            miSaveAs.setEnabled(true);
        else
            miSaveAs.setEnabled(false);

        // undo-Menueeintrag
        if ((miUndo != null)
                && ((IUndoableRedoable) pointSetAlgebra).isUndoable())
            miUndo.setEnabled(true);
        else
            miUndo.setEnabled(false);

        // redo-Menueeintrag
        if ((miRedo != null)
                && ((IUndoableRedoable) pointSetAlgebra).isRedoable())
            miRedo.setEnabled(true);
        else
            miRedo.setEnabled(false);

        pPointSetAlgebra.refresh();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == pPointSetAlgebra) {
            pPointSetAlgebra.requestFocusInWindow();

            int clickCount = e.getClickCount();

            // bei Doppelklick: neuen Punkt an Cursor-Position erzeugen
            if (clickCount == 2) {
                Object[] args = { new Point(e.getX(), e.getY()) };
                fireOperationEvent(new PointSetOperationEvent(this,
                        EPointSetOperation.ADD_POINT, args));
                pPointSetAlgebra.setSelectedPoint(null);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == pPointSetAlgebra) {
            pPointSetAlgebra.requestFocusInWindow();
            int cursorX = e.getX();
            int cursorY = e.getY();

            // bei Klick naechsten Punkt in angegebenem Radius auswaehlen
            Point selectedPoint = pointSetAlgebra.getNearestPointWithinRange(
                    cursorX, cursorY, 5);
            pPointSetAlgebra.setSelectedPoint(selectedPoint);

            // falls ein Punkt ausgewaehlt wurde:
            // den x- und y-Achsenabstand zwischen ausgewaehltem Punkt und
            // Mauszeiger hinterlegen; Punktbewegung ermoeglichen
            if (selectedPoint != null) {
                pPointSetAlgebra.setdX(selectedPoint.getxPos() - cursorX);
                pPointSetAlgebra.setdY(selectedPoint.getyPos() - cursorY);
                pPointSetAlgebra.setUserMovingPoint(true);
            }
            refresh();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        pPointSetAlgebra.setdX(0);
        pPointSetAlgebra.setdY(0);
        pPointSetAlgebra.setUserMovingPoint(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent
     * )
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (pPointSetAlgebra.isUserMovingPoint()) {
            // neue Punktposition aus alter und Mauszeigerposition erzeugen
            Point newPos = new Point(e.getX() + pPointSetAlgebra.getdX(),
                    e.getY() + pPointSetAlgebra.getdY());

            // Überschreiben bestehender Punkte beim Verschieben vermeiden
            if (!pointSetAlgebra.contains(newPos)) {
                // Verschiebekommando an Controller senden
                Object[] args = { pPointSetAlgebra.getSelectedPoint(), newPos };
                fireOperationEvent(new PointSetOperationEvent(this,
                        EPointSetOperation.MOVE_POINT, args));
                // ausgewaehlten Punkt verschieben
                pPointSetAlgebra.getSelectedPoint().moveTo(newPos.getxPos(),
                        newPos.getyPos());
                refresh();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // bei Backspace- oder Entf-Tasteneingabe wird der ausgewaehlte Punkt
        // (falls vorhanden) geloescht
        if (((keyCode == KeyEvent.VK_BACK_SPACE) || (keyCode == KeyEvent.VK_DELETE))
                && (pPointSetAlgebra.getSelectedPoint() != null)) {
            Object[] args = { pPointSetAlgebra.getSelectedPoint() };
            fireOperationEvent(new PointSetOperationEvent(this,
                    EPointSetOperation.REMOVE_POINT, args));
            pPointSetAlgebra.setSelectedPoint(null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }
}
