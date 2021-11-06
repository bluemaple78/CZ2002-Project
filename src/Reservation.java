import java.sql.Time;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


public class Reservation {

	private Instant date;
	private int pax;
	private int reservationID;
	private int tableNum;
	//parameter to count how long late and if need be can cancel their reservation
	//private Instant

	/**
	 *
	 * @param date
	 * @param pax
	 * @param tableNum
	 */

	public Reservation(Instant date, int pax, int tableNum) {
		this.date = date;
		this.pax = pax;
		this.tableNum = tableNum;
	}

	public Instant getDate() {
		return this.date;
	}

	/**
	 *
	 * @param date
	 */
	public void setDate(Instant date) {
		this.date = date;
	}

	public int getPax() {
		return this.pax;
	}

	/**
	 *
	 * @param pax
	 */
	public void setPax(int pax) {
		this.pax = pax;
	}

	public int getReservationID() {
		return this.reservationID;
	}

	/**
	 *
	 * @param reservationID
	 */
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}	//reservationID just be phone number

	/**
	 *
	 * @param arrival
	 */
	public boolean isExpired() {
		if (Instant.now().isAfter(date.plus(1, ChronoUnit.HOURS))){
			return true;
		}

		return false;
	}

	public int getTableNum() {
		return this.tableNum;
	}

}