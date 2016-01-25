/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interpretador.music;

import jm.JMC;
import jm.constants.ProgramChanges;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import jm.util.View;

/**
 *
 * @author rsantos34
 */
public class PlayMidiFileTeste {
    
    public static void main(String[] args) {
        
        Score score = new Score("Vai");
        Read.midi(score, "C:/Users/rsantos34/Documents/NetBeansProjects/InterpretadorPrototipo/src/main/resources/Vai,_Steve_-_For_the_Love_Of_God.mid");
        
        View.print(score);
        score.getPart(0).setInstrument(JMC.ACOUSTIC_GRAND);
        Play.midi(score,true,true,9, 9);
        
    }
    
}
