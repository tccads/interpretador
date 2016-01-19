package com.interpretador.logic.rotator;

import com.interpretador.logic.Notes.Note;

public class NoteRotator {

    private Note note;
    private int currentShape = 0;

    public NextNoteInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % note.getShapeMatrix().size();
        return new NextNoteInfo(note.getShapeMatrix().get(nextShape), nextShape);
    }

    public int[][] getCurrentShape() {
        return note.getShapeMatrix().get(currentShape);
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    public void setNoteBrick(Note note) {
        this.note = note;
        currentShape = 0;
    }


}
