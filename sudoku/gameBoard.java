package sudoku;

public class gameBoard {
    static Cell[][] cells;
    static Row[] rows;
    static Row[] cols;
    static Square[][] squares;
    static int size;
    static int sizeSqrt;
    static boolean solved;

    public static Cell[][] getCells() {
        return cells;
    }

    public static Row[] getRows() {
        return rows;
    }

    public static Row[] getCols() {
        return cols;
    }

    public static Square[][] getSquares() {
        return squares;
    }

    public static int getSize() {
        return size;
    }

    public static int getSizeSqrt() {
        return sizeSqrt;
    }

    public gameBoard(int[][] game) {
        this.size = game[0].length;
        this.sizeSqrt = (int)Math.sqrt(size);
        this.squares = new Square[sizeSqrt][sizeSqrt];
        for (int i = 0; i < sizeSqrt; i++) {
            for (int j = 0; j < sizeSqrt; j++) {
                this.squares[i][j] = new Square(size, i, j);
            }
        }

        cells = new Cell[size][size];

        this.rows = new Row[size];
        this.cols = new Row[size];
        for(int i=0;i<size;i++){
            this.rows[i] = new Row(size, false);
            this.cols[i] = new Row(size,true);

        }
        buildSolverTable(game);
        if (size==9) {
            int emptyGroups = 0;
            for (int i = 0; i < sizeSqrt; i++) {
                for (int j = 0; j < sizeSqrt; j++) {
                    if (this.squares[i][j].numbers.size() < 1) emptyGroups++;
                }
            }
            for (int i = 0; i < size; i++) {
                if (this.rows[i].missingNumbers.size() == 9) emptyGroups++;
                if (this.cols[i].missingNumbers.size() == 9) emptyGroups++;

            }
            if (emptyGroups > 8) {
                System.out.println("To Many Empty Groups (Max is 8)");
                System.exit(1);
            }
        }

    }


        public static boolean solve(int[][] game){
            solved = false;
            int numOfRounds = size;
            while (!solved){
                if (numOfRounds<1){
                    for (int i= 0; i<size;i++)
                        for (int j=0;j<size;j++){
                            for (int number:cells[i][j].getPossibleNumbers()){
                                int[][] newGame = new int[size][size];
                                copyGame(newGame);
                                newGame[i][j] = number;
                                gameBoard board = new gameBoard(newGame);
                                if (board.solve(newGame)){
                                    board.copyGame(game);
                                    return true;
                                }

                            }
                        }
                    return false;
                }

                solved=true;
                for (int x=0;x<sizeSqrt;x++){
                    for (int y=0;y<sizeSqrt;y++){
                        squares[x][y].clean();
                        for (int row=0;row<sizeSqrt;row++){
                            for (int col=0;col<sizeSqrt;col++){
                                if(cells[(x*sizeSqrt)+row][(y*sizeSqrt)+col].getNumber()>0) continue;
                                else{
                                    solved = false;
                                    cells[(x*sizeSqrt)+row][(y*sizeSqrt)+col].check();
                                }
                            }
                        }
                    }

                }

                numOfRounds--;

            }
            for (int x=0;x<size;x++) {
                for (int y = 0; y < size; y++) {
                    game[x][y] = cells[x][y].getNumber();
                }
            }
            return solved;
        }

    private static void copyGame(int[][] newGame) {
        for (int i = 0; i<size; i++){
            for (int j=0;j<size;j++){
                newGame[i][j] = cells[i][j].getNumber();
            }
        }
    }

    public static void buildSolverTable(int[][] game) {
        for(int x=0;x<sizeSqrt;x++){
            for(int y=0;y<sizeSqrt;y++){
                for(int r=0;r<sizeSqrt;r++){
                    for (int c=0;c<sizeSqrt;c++){
                        cells[(x*sizeSqrt)+r][(y*sizeSqrt)+c] =
                                new Cell(squares[x][y],rows[(x*sizeSqrt)+r],
                                        cols[(y*sizeSqrt)+c],game[(x*sizeSqrt)+r][(y*sizeSqrt)+c],
                                        (x*sizeSqrt)+r,(y*sizeSqrt)+c);
                        rows[(x*sizeSqrt)+r].setCell((y*sizeSqrt)+c ,
                                cells[(x*sizeSqrt)+r][(y*sizeSqrt)+c]);
                        cols[(y*sizeSqrt)+c].setCell((x*sizeSqrt)+r ,
                                cells[(x*sizeSqrt)+r][(y*sizeSqrt)+c]);
                        squares[x][y].setCell(cells[(x*sizeSqrt)+r][(y*sizeSqrt)+c]);
                    }

                }
            }
        }
    }




}
