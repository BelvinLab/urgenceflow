/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.urgence.model;

import it.urgence.exception.DatiNonValidiException;
import it.urgence.model.enums.Turno;
import java.time.LocalDate;

/**
 *
 * @author belvinlabs
 */
public abstract class Personale extends Persona{
    private String matricola;
    private Turno turno;

    public Personale(String matricola, Turno turno, String codiceFiscale, String nome, String cognome, LocalDate dataNascita, String telefono) throws  DatiNonValidiException{
        super(codiceFiscale, nome, cognome, dataNascita, telefono);
        if (matricola == null) {
            throw new DatiNonValidiException("La matricola è obligatoria");
        }
        this.matricola = matricola;
        this.turno = turno;
    }
    
    public String getMatricola(){
        return this.matricola;
    }
    
    public Turno getTurno(){
        return  this.turno;
    }
    
    public abstract String getRuolo();
}
