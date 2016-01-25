/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interpretador.music;

import com.interpretador.music.instruments.*;
import java.util.Vector;
import jm.audio.AOException;
import jm.audio.Instrument;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import jm.util.View;
import jm.util.Write;
import jm.audio.Instrument.*;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 *
 * @author rsantos34
 */
public class ReadMusicTeste {
    
    static float[] musicData = new float[1];
    
    public static void main(String[] args) {
        //Read.audio(musicData, "C:/Users/rsantos34/Documents/NetBeansProjects/InterpretadorPrototipo/src/main/resources/Kalimba.wav");
        
        Score score = new Score("Vai");
        Read.midi(score, "C:/Users/rsantos34/Documents/NetBeansProjects/InterpretadorPrototipo/src/main/resources/Vai,_Steve_-_For_the_Love_Of_God.mid");
       
        System.out.println("Tempo da música: "+ score.getTempo());
        
        Part[] parts = score.getPartArray();
        
        int indice = 0;
        for (Part part : parts) {
            System.out.println("\n===========================\n");
            System.out.println("Dados da parte: "+indice);
            System.out.println("Título: "+ part.getTitle());
            System.out.println("Canal: "+ part.getChannel());
            System.out.println("Tamanho: "+part.getSize());
            System.out.println("Instrumento: "+ part.getInstrument());
            
            Phrase[] frases = part.getPhraseArray();
            
            for (Phrase frase : frases) {
                System.out.println("Notas da frase "+ frase.getTitle() +": ");
                Note[] notas = frase.getNoteArray();
                for (Note nota : notas) {
                    System.out.println(nota.getNote());
                }
            }
            
            indice = indice+1;
        }
        
        Instrument[] insts = new Instrument[5];
        for (int i=0; i<2; i++) {
            insts[i] = new instruments.PulsewaveInst(44100);
        }
        
        Write.au(score, "Vai.wav", insts);
       //Método da classe Play que toca um arquivo passado por parâmetro.
       //Play.au("C:/Users/rsantos34/Documents/NetBeansProjects/InterpretadorPrototipo/src/main/resources/Kalimba.wav");
    }
    
}
