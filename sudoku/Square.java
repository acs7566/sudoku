package sudoku;

import java.util.*;

public class Square {
    ArrayList<Cell> cells;
    ArrayList<Integer> numbers; //existing numbers
    int sqrtSize;
    int size;
    int[] location = new int[2];

    public Square(int size,int x, int y) {
        this.size = size;
        sqrtSize = (int) Math.sqrt(size);
        cells = new ArrayList<>();
        numbers = new ArrayList<>();
        location[0] = x;
        location[1] = y;

    }

    public void setCell(Cell cell) {
        cells.add(cell);
        if (cell.getNumber()>0) {
            numbers.add(cell.getNumber());
            cell.getPossibleNumbers().clear();
        }
    }

    public void clean() {
        List<Integer> toClean = new ArrayList<>();

        for (int number = 1; number<=size; number++){
            if (numbers.contains(number)) continue;
            Cell nomanee = new Cell();
            Row row = new Row(size,false);
            Row col = new Row(size,true);
            boolean sameRow = false;
            boolean sameCol = false;
            int nomanees=0;
            for (Cell cell:cells){
                if (cell.getPossibleNumbers().contains(number)){
                    if (nomanees==0){
                      nomanee = cell;
                      row = cell.getRow();
                      col = cell.getCol();
                      sameRow=true;
                      sameCol=true;
                    }
                    else {
                        if (sameRow) sameRow = cell.getRow().isEqual(row);
                        if (sameCol) sameCol = cell.getCol().isEqual(col);
                    }
                    nomanees++;
                }
            }
            if (nomanees==0) continue;
            if (nomanees==1){
                nomanee.changeTo(number);
            }
            else if (sameRow){

                for (Cell cell:row.cells){

                    if (!cell.getSquare().isEqual(nomanee.getSquare())){
                        cell.remove(Integer.valueOf(number));

                    }
                }
            }
            else if (sameCol){
                for (Cell cell:col.cells){
                    if (!cell.getSquare().isEqual(nomanee.getSquare())){
                        cell.remove(Integer.valueOf(number));
                    }
                }
            }
        }
        for (Cell cell : cells) {
            for (int number : cell.getPossibleNumbers()) {
                if (numbers.contains(number)) toClean.add(number);
            }
            cell.removeAll(toClean);
            toClean.clear();
        }
        HashMap<Integer,Stack<Cell>> sortBySizes = new HashMap<>();
        for (Cell cell:cells){
            int possibleNumbersSize = cell.getPossibleNumbers().size();
            if (possibleNumbersSize<=1) continue;
            if (sortBySizes.containsKey(possibleNumbersSize))
                sortBySizes.get(possibleNumbersSize).add(cell);
            else {
                Stack<Cell> list = new Stack<>();
                list.add(cell);
                sortBySizes.put(possibleNumbersSize, list);
            }
        }
        for (int key:sortBySizes.keySet()){
            int counter = 1;
            while (sortBySizes.get(key).size()>0) {
                Cell cell = sortBySizes.get(key).pop();
                for (Cell otherCell : sortBySizes.get(key)) {
                    otherCell.removeAll(cell.getPossibleNumbers());
                    if (otherCell.getPossibleNumbers().size() == 0) counter++;
                    otherCell.getPossibleNumbers().addAll(cell.getPossibleNumbers());

                }
                if (counter == key) {
                    for (Cell otherCell : sortBySizes.get(key)) {
                        otherCell.removeAll(cell.getPossibleNumbers());
                        if (otherCell.getPossibleNumbers().size() == 0) {
                            otherCell.getPossibleNumbers().addAll(cell.getPossibleNumbers());
                        }
                    }
                }
            }
        }
    }

    private boolean isEqual(Square other) {
        return this.location[0] == other.location[0] && this.location[1] == other.location[1];
    }


}
