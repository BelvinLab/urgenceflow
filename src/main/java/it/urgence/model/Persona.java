/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.urgence.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 *
 * @author belvinlabs
 */
public abstract class Persona {
    private final String codiceFiscale;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String telefono;

    public Persona(String codiceFiscale, String nome, String cognome, LocalDate dataNascita,String telefono) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.telefono=telefono;
 
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }
    
    
    public int getEta(){
        return Period.between(dataNascita,LocalDate.now()).getYears();
    }    
    
    public String getNomeCompleto(){
        return this.cognome+" "+this.nome;
    }
    @Override
    public boolean equals(Object o){
        Persona altra = (Persona) o;
        return this.codiceFiscale.equals(altra.codiceFiscale);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(this.codiceFiscale);
    }
  
}


