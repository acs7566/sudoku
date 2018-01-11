package sudoku;

import java.util.ArrayList;
import java.util.List;

public class Row {
    Cell[] cells;
    ArrayList<Integer> missingNumbers;
    boolean isCol;

    public Row(int size,boolean isCol){
        this.isCol=isCol;
        cells = new Cell[size];
        missingNumbers = new ArrayList<>();
        for(int i = 1; i<=size; i++){
            missingNumbers.add(i);
        }
    }

    public void setCell(int location, Cell cell) {
        cells[location] = cell;
        if(cell.getNumber() >0){
            missingNumbers.remove(Integer.valueOf(cell.getNumber()));
        }
    }

    public void clean(){

        for (Cell cell:cells){
            if (cell.getNumber()>0){
                missingNumbers.remove(Integer.valueOf(cell.getNumber()));
            }
        }
        for (Cell cell:cells){
            List<Integer> toClean = new ArrayList<>();

            for (int num:cell.getPossibleNumbers()){
                if (!missingNumbers.contains(Integer.valueOf(num)))
                    toClean.add(Integer.valueOf(num));
            }
            cell.removeAll(toClean);
        }

    }
    public boolean isEqual(Row other){
        if (this.isCol!=other.isCol) return false;
        int i=0;
        if (this.isCol) i=1;
        return this.cells[0].getLocation(i)==other.cells[0].getLocation(i);
    }
}
