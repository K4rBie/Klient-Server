/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Piotr
 */
package src.main.klient_serwer;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class main1  {

    static JButton IPMainB ;
    static JButton FileB ;
    static JButton PortB ;
    static JButton GivePeselB ;
    static JButton FinishPeselB ;
    static JButton ConnectMain;
    static JButton POM;
    static JTextField IPMainT;
    static JTextField FileT;
    static JTextField PortT;
    static JTextField GivePeselT;
    static JLabel AlarmL;
    
    static String IPMain="";
    static int portMain=0;
    static String FileMain="";
    static String Pesel;
    static int koniec;
    static ArrayList mapaMain;
    
    
    static PrintStream output; 
    static ObjectInputStream osoba;
    
    public static void main(String []args)  throws IOException
    {
        
        mapaMain=new ArrayList();
        koniec=2;
        
       
        
        JFrame GuiMain = new JFrame();
        FrameStart(GuiMain);
        //GuiMain.setVisible(true); 
       
        
         IPMainB = new JButton("Zapisz IP Serwera");
        JTextField IPMainT = new JTextField("Wpisz IP");
        
        FileB = new JButton("zapisz ścieżkę pliku");
        JTextField FileT = new JTextField("Scieżka");
        
        PortB = new JButton("Zapisz port");
        JTextField PortT = new JTextField("Port");
        
        GivePeselB = new JButton("Zapisz podany pesel");
        FinishPeselB = new JButton("Zakończ podawanie peseli");
        JTextField GivePeselT = new JTextField("   Pesel   ",11);
        
        ConnectMain = new JButton("Połącz sie");
       
        JLabel AlarmL = new JLabel("Alarm");
        
        POM = new JButton("");
        
        
        
        
        Container paneMain = GuiMain.getContentPane();
        
        paneMain.setLayout(new GridLayout(4,3,5,5));
        paneMain.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        paneMain.setBackground(new Color(255,255,0));
        paneMain.add(IPMainT);
        paneMain.add(PortT);
        paneMain.add(FileT);
        paneMain.add(IPMainB);
        paneMain.add(PortB);
        paneMain.add(FileB);
        paneMain.add(GivePeselT);
        paneMain.add(GivePeselB);
        paneMain.add(FinishPeselB);
        paneMain.add(ConnectMain);
        paneMain.add(AlarmL);
        paneMain.add(POM);
        
        
        ConnectMain.setEnabled(false);
        GivePeselB.setEnabled(false);
        FinishPeselB.setEnabled(false);
        
       
        
        IPMainB.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        IPMain = IPMainT.getText();
         if(IPMain!=""&&portMain!=0&&FileMain!="")
        {
        ConnectMain.setEnabled(true);
        }
        }});
        
        PortB.addActionListener((ActionEvent e) -> {
            portMain=Integer.parseInt(PortT.getText());
             if(IPMain!=""&&portMain!=0&&FileMain!="")
        {
        ConnectMain.setEnabled(true);
        }            
        });
        FileB.addActionListener((ActionEvent e) -> {
            FileMain=FileT.getText();
             if(IPMain!=""&&portMain!=0&&FileMain!="")
        {
        ConnectMain.setEnabled(true);
        }
        });
        GivePeselB.addActionListener((ActionEvent e) -> {
            try {
                AlarmL.setText("Alarm");
                Pesel=GivePeselT.getText();
                output.print(Pesel+"\n");
                Person person;
                person = (Person)osoba.readObject();
                if(person==null)
                {AlarmL.setText("Nie ma takiego peselu w bazie");}
                else
                mapaMain.add(person);
                
            } catch (IOException ex) {
                AlarmL.setText("Błąd pesel może nie istnieć");
            } catch (ClassNotFoundException ex) {
                AlarmL.setText("Problem z klasą");
            }
        });
        FinishPeselB.addActionListener((ActionEvent e) -> {
            try {
                AlarmL.setText("Alarm");
                koniec=0;
                osoba.close();
                output.close();
                Collections.sort(mapaMain);
                makeFileAndWrite(FileMain,mapaMain);
            } catch (IOException ex) {
                AlarmL.setText("Plik może nie być poprawny");
            }
        });
    ConnectMain.addActionListener((ActionEvent e) -> {
            koniec=1;
            System.out.println("CM"+koniec);
            
            try {
                AlarmL.setText("Alarm");
                Socket gniazdo;
                gniazdo = new Socket(IPMain,portMain);
                System.out.println("JEST1");
                output = new PrintStream(gniazdo.getOutputStream());
                System.out.println("JEST2");
                osoba = new ObjectInputStream(gniazdo.getInputStream());
                System.out.println("JEST3");
                GivePeselB.setEnabled(true);
                FinishPeselB.setEnabled(true);
            } catch (IOException ex) {
                AlarmL.setText("Niepoprawne Dane IP Lub Portu, podaj jescze raz");
            }
            
        });
        GuiMain.setVisible(true); 
    }
    public static void FrameStart(JFrame FrameJ)
    {
        FrameJ.setSize(1000,600);
    FrameJ.setLocationRelativeTo(null);
    FrameJ.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    FrameJ.setTitle("Klient");
    
    }
   
    public static void makeFileAndWrite(String FN, ArrayList mapa)
    {
        String CalaOsoba="";
        File filePerson=new File(FN);
        if(filePerson.exists())
        {
            PrintWriter writer=null;
            try {
                //System.out.println("nadpisuje plik istniejący");
                writer = new PrintWriter(filePerson);
                int licznik1=0;
                while(licznik1<mapa.size())
                {
                    CalaOsoba=String.valueOf(((Person)mapa.get(licznik1)).pesel)+" "+((Person)mapa.get(licznik1)).name+" "+((Person)mapa.get(licznik1)).surname+" "+String.valueOf(((Person)mapa.get(licznik1)).age);
                    writer.println(CalaOsoba);
                    licznik1++;
                }
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                writer.close();
            }
        }
        else
        {
            try {
                filePerson.createNewFile();
                PrintWriter writer=null;
                try {
                //System.out.println("nadpisuje plik istniejący");
                writer = new PrintWriter(filePerson);
                int licznik2=0;
                while(licznik2<mapa.size())
                {
                    CalaOsoba=String.valueOf(((Person)mapa.get(licznik2)).pesel)+" "+((Person)mapa.get(licznik2)).name+" "+((Person)mapa.get(licznik2)).surname+" "+String.valueOf(((Person)mapa.get(licznik2)).age);
                    System.out.println("CalaOsoba");
                    writer.println(CalaOsoba);
                    licznik2++;
                }
            } catch (FileNotFoundException ex) {
               // Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                writer.close();
            }
            } catch (IOException ex) {
                //Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
   
    }


