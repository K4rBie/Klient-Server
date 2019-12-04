/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Piotr
 */
import java.util.*;
import java.io.*;
import java.net.*;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;

/**
 *
 * @author karol
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = null;
        Database database = null;
        Socket client = null;
        ObjectOutputStream oos = null;
        BufferedReader input = null;
        String input_line = null, output_line = null;
        
        
//         JFrame.setDefaultLookAndFeelDecorated(true);
//         JFrame frame = new JFrame ( " HelloWorldSwing " ) ;
//         frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
//         frame.setSize (300,50);
//         frame.setVisible(true);

            //System.out.println("A");
    
        try {
            //System.out.println("B");
        
            server = new ServerSocket(5567);
            //System.out.println("C");
            database = new Database("main.db");
            //System.out.println("baza załadowana1");
            client = server.accept();
            
            //System.out.println("baza załadowana2");
            //PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            oos = new ObjectOutputStream(client.getOutputStream());
             //System.out.println("baza załadowana3");
             InputStreamReader iis=new InputStreamReader(client.getInputStream());
              //System.out.println("baza załadowana4");
            input = new BufferedReader(iis);
            //System.out.println("gggg"+input.readLine());
         //System.out.println("baza załadowana5");
        }catch(Exception e){
            System.out.println("Coś innego 1");
        }
            //output_line = "Napisz coś [’q’ kończy]";
            //output.println(output_line);
        try{
            while ((input_line = input.readLine()) != null){
                if (input_line.equals("k")) break;
                System.out.println(input_line);

                
                Person record = database.get_record(Long.parseLong(input_line));
                System.out.println(""+record.get_record());
                oos.writeObject(record);     
                oos.flush();
                //oos.reset();
                //output.println("pewnie wysłano");
                
                //oos.close();
                //input.close();
                
            }
            input.close();
            oos.close();
            client.close();
                server.close();
        }catch(NumberFormatException e) {
                System.out.println("To nie jest liczba");
        }catch(Exception e){
            System.out.println("????: " +e + e.getCause());
        }
    }
}   
