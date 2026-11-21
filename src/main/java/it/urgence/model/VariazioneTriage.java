/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package it.urgence.model;
import it.urgence.model.enums.CodiceTriage;

import java.time.LocalDateTime;

/**
 *
 * @author belvinlabs
 */
public record VariazioneTriage(
        
        LocalDateTime quando,
        CodiceTriage da,
        CodiceTriage a
        
) {
    
}
