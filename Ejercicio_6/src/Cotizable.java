/** Contrato para integraciones que calculan cotizaciones de envío. */
public interface Cotizable {
    ResultadoOperacion cotizar(double peso);
}
