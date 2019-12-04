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
public class name implements Comparable, Serializable{
    String pesel;
    String surname;
    String name;
    int age;
    name (String pesel, String nazwisko, String imie, int wiek)
    {
        this.pesel=pesel;
        this.surname=nazwisko;
        this.name=imie;
        this.age=wiek;
    }

    @Override
    public int compareTo(Object o) {
        int a;
        a=((name)o).age;
        return(this.age-a);
    }
}
