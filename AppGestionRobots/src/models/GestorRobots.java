package models;

import com.google.gson.reflect.TypeToken;
import exceptions.EscrituraArchivoException;
import exceptions.LecturaArchivoException;
import interfaces.IGestorDatos;
import interfaces.IGestorRobots;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GestorRobots implements IGestorRobots {

    private static final String ARCHIVO_DOMESTICOS = "robots_domesticos.json";
    private static final String ARCHIVO_INDUSTRIALES = "robots_industriales.json";

    private final IGestorDatos<RobotDomestico> gestorDomesticos;
    private final IGestorDatos<RobotIndustrial> gestorIndustriales;

    public GestorRobots() {
        this.gestorDomesticos = new JsonManager<>();
        this.gestorIndustriales = new JsonManager<>();
    }

    @Override
    public void guardarRobots(List<Robot> listaCompleta) throws EscrituraArchivoException {
        List<RobotDomestico> domesticos = new ArrayList<>();
        List<RobotIndustrial> industriales = new ArrayList<>();

        for (Robot r : listaCompleta) {
            if (r instanceof RobotDomestico) {
                domesticos.add((RobotDomestico) r);
            } else if (r instanceof RobotIndustrial) {
                industriales.add((RobotIndustrial) r);
            }
        }

        gestorDomesticos.guardarLista(domesticos, ARCHIVO_DOMESTICOS);
        gestorIndustriales.guardarLista(industriales, ARCHIVO_INDUSTRIALES);
    }

    @Override
    public List<Robot> cargarRobots() throws LecturaArchivoException {
        List<Robot> listaTotal = new ArrayList<>();

        Type typeDomesticos = new TypeToken<List<RobotDomestico>>() {
        }.getType();
        Type typeIndustriales = new TypeToken<List<RobotIndustrial>>() {
        }.getType();

        List<RobotDomestico> domesticos = gestorDomesticos.cargarLista(ARCHIVO_DOMESTICOS, typeDomesticos);
        List<RobotIndustrial> industriales = gestorIndustriales.cargarLista(ARCHIVO_INDUSTRIALES, typeIndustriales);

        listaTotal.addAll(domesticos);
        listaTotal.addAll(industriales);

        return listaTotal;
    }
}
