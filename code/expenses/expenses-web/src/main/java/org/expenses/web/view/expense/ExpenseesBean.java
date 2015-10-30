package org.expenses.web.view.expense;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.expenses.core.model.Conference;
import org.expenses.core.model.Expense;
import org.expenses.core.model.Reimbursement;
import org.expenses.core.service.ReimbursementService;

/**
 * Main Backing bean for creating expensee.
 * <p/>
 * This class provides CRUD functionality for all Expense entities. It focuses purely on Java EE 6 standards (e.g.
 * <tt>&#64;ConversationScoped</tt> for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or custom base class.
 */

@Named
@ViewScoped
public class ExpenseesBean implements Serializable
{

   @Inject
   private ReimbursementService service;

   @Inject
   private FacesContext facesContext;

   private Conference conference;

   private Expense expense = new Expense();

   private Reimbursement reimbursement = new Reimbursement();

   public String addExpense()
   {
      reimbursement.add(expense);
      facesContext.addMessage(null,
               new FacesMessage(FacesMessage.SEVERITY_INFO, "Expense added " + expense.getDescription(), ""));
      expense = new Expense();
      return null;
   }

   public String confirm()
   {
      service.persist(reimbursement);
      return "/index";
   }

   public Conference getConference()
   {
      return conference;
   }

   public void setConference(Conference conference)
   {
      this.conference = conference;
   }

   public Expense getExpense()
   {
      return expense;
   }

   public void setExpense(Expense expense)
   {
      this.expense = expense;
   }

   public Reimbursement getReimbursement()
   {
      return reimbursement;
   }

   public void setReimbursement(Reimbursement reimbursement)
   {
      this.reimbursement = reimbursement;
   }
}