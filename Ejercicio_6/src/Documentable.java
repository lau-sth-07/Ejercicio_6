/** Contrato para integraciones que generan documentos. */
public interface Documentable {
    ResultadoOperacion generarDocumento(String datos);
}
