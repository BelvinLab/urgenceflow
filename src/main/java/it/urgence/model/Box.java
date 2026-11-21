/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.urgence.model;

import it.urgence.model.enums.StatoBox;
import it.urgence.model.enums.TipoBox;

/**
 *
 * @author belvinlabs
 */
public class Box {
    private String codice;
    private TipoBox tipo;
    private StatoBox stato;
    private Accesso accessoCorrente;

    public Box() {
        this.stato = StatoBox.LIBERO;
       
    }
    
    
    public boolean isLibero(){
        return this.stato == StatoBox.LIBERO;
    }
    
    public void occupa(Accesso accesso){
        if (this.stato==StatoBox.LIBERO) {
            this.accessoCorrente=accesso;
            this.stato=StatoBox.OCCUPATO;
        }
        
    }
    
    public void libera(){
        this.stato=StatoBox.IN_PULIZIA;
        this.accessoCorrente=null;
    }
    
    public void segnaPulito(){
        if (this.stato==StatoBox.IN_PULIZIA) {
            this.stato=StatoBox.LIBERO;
        }
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public TipoBox getTipo() {
        return tipo;
    }

    public void setTipo(TipoBox tipo) {
        this.tipo = tipo;
    }

    public StatoBox getStato() {
        return stato;
    }

   

    public Accesso getAccessoCorrente() {
        return accessoCorrente;
    }

   
    
}
