package ibermatica.model;

import java.util.Date;

public class Reserva {
    private String maquina;
    private Date inicio;
    private Date finall;
    
    public Reserva(String maquina, Date inicio, Date finall) {
        this.maquina = maquina;
        this.inicio = inicio;
        this.finall = finall;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFinall() {
        return finall;
    }

    public void setFinall(Date finall) {
        this.finall = finall;
    }

    
}
