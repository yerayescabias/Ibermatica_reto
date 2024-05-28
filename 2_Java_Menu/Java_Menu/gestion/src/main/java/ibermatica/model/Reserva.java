package ibermatica.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Reserva {
    private String maquina;
    private LocalDate inicio;
    private LocalDate finall;
    public ArrayList<LocalDate> datas = new ArrayList<>();
    public Reserva(String maquina, LocalDate inicio, LocalDate finall) {
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

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFinall() {
        return finall;
    }

    public void setFinall(LocalDate finall) {
        this.finall = finall;
    }

    public ArrayList<LocalDate> fechas(String maquina){
        
        if(maquina.equals(getMaquina())){
            LocalDate incial=inicio;
        datas.add(inicio);
        while (!(incial.isEqual(finall))) {
            datas.add(incial);
            if(incial.getDayOfMonth()==(incial.lengthOfMonth())){
                incial=incial.plusMonths(1);
                incial=incial.minusDays(incial.lengthOfMonth()-1);
            }else if (incial.getDayOfYear()== incial.lengthOfYear()){
                incial= incial.plusYears(1);
            }else{
                incial=incial.plusDays(1);
            }
            
            
            
        }
        datas.add(finall);
        
        return datas;
        } else{
            return null;
        }
        
    }

}
