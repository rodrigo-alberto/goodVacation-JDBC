package agency.vacation.good.main;

import agency.vacation.good.navigation.MainIO;

/* Este projeto é um sistema simples que simula algumas operações de CRUD de uma agência de viagens, a Good Vacation Agency.
 * 
 * A entrada e saída de dados é realizada através do console, com o uso das classse IO (input e output), que
 * também ficaram responsáveis por aplicar a regra de negócio mínima do sistema.
 * 
 * Usuário-administrador padrão: 
 * - nome: admin
 * - senha: admin
 */

public class Main {
	public static void main(String[] args) {
		MainIO.printMainMenu();
	}
}