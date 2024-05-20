package ibermatica.model;

import java.util.Date;

public class Reservas {
    String user_id;
    String serial_num;
    Date start_date;
    Date end_Date;
    Date cancell_date;
    int reservation_id;


    public Reservas(String user_id, String serial_num, Date start_date, Date end_Date, int reservation_id) {
        this.user_id = user_id;
        this.serial_num = serial_num;
        this.start_date = start_date;
        this.end_Date = end_Date;
        this.reservation_id = reservation_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(Date end_Date) {
        this.end_Date = end_Date;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    
    

}
