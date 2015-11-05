package org.expenses.billing.service;

import java.util.logging.Logger;

import javax.enterprise.event.Observes;

import org.expenses.core.model.Reimbursement;

public class BillingService
{

   private Logger logger = Logger.getLogger(BillingService.class.getName());

   public void reimbursementToBill(@Observes Reimbursement event)
   {
      logger.info("&&&&&&&&&&&&&&&&&&& " + event);
   }
}