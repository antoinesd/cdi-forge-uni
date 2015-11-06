package org.expenses.banking.service;

import org.expenses.core.model.Reimbursement;

import javax.enterprise.event.Observes;
import java.util.logging.Logger;

public class BankingService {

    private Logger logger = Logger.getLogger(BankingService.class.getName());

    public void reimbursementToBePaid(@Observes Reimbursement event) {
        logger.info("-- BankingService -- ");
        logger.info("-- " + event);
        logger.info("-------------------- ");
    }
}