/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.urgence.model;

import it.urgence.model.enums.Turno;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author belvinlabs
 */
public class Medico extends Personale{
    private String specializzazione;
    private int maxPazienti=3;
    private List<Accesso> accessiInCarico;

    public Medico(String specializzazione, String maricola, Turno turno, String codiceFiscale, String nome, String cognome, LocalDate dataNascita, String telefono) {
        super(maricola, turno, codiceFiscale, nome, cognome, dataNascita, telefono);
        this.specializzazione = specializzazione;
        this.accessiInCarico = new ArrayList<>();
    }
    
    
    public boolean isDisponibile(){
        return this.maxPazienti>0;
    }
    
    public void assegna(Accesso accesso){
        if (this.isDisponibile()) {
            this.accessiInCarico.add(accesso);
            maxPazienti--;
        }
    }
    
    public void rilascia(Accesso accesso){
        if (accessiInCarico.contains(accesso)) {
            accessiInCarico.remove(accesso);
            maxPazienti++;
            System.out.println("Accesso N° "+accesso.getIdAccesso()+" Rimosso con successo!");
        }
    }
    
    public int getCarico(){
        return this.maxPazienti;
    }
    @Override
    public String getRuolo(){
        return "Medico specialista in "+ this.specializzazione;
    }
            
            
    
    
}
