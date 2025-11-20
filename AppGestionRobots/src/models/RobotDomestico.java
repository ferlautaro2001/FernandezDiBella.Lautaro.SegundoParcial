package models;

public class RobotDomestico extends Robot {

    private int cantidadTareas;

    public RobotDomestico(String nombre, int nivelEnergia, int numeroSerie, int cantidadTareas) {
        super(nombre, nivelEnergia, numeroSerie);
        this.cantidadTareas = cantidadTareas;
        this.tipo = "Doméstico";
    }

    public int getCantidadTareas() {
        return cantidadTareas;
    }

    public void setCantidadTareas(int cantidadTareas) {
        this.cantidadTareas = cantidadTareas;
    }

    @Override
    public String getDetalleEspecifico() {
        return "Tareas: " + cantidadTareas;
    }
}
