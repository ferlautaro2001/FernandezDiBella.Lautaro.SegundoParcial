package exceptions;

public class EscrituraArchivoException extends PersistenciaException {

    public EscrituraArchivoException(String nombreArchivo, Throwable cause) {
        super("Error al escribir en el archivo: " + nombreArchivo, cause);
    }
}
