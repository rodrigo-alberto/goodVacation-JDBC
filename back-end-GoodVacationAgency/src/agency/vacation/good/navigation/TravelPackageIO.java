package agency.vacation.good.navigation;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import agency.vacation.good.daos.TravelPackageDao;
import agency.vacation.good.daos.TripDao;
import agency.vacation.good.daos.UserClientDao;
import agency.vacation.good.models.TravelPackage;
import agency.vacation.good.models.Trip;
import agency.vacation.good.models.UserClient;
import agency.vacation.good.utils.CrudMenu;
import agency.vacation.good.utils.SystemAlerts;

public class TravelPackageIO {
	private static TravelPackageDao travelPackageDao = new TravelPackageDao();
	private static TravelPackage travelPackage;
	
	public static void printTravelPackageMenu(Scanner scan, boolean getOut) {
		UserClientDao userClientDao = new UserClientDao();
		TripDao tripDao = new TripDao();
		UserClient selectedUserClient;
		Trip selectedTrip;
		
		int option = 0;
		
		do {
			option = CrudMenu.printCrudMemnu(scan, "Menu - Pacote de viagem", "");

			switch (option) {
				case 1:
					System.out.println("\n * Para realizar a operação é preciso ter uma conta de usuário prévia *\n");
					System.out.print("    - Informe o id do usuário: ");
					selectedUserClient = userClientDao.getUserbyid(scan.nextInt());
					
					if (UserClientIO.validateUser(selectedUserClient)) {
						System.out.print("    - Informe o id da viagem escolhida: ");
						selectedTrip = tripDao.getTripById(scan.nextInt());						
						
						if(TripIO.validadeTrip(selectedTrip)) {
							TravelPackage travelPackage = new TravelPackage(selectedTrip, selectedUserClient);
							
							travelPackageDao.createTravelPackage(travelPackage);
						}
					}		
							
					break;
				case 2:
					System.out.print("    - Informe o id do usuário que deseja visualizar seus pacotes: ");
					selectedUserClient = userClientDao.getUserbyid(scan.nextInt());
					
					if (UserClientIO.validateUser(selectedUserClient)) {
						ArrayList<TravelPackage> arrTravelPackages = travelPackageDao.getAllTravelPackagesByidUser(selectedUserClient.getIdUser());
						
						printTravelPackageData(arrTravelPackages);
					}
					
					break;
				case 3:
					SystemAlerts.printAlertMessage(SystemAlerts.OPERATING_RESTRICTION);
					System.out.println("\n      * Após o pacote ser criado, ou seja, após uma compra de uma viagem ser efetuada, \n"
							+ "nenhum usuário poderá alterar os dados do pacote, apenas visualiza-lo e excluí-lo (cancelar a compra da viagem). *\n");
					
					break;
				case 4:
					System.out.print("    - Informe o id do usuário que deseja excluir seus pacotes: ");
					selectedUserClient = userClientDao.getUserbyid(scan.nextInt());
					
					if (UserClientIO.validateUser(selectedUserClient)) {
						if(validateUserHasTravelPackages(selectedUserClient.getIdUser())) {
							System.out.print("    - Informe o id do pacote que deseja excluir: ");
							travelPackage = travelPackageDao.getTravelPackageById(scan.nextInt());
									
							if(validateTravelPackage(travelPackage, selectedUserClient)) {
								if(JOptionPane.OK_OPTION == SystemAlerts.printAlertConfirm(SystemAlerts.CONFIRM_EXCLUSION)) {
									travelPackageDao.deleteTravelPackageById(travelPackage.getIdPackage());																			
								}
							}							
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
	
	public static boolean validatePackageBelongsUser(TravelPackage travelPackage) {
		if(TravelPackage.NOT_FOUND_PACKAGE != travelPackage) {
			return true;
		}else {
			SystemAlerts.printToConsole(SystemAlerts.PACKAGE_NOT_FOUND);
			return false;
		}
	}
	
	public static boolean validateTravelPackage(TravelPackage travelPackage, UserClient userClient) {
		if(TravelPackage.NOT_FOUND_PACKAGE != travelPackage) {
			if(travelPackageDao.getAllTravelPackagesByidUser(userClient.getIdUser()).contains(travelPackage)) {
				return true;				
			}else {
				SystemAlerts.printToConsole(SystemAlerts.PACKAGE_NOT_ASSOCIATED_USER);
				return false;
			}
		}else {
			SystemAlerts.printToConsole(SystemAlerts.PACKAGE_NOT_FOUND);
			return false;
		}
	}
	
	public static boolean validateUserHasTravelPackages(int idUser) {
		if(!travelPackageDao.getAllTravelPackagesByidUser(idUser).isEmpty()) {
			return true;
		}else {
			SystemAlerts.printToConsole(SystemAlerts.USER_WITHOUT_PACKAGES);
			return false;
		}
	}
	
	private static void printTravelPackageData(ArrayList<TravelPackage> arr) {
		double totalPackage = 0;

		if(!arr.isEmpty()) {
			for (int i = 0; i < arr.size(); i++) {
				System.out.println("\n * id - ["+arr.get(i).getIdPackage()+"]");
				System.out.println("  - Usuário["+arr.get(i).getUserClient().getIdUser()+"] - "+arr.get(i).getUserClient().getName());
				System.out.println("  - Viagem["+arr.get(i).getTrip().getIdTrip()+"] - "+arr.get(i).getTrip().getDestiny().getName());
				System.out.println("  - Data de saída: "+arr.get(i).getTrip().getDepartureDate());
				System.out.println("  - Preço para esta viagem: "+arr.get(i).getTrip().getTravelPrice());
				totalPackage += arr.get(i).getTrip().getTravelPrice();
			}
			System.out.println("\n  - Preço total do pacote (R$): "+totalPackage);
		}else {
			SystemAlerts.printToConsole(SystemAlerts.USER_WITHOUT_PACKAGES);
		}
	}
}