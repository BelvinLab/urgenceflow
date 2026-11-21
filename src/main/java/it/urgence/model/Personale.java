/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.urgence.model;

import it.urgence.model.enums.Turno;
import java.time.LocalDate;

/**
 *
 * @author belvinlabs
 */
public abstract class Personale extends Persona{
    private String maricola;
    private Turno turno;

    public Personale(String maricola, Turno turno, String codiceFiscale, String nome, String cognome, LocalDate dataNascita, String telefono) {
        super(codiceFiscale, nome, cognome, dataNascita, telefono);
        this.maricola = maricola;
        this.turno = turno;
    }
    
    public String getMatricola(){
        return this.maricola;
    }
    
    public Turno getTurno(){
        return  this.turno;
    }
    
    public abstract String getRuolo();
}
