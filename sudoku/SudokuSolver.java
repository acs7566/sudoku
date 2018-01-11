package sudoku;

import java.io.*;

public class SudokuSolver {
    static int size;

    public static void main(String[] args){
        if (args.length<2){
            System.out.println("Please Insert: input file , output file");
            System.exit(1);
        }
        int [][]game=parsInput(args[0]);
        gameBoard board = new gameBoard(game);
        board.buildSolverTable(game);


        if (!board.solve(game)){
            System.out.println("Can't Solve this Game");
            System.exit(1);
        }
        writeTo(args[1],game);
        System.out.println("Done");




    }

    private static void writeTo(String outPutFileName, int[][] game) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outPutFileName),"utf-8"))) {
            for (int i=0;i<size;i++){
                for (int j=0;j<size;j++){
                    bw.write(game[i][j]+'0');
                }
                bw.write('\n');
            }
        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    private static int[][] parsInput(String inputFileName) {
        int[][] game = null;
        int clues=0;
        File file = new File(inputFileName);
        BufferedReader reader = null;
        try{
            reader=new BufferedReader(new FileReader(file));
            String s = null;
            if ((s=reader.readLine())!= null){
                size = s.length();
                game = new int[size][size];
                for (int j = 0; j < size; j++)
                    game[0][j] = s.charAt(j)-'0';
            }

            for (int i=1;i<size;i++) {
                String str;
                if ((str = reader.readLine()) != null){
                    if (str.length()!=size){
                        System.out.println("Not Square Table");
                        System.exit(1);
                    }
                    for (int j = 0; j < size; j++) {
                        game[i][j] = str.charAt(j)-'0';
                    }
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("File Not Found");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (reader!=null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int sizeSqrt = (int)Math.sqrt(size);
        if (sizeSqrt<Math.sqrt(size)|| size != game[0].length){
            System.out.println("Wrong Size");
            System.exit(1);
        }

        for(int row=0;row<size;row++){
            for (int col = 0;col<size;col++){
                if (game[row][col]>size || game[row][col]<1) game[row][col]=0;
                else clues++;
            }
        }
        if (size==9 && clues<17) {
            System.out.println("Not Enough Clues (Minimum is 17");
            System.exit(1);
        }


        return game;
    }



}
