package agency.vacation.good.models;

public class Destiny {
	public static final Destiny NOT_FOUND_DESTINY = null;
	
	private int idDestiny;
	private String name;
	private String images;
	private String city;
	
	public Destiny() {
	}

	public int getIdDestiny() {
		return idDestiny;
	}

	public void setIdDestiny(int idDestiny) {
		this.idDestiny = idDestiny;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}