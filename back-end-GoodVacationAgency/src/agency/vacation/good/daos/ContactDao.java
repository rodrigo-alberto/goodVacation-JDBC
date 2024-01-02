package agency.vacation.good.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import agency.vacation.good.connection.ConnectionBd;
import agency.vacation.good.models.Contact;
import agency.vacation.good.models.UserClient;
import agency.vacation.good.utils.SystemAlerts;

public class ContactDao {
	public boolean createContact(Contact contact) {
		String sql = "INSERT INTO contact (email, `subject`, message, messageTime, fk_userClient_idUser) "
				+ "VALUES(?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			pst.setString(1, contact.getEmail());
			pst.setString(2, contact.getSubject());
			pst.setString(3, contact.getMessage());	
			pst.setTimestamp(4, Timestamp.valueOf(contact.getMessageTime()));
			pst.setInt(5, contact.getUserClient().getIdUser());
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
	
	public ArrayList<Contact> getAllContactsByidUser(int idUser) {
		ArrayList<Contact> arrContacts = new ArrayList<Contact>();
		String sql = "SELECT * "
				+ "FROM contact as c INNER JOIN userClient as u "
				+ "ON c.fk_userClient_idUser = u.idUser "
				+ "WHERE c.fk_userClient_idUser = ? ";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			ResultSet rs;
			
			pst.setInt(1, idUser);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				UserClient contactUser = new UserClient();

				contactUser.setIdUser(rs.getInt("idUser"));
				contactUser.setName(rs.getString("name"));
				contactUser.setPassword(rs.getString("password"));
				contactUser.setClient(rs.getBoolean("isClient"));
				contactUser.setProfilePicture(rs.getString("profilePicture"));
				contactUser.setAcessLevel(rs.getString("acessLevel"));
				contactUser.setDateBirth(LocalDate.parse(rs.getDate("dateBirth").toString()));
				contactUser.setCpf(rs.getString("cpf"));
				contactUser.setSex(rs.getString("sex"));

				Contact contact = new Contact(contactUser);
				
				contact.setIdContact(rs.getInt("idContact"));
				contact.setEmail(rs.getString("email"));
				contact.setSubject(rs.getString("subject"));
				contact.setMessage(rs.getString("message"));
				contact.setMessageTime(rs.getTimestamp("messageTime").toLocalDateTime());
				arrContacts.add(contact);
			}
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			System.out.println(e);
			SystemAlerts.printAlertMessage(SystemAlerts.QUERY_ERROR);
			SystemAlerts.printAlertException(e);
		}
		
		return arrContacts;
	}
	
	public Contact getContactById(int idContact) {
		String sql = "SELECT * FROM contact as c "
				+ "INNER JOIN userClient as u "
				+ "ON c.fk_userClient_idUser = u.idUser "
				+ "WHERE idContact = ?";
		Contact outputContact = null;
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			ResultSet rs;
			
			pst.setInt(1, idContact);
			rs = pst.executeQuery();
			
			while (rs.next()) {
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
				
				outputContact = new Contact(userClient);
				
				outputContact.setIdContact(rs.getInt("idContact"));
				outputContact.setEmail(rs.getString("email"));
				outputContact.setSubject(rs.getString("subject"));
				outputContact.setMessage(rs.getString("message"));
				outputContact.setMessageTime(rs.getTimestamp("messageTime").toLocalDateTime());		
			}
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			SystemAlerts.printAlertException(e);
		}
		
		return outputContact;
	}
	
	public boolean deleteAllContacts(int fkIdUser) {
		String sql = "DELETE FROM contact WHERE fk_userClient_idUser = ?";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			pst.setInt(1, fkIdUser);
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
	
	public boolean deleteContactById(int idContact) {
		String sql = "DELETE FROM contact WHERE idContact = ?";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			pst.setInt(1, idContact);
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