package models;

public class RobotIndustrial extends Robot {

    private double capacidadCarga;

    public RobotIndustrial(String nombre, int nivelEnergia, int numeroSerie, double capacidadCarga) {
        super(nombre, nivelEnergia, numeroSerie);
        this.capacidadCarga = capacidadCarga;
        this.tipo = "Industrial";
    }

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    @Override
    public String getDetalleEspecifico() {
        return "Carga: " + capacidadCarga + "kg";
    }
}
