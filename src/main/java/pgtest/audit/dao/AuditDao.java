package pgtest.audit.dao;


import pgtest.audit.domain.AuditTrace;

/**
 *
 */
public interface AuditDao {
    void insertAuditTrace(AuditTrace auditTrace);
}
