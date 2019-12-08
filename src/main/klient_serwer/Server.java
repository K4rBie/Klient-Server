package src.main.klient_serwer;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Główna klasa programu Server.
 * @author karol
 */
public class Server {

    /**
    * Tworzy interfejs graficzny i czeka na akcję.
    * @author karol
    * @param args argumenty dla maina. Nieprzydatne.
    */
    public static void main(String[] args) throws Exception {
        
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame("Serwer");
        
        var label1 = new JLabel ("Serwer zatrzymany.");
        frame.getContentPane().add(label1, BorderLayout.NORTH);
        
        var startstop = new JButton ("Uruchom serwer");
        frame.getContentPane().add(startstop, BorderLayout.CENTER);
        startstop.addActionListener(new Server_Action(startstop, label1));
        
        frame.pack();
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setSize (300,200);
        frame.setVisible(true);
    }
    
}


/**
* Po wykonaniu uruchamia się serwer.
* @author karol
*/

class Server_Action implements ActionListener {
    JButton b;
    JLabel l;
    //Server server;
    
    /**
    * Konstruktor.
    * @author karol
    * @param b przycisk, którego akcja jest wykonywana.
    * @param l pole tekstowe, które będzie aktualizowane.
    */
    Server_Action(JButton b, JLabel l) {
        this.b = b;
        this.l = l;
    }

    /**
    * Zajmuje się interfejsem graficznym przy uruchamianiu serwera.
    * @author karol
    * @param event wykonywane zdarzenie.
    */
    public void actionPerformed (ActionEvent event) {
        //b.setText("Zatrzymaj Serwer");
        //b.removeActionListener(this);
        l.setText("Serwer uruchomiony.");
        spinServer();
        //l.setText("Serwer zatrzymany.");
        
    }
    
  /**
    * Właściwe uruchomienie serwera. Po uruchomieniu czeka na decyzję klienta o rozłączeniu.
    * @author karol
    */
    public void spinServer() {
    
        ServerSocket server = null;
        Database database = null;
        Socket client = null;
        ObjectOutputStream oos = null;
        BufferedReader input = null;
        String input_line = null, output_line = null;
        
        try {
            server = new ServerSocket(5568);
            database = new Database("main.db");
            System.out.println("baza załadowana");
            client = server.accept();
            
            oos = new ObjectOutputStream(client.getOutputStream());
            InputStreamReader iis = new InputStreamReader(client.getInputStream());
            input = new BufferedReader(iis);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        String input_line_old;
        try{
            while ((input_line = input.readLine()) != null){
                if (input_line.equals("k")) {
                    System.out.println("Przerwano działanie serwera");
                    break;
                }
                //if (!(input_line.equals(input_line_old))){
                System.out.println("Pobrano pesel: " + input_line);
                //l.setText("Pobrano pesel: " + input_line);

                Person record = database.get_record(Long.parseLong(input_line));
                if (record != null) {
                    System.out.println("Istnieje rekord o takim peselu: " + record.get_record());
                } else {
                    System.out.println("Nie istnieje rekord o takim peselu.");
                }
                oos.writeObject(record);     
                oos.flush();
            }
            
            input.close();
            oos.close();
            client.close();
            server.close();
            
        }catch(NumberFormatException e) {
            System.out.println("To nie jest liczba");
        }catch(Exception e){
            System.out.println("????: " + e.getMessage());
        }
    }
}   
