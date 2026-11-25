
package it.urgence.model;

import it.urgence.exception.DatiNonValidiException;
import it.urgence.exception.UrgenceFlowException;
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

    protected Persona(String codiceFiscale, String nome, String cognome, LocalDate dataNascita,String telefono) throws DatiNonValidiException{
        
        if (codiceFiscale == null || codiceFiscale.isBlank()) {
            throw  new DatiNonValidiException("Il codice fiscale è obligatorio");
        }
        
        String cf =codiceFiscale.trim().toUpperCase();
        if (!cf.matches("[A-Z0-9]{16}")) {
            
             throw new DatiNonValidiException("Il codice fiscale deve contenere 16 lettere o cifre");
        }
        this.codiceFiscale = cf;
        if (nome==null || nome.isBlank()) {
            throw new DatiNonValidiException("Il nome è obligatorio");
        }
        this.nome = nome.trim();
        
        if (cognome==null || cognome.isBlank()) {
            throw new DatiNonValidiException("Il nome è obligatorio");
        }
        this.cognome = cognome.trim();
        
        if (dataNascita == null) {
            throw new DatiNonValidiException("La data di nascita è obligatoria");
        }
        
        if (dataNascita.isAfter(LocalDate.now())) {
            throw new DatiNonValidiException("La data di nascita non può essere nel futuro");
        }
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

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNome() {
        return nome;
    }
    
    public void setTelefono(String telefono){
        this.telefono=telefono;
    }
    
    public String getTelefono(){
        return this.telefono;
    }
    public int getEta(){
        return Period.between(dataNascita,LocalDate.now()).getYears();
    }    
    
    public String getNomeCompleto(){
        return this.cognome+" "+this.nome;
    }
    @Override
    public boolean equals(Object o){
        if (this== o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return  false;
        }
        Persona altra = (Persona) o;
        return this.codiceFiscale.equals(altra.codiceFiscale);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(this.codiceFiscale);
    }
  
}


