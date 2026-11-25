
package it.urgence.exception;

import it.urgence.model.Medico;

/**
 *
 * @author belvinlabs
 */
public class MedicoNonDisponibileException extends RisorsaNonDisponibileException{
    private Medico medico;
    public MedicoNonDisponibileException(Medico medico){
        super(medico.getNomeCompleto()+" segue già "+medico.getCarico()+" pazienti");
        this.medico=medico;
    }
    
    public Medico getMedico(){
        return this.medico;
    }
            
}
