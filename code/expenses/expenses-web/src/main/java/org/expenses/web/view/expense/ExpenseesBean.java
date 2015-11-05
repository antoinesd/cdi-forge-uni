package org.expenses.web.view.expense;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.expenses.core.model.Conference;
import org.expenses.core.model.Currency;
import org.expenses.core.model.Expense;
import org.expenses.core.model.Reimbursement;
import org.expenses.core.service.CurrencyService;
import org.expenses.core.service.ReimbursementService;
import org.expenses.core.service.UserService;

/**
 * Main Backing bean for creating expensee.
 * <p/>
 * This class provides CRUD functionality for all Expense entities. It focuses purely on Java EE 6 standards (e.g.
 * <tt>&#64;ConversationScoped</tt> for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or custom base class.
 */

@Named
@ConversationScoped
public class ExpenseesBean implements Serializable
{

   @Inject
   private ReimbursementService service;

   @Inject
   private UserService userService;

   @Inject
   private FacesContext facesContext;

   @Inject
   private Conversation conversation;

   @Inject
   private CurrencyService currencyService;

   private Currency currency;

   private Conference conference;

   private Expense expense = new Expense();

   private Reimbursement reimbursement;



   public String addExpense()
   {
      if (this.conversation.isTransient())
      {
         this.conversation.begin();
         reimbursement = new Reimbursement();
      }

      expense.setCurrency(currency);
      reimbursement.add(expense);
      facesContext.addMessage(null,
               new FacesMessage(FacesMessage.SEVERITY_INFO, "Expense added " + expense.getDescription(), ""));
      expense = new Expense();
      return null;
   }

   public String recap()
   {
      reimbursement.setUser(userService.findById(1000L));
      reimbursement.setDate(new Date());
      reimbursement.setConference(conference);
      currency=reimbursement.getCurrency();
      return "/expense/recap";
   }

   public String back()
   {
      return "/expense/create";
   }

   public String confirm()
   {
      reimbursement.setCurrency(currency);
      service.persist(reimbursement);
      this.conversation.end();
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

   public Reimbursement getReimbursement()
   {
      return reimbursement;
   }

   public void setReimbursement(Reimbursement reimbursement)
   {
      this.reimbursement = reimbursement;
   }


   public float getTotalAmount() {
      if(currency.equals(reimbursement.getCurrency()))
         return reimbursement.getTotalAmount();
      else
         return  currencyService.change(reimbursement.getTotalAmount(),currency);
   }

   public Currency getCurrency() {
      return currency;
   }

   public void setCurrency(Currency currency) {
      this.currency = currency;
   }
}
