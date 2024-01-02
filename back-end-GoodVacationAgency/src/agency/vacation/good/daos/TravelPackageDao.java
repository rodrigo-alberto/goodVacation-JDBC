package agency.vacation.good.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import agency.vacation.good.connection.ConnectionBd;
import agency.vacation.good.models.Destiny;
import agency.vacation.good.models.TravelPackage;
import agency.vacation.good.models.Trip;
import agency.vacation.good.models.UserClient;
import agency.vacation.good.utils.SystemAlerts;

public class TravelPackageDao {
	public boolean createTravelPackage(TravelPackage travelPackage) {
		String sql = "INSERT INTO travelPackage (fk_trip_idTrip, fk_userClient_idUser) "
				+ "VALUES(?, ?)";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			pst.setInt(1, travelPackage.getTrip().getIdTrip());
			pst.setInt(2, travelPackage.getUserClient().getIdUser());
			pst.execute();
			pst.close();		
		} catch (SQLException e) {
			SystemAlerts.printAlertMessage(SystemAlerts.REGISTRY_ERROR);
			SystemAlerts.printAlertException(e);
			return false;
		}
		
		SystemAlerts.printAlertMessage(SystemAlerts.REGISTRY_SUCCESS);
		return true;
	}
	
	public ArrayList<TravelPackage> getAllTravelPackagesByidUser(int idUser) {
		ArrayList<TravelPackage> arrTravelPackages = new ArrayList<TravelPackage>();
		String sql = "SELECT * "
				+ "FROM travelPackage as tp INNER JOIN userClient as u "
				+ "ON tp.fk_userClient_idUser = u.idUser INNER JOIN trip as t "
				+ "ON tp.fk_trip_idTrip = t.idTrip INNER JOIN destiny as d "
				+ "ON t.fk_destiny_idDestiny = idDestiny "
				+ "WHERE fk_userClient_idUser = ?";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			ResultSet rs;
			
			pst.setInt(1, idUser);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Destiny destiny = new Destiny();
				
				destiny.setIdDestiny(rs.getInt("idDestiny"));
				destiny.setName(rs.getString("nameDestiny"));
				destiny.setImages(rs.getString("images"));
				destiny.setCity(rs.getString("city"));
				
				Trip trip = new Trip(destiny);
				
				trip.setIdTrip(rs.getInt("idTrip"));
				trip.setDepartureDate(rs.getTimestamp("departureDate").toLocalDateTime());
				trip.setArrivalDate(rs.getTimestamp("arrivalDate").toLocalDateTime());
				trip.setTravelPrice(rs.getDouble("travelPrice"));
				trip.setPromotion(rs.getBoolean("isPromotion"));
				
				UserClient userClient = new UserClient();

				userClient.setIdUser(rs.getInt("idUser"));
				userClient.setName(rs.getString("name"));
				userClient.setPassword(rs.getString("password"));
				userClient.setClient(rs.getBoolean("isClient"));
				userClient.setProfilePicture(rs.getString("profilePicture"));
				userClient.setAcessLevel(rs.getString("acessLevel"));
				userClient.setDateBirth(LocalDate.parse(rs.getDate("dateBirth").toString()));
				userClient.setCpf(rs.getString("cpf"));
				userClient.setSex(rs.getString("sex"));
	
				TravelPackage travelPackage = new TravelPackage(trip, userClient);
				
				travelPackage.setIdPackage(rs.getInt("idPackage"));			
				arrTravelPackages.add(travelPackage);
			}
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			System.out.println(e);
			SystemAlerts.printAlertMessage(SystemAlerts.QUERY_ERROR);
			SystemAlerts.printAlertException(e);
		}
		
		return arrTravelPackages;
	}
	
	public TravelPackage getTravelPackageById(int idPackage) {
		TravelPackage travelPackage = null;
		String sql = "SELECT * "
				+ "FROM travelPackage as tp INNER JOIN userClient as u "
				+ "ON tp.fk_userClient_idUser = u.idUser INNER JOIN trip as t "
				+ "ON tp.fk_trip_idTrip = t.idTrip INNER JOIN destiny as d "
				+ "ON t.fk_destiny_idDestiny = idDestiny "
				+ "WHERE idPackage = ?";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			ResultSet rs;
			
			pst.setInt(1, idPackage);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Destiny destiny = new Destiny();
				
				destiny.setIdDestiny(rs.getInt("idDestiny"));
				destiny.setName(rs.getString("nameDestiny"));
				destiny.setImages(rs.getString("images"));
				destiny.setCity(rs.getString("city"));
				
				Trip trip = new Trip(destiny);
				
				trip.setIdTrip(rs.getInt("idTrip"));
				trip.setDepartureDate(rs.getTimestamp("departureDate").toLocalDateTime());
				trip.setArrivalDate(rs.getTimestamp("arrivalDate").toLocalDateTime());
				trip.setTravelPrice(rs.getDouble("travelPrice"));
				trip.setPromotion(rs.getBoolean("isPromotion"));
				
				UserClient userClient = new UserClient();

				userClient.setIdUser(rs.getInt("idUser"));
				userClient.setName(rs.getString("name"));
				userClient.setPassword(rs.getString("password"));
				userClient.setClient(rs.getBoolean("isClient"));
				userClient.setProfilePicture(rs.getString("profilePicture"));
				userClient.setAcessLevel(rs.getString("acessLevel"));
				userClient.setDateBirth(LocalDate.parse(rs.getDate("dateBirth").toString()));
				userClient.setCpf(rs.getString("cpf"));
				userClient.setSex(rs.getString("sex"));
	
				travelPackage = new TravelPackage(trip, userClient);
				
				travelPackage.setIdPackage(rs.getInt("idPackage"));	
			}
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			SystemAlerts.printAlertException(e);
		}
		
		return travelPackage;
	}
	
	public boolean deleteTravelPackageById(int idPackage) {
		String sql = "DELETE FROM travelPackage WHERE idPackage = ?";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			pst.setInt(1, idPackage);
			pst.execute();
			pst.close();
			
		} catch (SQLException e) {
			SystemAlerts.printAlertMessage(SystemAlerts.DELETE_ERROR);
			SystemAlerts.printAlertException(e);
			return false;
		}
		
		SystemAlerts.printAlertMessage(SystemAlerts.DELETE_SUCCESS);
		return true;
	}
}