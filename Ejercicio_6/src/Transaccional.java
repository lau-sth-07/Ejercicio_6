/** Contrato para integraciones que realizan operaciones financieras. */
public interface Transaccional {
    ResultadoOperacion cobrar(double monto);

    ResultadoOperacion reintegrar(double monto);
}
