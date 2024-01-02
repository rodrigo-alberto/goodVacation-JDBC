package agency.vacation.good.navigation;

import java.util.ArrayList;
import java.util.Scanner;
import agency.vacation.good.daos.DestinyDao;
import agency.vacation.good.models.Destiny;
import agency.vacation.good.utils.CrudMenu;
import agency.vacation.good.utils.SystemAlerts;

public class DestinyIO {
	private static DestinyDao destinyDao = new DestinyDao();
	private static Destiny destiny;
	
	public static void printDestinyMenu(Scanner scan, boolean getOut) {
		int option = 0;
		
		do {
			option = CrudMenu.printCrudMemnu(scan, "Menu - Destino", "");
			
			switch (option) {
				case 1:
					if(UserClientIO.validateAdmUser(scan)) {
						Destiny destinyFilled = getDestinyData(scan, new Destiny());
						
						destinyDao.createDestiny(destinyFilled);														
					}

					break;
				case 2:
					if(UserClientIO.validateAdmUser(scan)) {
						ArrayList<Destiny> arrDestinyFilled = destinyDao.getAllDestinys();
						
						printDestinyData(arrDestinyFilled);
					}
					
					break;
				case 3:
					if(UserClientIO.validateAdmUser(scan)) {
						System.out.print("    - Informe o id do destino que deseja atualizar: ");
						destiny = destinyDao.getDestinyById(scan.nextInt());

						if(validateDestiny(scan, destiny)) {
							ArrayList<Destiny> arrDestiny = new ArrayList<Destiny>();

							arrDestiny.add(destiny);
							printDestinyData(arrDestiny);
							System.out.println("\n      * Destino encontrado! - Atualize os seus dados: *\n");
							destiny = getDestinyData(scan, destiny);
							destinyDao.updateDestiny(destiny.getIdDestiny(), destiny);
						}
					}
					
					break;
				case 4:
					if(UserClientIO.validateAdmUser(scan)) {
						System.out.print("\n    - Informe o id do destino que deseja deletar: ");
						destiny = destinyDao.getDestinyById(scan.nextInt());	
						
						if(validateDestiny(scan, destiny)) {
							destinyDao.deleteDestiny(destiny.getIdDestiny());
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
	
	public static boolean validateDestiny(Scanner scan, Destiny destiny) {
		if(Destiny.NOT_FOUND_DESTINY != destiny) {
			return true;
		}else {
			SystemAlerts.printToConsole(SystemAlerts.DESTINY_NOT_FOUND);
			return false;
		}
	}
	
	private static Destiny getDestinyData(Scanner scan, Destiny destiny) {
		scan.nextLine();
		
		System.out.print("\n - Informe o nome do destino: ");
		destiny.setName(scan.nextLine());
		System.out.print(" - Informe o caminho (referência) da imagem do destino: ");
		destiny.setImages(scan.nextLine());
		System.out.print(" - Informe o nome da cidade do destino: ");
		destiny.setCity(scan.nextLine());
		
		return destiny;
	}
	
	private static void printDestinyData(ArrayList<Destiny> arr) {
		if(!arr.isEmpty()) {
			for (int i = 0; i < arr.size(); i++) {
				System.out.println("\n * Destino["+arr.get(i).getIdDestiny()+"]");
				System.out.println("  - Nome: "+arr.get(i).getName());
				System.out.println("  - Caminho (referência) da imagem: "+arr.get(i).getImages());
				System.out.println("  - Cidade do destino: "+arr.get(i).getCity());
			}			
		}else {
			System.out.println("\n      * Não há destinos cadastrados no sistema\n"); //O que na teoria nunca deveria acontecer =);
		}
	}
}