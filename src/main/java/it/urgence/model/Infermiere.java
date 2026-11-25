/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.urgence.model;

import it.urgence.exception.DatiNonValidiException;
import it.urgence.model.enums.Turno;
import java.time.LocalDate;

/**
 *
 * @author belvinlabs
 */
public class Infermiere extends Personale {

    private String area;

   

    public Infermiere(String area, String matricola, Turno turno, String codiceFiscale, String nome, String cognome, LocalDate dataNascita, String telefono) throws  DatiNonValidiException{
        super(matricola, turno, codiceFiscale, nome, cognome, dataNascita, telefono);
        if (area == null) {
            throw new DatiNonValidiException("IL campo area è obligatorio");
        }
        this.area = area;
    }

    @Override
    public String getRuolo() {
        return "Infermiera";
    }
}
