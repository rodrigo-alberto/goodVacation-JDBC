package agency.vacation.good.models;

import java.time.LocalDate;
import agency.vacation.good.utils.AcessLevel;

public class UserClient {
	public static final int NOT_FOUND_ID_USER = 0;
	
	private int idUser;
	private String name;
	private String password;
	private boolean isClient;
	private String profilePicture;
	private LocalDate dateBirth;
	private String cpf;
	private AcessLevel acessLevel;
	private Sex sex;
	
	public enum Sex {
		M, F;
	}

	public UserClient() {
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isClient() {
		return isClient;
	}

	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public LocalDate getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(LocalDate dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public AcessLevel getAcessLevel() {
		return acessLevel;
	}
	
	public void setAcessLevel(int codAcessLevel) {
		if(AcessLevel.ADMIN_ACESS.getCod() == codAcessLevel) {
			this.acessLevel = AcessLevel.ADMIN_ACESS;
		}else {
			this.acessLevel = AcessLevel.COMMON_ACCESS;
		}
	}

	public void setAcessLevel(String AcessLevelString) {
		if(AcessLevel.ADMIN_ACESS.getName().equals(AcessLevelString)) {
			this.acessLevel = AcessLevel.ADMIN_ACESS;
		}else {
			this.acessLevel = AcessLevel.COMMON_ACCESS;
		}
	}
	
	public Sex getSex() {
		return sex;
	}

	public void setSex(String sexString) {
		if(Sex.M.toString().equalsIgnoreCase(sexString)) {
			this.sex = Sex.M;
		}else {
			this.sex = Sex.F;		
		}
	}
}