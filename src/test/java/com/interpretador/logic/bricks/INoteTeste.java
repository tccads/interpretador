package com.interpretador.logic.bricks;

import com.interpretador.logic.Notes.Note;
//import com.interpretador.logic.Notes.SingleNote;
import com.interpretador.logic.Notes.SingleNote;
import org.junit.Assert;
import org.junit.Test;

public class INoteTeste {

    @Test
    public void testGetShapeMatrix() throws Exception {
        Note note = new SingleNote();
        note.getShapeMatrix().get(0)[0][0] = 2;
        note.getShapeMatrix().get(0)[1][0] = 3;
        Assert.assertEquals(0, note.getShapeMatrix().get(0)[0][0]);
        Assert.assertEquals(1, note.getShapeMatrix().get(0)[1][0]);
    }

    @Test
    public void testGetShapeMatrixList() throws Exception {
        Note note = new SingleNote();
        note.getShapeMatrix().remove(0);
        Assert.assertEquals(2, note.getShapeMatrix().size());
    }
}