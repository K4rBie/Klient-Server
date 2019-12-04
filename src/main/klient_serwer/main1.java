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
public class main1 {
    public static void main(String []args)
    {
        // wczytywanie danych o serwerze
        Scanner in = new Scanner(System.in);
        //System.out.println("Podaj host serwera");
        //String hostSerwer = in.nextLine();
        //System.out.println("Podaj adres port serwera");
        //int portSerwer = in.nextInt();
        
        // wczytywanie peseli osób któryh dane chcemy odczytać
        boolean koniec=false;
        String pesel="0";
        String wczytaj;
        
         ArrayList mapa1 =new ArrayList();
        boolean ok;
        ok=true;
        
 /*       while(koniec==false)
        {
             System.out.println("Podaj pesel osoby której dane mamy pobrać, jeżeli chcesz przerwać to napisz k");
             wczytaj=in.next();
             if(wczytaj.equals("k"))
                 koniec=true;
             else
                 pesel=wczytaj;
        }*/
        // komunikacja
        
        try
        {
            Socket gniazdo=new Socket("localhost",5567);
            PrintStream output = new PrintStream(gniazdo.getOutputStream());
            ObjectInputStream osoba = new ObjectInputStream(gniazdo.getInputStream());
            while(koniec==false)
            {
                wczytaj="0";
                pesel="0";
                System.out.println("Podaj pesel osoby której dane mamy pobrać, jeżeli chcesz przerwać to napisz k");
                wczytaj=in.next();
                
                if(wczytaj.equals("k"))
                {
                    break;
                }
                
                else
                    pesel=wczytaj;
        
                output.print(pesel+"\n");
                Person person = (Person)osoba.readObject();
                mapa1.add(person);
          
                //output.flush();
            }
 
            osoba.close();
            output.close();
            Collections.sort(mapa1);
            /*int licznik=0;
            while(licznik<=mapa1.size())
            {
                ((Person)mapa1.get(licznik)).outAge();
                licznik++;//System.out.println(mapa1.);
            }*/
            String FN1="plik.txt";
          makeFileAndWrite(FN1,mapa1);
        }
        catch(UnknownHostException e){
            ok=false;}
        catch(IOException e){
            ok=false;}
        catch ( Exception e ) { System.out.println(" blad we/wy");}
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
    


