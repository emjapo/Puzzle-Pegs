// Emily Port

public class PuzzlePegs {

    private static int[][] board;
    private static boolean solved = false;
    private static int[] steps = new int[26];
    private static int stepCount = 0;
    // 2D array implementation
    // 1 3 6 10 15
    // 2 5 9 14
    // 4 8 13
    // 7 12
    // 11

    public static int[] holeIndexConversion(int hole) {
        int[] index= new int[2];
        switch(hole) {
            case 1: 
                index[0] = 0;
                index[1] = 0;
                break;
            case 2:
                index[0] = 1;
                index[1] = 0;
                break;
            case 3:
                index[0] = 0;
                index[1] = 1;
                break;
            case 4:
                index[0] = 2;
                index[1] = 0;
                break;
            case 5:
                index[0] = 1;
                index[1] = 1;
                break;
            case 6:
                index[0] = 0;
                index[1] = 2;
                break;
            case 7:
                index[0] = 3;
                index[1] = 0;
                break;
            case 8:
                index[0] = 2;
                index[1] = 1;
                break;
            case 9:
                index[0] = 1;
                index[1] = 2;
                break;
            case 10:
                index[0] = 0;
                index[1] = 3;
                break;
            case 11:
                index[0] = 4;
                index[1] = 0;
                break;
            case 12:
                index[0] = 3;
                index[1] = 1;
                break;
            case 13:
                index[0] = 2;
                index[1] = 2;
                break;
            case 14:
                index[0] = 1;
                index[1] = 3;
                break;
            case 15:
                index[0] = 0;
                index[1] = 4;
                break;
            default:
                System.err.println("Not a valid hole");
        }
        return index;
    }

    public static int indexHoleConversion(int row, int col) {
        int hole = 0;
        switch (row) {
        case 0:
            switch(col) {
            case 0:
                hole = 1;
                break;
            case 1:
                hole = 3;
                break;
            case 2:
                hole = 6;
                break;
            case 3:
                hole = 10;
                break;
            case 4:
                hole = 15;
                break;
            default:
                break;
            }
            break;
        case 1:
            switch (col) {
            case 0:
                hole = 2;
                break;
            case 1:
                hole = 5;
                break;
            case 2:
                hole = 9;
                break;
            case 3:
                hole = 14;
                break;
            default:
                break;
            }
            break;
        case 2:
            switch (col) {
            case 0:
                hole = 4;
                break;
            case 1:
                hole = 8;
                break;
            case 2:
                hole = 13;
                break;
            default:
                break;
            }
            break;
        case 3:
            switch (col) {
            case 0:
                hole = 7;
                break;
            case 1:
                hole = 12;
                break;
            default:
                break;
            }
            break;
        case 4:
            switch (col) {
            case 0:
                hole = 11;
                break;
            default:
                break;
            }
            break;
        default:
            System.err.println("Not a valid index");
        }
        return hole;
    }

    public static int[][] createBoard(int hole){
        int[] holeIndex = holeIndexConversion(hole);
        int[][] board = { // -1 represents a boundary
            {1, 1, 1, 1, 1}, //             [0][0] 
            {1, 1, 1, 1, -1},//         [1][0] [0][1]
            {1, 1, 1, -1, -1},//     [2][0] [1][1] [0][2]
            {1, 1, -1, -1, -1},//   [3][0] [2][1] [1][2] [0][3]
            {1, -1, -1, -1, -1}//[4][0] [3][1] [2][2] [1][3] [0][4]
        }; 
        board[holeIndex[0]][holeIndex[1]] = 0;
        return board;
    }

    public static void printBoard(){
        // triangle format
        for (int k=0; k < 5; k++){
            int j = 0;
            for (int l=4; l>k; l--){
                System.out.print(" ");
            }
            for (int i=k; i >=0; i--){
                if (board[i][j] == 1) {
                    System.out.print("* "); // when a peg is present
                } else {
                    System.out.print("o "); // empty hole
                }
                j++;
            }
            System.out.println();
        }
    }

    public static void addBoardToSteps(int to, int from) {
        steps[stepCount] = to;
        stepCount++;
        steps[stepCount] = from;
        stepCount++;
    }

    public static void printSteps() {
        int to;
        int from;
        System.out.println("Initial Board");
        printBoard();
        for (int i = 25; i > 0; i-=2) {
           from = steps[i];
           to = steps[i-1];
           System.out.println("Move the peg in hole " + from + " to hole " + to); 
           jump(to, from);
           printBoard();
        }
    }

    public static void jump(int to, int from){
        int[] toIndex = holeIndexConversion(to);
        int[] fromIndex = holeIndexConversion(from);

        board[toIndex[0]][toIndex[1]] = 1;
        board[fromIndex[0]][fromIndex[1]] = 0;

        // find the one it jumps over
        board[(toIndex[0] + fromIndex[0]) / 2][(toIndex[1] + fromIndex[1]) / 2] = 0;
        
    }

    public static void unjump(int to, int from){
        int[] toIndex = holeIndexConversion(to);
        int[] fromIndex = holeIndexConversion(from);

        board[toIndex[0]][toIndex[1]] = 0;
        board[fromIndex[0]][fromIndex[1]] = 1;

        // find the one it jumps over
        board[(toIndex[0] + fromIndex[0]) / 2][(toIndex[1] + fromIndex[1]) / 2] = 1;

    }
    

    public static boolean possibleJump(int lastHole) {
        int[] jumpCheck = {1,1,0};
        int to = 0;
        int from = 0;
        int i = 0;


        // check up
        for (int col = 0; col < 5; col++) {
            for (int row = 4; row >= 0; row--) {
                i = 0;
                while (i < jumpCheck.length && row - i > -1 && jumpCheck[i] == board[row - i][col]) {
                    i++;
                }
                if (i == jumpCheck.length) {
                    from = indexHoleConversion(row, col);
                    to = indexHoleConversion(row - 2, col);
                    jump(to, from);
                    possibleJump(lastHole);
                    isSolved(lastHole);
                    if (solved) {
                        addBoardToSteps(to, from);
                        unjump(to, from);
                        return true;
                    }
                    unjump(to, from);
                }
            }
        }

        // check down
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                i = 0;
                while (i < jumpCheck.length && row + i < 5 && jumpCheck[i] == board[row + i][col]) {
                    i++;
                }
                if (i == jumpCheck.length) {
                    from = indexHoleConversion(row, col);
                    to = indexHoleConversion(row + 2, col);
                    jump(to, from);
                    possibleJump(lastHole);
                    isSolved(lastHole);
                    if (solved) {
                        addBoardToSteps(to, from);
                        unjump(to, from);
                        return true;
                    }
                    unjump(to, from);
                }
            }
        }

        // check left
        for (int row = 0; row < 5; row++) {
            for (int col = 4; col >= 0;col--){ 
                i = 0;
                while (i < jumpCheck.length && col - i > -1 && jumpCheck[i] == board[row][col - i]) {
                    i++;
                }
                if (i == jumpCheck.length) {
                    from = indexHoleConversion(row, col);
                    to = indexHoleConversion(row, col - 2);
                    jump(to, from);
                    possibleJump(lastHole);
                    isSolved(lastHole);
                    if (solved) {
                        addBoardToSteps(to, from);
                        unjump(to, from);
                        return true;
                    }
                    unjump(to, from);
                }
            }
        }

        // check right
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5 && board[row][col] >= 0; col++) {
                i = 0;
                while (i < jumpCheck.length && col+i < 5 && jumpCheck[i] == board[row][col + i]) {
                    i++;
                }
                if (i == jumpCheck.length) {
                    from = indexHoleConversion(row, col);
                    to = indexHoleConversion(row, col + 2);
                    jump(to, from);
                    possibleJump(lastHole);
                    isSolved(lastHole);
                    if (solved) {
                        addBoardToSteps(to, from);
                        unjump(to, from);
                        return true;
                    }
                    unjump(to, from);
                }
            }
        }

        // check bottom left to top right
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                i = 0;
                while (i < jumpCheck.length && row - i > -1 && col + i < 5 && jumpCheck[i] == board[row - i][col + i]) {
                    i++;
                }
                if (i == jumpCheck.length) {
                    from = indexHoleConversion(row, col);
                    to = indexHoleConversion(row - 2, col + 2);
                    jump(to, from);
                    possibleJump(lastHole);
                    isSolved(lastHole);
                    if (solved) {
                        addBoardToSteps(to, from);
                        unjump(to, from);
                        return true;
                    }
                    unjump(to, from);
                }
            }
        }

        // check top right to bottom left
        for (int row = 4; row >= 0; row--) {
            for (int col = 4; col >= 0; col--) {
                i = 0;
                while (i < jumpCheck.length && col - i > -1 && row + i < 5 && jumpCheck[i] == board[row + i][col - i]) {
                    i++;
                }
                if (i == jumpCheck.length) {
                    from = indexHoleConversion(row, col);
                    to = indexHoleConversion(row + 2, col - 2);
                    jump(to, from);
                    possibleJump(lastHole);
                    isSolved(lastHole);
                    if (solved) {
                        addBoardToSteps(to, from);
                        unjump(to, from);
                        return true;
                    }
                    unjump(to, from);
                }
            }
        }

        return false;
    }

    public static void isSolved(int hole){
        int pegCount = 0;
        int row = 0;
        int col = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j] == 1) {
                    pegCount++;
                    row = i;
                    col = j;
                }
            }
        }

        if(pegCount == 1 || solved == true) {
            int lastPeg = indexHoleConversion(row, col);
            if (lastPeg == hole || hole == 0 || solved == true) {
                solved = true;
            } else {
                solved = false;
            }
        } else {
            solved = false;
        }
    }


    public static void main(String[] args){
        int lastHole = 0;
        int startHole = 13;

        if (args.length == 2) {
            startHole = Integer.parseInt(args[0]);
            lastHole = Integer.parseInt(args[1]);
        } else if (args.length == 1) {
            startHole = Integer.parseInt(args[0]);
        }

        //error checking
        if (startHole <= 15 && startHole > 0 && lastHole >= 0 && lastHole <= 15) {
            board = createBoard(startHole);
            possibleJump(lastHole);
            printSteps();
        } else {
            System.out.println("Invaild hole selections");
        }
        
        
    }
  
}
 