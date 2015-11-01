package org.expenses.billing.service;

import javax.enterprise.event.Observes;

import org.expenses.core.model.Reimbursement;

public class BillingService
{

   public void reimbursementToBill(@Observes Reimbursement event)
   {
	   System.out.println("££££££££££££££££ " + event);
   }
}