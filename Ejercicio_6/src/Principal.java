import java.util.ArrayList;
import java.util.Scanner;

/** Vista de consola y punto de entrada del programa. */
public class Principal {
    private final Scanner lector;
    private final ControladorSistema controlador;

    public Principal() {
        lector = new Scanner(System.in);
        CatalogoIntegraciones catalogo = new CatalogoIntegraciones();
        controlador = new ControladorSistema(catalogo);
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.ejecutar();
    }

    public void ejecutar() {
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opción: ");

            if (opcion == 1) {
                mostrarIntegraciones(controlador.listarIntegraciones());
            } else if (opcion == 2) {
                mostrarBusqueda();
            } else if (opcion == 3) {
                mostrarIntegraciones(controlador.ordenarPorLatencia());
            } else if (opcion == 4) {
                procesarOperacionFinanciera(false);
            } else if (opcion == 5) {
                procesarOperacionFinanciera(true);
            } else if (opcion == 6) {
                consultarEstado();
            } else if (opcion == 7) {
                cotizarEnvio();
            } else if (opcion == 8) {
                generarDocumento();
            } else if (opcion == 0) {
                continuar = false;
                System.out.println("Programa finalizado.");
            } else {
                System.out.println("Opción no válida. Intenta de nuevo.");
            }
            System.out.println();
        }
        lector.close();
    }

    private void mostrarMenu() {
        System.out.println("=== Catálogo de integraciones ===");
        System.out.println("1. Listar integraciones y su estado");
        System.out.println("2. Buscar una integración por identificador");
        System.out.println("3. Ordenar integraciones por latencia");
        System.out.println("4. Simular un cobro");
        System.out.println("5. Simular un reintegro");
        System.out.println("6. Consultar estado de envío");
        System.out.println("7. Cotizar envío");
        System.out.println("8. Generar documento");
        System.out.println("0. Salir");
    }

    private void mostrarIntegraciones(ArrayList<IntegracionApi> integraciones) {
        System.out.println("Integraciones:");
        for (IntegracionApi integracion : integraciones) {
            System.out.println(integracion);
        }
    }

    private void mostrarBusqueda() {
        String identificador = leerTexto("Identificador (ejemplo PAG-001): ");
        IntegracionApi integracion = controlador.buscarIntegracion(identificador);
        if (integracion == null) {
            System.out.println("No se encontró una integración con ese identificador.");
        } else {
            System.out.println(integracion);
            System.out.println("Configuración: "
                    + integracion.getParametrosConfiguracion());
            System.out.println("Última auditoría: " + integracion.getUltimaAuditoria());
        }
    }

    private void procesarOperacionFinanciera(boolean esReintegro) {
        String identificador = leerTexto("Identificador de la integración: ");
        IntegracionApi integracion = controlador.buscarIntegracion(identificador);
        double monto = leerDecimal("Monto: ");
        ResultadoOperacion resultado;
        if (esReintegro) {
            resultado = controlador.procesarReintegro(integracion, monto);
        } else {
            resultado = controlador.procesarCobro(integracion, monto);
        }
        mostrarResultado(resultado);
    }

    private void consultarEstado() {
        String identificador = leerTexto("Identificador de la integración: ");
        IntegracionApi integracion = controlador.buscarIntegracion(identificador);
        String referencia = leerTexto("Código de rastreo: ");
        mostrarResultado(controlador.consultarEstado(integracion, referencia));
    }

    private void cotizarEnvio() {
        String identificador = leerTexto("Identificador de la integración: ");
        IntegracionApi integracion = controlador.buscarIntegracion(identificador);
        double peso = leerDecimal("Peso del paquete en kg: ");
        mostrarResultado(controlador.cotizar(integracion, peso));
    }

    private void generarDocumento() {
        String identificador = leerTexto("Identificador de la integración: ");
        IntegracionApi integracion = controlador.buscarIntegracion(identificador);
        String datos = leerTexto("Descripción o datos del documento: ");
        mostrarResultado(controlador.generarDocumento(integracion, datos));
    }

    private String leerTexto(String indicacion) {
        System.out.print(indicacion);
        return lector.nextLine().trim();
    }

    private int leerEntero(String indicacion) {
        String texto = leerTexto(indicacion);
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException error) {
            return -1;
        }
    }

    private double leerDecimal(String indicacion) {
        String texto = leerTexto(indicacion).replace(',', '.');
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException error) {
            return -1.0;
        }
    }

    private void mostrarResultado(ResultadoOperacion resultado) {
        System.out.println(resultado);
    }
}
