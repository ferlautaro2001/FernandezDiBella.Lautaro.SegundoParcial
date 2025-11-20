package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.EscrituraArchivoException;
import exceptions.LecturaArchivoException;
import interfaces.IGestorDatos;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonManager<T> implements IGestorDatos<T> {

    private final Gson gson;

    public JsonManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void guardarLista(List<T> lista, String nombreArchivo) throws EscrituraArchivoException {
        try (Writer writer = new FileWriter(nombreArchivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            throw new EscrituraArchivoException(nombreArchivo, e);
        }
    }

    @Override
    public List<T> cargarLista(String nombreArchivo, Type tipoLista) throws LecturaArchivoException {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(nombreArchivo)) {
            return gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            throw new LecturaArchivoException(nombreArchivo, e);
        }
    }
}
