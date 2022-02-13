import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseButton;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class App extends Application {
    GridPane grid = new GridPane();
    Label board[][] = new Label[3][3];
    int size = 240;
    int cellSize = size / 3;
    boolean xTurn = true;
    boolean over = false;

    void initBoard(Label[][] board, GridPane gridPane, int size) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                Label label = new Label();
                label.setText(".");
                label.setMinHeight(size);
                label.setMinWidth(size);
                label.setAlignment(Pos.CENTER);
                label.setMouseTransparent(true);
                board[row][col] = label;
                gridPane.add(label, col, row);
            }
        }
    }

    boolean move(Label[][] board, int row, int column) {
        if (board[row][column].getText() != ".") {
            return false;
        } else {
            return true;
        }
    }

    boolean win(Label[][] board, int column, int row, String playerLetter) {
        int inARow = checkTheRow(board, row, playerLetter);
        int inAColumn = checkTheColumn(board, column, playerLetter);
        int inADiagonal = checkFirstDiagonal(board, playerLetter);
        int inOtherDiagonal = checkOtherDiagonal(board, playerLetter);
        if (inARow == 3 || inAColumn == 3 || inADiagonal == 3 || inOtherDiagonal == 3) {
            return true;
        } else {
            return false;
        }

    }

    int checkTheRow(Label[][] board, int row, String playerLetter) {
        int numInARow = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[row][i].getText() == playerLetter) {
                numInARow++;
            }
        }
        return numInARow;
    }

    int checkTheColumn(Label[][] board, int column, String playerLetter) {
        int numInAColumn = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][column].getText() == playerLetter) {
                numInAColumn++;
            }
        }
        return numInAColumn;
    }

    int checkFirstDiagonal(Label[][] board, String playerLetter) {
        int numInDiagonal = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][i].getText() == playerLetter) {
                numInDiagonal++;
            }
        }
        return numInDiagonal;
    }

    int checkOtherDiagonal(Label[][] board, String playerLetter) {
        int numInDiagonal = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = board.length - 1; j >= 0; j--) {
                if (board[i][j].getText() == playerLetter) {
                    numInDiagonal++;
                }
            }
        }
        return numInDiagonal;
    }

    void makeMove(Label[][] board, String player, int rowPosition, int colPosition) {
        boolean canMove = move(board, rowPosition, colPosition);
        if (canMove) {

            board[rowPosition][colPosition].setText(player);

            boolean won = win(board, colPosition, rowPosition, player);

            if (won) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Player " + player + " won!");
                alert.showAndWait();
                over = true;
            }
        } else {
            System.out.println("your an idiot, choose another spot");
        }

    }

    @Override
    public void start(Stage primaryStage) {
        grid.setGridLinesVisible(true);
        //*
        grid.setOnMouseClicked(event ->
        {
            Node cell = (Node) event.getTarget();
            int col = -1, row = -1;
            String player;

            if (over) {
                return;
            }

            col = (int)(event.getX() / cellSize);
            row = (int)(event.getY() / cellSize);

            System.out.format("mouseClick col=%d row=%d\n", col, row);

            if (event.getButton() == MouseButton.PRIMARY) {
                if (!xTurn) {
                    return;
                }
                xTurn = !xTurn;
                player = "X";
            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (xTurn) {
                    return;
                }
                xTurn = !xTurn;
                player = "O";
            } else {
                return;
            }

            makeMove(board, player, row, col);
        });
        // */

        initBoard(board, grid, size / 3);

        // create a scene specifying the root and the size
        Scene scene = new Scene(grid, size, size);
        // add scene to the stage
        primaryStage.setScene(scene);
        // make the stage visible
        primaryStage.show();
    }

    public static void main(String[] args) {
        // launch the HelloWorld application.
        // Since this method is a member of the HelloWorld class the first
        // parameter is not required
        Application.launch(App.class, args);
    }
}