package org.expenses.banking.service;

import java.util.logging.Logger;

import javax.enterprise.event.Observes;

import org.expenses.core.model.Reimbursement;

public class BankingService
{

   private Logger logger = Logger.getLogger(BankingService.class.getName());

   public void reimbursementToBePaid(@Observes Reimbursement event)
   {
      logger.info("&&&&&&&&&&&&&&&&&&& " + event);
   }
}