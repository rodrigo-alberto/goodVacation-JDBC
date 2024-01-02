package agency.vacation.good.navigation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import agency.vacation.good.daos.DestinyDao;
import agency.vacation.good.daos.TripDao;
import agency.vacation.good.models.Destiny;
import agency.vacation.good.models.Trip;
import agency.vacation.good.utils.CrudMenu;
import agency.vacation.good.utils.SystemAlerts;

public class TripIO {
	private static final TripDao tripDao = new TripDao();
	private static Trip trip;
	
	public static void printTripMenu(Scanner scan, boolean getOut) {
		DestinyDao destinyDao = new DestinyDao();
		int option = 0;
		
		do {
			option = CrudMenu.printCrudMemnu(scan, "Menu - Viagem", "");

			switch (option) {
				case 1:
					if(UserClientIO.validateAdmUser(scan)) {
						System.out.print("    - Informe o id do destino para esta viagem: ");
						Destiny selectedDestiny = destinyDao.getDestinyById(scan.nextInt());
						
						if(DestinyIO.validateDestiny(scan, selectedDestiny)) {
							Trip tripFilled = getTripData(scan, selectedDestiny);
							
							tripDao.createTrip(tripFilled);
						}					
					}
							
					break;
				case 2:
					ArrayList<Trip> arrTripFilled = tripDao.getAllTrips();
						
					printTripData(arrTripFilled);
					
					break;
				case 3:
					if(UserClientIO.validateAdmUser(scan)) {
						System.out.print("    - Informe o id da viagem que deseja atualizar: ");
						int idTrip = scan.nextInt();
						trip = tripDao.getTripById(idTrip);
						
						if(validadeTrip(trip)) {
							ArrayList<Trip> arrTrip = new ArrayList<Trip>();
							
							arrTrip.add(trip);
							printTripData(arrTrip);
							System.out.println("\n      * Viagem encontrada! - Atualize os seus dados: *");
							trip = getTripData(scan, trip.getDestiny());
							trip.setIdTrip(idTrip);
							tripDao.updateTrip(trip.getIdTrip(), trip);
						}
					}
					
					break;
				case 4:
					if(UserClientIO.validateAdmUser(scan)) {
						System.out.print("\n    - Informe o id da viagem que deseja deletar: ");
						trip = tripDao.getTripById(scan.nextInt());
						
						if(validadeTrip(trip)) {
							tripDao.deleteTrip(trip.getIdTrip());							
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
	
	public static boolean validadeTrip(Trip trip) {
		if(Trip.NOT_FOUND_TRIP != trip) {
			return true;
		}else {
			SystemAlerts.printToConsole(SystemAlerts.TRIP_NOT_FOUND);
			return false;
		}	
	}
	
	private static Trip getTripData(Scanner scan, Destiny destiny) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		Trip filledTrip = new Trip(destiny);
		scan.nextLine();
		
		System.out.print("\n - Informe o horário de partida da viagem (dd/MM/yyyy HH:mm:ss): ");
		filledTrip.setDepartureDate(LocalDateTime.parse(scan.nextLine(), formatter));
		System.out.print(" - Informe o horário de chegada da viagem (dd/MM/yyyy HH:mm:ss): ");
		filledTrip.setArrivalDate(LocalDateTime.parse(scan.nextLine(), formatter));	
		System.out.print(" - Informe o preço para esta viagem em reais (Ex. 100,00): ");
		filledTrip.setTravelPrice(scan.nextDouble());
		System.out.print(" - Esta viagem está na promoção? (true ou false): ");
		filledTrip.setPromotion(scan.nextBoolean());

		return filledTrip;
	}
	
	private static void printTripData(ArrayList<Trip> arr) {
		if(!arr.isEmpty()) {
			for (int i = 0; i < arr.size(); i++) {
				System.out.println("\n * Viagem ["+arr.get(i).getIdTrip()+"]");
				System.out.println("  - Destino da viagem: "+arr.get(i).getDestiny().getName());
				System.out.println("  - Data e horário de partida da viagem: "+arr.get(i).getDepartureDate());
				System.out.println("  - Data e horário de chegada da viagem: "+arr.get(i).getArrivalDate());
				System.out.println("  - Preço desta viagem (R$): "+arr.get(i).getTravelPrice());
				System.out.println("  - Esta viagem está na promoção?: "+arr.get(i).isPromotion());
			}			
		}else {
			System.out.println("\n      * Não há viagens cadastrados no sistema *\n");
		}
	}
}
