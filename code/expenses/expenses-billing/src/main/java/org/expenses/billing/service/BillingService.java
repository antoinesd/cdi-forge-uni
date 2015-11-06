package org.expenses.billing.service;

import org.expenses.core.model.Reimbursement;

import java.util.logging.Logger;

public class BillingService {

    private Logger logger = Logger.getLogger(BillingService.class.getName());

    public void reimbursementToBill(Reimbursement event) {
        logger.info("-- BillingService -- ");
        logger.info("-- " + event);
        logger.info("-------------------- ");
    }
}