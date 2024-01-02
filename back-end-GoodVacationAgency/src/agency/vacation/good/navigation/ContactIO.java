package agency.vacation.good.navigation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import agency.vacation.good.daos.ContactDao;
import agency.vacation.good.daos.UserClientDao;
import agency.vacation.good.models.Contact;
import agency.vacation.good.models.UserClient;
import agency.vacation.good.utils.CrudMenu;
import agency.vacation.good.utils.SystemAlerts;

public class ContactIO {
	private static ContactDao contactDao = new ContactDao();
	private static Contact contact;
	
	public static void printUserContactMenu(Scanner scan, boolean getOut) {
		UserClientDao userClientDao = new UserClientDao();
		UserClient selectedUserClient;
		int option = 0;
		
		do {
			option = CrudMenu.printCrudMemnu(scan, "Menu - Contato", "");

			switch (option) {
				case 1:
					System.out.println("\n * Para realizar a operação é preciso ter uma conta de usuário prévia *\n");
					System.out.print("    - Informe o id do usuário: ");
					selectedUserClient = userClientDao.getUserbyid(scan.nextInt());
					
					if (UserClientIO.validateUser(selectedUserClient)) {
						Contact filledContact = getContactData(scan, selectedUserClient);
						
						contactDao.createContact(filledContact);
					}		
							
					break;
				case 2:
					System.out.print("    - Informe o id do usuário que deseja visualizar seus contatos: ");
					selectedUserClient = userClientDao.getUserbyid(scan.nextInt());
					
					if (UserClientIO.validateUser(selectedUserClient)) {
						ArrayList<Contact> arrContactsFilled = contactDao.getAllContactsByidUser(selectedUserClient.getIdUser());
						
						printContactData(arrContactsFilled);
					}
					
					break;
				case 3:
					SystemAlerts.printAlertMessage(SystemAlerts.OPERATING_RESTRICTION);
					System.out.println("\n      * Após o contato ser criado, ou seja, após o formulário de e-mail do front-end base do projeto ser enviado, \n"
							+ "nenhum usuário poderá editar a mensagem, apenas visualiza-la e excluí-la da sua perspectiva.*\n");
					
					break;
				case 4:
					System.out.print("    - Informe o id do usuário que deseja excluir seus contatos: ");
					selectedUserClient = userClientDao.getUserbyid(scan.nextInt());
					
					if (UserClientIO.validateUser(selectedUserClient)) {
						if(validateUserHasContacts(selectedUserClient.getIdUser())) {
							int subOption = 0;
							
							System.out.println("\n 1 - Excluir todos os meus contatos;\n 2 - Excluir contato específico;");
							System.out.println("---------------------------------------------");
							System.out.print(" - Escolha uma opção: ");
							subOption = scan.nextInt();
							
							if(subOption == 1) {
								if(JOptionPane.OK_OPTION == SystemAlerts.printAlertConfirm(SystemAlerts.CONFIRM_EXCLUSION)) {
									contactDao.deleteAllContacts(selectedUserClient.getIdUser());								
								}
							}else {
								if(subOption == 2) {
									System.out.print("    - Informe o id do contato que deseja excluir: ");
									contact = contactDao.getContactById(scan.nextInt());
									
									if(validateContact(contact, selectedUserClient)) {
										contactDao.deleteContactById(contact.getIdContact());								
									}
								}else {
									SystemAlerts.printToConsole(SystemAlerts.OPTION_RESTRICTION);
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
	
	public static boolean validateContact(Contact contact, UserClient userClient) {
		if(Contact.NOT_FOUND_CONTACT != contact) {
			if(contactDao.getAllContactsByidUser(userClient.getIdUser()).contains(contact)) {
				return true;				
			}else {
				SystemAlerts.printToConsole(SystemAlerts.CONTACT_NOT_ASSOCIATED_USER);
				return false;
			}
		}else {
			SystemAlerts.printToConsole(SystemAlerts.CONTACT_NOT_FOUND);
			return false;
		}
	}
	
	public static boolean validateUserHasContacts(int idUser) {
		if(!contactDao.getAllContactsByidUser(idUser).isEmpty()) {
			return true;
		}else {
			SystemAlerts.printToConsole(SystemAlerts.USER_WITHOUT_CONTACTS);
			return false;
		}
	}
	
	private static Contact getContactData(Scanner scan, UserClient selectedUserClient) {
		Contact contact = new Contact(selectedUserClient);
		scan.nextLine();
		
		System.out.print("\n - Informe seu e-mail: ");
		contact.setEmail(scan.next());
		System.out.print(" - Informe o assunto da mensagem: ");
		scan.nextLine();
		contact.setSubject(scan.nextLine());
		System.out.print(" - Descreva sua mensagem: ");
		contact.setMessage(scan.nextLine());
		contact.setMessageTime(LocalDateTime.now());
		
		return contact;
	}
	
	private static void printContactData(ArrayList<Contact> arr) {
		if(!arr.isEmpty()) {
			for (int i = 0; i < arr.size(); i++) {
				System.out.println("\n * id - ["+arr.get(i).getIdContact()+"]");
				System.out.println("  - Usuário["+arr.get(i).getUserClient().getIdUser()+"] - "+arr.get(i).getUserClient().getName());
				System.out.println("  - Horário da mensagem: ["+arr.get(i).getMessageTime()+"]");
				System.out.println("  - Email: "+arr.get(i).getEmail());
				System.out.println("  - Assunto: "+arr.get(i).getSubject());
				System.out.println("  - Mensagem: "+arr.get(i).getMessage());
			}			
		}else {
			SystemAlerts.printToConsole(SystemAlerts.USER_WITHOUT_CONTACTS);
		}
	}
}
