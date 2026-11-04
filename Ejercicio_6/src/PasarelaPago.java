/** Integración de ejemplo para una pasarela de pago. */
public class PasarelaPago extends IntegracionApi
        implements Transaccional, Auditable {
    private final String tipoPago;

    public PasarelaPago(String identificador, String proveedor,
                        String parametrosConfiguracion, boolean conectada,
                        double latenciaPromedio, String tipoPago) {
        super(identificador, proveedor, parametrosConfiguracion,
                conectada, latenciaPromedio);
        this.tipoPago = tipoPago;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    @Override
    public String getTipo() {
        return "Pago (" + tipoPago + ")";
    }

    @Override
    public ResultadoOperacion cobrar(double monto) {
        if (!isConectada()) {
            registrarAuditoria("Cobro rechazado: integración desconectada");
            return new ResultadoOperacion(false,
                    "La pasarela " + getProveedor() + " está desconectada.");
        }
        if (monto <= 0) {
            registrarAuditoria("Cobro rechazado: monto inválido");
            return new ResultadoOperacion(false, "El monto debe ser mayor que cero.");
        }
        registrarAuditoria("Cobro simulado por " + monto);
        return new ResultadoOperacion(true,
                "Cobro simulado por " + monto + " usando " + getProveedor() + ".");
    }

    @Override
    public ResultadoOperacion reintegrar(double monto) {
        if (!isConectada()) {
            registrarAuditoria("Reintegro rechazado: integración desconectada");
            return new ResultadoOperacion(false,
                    "La pasarela " + getProveedor() + " está desconectada.");
        }
        if (monto <= 0) {
            registrarAuditoria("Reintegro rechazado: monto inválido");
            return new ResultadoOperacion(false, "El monto debe ser mayor que cero.");
        }
        registrarAuditoria("Reintegro simulado por " + monto);
        return new ResultadoOperacion(true,
                "Reintegro simulado por " + monto + " usando " + getProveedor() + ".");
    }

    @Override
    public void registrarAuditoria(String detalle) {
        guardarAuditoria(detalle);
    }
}
