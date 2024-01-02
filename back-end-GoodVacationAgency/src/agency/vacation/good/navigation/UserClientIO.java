package agency.vacation.good.navigation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import agency.vacation.good.daos.UserClientDao;
import agency.vacation.good.models.UserClient;
import agency.vacation.good.utils.AcessLevel;
import agency.vacation.good.utils.CrudMenu;
import agency.vacation.good.utils.SystemAlerts;

public class UserClientIO {
	private static UserClientDao userClientDao = new UserClientDao();
	private static UserClient userClient;

	public static void printUserClientMenu(Scanner scan, boolean getOut) {
		int option = 0;
		
		do {
			option = CrudMenu.printCrudMemnu(scan, "Menu - Usuário/Cliente", "");

			switch (option) {
				case 1:
					userClient = getUserClientData(scan, new UserClient());

					userClientDao.createUserClient(userClient);
			
					break;
				case 2:
					System.out.print("\n - Usuários da aplicação:\n");
					printUserClientData(userClientDao.getAllUsers());
					
					break;
				case 3:					
					System.out.print("    - Informe o id do usuário que deseja atualizar: ");
					userClient = userClientDao.getUserbyid(scan.nextInt());
					
					if(UserClientIO.validateUser(userClient)) {
						ArrayList<UserClient> filledArrUser = new ArrayList<UserClient>();
						
						filledArrUser.add(userClient);
						printUserClientData(filledArrUser);

						System.out.println("\n      * Usuário encontrado! - Atualize seus dados: *\n");
						userClient = getUserClientData(scan, userClient);
						userClientDao.updateUserClient(userClient.getIdUser(), userClient);	
					}
					
					break;
				case 4:
					System.out.print("    - Informe o id do usuário que deseja deletar: ");
					userClient = userClientDao.getUserbyid(scan.nextInt());
					
					if(UserClientIO.validateUser(userClient)) {
						if(JOptionPane.OK_OPTION == SystemAlerts.printAlertConfirm(SystemAlerts.CONFIRM_USER_EXCLUSION)) {
							userClientDao.deleteUserClient(userClient.getIdUser()); //Deleta o usuário selecionado, bem como todos os seus Contatos e Pacotes de viagens;
						}
					}
					
					break;
				case 5:
					getOut = true;
					
					break;
				default:
					SystemAlerts.printToConsole(SystemAlerts.OPTION_RESTRICTION);
			}
		} while (!getOut);
	}
	
	private static boolean validateAcessLevel(Scanner scan, UserClient selectedUserClient) {
		Boolean isFilled = false;
		int codAcessLevel;
		
		do {
			System.out.print(" - Informe o nível de acesso do usuário (1 p/ Administrativo || 2 p/ Comum): ");
			codAcessLevel = scan.nextInt();
			
			if(AcessLevel.ADMIN_ACESS.getCod() == codAcessLevel) {			
				if(validateAdmUser(scan)) {
					selectedUserClient.setAcessLevel(codAcessLevel);
					isFilled = true;
				}
			}else {
				if(AcessLevel.COMMON_ACCESS.getCod() == codAcessLevel) {
					selectedUserClient.setAcessLevel(codAcessLevel);
					isFilled = true;
				}else {
					SystemAlerts.printToConsole(SystemAlerts.OPTION_RESTRICTION);
				}
			}
		} while (!isFilled);
		return true;
	}
	
	public static boolean validateAdmUser(Scanner scan) {
		System.out.println("\n * Para realizar a operação é preciso ter uma conta de usuário(ADM) *\n");
		System.out.print("    - Informe o id do usuário(ADM): ");
		UserClient admUserClient = userClientDao.getUserbyid(scan.nextInt());
		
		if(UserClient.NOT_FOUND_ID_USER != admUserClient.getIdUser() && AcessLevel.ADMIN_ACESS == admUserClient.getAcessLevel()) {
			return true;
		}else {
			SystemAlerts.printToConsole(SystemAlerts.USER_NOT_FOUND);		
			return false;
		}
	}
	
	public static boolean validateUser(UserClient userClient) {
		if(UserClient.NOT_FOUND_ID_USER != userClient.getIdUser()) {
			return true;
		}else {
			SystemAlerts.printToConsole(SystemAlerts.USER_NOT_FOUND);		
			return false;
		}
	}
	
	private static UserClient getUserClientData(Scanner scan, UserClient selectedUserClient) {
		scan.nextLine();
		
		System.out.print("\n - Informe o primeiro nome do usuário: ");
		selectedUserClient.setName(scan.next());
		System.out.print(" - Informe a senha do usuário: ");
		selectedUserClient.setPassword(scan.next());
		System.out.print(" - Informe o caminho (referência) da imagem de perfil do usuário: "); //Simulação de upload da imagem de perfil;
		selectedUserClient.setProfilePicture(scan.next());
		
		if(validateAcessLevel(scan, selectedUserClient)) {
			if(selectedUserClient.isClient()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				scan.nextLine();
				
				System.out.print(" - Data de nascimento (dd/mm/yyyy): ");
				selectedUserClient.setDateBirth(LocalDate.parse(scan.next(), formatter));				
				System.out.print(" - CPF (com pontuação): ");
				selectedUserClient.setCpf(scan.next());
				System.out.print(" - Sexo (M p/ Masculino ou F p/ Feminino): ");
				selectedUserClient.setSex(scan.next());
			}			
		}
		
		return selectedUserClient;
	}
	
	private static void printUserClientData(ArrayList<UserClient> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println("\n * Usuário["+arr.get(i).getIdUser()+"]");
			System.out.println("  - Nome: "+arr.get(i).getName());
			System.out.println("  - Senha: "+arr.get(i).getPassword());
			System.out.println("  - Caminho da imagem de perfil: "+arr.get(i).getProfilePicture());
			System.out.println("  - Nível de acesso: "+arr.get(i).getAcessLevel());
			System.out.println("  - É cliente? ["+arr.get(i).isClient()+"]");
			
			//Mostra os dados específicos do cliente, se o usuário for considerado um;
			if(arr.get(i).isClient()) {
				System.out.println("  - Data de nascimento: "+arr.get(i).getDateBirth());
				System.out.println("  - CPF: "+arr.get(i).getCpf());
				System.out.println("  - Sexo: "+arr.get(i).getSex());
			}
		}
	}
}