package org.expenses.billing.service;

import org.expenses.core.model.Reimbursement;

import javax.enterprise.event.Observes;
import java.util.logging.Logger;

public class BillingService {

    private Logger logger = Logger.getLogger(BillingService.class.getName());

    public void reimbursementToBill(@Observes Reimbursement event) {
        logger.info("&&&&&&&&&&&&&&&&&&& " + event);
    }
}