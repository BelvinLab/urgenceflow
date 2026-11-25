package it.urgence.exception;

import it.urgence.model.Accesso;



/**
 *
 * @author belvinlabs
 */
public class StatoAccessoNonValidoException extends OperazioneNonConsentitaException{
    private final Accesso accesso;
    public StatoAccessoNonValidoException(Accesso accesso, String message){
        super(message);
        this.accesso=accesso;
        
    }
    
    public Accesso getAccesso(){
        return this.accesso;
    }
    
}
