package com.interpretador.logic.Notes;

import java.util.List;

public interface Note {

    void setNoteFret(int fret);
    
    int getNoteFret();
    
    List<int[][]> getShapeMatrix();
}
