package src.main.klient_serwer;
import java.util.*;
import java.io.*;
/**
 * Klasa reprezentująca jeden rekord w bazie danych.
 * @author Karol
 */
public class Person implements Comparable, Serializable{
	String name;
	String surname;
	long pesel;
	int age;
	
	Person (long pesel, String name, String surname, int age){
		this.name = name;
		this.surname = surname;
		this.pesel = pesel;
		this.age = age;
	}
	
	/**
    * Implementacja potrzebna dla Comparable
    * @author Karol
    */
        @Override
	public int compareTo (Object tv) {
		int a;
		a = ((Person) tv).age;
		return (this.age-a);
	}
	
// 	public long get_pesel(){
// 		return this.pesel;
// 	}
	
    /**
    * Zwraca zawartość rekordu w postaci Stringa.
    * @author Karol
    */
	public String get_record(){
		String record = "" + this.pesel + " " + this.name + " " + this.surname  + " " + this.age;
		return record;
	}
        public void outAge(){
        System.out.println(this.age);
        }
}
