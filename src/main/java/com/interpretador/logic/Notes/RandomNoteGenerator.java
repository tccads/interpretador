package com.interpretador.logic.Notes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import com.interpretador.gui.*;

public class RandomNoteGenerator implements NoteGenerator {

    private final List<Note> noteList;

    private final Deque<Note> nextNotes = new ArrayDeque<>();

    //Método que renderiza as notas.
    public RandomNoteGenerator() {
        noteList = new ArrayList<>();
        noteList.add(new SingleNote());
//        brickList.add(new IBrick());
//        brickList.add(new JBrick());
//        brickList.add(new LBrick());
//        brickList.add(new OBrick());
//        brickList.add(new SBrick());
//        brickList.add(new TBrick());
//        brickList.add(new ZBrick());
        nextNotes.add(noteList.get(ThreadLocalRandom.current().nextInt(noteList.size())));
        nextNotes.add(noteList.get(ThreadLocalRandom.current().nextInt(noteList.size())));
    }

    /**Método que gera automaticamente as notas e adiciona na lista de próximas notas.
     * AQUI DEVE SER CHAMADO OS MÉTODOS DO TRADUTOR DO MIDI PARA AS NOTAS REAIS
     */
    @Override
    public Note getNote() {
        Note note;
        
        if (nextNotes.size() <= 1) {
            nextNotes.add(noteList.get(ThreadLocalRandom.current().nextInt(noteList.size())));
        }
        note = nextNotes.poll();
        
        note.setNoteFret(ThreadLocalRandom.current().nextInt());
        return note;
    }

    @Override
    public Note getNextNote() {
        return nextNotes.peek();
    }
}
