package agency.vacation.good.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Contact {
	public static final Contact NOT_FOUND_CONTACT = null;
	
	private int idContact;
	private String email;
	private String subject;
	private String message;
	private LocalDateTime messageTime;
	private final UserClient userClient;

	public Contact(UserClient userClient) {
		this.userClient = userClient;
	}

	public int getIdContact() {
		return idContact;
	}

	public void setIdContact(int idContact) {
		this.idContact = idContact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(LocalDateTime messageTime) {
		this.messageTime = messageTime;
	}

	public UserClient getUserClient() {
		return userClient;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contact contact = (Contact) obj;
        return idContact == contact.idContact;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContact);
    }
}