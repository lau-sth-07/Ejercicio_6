import java.util.ArrayList;

/** Coordina las solicitudes de la vista con el catálogo y las integraciones. */
public class ControladorSistema {
    private final CatalogoIntegraciones catalogo;

    public ControladorSistema(CatalogoIntegraciones catalogo) {
        this.catalogo = catalogo;
    }

    public ArrayList<IntegracionApi> listarIntegraciones() {
        return catalogo.obtenerTodas();
    }

    public IntegracionApi buscarIntegracion(String identificador) {
        return catalogo.buscarPorIdentificador(identificador);
    }

    public ArrayList<IntegracionApi> ordenarPorLatencia() {
        return catalogo.ordenarPorLatencia();
    }

    public ResultadoOperacion procesarCobro(IntegracionApi destino, double monto) {
        if (destino instanceof Transaccional) {
            return procesarCobro((Transaccional) destino, monto);
        }
        return operacionNoAdmitida(destino, "cobros");
    }

    public ResultadoOperacion procesarCobro(Transaccional destino, double monto) {
        if (destino == null) {
            return new ResultadoOperacion(false, "No se encontró la integración.");
        }
        return destino.cobrar(monto);
    }

    public ResultadoOperacion procesarReintegro(IntegracionApi destino, double monto) {
        if (destino instanceof Transaccional) {
            return procesarReintegro((Transaccional) destino, monto);
        }
        return operacionNoAdmitida(destino, "reintegros");
    }

    public ResultadoOperacion procesarReintegro(Transaccional destino, double monto) {
        if (destino == null) {
            return new ResultadoOperacion(false, "No se encontró la integración.");
        }
        return destino.reintegrar(monto);
    }

    public ResultadoOperacion consultarEstado(IntegracionApi destino, String referencia) {
        if (destino instanceof Consultable) {
            return consultarEstado((Consultable) destino, referencia);
        }
        return operacionNoAdmitida(destino, "consultas de estado");
    }

    public ResultadoOperacion consultarEstado(Consultable destino, String referencia) {
        if (destino == null) {
            return new ResultadoOperacion(false, "No se encontró la integración.");
        }
        return destino.consultarEstado(referencia);
    }

    public ResultadoOperacion cotizar(IntegracionApi destino, double peso) {
        if (destino instanceof Cotizable) {
            return cotizar((Cotizable) destino, peso);
        }
        return operacionNoAdmitida(destino, "cotizaciones");
    }

    public ResultadoOperacion cotizar(Cotizable destino, double peso) {
        if (destino == null) {
            return new ResultadoOperacion(false, "No se encontró la integración.");
        }
        return destino.cotizar(peso);
    }

    public ResultadoOperacion generarDocumento(IntegracionApi destino, String datos) {
        if (destino instanceof Documentable) {
            return generarDocumento((Documentable) destino, datos);
        }
        return operacionNoAdmitida(destino, "generación de documentos");
    }

    public ResultadoOperacion generarDocumento(Documentable destino, String datos) {
        if (destino == null) {
            return new ResultadoOperacion(false, "No se encontró la integración.");
        }
        return destino.generarDocumento(datos);
    }

    private ResultadoOperacion operacionNoAdmitida(IntegracionApi destino,
                                                    String operacion) {
        if (destino == null) {
            return new ResultadoOperacion(false, "No se encontró la integración.");
        }
        return new ResultadoOperacion(false, destino.getProveedor()
                + " no admite " + operacion + ". La solicitud fue ignorada.");
    }
}
