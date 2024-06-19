import java.util.*;

public class minimax_912540205 extends AIModule {
    int player;
    int opponent;
    int maxDepth = 5;
    int bestMoveSeen;
    

    public void getNextMove(final GameStateModule game) {
        player = game.getActivePlayer();
        opponent = (game.getActivePlayer() == 1 ? 2 : 1);
        // begin recursion
        while (!terminate) {
            minimax(game, 0, player);
            if (!terminate)
                chosenMove = bestMoveSeen;
        }
        if (game.canMakeMove(chosenMove))
            game.makeMove(chosenMove);
    }

    private int minimax(final GameStateModule state, int depth, int playerID) {
        amIsecond(state);
        if (terminate)
            return 0;
        if (depth == maxDepth) {
            return eval(state);
        }
        depth++;
        int value = 0;
        // max's turn
        
        if (playerID == player) {
            value = Integer.MIN_VALUE + 1;
            int bestVal = Integer.MIN_VALUE;
            for (int i = 0; i < state.getWidth(); i++) {
                if (state.canMakeMove(i)) {
                    state.makeMove(i);
                    value = Math.max(value, minimax(state, depth, opponent));
                    state.unMakeMove();
                    if (value > bestVal) {
                        bestVal = value;
                        if (depth == 1) { // top of recursion, make our move choice
                            bestMoveSeen = i;
                        }
                    }
                }
            }
            return bestVal;
        }

        else { // min's turn
            value = Integer.MAX_VALUE;
            int bestVal = Integer.MAX_VALUE -1;
            for (int i = 0; i < state.getWidth(); i++) {
                if (state.canMakeMove(i)) {
                    state.makeMove(i);
                    value = Math.min(value, minimax(state, depth, player));
                    state.unMakeMove();
                }
                if (value < bestVal){
                    bestVal = value;
                }
            }
            return bestVal;
        }
    }

    private int eval(final GameStateModule state) {
        int coins= 0;
        

        if (state.isGameOver()) {
            if (state.getWinner() == 1) {
                coins += 2000000;
                
            }
            else if (state.getWinner() == 2) {
                coins -= 3000000;
                
            }
        }

        if (state.getCoins() < 12){
        for (int i = 0; i < state.getWidth(); i++) {
            for (int j = 0; j < state.getHeight(); j++) {
                if (state.getAt(i, j) == 1) {
                    coins += getSlotVal(i, j);
                    
                }
                else if (state.getAt(i, j) == 2) {
                    coins -= getSlotVal(i, j);
                    
                }
            }
        }
    }

        // Horizonal 0110
        for (int y = 0; y < state.getHeight(); y++){
            for (int x = 0; x <= state.getWidth() - 4; x++){
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 1 && state.getAt(x+2, y) == 1 && state.getAt(x+3, y) == 0){
                    coins += 200;
                }
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 2 && state.getAt(x+2, y) == 2 && state.getAt(x+3, y) == 0){
                    coins -= 200;
                }
            }
        }

        // Horizonal 1110
        for (int y = 0; y < state.getHeight(); y++){
            for (int x = 0; x <= state.getWidth() - 4; x++){
                if (state.getAt(x, y) == 1 && state.getAt(x+1, y) == 1 && state.getAt(x+2, y) == 1 && state.getAt(x+3, y) == 0){
                    coins += 200;
                }
                if (state.getAt(x, y) == 2 && state.getAt(x+1, y) == 2 && state.getAt(x+2, y) == 2 && state.getAt(x+3, y) == 0){
                    coins -= 200;
                }
            }
        }

        // Vertical
        for (int x = 0; x < state.getWidth(); x++){
            for (int y = 0; y <= state.getHeight() - 4; y++){
                if (state.getAt(x, y) == 1 && state.getAt(x, y+1) == 1 && state.getAt(x, y+2) == 1 && state.getAt(x, y+3) == 0){
                    coins += 100;
                }
                if (state.getAt(x, y) == 2 && state.getAt(x, y+1) == 2 && state.getAt(x, y+2) == 2 && state.getAt(x, y+3) == 0){
                    coins -= 100;
                }
            }
        }

        // Horizonal 11011
        for (int y = 0; y < state.getHeight(); y++){
            for (int x = 0; x <= state.getWidth() - 3; x++){
                if (state.getAt(x, y) == 1 && state.getAt(x+1, y) == 1 && state.getAt(x+2, y) == 0 && state.getAt(x+3, y) == 1 && state.getAt(x+4, y) == 1){
                    coins += 200;
                }
                if (state.getAt(x, y) == 2 && state.getAt(x+1, y) == 2 && state.getAt(x+2, y) == 0 && state.getAt(x+3, y) == 2 && state.getAt(x+4, y) == 2){
                    coins -= 200;
                }
            }
        }

        // Horizonal 00110
        for (int y = 0; y < state.getHeight(); y++){
            for (int x = 0; x <= state.getWidth() - 3; x++){
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 0 && state.getAt(x+2, y) == 1 && state.getAt(x+3, y) == 1 && state.getAt(x+4, y) == 0){
                    coins += 300;
                }
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 0 && state.getAt(x+2, y) == 2 && state.getAt(x+3, y) == 2 && state.getAt(x+4, y) == 0){
                    coins -= 300;
                }
            }
        }

        //Horizontal 01110
        for (int y = 0; y < state.getHeight(); y++){
            for (int x = 0; x <= state.getWidth() - 3; x++){
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 1 && state.getAt(x+2, y) == 1 && state.getAt(x+3, y) == 1 && state.getAt(x+4, y) == 0){
                    coins += 500;
                }
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 2 && state.getAt(x+2, y) == 2 && state.getAt(x+3, y) == 2 && state.getAt(x+4, y) == 0){
                    coins -= 500;
                }
            }
        }
        //Horizontal 001100
        for (int y = 0; y < state.getHeight(); y++){
            for (int x = 0; x <= state.getWidth() - 2; x++){
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 0 && state.getAt(x+2, y) == 1 && state.getAt(x+3, y) == 1 && state.getAt(x+4, y) == 0 && state.getAt(x+5, y) == 0){
                    coins += 200;
                }
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 0 && state.getAt(x+2, y) == 2 && state.getAt(x+3, y) == 2 && state.getAt(x+4, y) == 0 && state.getAt(x+5, y) == 0){
                    coins -= 200;
                }
            }
        }

        // Horizonal 1100 0011
        for (int y = 0; y < state.getHeight(); y++){
            for (int x = 0; x <= state.getWidth() - 4; x++){
                if (state.getAt(x, y) == 1 && state.getAt(x+1, y) == 1 && state.getAt(x+2, y) == 0 && state.getAt(x+3, y) == 0){
                    coins += 50;
                }
                if (state.getAt(x, y) == 2 && state.getAt(x+1, y) == 2 && state.getAt(x+2, y) == 0 && state.getAt(x+3, y) == 0){
                    coins -= 50;
                }
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 0 && state.getAt(x+2, y) == 1 && state.getAt(x+3, y) == 1){
                    coins += 50;
                }
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y) == 0 && state.getAt(x+2, y) == 2 && state.getAt(x+3, y) == 2){
                    coins -= 50;
                }
            }
        }

        // Diagonal up right 0110
        for (int x = 0; x <= state.getWidth() - 4; x++){
            for (int y = 0; y <= state.getHeight() - 4; y++){
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y+1) == 1 && state.getAt(x+2, y+2) == 1 && state.getAt(x+3, y+3) == 0){
                    coins += 100;
                }
                if (state.getAt(x, y) == 0 && state.getAt(x+1, y+1) == 2 && state.getAt(x+2, y+2) == 2 && state.getAt(x+3, y+3) == 0){
                    coins -= 1000;
                }
            }
        }

        // Diagonal up left 0110
        for (int x = state.getWidth() - 4; x >= 0; x--){
            for (int y = state.getHeight() - 4; y >=0; y--){
                if (state.getAt(x, y) == 0 && state.getAt(x-1, y-1) == 1 && state.getAt(x-2, y-2) == 1 && state.getAt(x-3, y-3) == 0){
                    coins += 100;
                }
                if (state.getAt(x, y) == 0 && state.getAt(x-1, y+1) == 2 && state.getAt(x-2, y+2) == 2 && state.getAt(x-3, y+3) == 0){
                    coins -= 1000;
                }
            }
        }

        // Diagonal up right 1101
        for (int x = 0; x <= state.getWidth() - 4; x++){
            for (int y = 0; y <= state.getHeight() - 4; y++){
                if (state.getAt(x, y) == 1 && state.getAt(x+1, y+1) == 1 && state.getAt(x+2, y+2) == 0 && state.getAt(x+3, y+3) == 1){
                    coins += 200;
                }
                if (state.getAt(x, y) == 2 && state.getAt(x+1, y+1) == 2 && state.getAt(x+2, y+2) == 0 && state.getAt(x+3, y+3) == 2){
                    coins -= 2000;
                }
            }
        }

        // Diagonal up left 1101
        for (int x = state.getWidth() - 4; x >= 0; x--){
            for (int y = state.getHeight() - 4; y >=0; y--){
                if (state.getAt(x, y) == 1 && state.getAt(x-1, y+1) == 1 && state.getAt(x-2, y+2) == 0 && state.getAt(x-3, y+3) == 1){
                    coins += 200;
                }
                if (state.getAt(x, y) == 2 && state.getAt(x-1, y+1) == 2 && state.getAt(x-2, y+2) == 0 && state.getAt(x-3, y+3) == 2){
                    coins -= 2000;
                }
            }
        }

        // Diagonal up right 1011
        for (int x = 0; x <= state.getWidth() - 4; x++){
            for (int y = 0; y <= state.getHeight() - 4; y++){
                if (state.getAt(x, y) == 1 && state.getAt(x+1, y+1) == 0 && state.getAt(x+2, y+2) == 1 && state.getAt(x+3, y+3) == 1){
                    coins += 200;
                }
                if (state.getAt(x, y) == 2 && state.getAt(x+1, y+1) == 0 && state.getAt(x+2, y+2) == 2 && state.getAt(x+3, y+3) == 2){
                    coins -= 2000;
                }
            }
        }

        // Diagonal up left 1011
        for (int x = state.getWidth() - 4; x >= 0; x--){
            for (int y = state.getHeight() - 4; y >=0; y--){
                if (state.getAt(x, y) == 1 && state.getAt(x-1, y+1) == 0 && state.getAt(x-2, y+2) == 1 && state.getAt(x-3, y+3) == 1){
                    coins += 200;
                }
                if (state.getAt(x, y) == 2 && state.getAt(x-1, y+1) == 0 && state.getAt(x-2, y+2) == 2 && state.getAt(x-3, y+3) == 2){
                    coins -= 2000;
                }
            }
        }

        if (second){
            return coins * -1;
        }

        return coins;
    }

    static private int flag = 0;
    static private boolean second = false;

    private void amIsecond(GameStateModule state){
        if (flag == 0){
            for (int i = 0; i < state.getWidth(); i++) {
                for (int j = 0; j < state.getHeight(); j++) {
                    if (state.getAt(i, j) != 0) {
                        flag = 1;
                        second = true;
                    }
                }
            }
            flag = 1;
        }
    }

    private int getSlotVal(int i, int j){

        if ((i == 0 && j == 0) || (i == 0 && j == 5) || (i == 6 && j == 0) || (i == 6 && j == 5)){
            return 3;
        }

        if ((i == 0 && j == 1) || (i == 1 && j == 0) || (i == 0 && j == 4) || (i == 1 && j == 5) || (i == 5 && j == 0) || 
        (i == 6 && j == 1) || (i == 5 && j == 5) || (i == 6 && j == 4)){
            return 4;
        }

        if ((i == 0 && j == 2) || (i == 0 && j == 3) || (i == 2 && j == 0) || (i == 2 && j == 5) || (i == 4 && j == 0) || 
        (i == 4 && j == 5) || (i == 6 && j == 2) || (i == 6 && j == 3)){
            return 5;
        }

        if ((i == 1 && j == 1) || (i == 1 && j == 4) || (i == 5 && j == 1) || (i == 5 && j == 4)){
            return 6;
        }

        if ((i == 3 && j == 0) || (i == 3 && j == 5)){
            return 7;
        }

        if ((i == 1 && j == 2) || (i == 1 && j == 3) || (i == 2 && j == 1) || (i == 2 && j == 4) || (i == 4 && j == 1) || 
        (i == 4 && j == 4) || (i == 5 && j == 2) || (i == 5 && j == 3)){
            return 8;
        }

        if ((i == 3 && j == 1) || (i == 3 && j == 4)){
            return 10;
        }

        if ((i == 2 && j == 2) || (i == 2 && j == 3) || (i == 4 && j == 2) || (i == 4 && j == 3)){
            return 11;
        }

        if ((i == 3 && j == 2) || (i == 3 && j == 3)){
            return 13;
        }
        return 0;
    }

}