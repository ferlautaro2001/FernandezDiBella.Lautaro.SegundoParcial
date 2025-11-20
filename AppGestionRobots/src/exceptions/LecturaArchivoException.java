package exceptions;

public class LecturaArchivoException extends PersistenciaException {

    public LecturaArchivoException(String nombreArchivo, Throwable cause) {
        super("Error al leer el archivo: " + nombreArchivo, cause);
    }
}
