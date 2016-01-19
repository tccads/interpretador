package com.interpretador.logic;

public interface VerticalMusicSheet {

    boolean moveNoteDown();

//    boolean moveNoteLeft();

//    boolean moveNoteRight();

//    boolean rotateLeftBrick();

    boolean createNewNote();

    int[][] getVerticalMusicSheetMatrix();

    ViewData getViewData();

    void mergeNoteToBackground();

    ClearRow clearRows();

    Score getScore();

    void newMusicSheet();
}
