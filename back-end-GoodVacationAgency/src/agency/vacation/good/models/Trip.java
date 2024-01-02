package agency.vacation.good.models;

import java.time.LocalDateTime;

public class Trip {
	public static final Trip NOT_FOUND_TRIP = null;
	
	private int idTrip;
	private LocalDateTime departureDate;
	private LocalDateTime arrivalDate;
	private Double travelPrice;
	private boolean isPromotion;
	private final Destiny destiny;
	
	public Trip(Destiny destiny) {
		this.destiny = destiny;
	}

	public int getIdTrip() {
		return idTrip;
	}

	public void setIdTrip(int idTrip) {
		this.idTrip = idTrip;
	}

	public LocalDateTime getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDateTime getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDateTime arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Double getTravelPrice() {
		return travelPrice;
	}

	public void setTravelPrice(Double travelPrice) {
		this.travelPrice = travelPrice;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public void setPromotion(boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public Destiny getDestiny() {
		return destiny;
	}
}