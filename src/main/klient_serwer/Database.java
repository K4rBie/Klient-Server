/**
 *
 * @author Karol
 */
package src.main.klient_serwer;
import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Klasa bazy danych. Zapisuje poszczególne rekordy w drzewie binarnym i pozwala na ich odczytanie po podaniu peselu.
 * @author Karol
 */
public class Database {
	TreeSet<Person> database = new TreeSet<Person>();
	
	Database (String db_name){
		try {
			// czytaj bazę danych z pliku
			FileReader io = new FileReader(db_name);
			BufferedReader bi = new BufferedReader(io);
			
			String record;
			while((record = bi.readLine()) != null){
				StringTokenizer strtk = new StringTokenizer(record);
				
				long p_pesel = Long.parseLong(strtk.nextToken());
				String p_name = strtk.nextToken();
				String p_surname = strtk.nextToken();
				int p_age = Integer.parseInt(strtk.nextToken());
				
				database.add(new Person(p_pesel, p_name, p_surname, p_age));                               
			}
			bi.close();
		} catch (Exception e){
			System.out.println (e.getMessage());
		}
	}
	
	/**
    * Zwraca rekord po podaniu peselu. Jeśli rekord o podanym peselu nie istnieje, zwraca null.
    * @author Karol
    */
	public Person get_record(long pesel_key) {
		Iterator<Person> iterator = database.iterator();
        while(iterator.hasNext()) {
            Person person = iterator.next();
            long pesel = person.get_pesel();
            if(pesel_key == pesel) {
               
				return person;
            }
        }
        return null; 
	}
	
}
