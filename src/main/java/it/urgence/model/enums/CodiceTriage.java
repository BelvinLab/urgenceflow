/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package it.urgence.model.enums;

/**
 *
 * @author belvinlabs
 */
public enum CodiceTriage {
    
    ROSSO(1,0,"Rosso"),
    ARANCIONE(2,15,"Arancione"),
    AZZURRO(3,60,"Azzurro"),
    VERDE(4,120,"Verde"),
    BIANCO(5,240,"Bianco");
    private  int livello;
    private int attesaMassima;
    private String etichetta;

    private CodiceTriage(int livello, int attesa,String etichetta) {
        this.livello= livello;
        this.attesaMassima=attesa;
        this.etichetta=etichetta;
    }
    
    public int getLivello() {
        return livello;
    }

    

    public int getAttesaMassima() {
        return attesaMassima;
    }

  

    public String getEtichetta() {
        return etichetta;
    }

   
    
    
    
}
