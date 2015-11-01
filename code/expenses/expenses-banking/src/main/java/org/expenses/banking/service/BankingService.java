package org.expenses.banking.service;

import javax.enterprise.event.Observes;

import org.expenses.core.model.Reimbursement;

public class BankingService
{

   public void reimbursementToBePaid(@Observes Reimbursement event)
   {
	   System.out.println("&&&&&&&&&&&&&&&&&&& " + event);
   }
}