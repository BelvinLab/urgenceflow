
package it.urgence.exception;

import it.urgence.model.Box;
import it.urgence.model.enums.StatoBox;

/**
 *
 * @author belvinlabs
 */
public class BoxNonDisponibileException extends RisorsaNonDisponibileException{
    private final Box box;
    
    public BoxNonDisponibileException(Box box,StatoBox stato){
        super(box.getCodice()+" è "+stato);
        this.box=box;
    }
    public Box getBox(){
        return this.box;
    }
}
