package agency.vacation.good.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CrudMenu {
	public static int printCrudMemnu(Scanner scan, String menuTitle, String extraOptions) {
		int option = 0;

		do {
			try {
				System.out.print(">>\n--------------------------------------------");
				System.out.println("\n# "+menuTitle+" #");
				System.out.print("--------------------------------------------\n");
				System.out.println(" 1 - Cadastrar;\n 2 - Listar todos;\n 3 - Atualizar;\n 4 - Deletar;\n 5 - Sair"+extraOptions);
				System.out.println("---------------------------------------------");
				System.out.print(" - Escolha uma opção: ");
				option = scan.nextInt();
			
			} catch (InputMismatchException e) {
				SystemAlerts.printToConsole(SystemAlerts.OPTION_RESTRICTION);
				scan.nextLine();
			}
			
		} while (option == 0);

		return option;
	}
}
