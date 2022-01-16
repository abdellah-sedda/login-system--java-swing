package login.sys;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class Espace extends JFrame implements ActionListener{
    
    JLabel matricule, nom, prenom, adresse, note;
    JTextField matriculeT, nomT, prenomT, adresseT, noteT;
    JMenuBar menuBar;
    JMenu nouveau, enregistrer, supprimer;
    JMenuItem nouveauI, enregistrerI, supprimerI;
    JPanel information;
    JTable table, table1;
    String matriculeTS, nomTS, prenomTS, adresseTS, noteTS;
    Object[] data;
    DefaultTableModel dat;
    JScrollPane scrollPane;
    ArrayList<String> clmn;
    
    Espace() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Espace enseignant");
        getContentPane().setBackground(new Color(0xBDC3C7));
        setBounds(200, 200, 700, 500);
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 25));
        
        menuParameter();
        
        panelInformation();
                
        createTable();
        
        add(information);
        add(scrollPane);
        setVisible(true);
    }

    private void menuParameter() {
        
        menuBar = new JMenuBar();
        
        nouveau = new JMenu("Nouveau");
        enregistrer = new JMenu("Enregistrer");
        supprimer = new JMenu("Supprimer");
        
        nouveauI = new JMenuItem("nouveau");
        enregistrerI = new JMenuItem("enregistrer");
        supprimerI = new JMenuItem("supprimer");
        
        menuBar.add(nouveau);
        menuBar.add(enregistrer);
        menuBar.add(supprimer);
        
        nouveau.add(nouveauI);
        enregistrer.add(enregistrerI);
        supprimer.add(supprimerI);
        
        setJMenuBar(menuBar);
        
        nouveauI.addActionListener(this);
        enregistrerI.addActionListener(this);
        supprimerI.addActionListener(this);
        
    }    

    private void addElementPI() {
        information.add(matricule);
        information.add(matriculeT);
        information.add(nom);
        information.add(nomT);
        information.add(prenom);
        information.add(prenomT);
        information.add(adresse);
        information.add(adresseT);
        information.add(note);
        information.add(noteT);
    }

    private void panelInformation() {
        
        information = new JPanel();
        information.setPreferredSize(new Dimension(350, 200));
        information.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 20));
        information.setBackground(new Color(0xBDC3C7));
        
        matricule = new JLabel("Matricule");
        nom = new JLabel("Nom");
        prenom = new JLabel("Prenom");
        adresse = new JLabel("Adresse");
        note = new JLabel("Note");
        
        matriculeT = new JTextField(20);
        nomT = new JTextField(20);
        prenomT = new JTextField(20);
        adresseT = new JTextField(20);
        noteT = new JTextField(20);
        noteT.setText("0.0");
        
        addElementPI();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
   
        if( e.getSource() == nouveauI ) {
            
            matriculeTS = matriculeT.getText();
            nomTS = matriculeT.getText();
            prenomTS = prenomT.getText();
            adresseTS = adresseT.getText();
            noteTS = noteT.getText();
        
            
            if (
                    !matriculeTS.isEmpty() &&
                    !nomTS.isEmpty() &&
                    !prenomTS.isEmpty() &&
                    !adresseTS.isEmpty() &&
                    !noteTS.isEmpty()) 
            {
                
                data = new Object[]{matriculeTS, nomTS, prenomTS, adresseTS, noteTS };
                dat.addRow(data);
            }
            
        }
        
        if( e.getSource() == enregistrerI ) {
            
            File file = new File("etudiant Informations.txt");
            System.out.println("enregister out of if");
            if (file.exists()) {
                System.out.println("enregistrer successe");
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    for (int i=0; i<dat.getRowCount(); i++) {
                        for (int j=0; j<dat.getColumnCount(); j++) {
                            fileWriter.write(dat.getValueAt(i, j)+"\n");
                        }
                    }
                    fileWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(Espace.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
        if( e.getSource() == supprimerI ) {
            
            int indexSelected = table.getSelectedRow();
            dat.removeRow(indexSelected);
            
        }
        
    }
    
    private void createTable() {
        
        clmn = new ArrayList<>();
        clmn.add("Matricule");
        clmn.add("Nom");
        clmn.add("Prenom");
        clmn.add("Adresse");
        clmn.add("Note");
        
        table = new JTable(){
            public boolean isCellEditable(int data, int columns) {
                return false;
            }
            
            public Component prepareRenderer(TableCellRenderer r, int data, int colmns){
                Component c = super.prepareRenderer(r, data, colmns);
                
                if(data % 2 == 0) {
                    if(data ==0) {
                        c.setBackground(new Color(0xBDC3C7));
                    } else {
                        c.setBackground(Color.white);
                    }
                } else {
                    c.setBackground(Color.lightGray);
                }
                if (isCellSelected(data, colmns)) {
                    c.setBackground(new Color(0x01569B));
                }
                return c;
            }
            
        };
        table.setPreferredScrollableViewportSize(new Dimension(600, 150));
        table.setGridColor(new Color(0xBDC3C7));
        scrollPane = new JScrollPane(table);
        
        dat = (DefaultTableModel) table.getModel();
        
        for (String colmn : clmn) {
            dat.addColumn(colmn);
        }
        
        getEtudiantInforamtionFromFile();

        
    }

    private void getEtudiantInforamtionFromFile() {
        
        File file = new File("etudiant Informations.txt");
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()) {
                    dat.addRow(
                            new Object[] {
                                scanner.nextLine(),
                                scanner.nextLine(),
                                scanner.nextLine(),
                                scanner.nextLine(),
                                scanner.nextLine()
                            });
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Espace.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("file not exist");
        }
    }

}
