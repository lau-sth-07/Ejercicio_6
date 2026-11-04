/*Contrato para integraciones que guardan una descripción de auditoría. */
public interface Auditable {
    void registrarAuditoria(String detalle);
}
