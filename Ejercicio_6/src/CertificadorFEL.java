/** Integración de ejemplo para un certificador de Factura Electrónica en Línea. */
public class CertificadorFEL extends IntegracionApi
        implements Documentable, Auditable {

    public CertificadorFEL(String identificador, String proveedor,
                           String parametrosConfiguracion, boolean conectada,
                           double latenciaPromedio) {
        super(identificador, proveedor, parametrosConfiguracion,
                conectada, latenciaPromedio);
    }

    @Override
    public String getTipo() {
        return "Certificador FEL";
    }

    @Override
    public ResultadoOperacion generarDocumento(String datos) {
        if (!isConectada()) {
            registrarAuditoria("Factura rechazada: integración desconectada");
            return new ResultadoOperacion(false,
                    "El certificador " + getProveedor() + " está desconectado.");
        }
        registrarAuditoria("Factura FEL simulada generada");
        return new ResultadoOperacion(true,
                "Factura FEL/XML simulada generada para: " + datos);
    }

    @Override
    public void registrarAuditoria(String detalle) {
        guardarAuditoria(detalle);
    }
}
