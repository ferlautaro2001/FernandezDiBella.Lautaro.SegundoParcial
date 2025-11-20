package interfaces;

public interface IRobot {

    String getNombre();

    void setNombre(String nombre);

    int getNivelEnergia();

    void setNivelEnergia(int nivelEnergia);

    int getNumeroSerie();

    void setNumeroSerie(int numeroSerie);

    String getTipo();

    String getDetalleEspecifico();
}
