package it.urgence.model;

import it.urgence.exception.BoxNonDisponibileException;
import it.urgence.exception.DatiNonValidiException;
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

    public Box(String codice,TipoBox tipo) throws  DatiNonValidiException{
        if (codice == null) {
            throw new DatiNonValidiException("Il codice box è obligatorio");
        }
        this.codice=codice;
        this.stato = StatoBox.LIBERO;
        this.tipo=tipo;
        
       
    }
    
    
    public boolean isLibero(){
        return this.stato == StatoBox.LIBERO;
    }
    
    public void occupa(Accesso accesso) throws BoxNonDisponibileException{
        if (this.stato != StatoBox.LIBERO) {
            throw new BoxNonDisponibileException(this, stato);
        }
        this.accessoCorrente=accesso;
        this.stato=StatoBox.OCCUPATO;
        
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
