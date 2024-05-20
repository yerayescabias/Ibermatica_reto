package ibermatica.model;

import java.util.Date;

public class Maquinas {
    Date adquisition_date;
    String name;
    String serial_num;
    Boolean status;
    
    public Maquinas(Date adquisition_date, String name, String serial_num, Boolean status) {
        this.adquisition_date = adquisition_date;
        this.name = name;
        this.serial_num = serial_num;
        this.status = status;
    }

    public Date getAdquisition_date() {
        return adquisition_date;
    }

    public void setAdquisition_date(Date adquisition_date) {
        this.adquisition_date = adquisition_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    
}
