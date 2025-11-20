package interfaces;

import exceptions.EscrituraArchivoException;
import exceptions.LecturaArchivoException;
import java.lang.reflect.Type;
import java.util.List;

public interface IGestorDatos<T> {

    void guardarLista(List<T> lista, String nombreArchivo) throws EscrituraArchivoException;

    List<T> cargarLista(String nombreArchivo, Type tipoLista) throws LecturaArchivoException;
}
