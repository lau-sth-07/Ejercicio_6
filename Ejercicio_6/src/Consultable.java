/** Contrato para integraciones que consultan estados o información. */
public interface Consultable {
    ResultadoOperacion consultarEstado(String referencia);
}
