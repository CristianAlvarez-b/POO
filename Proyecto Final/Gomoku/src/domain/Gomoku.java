package domain;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Gomoku{
    private Board board;
    private Player player1;
    private Player player2;
    private int stoneLimit = 800;
    private int timeLimit;

    public Gomoku(int size, int stoneLimit, int timeLimit, int porcentajeEspeciales) throws Exception {
        if(size < 10){
            throw new GomokuException(GomokuException.INVALID_BOARD_SIZE);
        }
        board = new Board(size, size, porcentajeEspeciales);
        this.stoneLimit = stoneLimit;
        this.timeLimit = timeLimit;
        this.setPlayers(Human.class, Human.class);
        board.setPlayers(new Player[]{this.player1,this.player2});
    }
    public void setPlayers(Class<? extends Player> playerClass1, Class<? extends Player> playerClass2) throws Exception {
        this.player1 = playerClass1.getDeclaredConstructor(Color.class, Board.class, int.class).newInstance(Color.BLACK, board, 0);
        this.player2 = playerClass2.getDeclaredConstructor(Color.class, Board.class, int.class).newInstance(Color.WHITE, board, 0);
        player1.refillStones(stoneLimit, board.getSpecialPercentage());
        player2.refillStones(stoneLimit, board.getSpecialPercentage());
        board.setPlayers(new Player[]{this.player1,this.player2});
    }
    public boolean play(int row, int column, Stone stone) throws Exception {
        boolean turn = board.getTurn();
        if(turn){
            if (player1 instanceof Human){
                player1.play(row, column, stone);
            }else {
                ((Machine)player1).play();
            }
        }else {
            if (player2 instanceof Human){
                player2.play(row, column, stone);
            }else {
                ((Machine)player2).play();
            }
        }
        return board.verifyGame(turn);
    }

    public boolean getTurn(){return board.getTurn();}
    public Player getPlayer1(){return player1;}
    public Player getPlayer2(){return player2;}

    public Cell[][] board() {
        return board.getCells();
    }

    public static Gomoku open(File archivo) throws GomokuException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            String header = (String) in.readObject();
            if (!header.equals("Gomoku storage\n")) {
                throw new GomokuException("El archivo no es un archivo de almacenamiento de gomoku válido.");
            }
            return (Gomoku) in.readObject();
        } catch (FileNotFoundException e) {
            throw new GomokuException("El archivo no existe: " + archivo.getName());
        } catch (IOException e) {
            throw new GomokuException("Error al leer el archivo: " + archivo.getName());
        }
    }
    public void save(File archivo) throws GomokuException {
        File archivoFinal = new File(archivo.getPath() + ".dat");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoFinal))) {
            out.writeObject("Gomoku storage\n");
            out.writeObject(this);
        } catch (IOException e) {
            throw new GomokuException("Error al escribir en el archivo: "+ archivo.getName());
        }
    }

    public Board getBoard() {
        return board;
    }
}