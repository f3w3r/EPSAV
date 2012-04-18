/**
 * Das Paket beinhaltet alle (abstrakten) Klassen, Interfaces und Enums, 
 * die für eine GUI zur Darstellung einer Punktmenge benoetigt werden.
 */
package de.feu.propra12.q8089884.epsav.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Die Klasse stellt eine Begruessungsseite für beliebige Programme dar.
 * 
 * @author Felix Wenz
 * 
 */
public class WelcomePanel extends JPanel {

    /**
     * Die Kennzahl der Klasse zur (De)serialisierung.
     */
    private static final long serialVersionUID = 1929508199462143086L;

    /**
     * Die Name-Beschriftung.
     */
    public JLabel lName;

    /**
     * Die Info-Beschriftung.
     */
    public JLabel lInfo;

    /**
     * Die Versions-Beschriftung.
     */
    public JLabel lVersion;

    /**
     * Oeffentlicher Konstruktor der Begrüßungsseite.
     */
    public WelcomePanel() {

        // TODO Größe des Begrueßungspanels mit Layoutmanager umsetzen und
        // variabel machen.

        super();

        setLayout(null);
        setSize(700, 450);
        setBounds(80, 45, 850, 450);
        setVisible(true);

        // Beschriftungen initialisieren
        lName = new JLabel();
        lName.setBounds(290, 0, 400, 100);
        lName.setFont(new Font(Font.SERIF, Font.BOLD, 80));
        lName.setForeground(Color.BLUE);
        lName.setHorizontalTextPosition(SwingConstants.CENTER);
        add(lName);

        lInfo = new JLabel();
        lInfo.setBounds(70, 150, 800, 100);
        lInfo.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        lInfo.setForeground(Color.GRAY);
        lInfo.setHorizontalTextPosition(SwingConstants.CENTER);
        add(lInfo);

        lVersion = new JLabel();
        lVersion.setBounds(390, 350, 100, 100);
        lVersion.setForeground(Color.LIGHT_GRAY);
        lVersion.setHorizontalTextPosition(SwingConstants.CENTER);
        add(lVersion);
    }

    /**
     * Oeffentlicher Konstruktor der Begrüßungsseite.
     * 
     * @param name
     *            Text des Namenfelds
     * @param info
     *            Text des Infofelds
     * @param version
     *            Text des Versionfelds
     */
    public WelcomePanel(String name, String info, String version) {
        this();
        lName.setText(name);
        lInfo.setText(info);
        lVersion.setText(version);
    }

}
