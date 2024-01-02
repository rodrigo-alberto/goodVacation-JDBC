package agency.vacation.good.daos;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import agency.vacation.good.connection.ConnectionBd;
import agency.vacation.good.models.UserClient;
import agency.vacation.good.utils.SystemAlerts;

public class UserClientDao {
	public boolean createUserClient(UserClient userClient) {
		String sql = "INSERT INTO userClient (`name`, `password`, isClient, profilePicture, acessLevel) "
				+ "values(?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			//Preenchendo dados do usuário, quando ele ainda não é considerado um cliente;
			pst.setString(1, userClient.getName());
			pst.setString(2, userClient.getPassword());
			pst.setBoolean(3, userClient.isClient());
			pst.setString(4, userClient.getProfilePicture());
			pst.setString(5, userClient.getAcessLevel().getName());
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
	
	public UserClient getUserbyid(int idUser) {
		String sql = "SELECT * FROM userClient WHERE idUser = ?";
		UserClient outputUserClient = new UserClient();
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			ResultSet rs;
			
			pst.setInt(1, idUser);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				outputUserClient.setIdUser(rs.getInt("idUser"));
				outputUserClient.setName(rs.getString("name"));
				outputUserClient.setPassword(rs.getString("password"));
				outputUserClient.setClient(rs.getBoolean("isClient"));
				outputUserClient.setProfilePicture(rs.getString("profilePicture"));
				outputUserClient.setAcessLevel(rs.getString("acessLevel"));
				outputUserClient.setDateBirth(LocalDate.parse(rs.getDate("dateBirth").toString()));
				outputUserClient.setCpf(rs.getString("cpf"));
				outputUserClient.setSex(rs.getString("sex"));				
			}
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			SystemAlerts.printAlertException(e);
		}
		
		return outputUserClient;
	}
	
	public ArrayList<UserClient> getAllUsers() {
		String sql = "SELECT * FROM userClient";
		ArrayList<UserClient> arrUsers = new ArrayList<UserClient>();
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
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
				arrUsers.add(userClient);
			}
			pst.close();
			rs.close();
		
		} catch (SQLException e) {
			System.out.println(e);
			SystemAlerts.printAlertMessage(SystemAlerts.QUERY_ERROR);
			SystemAlerts.printAlertException(e);
		}
		
		return arrUsers;
	}
	
	public boolean updateUserClient(int idUser, UserClient userClient) {
        String sql = "UPDATE userClient SET name = ?, password = ?, profilePicture = ?, acessLevel = ?, dateBirth = ?, cpf = ?, sex = ?"
        		+ "WHERE idUser = ?";
        
        try {
        	PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
            pst.setString(1, userClient.getName());
            pst.setString(2, userClient.getPassword());
            pst.setString(3, userClient.getProfilePicture());
            pst.setString(4, userClient.getAcessLevel().getName());
     
            if(userClient.isClient()) {
            	pst.setDate(5, Date.valueOf(userClient.getDateBirth()));
                pst.setString(6, userClient.getCpf());
                pst.setString(7, userClient.getSex().name());
            }else {
            	pst.setDate(5,  Date.valueOf(LocalDate.now()));
                pst.setString(6, "000.000.000-00");
                pst.setString(7, "I");
            }
     
            pst.setInt(8, idUser);
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
	
	public boolean deleteUserClient(int idUser) {
		String sql = "DELETE FROM userClient WHERE idUser = ?";
		
		try {
			PreparedStatement pst = ConnectionBd.getConnection().prepareStatement(sql);
			pst.setInt(1, idUser);
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