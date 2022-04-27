package model;

public class Passenger {

    private int id;
    private String passengerName;
    private String passengerPhone;
    private int address_id;

    public Passenger() {
    }

    public Passenger(int id, String passengerName, String passengerPhone, int address_id) {
        this.id = id;
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.address_id = address_id;
    }

    public Passenger(String passengerName, String passengerPhone, int address_id) {
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.address_id = address_id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", passengerName='" + passengerName + '\'' +
                ", passengerPhone='" + passengerPhone + '\'' +
                ", address_id=" + address_id +
                '}';
    }
}
