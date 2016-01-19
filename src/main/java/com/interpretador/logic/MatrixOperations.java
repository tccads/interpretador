package com.interpretador.logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixOperations {


    //We don't want to instantiate this utility class
    private MatrixOperations(){

    }

    /** Método que avalia se o noteBrick (nota) tocou 
     * ou não o fim da tela (matriz).
     * 
     */
    public static boolean intersect(final int[][] matrix, final int[][] noteBrick, int x, int y) {
        for (int i = 0; i < noteBrick.length; i++) {
            for (int j = 0; j < noteBrick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (noteBrick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    System.out.println("A nota tocou o fim da tela");
                    return true;
                    
                    //Se tocar o fim da tela, e a tecla da nota for correta, então some o score, e diga que o aluno acertou.
                    //Caso contrário não.
                }
                System.out.println("A nota não tocou o fim da tela!");
            }
        }
        return false;
    }

    /**Método que diz se a nota está fora da matriz da SheetView (tela)
     * 
     * @param matrix Matriz que indica o tamanho da tela
     * @param targetX localização em x do elemento na matriz.
     * @param targetY localização em y do elemento na matriz.
     * @return verdadeiro se estiver fora da matriz
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }

    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }

    /**
    * Método que checa as remoções das notas. Será necessário fazer um método que
    * verifica se o aluno acertou a nota nas três dimensões (altura, duração, e intensidade)
    * Se ele acertou, remove a linha,
    */  
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        //Percorre a matriz horizontalmente:
        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                
                //Essa lógica precisa ser alterada, pois as linhas 
                //que estão com as notas tocadas corretamente devem ser tiradas, mesmo que não seja a linha cheia.
                if (matrix[i][j] == 0) {
                    rowToClear = true;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                // Se row to clear for verdadeiro: Adicione a linhas a serem limpadas.
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }
    
    /** Método que remove as notas que foram tocadas corretamente.
     * Deve ser chamado por um método que avalia a performace do aluno.
     * Uma nota só é removida se o aluno a tocou corretamente.
     */
    public static ClearRow checkRemovingNote(final int[][] matrix){
    
        //
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        //Percorre a matriz horizontalmente:
        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                
                //Essa lógica precisa ser alterada, pois as linhas 
                //que estão com as notas tocadas corretamente devem ser tiradas, mesmo que não seja a linha cheia.
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                // Se row to clear for verdadeiro: Adicione a linhas a serem limpadas.
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    
    }

    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
