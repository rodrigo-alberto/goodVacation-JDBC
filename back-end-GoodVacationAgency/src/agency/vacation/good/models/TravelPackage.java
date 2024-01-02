package agency.vacation.good.models;

import java.util.Objects;

public class TravelPackage {
	public static final TravelPackage NOT_FOUND_PACKAGE = null;
	
	private int idPackage;
	private final Trip trip;
	private final UserClient userClient;
	
	public TravelPackage(Trip trip, UserClient userClient) {
		this.trip = trip;
		this.userClient = userClient;
	}	
	
	public int getIdPackage() {
		return idPackage;
	}

	public void setIdPackage(int idPackage) {
		this.idPackage = idPackage;
	}

	public Trip getTrip() {
		return trip;
	}

	public UserClient getUserClient() {
		return userClient;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TravelPackage travelPackage = (TravelPackage) obj;
        return idPackage == travelPackage.idPackage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPackage);
    }
}