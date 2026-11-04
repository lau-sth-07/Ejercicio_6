/**
 * Clase base para las integraciones del catálogo.
 * Las operaciones de red se simulan en este prototipo.
 */
public abstract class IntegracionApi implements Comparable<IntegracionApi> {
    private final String identificador;
    private final String proveedor;
    private final String parametrosConfiguracion;
    private boolean conectada;
    private double latenciaPromedio;
    private String ultimaAuditoria;

    protected IntegracionApi(String identificador, String proveedor,
                             String parametrosConfiguracion, boolean conectada,
                             double latenciaPromedio) {
        this.identificador = identificador == null ? "" : identificador.trim();
        this.proveedor = proveedor == null ? "" : proveedor.trim();
        this.parametrosConfiguracion = parametrosConfiguracion == null
                ? "" : parametrosConfiguracion.trim();
        this.conectada = conectada;
        this.latenciaPromedio = Math.max(0.0, latenciaPromedio);
        this.ultimaAuditoria = "Sin actividad registrada";
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getParametrosConfiguracion() {
        return parametrosConfiguracion;
    }

    public boolean isConectada() {
        return conectada;
    }

    public void setConectada(boolean conectada) {
        this.conectada = conectada;
    }

    public double getLatenciaPromedio() {
        return latenciaPromedio;
    }

    public void setLatenciaPromedio(double latenciaPromedio) {
        this.latenciaPromedio = Math.max(0.0, latenciaPromedio);
    }

    public String getUltimaAuditoria() {
        return ultimaAuditoria;
    }

    protected void guardarAuditoria(String detalle) {
        ultimaAuditoria = detalle == null ? "" : detalle;
    }

    public abstract String getTipo();

    @Override
    public int compareTo(IntegracionApi otra) {
        int comparacionLatencia = Double.compare(
                this.latenciaPromedio, otra.latenciaPromedio);
        if (comparacionLatencia != 0) {
            return comparacionLatencia;
        }
        return this.identificador.compareToIgnoreCase(otra.identificador);
    }

    @Override
    public String toString() {
        String estado = conectada ? "Conectada" : "Desconectada";
        return identificador + " | " + proveedor + " | " + getTipo()
                + " | " + estado + " | " + latenciaPromedio + " ms";
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof IntegracionApi)) {
            return false;
        }
        IntegracionApi integracion = (IntegracionApi) otro;
        return identificador.equalsIgnoreCase(integracion.identificador);
    }

    @Override
    public int hashCode() {
        return identificador.toLowerCase().hashCode();
    }
}
