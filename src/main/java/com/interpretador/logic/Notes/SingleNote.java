package com.interpretador.logic.Notes;

import com.interpretador.logic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

public final class SingleNote implements Note {

    private List<int[][]> brickMatrix = new ArrayList<>();

    private int fret;
    
    //TODO: Estudar como melhorar o tamanho da nota.
    public SingleNote() {
        brickMatrix.add(new int[][]{
                {1},
        });
//        brickMatrix.add(new int[][]{
//                {0, 1, 0, 1},
//                {0, 1, 0, 1},
//                {0, 1, 0, 1},
//                {0, 1, 0, 1}
//        });
        
//        brickMatrix.add(new int[][]{
//                {0, 0, 0, 0},
//                {1, 1, 1, 1},
//                {0, 0, 0, 0},
//                {0, 0, 0, 0}
//        });
//        brickMatrix.add(new int[][]{
//                {0, 1, 0, 0},
//                {0, 1, 0, 0},
//                {0, 1, 0, 0},
//                {0, 1, 0, 0}
//        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

    @Override
    public void setNoteFret(int fret) {
        if(fret >= 0 && fret <=25)
        this.fret = fret;
    }

    @Override
    public int getNoteFret() {
        return this.fret;
    }

}
