package src.main.klient_serwer;
import java.util.*;
import java.io.*;
/**
 * Reprezentuje jeden rekord w bazie danych – osobę.
 * @author Karol
 */
public class Person implements Comparable, Serializable{
	String name;
	String surname;
	long pesel;
	int age;
	
    /**
    * Konstruktor pobiera dane i tworzy z nich instancję klasy Person.
    * @author karol
    * @param pesel numer PESEL osoby.
    * @param name imię osoby.
    * @param surname nazwisko osoby.
    * @param age wiek osoby.
    */
	Person (long pesel, String name, String surname, int age){
		this.name = name;
		this.surname = surname;
		this.pesel = pesel;
		this.age = age;
	}
	
	/**
    * Implementacja potrzebna dla Comparable
    * @author Karol
    * @param tv obiekt castowany do instancji klasy Person.
    * @return różnicę wieku.
    */
        @Override
	public int compareTo (Object tv) {
		int a;
		a = ((Person) tv).age;
		return (this.age-a);
	}
	
    /**
    * Zwraca numer PESEL osoby.
    * @author Karol
    * @return Zwraca numer PESEL osoby.
    */
	public long get_pesel(){
		return this.pesel;
	}
	
    /**
    * Zwraca zawartość rekordu w postaci Stringa.
    * @author Karol
    * @return Zwraca zawartość rekordu w postaci Stringa.
    */
	public String get_record(){
		String record = "" + this.pesel + " " + this.name + " " + this.surname  + " " + this.age;
		return record;
	}
        public void outAge(){
        System.out.println(this.age);
        }
}
