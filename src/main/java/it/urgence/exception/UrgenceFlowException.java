
package it.urgence.exception;

/**
 *
 * @author belvinlabs
 */
public class UrgenceFlowException extends Exception{
    public UrgenceFlowException(String message){
        super(message);
    }
    
    public UrgenceFlowException(String message, Throwable causa){
        super(message,causa);
    }
    
}
