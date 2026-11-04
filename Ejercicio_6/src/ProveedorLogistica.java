/** Integración de ejemplo para una empresa de logística. */
public class ProveedorLogistica extends IntegracionApi
        implements Consultable, Cotizable, Documentable, Auditable {

    public ProveedorLogistica(String identificador, String proveedor,
                              String parametrosConfiguracion, boolean conectada,
                              double latenciaPromedio) {
        super(identificador, proveedor, parametrosConfiguracion,
                conectada, latenciaPromedio);
    }

    @Override
    public String getTipo() {
        return "Logística";
    }

    @Override
    public ResultadoOperacion consultarEstado(String referencia) {
        if (!isConectada()) {
            registrarAuditoria("Consulta rechazada: integración desconectada");
            return new ResultadoOperacion(false,
                    "El proveedor " + getProveedor() + " está desconectado.");
        }
        registrarAuditoria("Consulta de envío " + referencia);
        return new ResultadoOperacion(true,
                "Estado simulado del envío " + referencia + ": en tránsito.");
    }

    @Override
    public ResultadoOperacion cotizar(double peso) {
        if (!isConectada()) {
            registrarAuditoria("Cotización rechazada: integración desconectada");
            return new ResultadoOperacion(false,
                    "El proveedor " + getProveedor() + " está desconectado.");
        }
        if (peso <= 0) {
            registrarAuditoria("Cotización rechazada: peso inválido");
            return new ResultadoOperacion(false, "El peso debe ser mayor que cero.");
        }
        double costoEstimado = peso * 12.50;
        registrarAuditoria("Cotización simulada para " + peso + " kg");
        return new ResultadoOperacion(true,
                "Cotización simulada: " + costoEstimado + " para " + peso + " kg.");
    }

    @Override
    public ResultadoOperacion generarDocumento(String datos) {
        if (!isConectada()) {
            registrarAuditoria("Documento rechazado: integración desconectada");
            return new ResultadoOperacion(false,
                    "El proveedor " + getProveedor() + " está desconectado.");
        }
        registrarAuditoria("Guía de envío simulada generada");
        return new ResultadoOperacion(true,
                "Guía de envío simulada generada para: " + datos);
    }

    @Override
    public void registrarAuditoria(String detalle) {
        guardarAuditoria(detalle);
    }
}
