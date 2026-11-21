/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.urgence.model;

import it.urgence.model.enums.Turno;
import java.time.LocalDate;

/**
 *
 * @author belvinlabs
 */
public class Infermiere extends Personale {

    private String area;

   

    public Infermiere(String area, String matricola, Turno turno, String codiceFiscale, String nome, String cognome, LocalDate dataNascita, String telefono) {
        super(matricola, turno, codiceFiscale, nome, cognome, dataNascita, telefono);
        this.area = area;
    }

    @Override
    public String getRuolo() {
        return "Infermiera";
    }
}
