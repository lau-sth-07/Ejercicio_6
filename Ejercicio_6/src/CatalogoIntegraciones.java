import java.util.ArrayList;
import java.util.Collections;

/** Mantiene la única lista polimórfica de integraciones del programa. */
public class CatalogoIntegraciones {
    private final ArrayList<IntegracionApi> integraciones;

    public CatalogoIntegraciones() {
        integraciones = new ArrayList<IntegracionApi>();
        cargarInicial();
    }

    public void cargarInicial() {
        agregar(new PasarelaPago("PAG-001", "Pago Maya",
                "Ambiente=Pruebas; Moneda=GTQ", true, 82.5, "Tarjeta"));
        agregar(new PasarelaPago("PAG-002", "Cobro Express",
                "Ambiente=Pruebas; Moneda=GTQ", true, 96.0, "Transferencia"));
        agregar(new PasarelaPago("PAG-003", "Billetera Chapina",
                "Ambiente=Pruebas; Moneda=GTQ", false, 140.2, "Billetera digital"));
        agregar(new PasarelaPago("PAG-004", "Banco Centro",
                "Ambiente=Pruebas; Moneda=GTQ", true, 110.8, "Transferencia"));

        agregar(new ProveedorLogistica("LOG-001", "Ruta Quetzal",
                "Ambiente=Pruebas; Cobertura=Nacional", true, 125.3));
        agregar(new ProveedorLogistica("LOG-002", "Envíos del Norte",
                "Ambiente=Pruebas; Cobertura=Norte", true, 178.4));
        agregar(new ProveedorLogistica("LOG-003", "Carga Segura",
                "Ambiente=Pruebas; Cobertura=Nacional", false, 210.0));
        agregar(new ProveedorLogistica("LOG-004", "Última Milla GT",
                "Ambiente=Pruebas; Cobertura=Urbana", true, 105.7));

        agregar(new CertificadorFEL("FEL-001", "Factura Clara",
                "Ambiente=Pruebas; Formato=XML", true, 155.9));
        agregar(new CertificadorFEL("FEL-002", "CertiFEL Centro",
                "Ambiente=Pruebas; Formato=XML", true, 132.6));
    }

    public boolean agregar(IntegracionApi integracion) {
        if (integracion == null || buscarPorIdentificador(
                integracion.getIdentificador()) != null) {
            return false;
        }
        integraciones.add(integracion);
        return true;
    }

    public IntegracionApi buscarPorIdentificador(String identificador) {
        if (identificador == null) {
            return null;
        }
        for (IntegracionApi integracion : integraciones) {
            if (integracion.getIdentificador().equalsIgnoreCase(identificador.trim())) {
                return integracion;
            }
        }
        return null;
    }

    /** Devuelve la lista del catálogo para consulta; no crea otra lista. */
    public ArrayList<IntegracionApi> obtenerTodas() {
        return integraciones;
    }

    public ArrayList<IntegracionApi> ordenarPorLatencia() {
        Collections.sort(integraciones);
        return integraciones;
    }
}
