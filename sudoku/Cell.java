package sudoku;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private Row row;
    private Row col;
    private Square square;


    private ArrayList<Integer> possibleNumbers;
    private int number;
    private int[] location = new int[2];

    public Cell(){
        this.number = 0;
    }
    public Cell(Square square, Row row, Row col, int number, int x, int y) {
        this.row = row;
        this.col = col;
        this.number = number;
        this.square = square;
        this.possibleNumbers = new ArrayList<>();
        if (this.number==0)
            for (int i=1;i<=9;i++) possibleNumbers.add(i);
        location[0]=x;
        location[1]=y;
    }

    public void check() {
        if (possibleNumbers.size()==1)
            changeTo(possibleNumbers.remove(0));

        else{
            List<Integer> toRemove = new ArrayList<>();
            for (int num:possibleNumbers){
                if (square.numbers.contains(num) ||
                        !row.missingNumbers.contains(num) ||
                        !col.missingNumbers.contains(num))toRemove.add(num) ;
            }
            possibleNumbers.removeAll(toRemove);
        }
    }

    public void changeTo(int number) {
        this.number=number;
        this.row.clean();
        this.col.clean();
        for (Cell cell:this.square.cells)
            cell.remove(Integer.valueOf(this.number));

        this.square.numbers.add(Integer.valueOf(this.number));
        this.possibleNumbers.clear();
    }

    public boolean samePossibleNumbers(Cell other){
        other.possibleNumbers.removeAll(this.possibleNumbers);
        boolean answer = other.possibleNumbers.size()==0;
        other.possibleNumbers.addAll(this.possibleNumbers);
        return answer;
    }

    public boolean isEqual(Cell cell) {
        return  this.location[0]==cell.location[0] && this.location[1]==cell.location[1];
    }

    public boolean remove(Integer integer) {
        return this.possibleNumbers.remove(integer);
    }

    public ArrayList<Integer> getPossibleNumbers() {
        return possibleNumbers;
    }

    public boolean removeAll(List<Integer> toClean) {
        return this.possibleNumbers.removeAll(toClean);

    }

    public Row getRow() {
        return row;
    }


    public Row getCol() {
        return col;
    }


    public Square getSquare() {
        return square;
    }

    public int getNumber() {
        return number;
    }

    public int getLocation(int i) {
        return location[i];
    }

}
