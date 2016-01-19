package com.interpretador.logic;

import com.interpretador.logic.Notes.Note;
import com.interpretador.logic.Notes.NoteGenerator;
import com.interpretador.logic.Notes.RandomNoteGenerator;
import com.interpretador.logic.rotator.NoteRotator;
import com.interpretador.logic.rotator.NextNoteInfo;

import java.awt.*;

/**
 * Classe que implementa a interface VerticalMusicSheet. 
 */
public class SimpleSheet implements VerticalMusicSheet {

    private final int width;
    private final int height;
    private final NoteGenerator noteGenerator;
    private final NoteRotator noteRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;

    public SimpleSheet(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        noteGenerator = new RandomNoteGenerator();
        noteRotator = new NoteRotator();
        score = new Score();
    }

    //Método que move as notas para baixo
    @Override
    public boolean moveNoteDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, noteRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }


//    @Override
//    public boolean moveNoteLeft() {
//        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
//        Point p = new Point(currentOffset);
//        p.translate(-1, 0);
//        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
//        if (conflict) {
//            return false;
//        } else {
//            currentOffset = p;
//            return true;
//        }
//    }

//    @Override
//    public boolean moveNoteRight() {
//        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
//        Point p = new Point(currentOffset);
//        p.translate(1, 0);
//        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
//        if (conflict) {
//            return false;
//        } else {
//            currentOffset = p;
//            return true;
//        }
//    }

//    @Override
//    public boolean rotateLeftBrick() {
//        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
//        NextNoteInfo nextShape = brickRotator.getNextShape();
//        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
//        if (conflict) {
//            return false;
//        } else {
//            brickRotator.setCurrentShape(nextShape.getPosition());
//            return true;
//        }
//    }

    //Método que insere uma nova nota na VerticalMusicSheet
    @Override
    public boolean createNewNote() {
        
        //Método que retorna uma nota gerada pelo NoteGenerator.
        Note currentNote = noteGenerator.getNote();
        noteRotator.setNoteBrick(currentNote);
        //currentOffset define o ponto em que a nota aparecerá no VerticalMusicSheet.
        currentOffset = new Point(8, 0);
        
        System.out.println("Note fret: " + currentNote.getNoteFret()+ "\n\r");
        // Retorna verdadeiro se a condição for satisfeita:
        return MatrixOperations.intersect(currentGameMatrix, noteRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getVerticalMusicSheetMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(noteRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), noteGenerator.getNextNote().getShapeMatrix().get(0));
    }

    //Método que faz com que a nota desapareça.
    //A nota deve ser mesclada ao background, se for tocada corretamente nos três âmbitos e o Score é adicionado.
    @Override
    public void mergeNoteToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, noteRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    @Override
    public Score getScore() {
        return score;
    }


    @Override
    public void newMusicSheet() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewNote();
    }
}
