package models;

import interfaces.IRobot;
import java.util.Objects;

public abstract class Robot implements IRobot {

    protected String nombre;
    protected int nivelEnergia;
    protected int numeroSerie;
    protected String tipo;

    public Robot(String nombre, int nivelEnergia, int numeroSerie) {
        this.nombre = nombre;
        this.nivelEnergia = nivelEnergia;
        this.numeroSerie = numeroSerie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivelEnergia() {
        return nivelEnergia;
    }

    public void setNivelEnergia(int nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    public int getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public abstract String getDetalleEspecifico();

    @Override
    public String toString() {
        return String.format("[%s] %s - Serie: %d - Energía: %d%% - %s",
                tipo, nombre, numeroSerie, nivelEnergia, getDetalleEspecifico());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Robot robot = (Robot) o;
        return numeroSerie == robot.numeroSerie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroSerie);
    }
}
