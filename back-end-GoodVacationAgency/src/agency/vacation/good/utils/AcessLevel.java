package agency.vacation.good.utils;

public enum AcessLevel {
	ADMIN_ACESS(1, "adm"),
	COMMON_ACCESS(2, "com");

	private final int cod;
	private final String name;

	private AcessLevel(int cod, String name) {
		this.cod = cod;
		this.name = name;
	}

	public int getCod() {
		return cod;
	}
	
	public String getName() {
		return name;
	}
}