package agency.vacation.good.utils;

// @author Rodrigo Alberto;

import javax.swing.JOptionPane;

public enum SystemAlerts {
	//Success alerts;
	REGISTRY_SUCCESS("Registro cadastrado com sucesso!", "Cadastro concluído", JOptionPane.INFORMATION_MESSAGE),
    UPDATE_SUCCESS("Registro atualizado com sucesso!", "Atualização concluída!", JOptionPane.INFORMATION_MESSAGE),
    DELETE_SUCCESS("Registro excluído com sucesso!", "Exclusão concluída!", JOptionPane.INFORMATION_MESSAGE),
    
    //Error alerts;
    REGISTRY_ERROR("Falha no registro!", "Erro no cadastro do(s) registro(s)", JOptionPane.ERROR_MESSAGE),
    QUERY_ERROR("Falha na consulta!", "Erro na consulta do(s) registro(s)", JOptionPane.ERROR_MESSAGE),
    UPDATE_ERROR("Falha na atualização!", "Erro na atualização do(s) registro(s)", JOptionPane.ERROR_MESSAGE),
    DELETE_ERROR("Falha na exclusão!", "Erro na exclusão do(s) registro(s)", JOptionPane.ERROR_MESSAGE),
    
    //Confirm alerts;
    CONFIRM_EXCLUSION("Atenção! Esta operação excluirá todos os registros.\n\n"
    		+ "Deseja continuar?", "Alerta de confirmação de exclusão", JOptionPane.OK_CANCEL_OPTION),
    CONFIRM_USER_EXCLUSION("Atenção! Exluir este usuário excluirá também todos os Contatos e Pacotes de viagens a ele associados.\n\n"
			+ "Deseja continuar?", "Alerta de confirmação de exclusão", JOptionPane.OK_CANCEL_OPTION),
	
    //Restriction alerts;
    OPERATING_RESTRICTION("Atenção! Segundo a regra de negócio atual da Good Vacation Agency, esta operação é inválida.\n\n",
    		"Alerta de confirmação de exclusão", JOptionPane.WARNING_MESSAGE),
    FOREIGN_KEY_RESTRICTION(" * Atenção! Este registro não pode ser excluído pois faz associações com outras entidades. *",
    		"Falha na exclusão!", JOptionPane.WARNING_MESSAGE),
    OPTION_RESTRICTION(" * Entrada inválida! *"),
    FORMAT_RESTRICTION(" * Formato inválido! *"),
	VERIFICATION_RESTRICTION(" * Verifique a janela pop-up gerada! *"),
	
	//Registration alerts not found;	
	USER_NOT_FOUND(" * Usuário não encontrado! *"),
	DESTINY_NOT_FOUND(" * Destino não encontrado! *"),
	CONTACT_NOT_FOUND(" * Contato não encontrado! *"),
	PACKAGE_NOT_FOUND(" * Pacote de viagem não encontrado! *"),
	TRIP_NOT_FOUND(" * Viagem não encontrada! *"),
	
	//Association incompatibility alerts;
	PACKAGE_NOT_ASSOCIATED_USER(" * Pacote não associado a este usuário! *"),
	CONTACT_NOT_ASSOCIATED_USER(" * Contato não associado a este usuário! *"),
	USER_WITHOUT_CONTACTS(" * Usuário sem contatos associados! *"),
	USER_WITHOUT_PACKAGES(" * Usuário sem pacotes de viagens comprados! *");

    private final String message;
    private String title;
    private int messageType;

    private SystemAlerts(String message) {
		this.message = message;
	}

	SystemAlerts(String message, String title, int messageType) {
        this.message = message;
        this.title = title;
        this.messageType = messageType;
    }
    
    public static void printToConsole(SystemAlerts alert) {
    	System.out.print("--------------------------------------------");
    	System.out.println("\n"+alert.message);
    	System.out.println("--------------------------------------------");
    }
    
    public static void printAlertException(Exception e) {  
		System.err.print("--------------------------------------------");
    	System.err.println("\n # Erro: "+e);
		System.err.println("--------------------------------------------");
    }
    
    public static void printAlertMessage(SystemAlerts error) {
    	printToConsole(VERIFICATION_RESTRICTION);
        JOptionPane.showMessageDialog(null, error.message, error.title, error.messageType);
    }
    
    public static int printAlertConfirm(SystemAlerts alert) {
    	printToConsole(VERIFICATION_RESTRICTION);
    	return JOptionPane.showConfirmDialog(null, alert.message, alert.title, alert.messageType);
    }
}