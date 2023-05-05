package tic.tac.toe;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Game {

	static final String ARA_CIZGI = "*************";
	static final String USERS_CHARACTER = "X";
	static final String COMPUTERS_CHARACTER = "O";
	static final String USER_WINNER = "XXX";
	static final String USER_NEAR_WINNER = "XX";
	static final String COMPUTER_WINNER = "OOO";
	
	private static void writeLog(String log) {
		try {

			FileWriter yazici = new FileWriter("tictactoe.txt", true);
			yazici.write(log);
			yazici.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss"); 
		String log = "Oyun başladı " + formatter.format(dateTime);

		writeLog(log);

		final String USER_QUESTION = "1-9 arası sayı seçerek hamlenizi yapınız:";

		String[] board = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		System.out.println("TIC TAC TOE");
		System.out.println(ARA_CIZGI);
		System.out.println();

		showBoard(board);

		boolean somebodywins = false;

		while (true) {
			System.out.print(USER_QUESTION);

			Scanner input = new Scanner(System.in);

			int userChoice = input.nextInt();

			if (board[userChoice - 1].equals("" + userChoice)) { 
				board[userChoice - 1] = USERS_CHARACTER;
			} else {
				System.out.println("Hatalı bir seçim yaptınız. Tekrar deneyiniz.");
				continue;
			}

			showBoard(board);

			somebodywins = checkWinner(board, USER_WINNER);

			if (somebodywins == true) {
				System.out.println("Oyuncu kazandı."); 
				writeLog("\tOyuncu kazandı.\n");
				break;
			}

			int notXFoundIndex = checkNearWinner(board);

			if (notXFoundIndex > -1) {
				board[notXFoundIndex] = COMPUTERS_CHARACTER;
			} else {

				Random rnd = new Random();

				while (true) {
					int computerChoice = rnd.nextInt(9); 

					if (board[computerChoice].equals("" + (computerChoice + 1))) {
						board[computerChoice] = COMPUTERS_CHARACTER;
						break;
					}
				}
			}
			System.out.println("Bilgisayar hamle yaptı :");
			showBoard(board);
			somebodywins = checkWinner(board, COMPUTER_WINNER);

			if (somebodywins == true) {
				System.out.println("Bilgisayar kazandı ");
				writeLog("\tBilgisayar kazandı.\\n");
				break;
			}

		}

	}

	private static boolean checkWinner(String[] board, String winnerString) {

		for (int i = 0; i < 7; i += 3) {
			if ((board[i] + board[i + 1] + board[i + 2]).equals(winnerString)) {
				return true;
			}
		}

		for (int i = 0; i < 3; i++) {
			if ((board[i] + board[i + 3] + board[i + 6]).equals(winnerString)) {
				return true;
			}
		}

		if ((board[0] + board[4] + board[8]).equals(winnerString)
				|| (board[2] + board[4] + board[6]).equals(winnerString)) {
			return true;
		}

		return false;


	}

	private static int checkNearWinner(String[] board) {

		int counter = 0;
		int notXFoundIndex = -1;

		
		for (int i = 0; i < 3; i++) {
			if (board[i].equals(USERS_CHARACTER)) {
				counter++;
			} else {
				notXFoundIndex = i;
			}
		}

		if (counter == 2) {
			return notXFoundIndex;
		}

		return -1;
	}

	private static void showBoard(String[] board) {
		System.out.println(ARA_CIZGI);
		System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
		System.out.println(ARA_CIZGI);
		System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
		System.out.println(ARA_CIZGI);
		System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
		System.out.println(ARA_CIZGI);
	}

}
