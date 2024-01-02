package agency.vacation.good.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import agency.vacation.good.connection.ConnectionBd;
import agency.vacation.good.models.Destiny;
import agency.vacation.good.models.Trip;
import agency.vacation.good.utils.SystemAlerts;

public class TripDao {
	public boolean createTrip(Trip trip) {
		String sql = "INSERT INTO trip (departureDate, arrivalDate, travelPrice, isPromotion, fk_destiny_idDestiny) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);

			pst.setTimestamp(1, Timestamp.valueOf(trip.getDepartureDate()));
			pst.setTimestamp(2, Timestamp.valueOf(trip.getArrivalDate()));
			pst.setDouble(3, trip.getTravelPrice());
			pst.setBoolean(4, trip.isPromotion());
			pst.setInt(5, trip.getDestiny().getIdDestiny());
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
	
	public ArrayList<Trip> getAllTrips() {
		String sql = "SELECT * FROM trip as t "
				+ "INNER JOIN destiny as d "
				+ "ON t.fk_destiny_idDestiny = d.idDestiny ";
		ArrayList<Trip> arrTrips = new ArrayList<Trip>();
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
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
				arrTrips.add(trip);
			}
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			SystemAlerts.printAlertException(e);
		}
		
		return arrTrips;
	}
	
	public Trip getTripById(int idTrip) {
		String sql = "SELECT * FROM trip as t "
				+ "INNER JOIN destiny as d "
				+ "ON t.fk_destiny_idDestiny = d.idDestiny "
				+ "WHERE idTrip = ?";
		Trip outputTrip = null;
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			ResultSet rs;
			
			pst.setInt(1, idTrip);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Destiny destiny = new Destiny();

				destiny.setIdDestiny(rs.getInt("idDestiny"));
				destiny.setName(rs.getString("nameDestiny"));
				destiny.setImages(rs.getString("images"));
				destiny.setCity(rs.getString("city"));

				outputTrip = new Trip(destiny);

				outputTrip.setIdTrip(rs.getInt("idTrip"));
				outputTrip.setDepartureDate(rs.getTimestamp("departureDate").toLocalDateTime());
				outputTrip.setArrivalDate(rs.getTimestamp("arrivalDate").toLocalDateTime());
				outputTrip.setTravelPrice(rs.getDouble("travelPrice"));
				outputTrip.setPromotion(rs.getBoolean("isPromotion"));			
			}
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			SystemAlerts.printAlertException(e);
		}
		
		return outputTrip;
	}
	
	public boolean updateTrip(int idTrip, Trip trip) {
		 String sql = "UPDATE trip SET departureDate = ?, arrivalDate = ?, travelPrice = ?, isPromotion = ?, fk_destiny_idDestiny = ? "
		 		+ "WHERE idTrip = ?";
	        
	        try {
	        	PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
	        	pst.setTimestamp(1, Timestamp.valueOf(trip.getDepartureDate()));
				pst.setTimestamp(2, Timestamp.valueOf(trip.getArrivalDate()));
				pst.setDouble(3, trip.getTravelPrice());
				pst.setBoolean(4, trip.isPromotion());
				pst.setInt(5, trip.getDestiny().getIdDestiny());
				pst.setInt(6, idTrip);
	            pst.execute();
	            pst.close();           
	        } catch (SQLException e) {
	        	SystemAlerts.printAlertMessage(SystemAlerts.UPDATE_ERROR);
				SystemAlerts.printAlertException(e);
				return false;
	        }
			
	        SystemAlerts.printAlertMessage(SystemAlerts.UPDATE_SUCCESS);
	        return true;
	}
	
	public boolean deleteTrip(int idTrip) {
		String sql = "DELETE FROM trip WHERE idTrip = ?";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			pst.setInt(1, idTrip);
			pst.execute();
			pst.close();
			
		}catch (SQLIntegrityConstraintViolationException e) {
			SystemAlerts.printAlertMessage(SystemAlerts.FOREIGN_KEY_RESTRICTION);
			return false;
		} catch (SQLException e) {
			SystemAlerts.printAlertMessage(SystemAlerts.DELETE_ERROR);
			SystemAlerts.printAlertException(e);
			return false;
		}
		
		SystemAlerts.printAlertMessage(SystemAlerts.DELETE_SUCCESS);
		return true;
	}
}
