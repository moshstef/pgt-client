package pgtest.audit.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pgtest.audit.dao.AuditDao;
import pgtest.audit.domain.AuditTrace;

/**
 *
 */
public class AuditService {
    private AuditDao auditDao;

    public void setAuditDao(AuditDao auditDao) {
        this.auditDao = auditDao;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertAuditTrace(AuditTrace auditTrace) {
        auditDao.insertAuditTrace(auditTrace);
    }


}