package interfaces;

import exceptions.EscrituraArchivoException;
import exceptions.LecturaArchivoException;
import java.util.List;
import models.Robot;

public interface IGestorRobots {

    void guardarRobots(List<Robot> listaCompleta) throws EscrituraArchivoException;

    List<Robot> cargarRobots() throws LecturaArchivoException;
}
