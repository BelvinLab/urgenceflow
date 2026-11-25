/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.urgence.model;

import it.urgence.exception.AccessoGiaIncorsoException;
import it.urgence.exception.DatiNonValidiException;
import it.urgence.model.enums.StatoAccesso;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author belvinlabs
 */
public class Paziente extends Persona{
    private char sesso;
    private String indirizzo;
    private List<Accesso> storicoAccessi;

    public Paziente(char sesso, String indirizzo, String codiceFiscale, String nome, String cognome, LocalDate dataNascita, String telefono) throws DatiNonValidiException{
        super(codiceFiscale, nome, cognome, dataNascita, telefono);
        if (sesso == ' ') {
            throw new DatiNonValidiException("Il sesso è obligatorio");
        }
        if (sesso != 'M' && sesso != 'F') {
            throw new DatiNonValidiException("Il sesso deve essere sia M oppure F");
        }
        this.sesso = sesso;
        this.indirizzo = indirizzo;
        this.storicoAccessi = new ArrayList<>();
    }
    
    public void aggiungiAccesso(Accesso accesso) throws AccessoGiaIncorsoException{
        if (this.haAccessoInCorso()) {
            throw new AccessoGiaIncorsoException(accesso);
        }
        this.storicoAccessi.add(accesso);
    }
    
    public List<Accesso> getStoricoAccessi(){
        return this.storicoAccessi;
    }
    
    public Optional<Accesso> getAccessoInCorso(){
        Optional<Accesso> inCorso = Optional.empty();
        
        for (Accesso accesso : this.storicoAccessi) {
            if ((accesso.getStato()==StatoAccesso.IN_CARICO) || (accesso.getStato()==StatoAccesso.IN_ATTESA)) {
                inCorso=Optional.of(accesso);
            }
        }
        return inCorso;
    }
    
    public boolean haAccessoInCorso(){
        boolean inCorso=false;
        for (Accesso accesso : this.storicoAccessi) {
            if ((accesso.getStato()==StatoAccesso.IN_CARICO) || (accesso.getStato()==StatoAccesso.IN_ATTESA)) {
                inCorso=true;
                break;
            }
        }
        return inCorso;
    }
    
}
