/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.urgence.model;

import it.urgence.exception.DatiNonValidiException;
import it.urgence.exception.StatoAccessoNonValidoException;
import it.urgence.model.enums.CodiceTriage;
import it.urgence.model.enums.Esito;
import it.urgence.model.enums.ModalitaArrivo;
import it.urgence.model.enums.StatoAccesso;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author belvinlabs
 */
public class Accesso implements Comparable<Accesso>{
    
    private int id;
    private Paziente paziente;
    private LocalDateTime arrivo;
    private ModalitaArrivo modalitaArrivo;
    private String sintomo;
    private String note;
    private CodiceTriage codice;
    private StatoAccesso stato;
    private Box box;
    private Medico medico;
    private LocalDateTime presaInCarico;
    private LocalDateTime dimissione;
    private Esito esito;
    private List<VariazioneTriage> storicoTriage;

    public Accesso(CodiceTriage codice,Paziente paziente,LocalDateTime arrivo,ModalitaArrivo modalitaArrivo,String sintomo) throws DatiNonValidiException{
        this.codice=codice;
        this.paziente=paziente;
        if (arrivo == null) {
            throw new DatiNonValidiException("L'orario di arrivo è obbligatorio");
        }
        if (arrivo.isAfter(LocalDateTime.now())) {
            throw new DatiNonValidiException("L'orario di arrivo non può essere nel futuro");
        }
        this.arrivo=arrivo;
        this.modalitaArrivo = modalitaArrivo;
        if (sintomo == null || sintomo.isBlank()) {
                throw new DatiNonValidiException("Il sintomo è obbligatorio");
        }
        this.sintomo = sintomo.trim();
        this.stato = StatoAccesso.IN_ATTESA;
        this.storicoTriage = new ArrayList<>();
    }
    
    
    
    public StatoAccesso getStato(){
        return this.stato;
    }
    public Medico getMedico() {
        return medico;
    }
    
    public void rivaluta(CodiceTriage nuovo) throws StatoAccessoNonValidoException{
        
        if (this.stato == StatoAccesso.CONCLUSO) {
            throw new StatoAccessoNonValidoException(this, "Impossible rivalutare un'accesso già conclusion");
        }
        VariazioneTriage variazione = new VariazioneTriage(LocalDateTime.now(), this.codice, nuovo);
        this.storicoTriage.add(variazione);
        this.codice=nuovo;
        
    }
    
    public  void prendiInCarico(Box box , Medico medico) throws StatoAccessoNonValidoException{
        if (this.stato==StatoAccesso.CONCLUSO) {
            throw new  StatoAccessoNonValidoException(this,"Si tratta di un'accesso già concluso");
        }else if (this.stato == StatoAccesso.IN_CARICO) {
            throw new StatoAccessoNonValidoException(this,"Si tratta di un'accesso già in corso" );
        }

            
            this.box=box;
            this.medico=medico;
            this.presaInCarico=LocalDateTime.now();
            this.stato=StatoAccesso.IN_CARICO;
        
    }
    
    public void dimetti(Esito esito) throws StatoAccessoNonValidoException{
        if (this.stato == StatoAccesso.CONCLUSO) {
            throw  new StatoAccessoNonValidoException(this, "Questo accesso è già stato concluso");
             
        }else if (this.stato == StatoAccesso.IN_ATTESA) {
            throw  new StatoAccessoNonValidoException(this, "Impossibile dimettere un'accesso in attesa");
        }
       this.esito=esito;
       this.dimissione=LocalDateTime.now();
       this.stato=StatoAccesso.CONCLUSO;
                
                
    }
    
    public long getAttesaMinuti(LocalDateTime ora){
        long minuti = ChronoUnit.MINUTES.between(arrivo, ora);
        return minuti;
        
    }
    
    public boolean isOltreAttesaMassima(LocalDateTime ora) {
        if (stato != StatoAccesso.IN_ATTESA) {
            return false;            // non è più in attesa: nessuna allerta
        }
        return getAttesaMinuti(ora) > codice.getAttesaMassima();
    }
   
    
    public int getIdAccesso(){
        return this.id;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    public LocalDateTime getArrivo() {
        return arrivo;
    }

    public ModalitaArrivo getModalitaArrivo() {
        return modalitaArrivo;
    }

    public String getSintomo() {
        return sintomo;
    }

    public CodiceTriage getCodice() {
        return codice;
    }

    public Box getBox() {
        return box;
    }

    public Esito getEsito() {
        return esito;
    }

    public List<VariazioneTriage> getStoricoTriage() {
        return storicoTriage;
    }
    
    
    @Override
    public int compareTo(Accesso altro){
        int confronto= Integer.compare(this.codice.getLivello(), altro.codice.getLivello());
        
        if (confronto != 0) {
            return confronto;
        }
        confronto = this.arrivo.compareTo(altro.arrivo);
        if (confronto != 0) {
            return  confronto;
        }
        
        return Integer.compare(id, altro.id);
    }
    
    
      
}
