/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interpretador.music.logic;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author reinaldo
 */
public class TranslateNote {
    
    public Enum<NOTE_NAME> translateNote(double frequencia){
        
        // Arrumar uma maneira de arredondar a frequÃªncia.
        Enum<NOTE_NAME> noteNameResponse;
        
        int frequenciaArredondada = 0;
        
        switch (frequenciaArredondada) {
            case 65:
                noteNameResponse = NOTE_NAME.C2;
                break;
            case 69:
                noteNameResponse = NOTE_NAME.C__SUSTENIDO2;
                break;
            case 73:
                noteNameResponse = NOTE_NAME.D2;
                break;
            case 77:
                noteNameResponse = NOTE_NAME.D_SUSTENIDO2;
                break;
            case 82:
                noteNameResponse = NOTE_NAME.E2;
                break;
            case 87:
                noteNameResponse = NOTE_NAME.F2;
                break;
            case 92:
                noteNameResponse = NOTE_NAME.F_SUSTENIDO2;
                break;
            case 97:
                noteNameResponse = NOTE_NAME.G2;
                break;
            case 103:
                noteNameResponse = NOTE_NAME.G_SUSTENIDO2;
                break;
            case 110:
                noteNameResponse = NOTE_NAME.A2;
                break;
            case 116:
                noteNameResponse = NOTE_NAME.A_SUSTENIDO2;
                break;
            case 123:
                noteNameResponse = NOTE_NAME.B2;
                break;
            case 130:
                noteNameResponse = NOTE_NAME.C3;
                break;
            case 138:
                noteNameResponse = NOTE_NAME.C_SUSTENIDO3;
                break;
            case 146:
                noteNameResponse = NOTE_NAME.D3;
                break;
            case 164:
                noteNameResponse = NOTE_NAME.D_SUSTENIDO3;
                break;
            case 174:
                noteNameResponse = NOTE_NAME.E3;
                break;
            case 184:
                noteNameResponse = NOTE_NAME.F3;
                break;
            case 196:
                noteNameResponse = NOTE_NAME.F_SUSTENIDO3;
                break;
            case 207:
                noteNameResponse = NOTE_NAME.G3;
                break;
            case 220:
                noteNameResponse = NOTE_NAME.G_SUSTENIDO3;
                break;
            case 233:
                noteNameResponse = NOTE_NAME.A3;
                break;
            case 247:
                noteNameResponse = NOTE_NAME.A_SUSTENIDO3;
                break;
            case 261:
                noteNameResponse = NOTE_NAME.B3;
                break;
            case 277:
                noteNameResponse = NOTE_NAME.C3;
                break;
            case 293:
                noteNameResponse = NOTE_NAME.C_SUSTENIDO3;
                break;
            case 311:
                noteNameResponse = NOTE_NAME.D3;
                break;
            case 329:
                noteNameResponse = NOTE_NAME.D_SUSTENIDO3;
                break;
            case 349:
                noteNameResponse = NOTE_NAME.E3;
                break;
            case 369:
                noteNameResponse = NOTE_NAME.F3;
                break;
            case 391:
                noteNameResponse = NOTE_NAME.F_SUSTENIDO3;
                break;
            default:
                noteNameResponse = NOTE_NAME.NOTA_MORTA;
                break;
        }
        return noteNameResponse;
    }
    
    //TODO: Adequar o mÃ©todo para arredondar melhor a frequÃªncia.
    private static int arredondarFrequencias(double frequencia){
        
//        BigDecimal freq = new BigDecimal(frequencia).round(MathContext.DECIMAL128);
//        if(freq.compareTo(frequencia+.5) < frequencia+.5 && freq.round(MathContext.DECIMAL32).floatValue() > frequencia -.5 ){        
//            return freq.intValueExact();
//        }
        return 0;
    }
    
    public static void main(String[] args) {
        
        double frequenciaTeste = 130.0149;
        System.out.println("Frequencia original: " + frequenciaTeste + 
                "Frequencia arredondada: " + arredondarFrequencias(frequenciaTeste));
        
    }
}
