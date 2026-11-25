
package it.urgence.exception;
import it.urgence.model.Accesso;


/**
 *
 * @author belvinlabs
 */
public class AccessoGiaIncorsoException extends OperazioneNonConsentitaException{
    private final Accesso accesso;
    public AccessoGiaIncorsoException(Accesso accesso){
        super(accesso.getPaziente().getNomeCompleto()+" ha già un accesso in corso ");
        this.accesso=accesso;
    }
    public Accesso getAccesso(){
        return this.accesso;
    }
}
