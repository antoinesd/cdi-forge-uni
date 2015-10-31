package org.expenses.web.view.account;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.expenses.core.model.User;
import org.expenses.core.model.UserRole;
import org.expenses.core.service.UserService;
import org.expenses.core.utils.DigestPassword;

import com.thedeanda.lorem.Lorem;

@Named
@SessionScoped
public class AccountBean implements Serializable
{

   // ======================================
   // = Attributes =
   // ======================================

   @Inject
   private BeanManager beanManager;

   @Inject
   private FacesContext facesContext;

   // Logged user
   private User user = new User();

   // Checks if the user is logged in and if he/she is an administrator (UserRole.Admin)
   private boolean loggedIn;
   private boolean admin;

   private String password1;
   private String password2;

   @Inject
   private Conversation conversation;

   @Inject
   private UserService service;

   // ======================================
   // = Business methods =
   // ======================================

   public String doSignup()
   {
      // Does the login already exists ?
      if (service.findByLogin(user.getLogin()).size() > 0)
      {
         facesContext.addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_WARN, "Login already exists " + user.getLogin(),
                           "You must choose a different login"));
         return null;
      }

      // Everything is ok, we can create the user
      user.setPassword(password1);
      user = service.persist(user);
      resetPasswords();
      facesContext.addMessage(null,
               new FacesMessage(FacesMessage.SEVERITY_INFO, "Hi " + user.getName(), "Welcome to this website"));
      loggedIn = true;
      if (user.getRole().equals(UserRole.ADMIN))
         admin = true;

      return "/index";
   }

   public String doSignin()
   {
      try
      {
         user = service.findByLoginPassword(user.getLogin(), user.getPassword());

         // If the user is an administrator
         if (user.getRole().equals(UserRole.ADMIN))
         {
            admin = true;
         }
         // The user is now logged in
         loggedIn = true;
         return "/index";
      }
      catch (NoResultException e)
      {
         facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Wrong user/password",
                  "Check your inputs or ask for a new password"));
         return null;
      }

   }

   public String doLogout()
   {
      AlterableContext ctx = (AlterableContext) beanManager.getContext(SessionScoped.class);
      Bean<?> myBean = beanManager.getBeans(AccountBean.class).iterator().next();
      ctx.destroy(myBean);

      return "/index";
   }

   public String doUpdateProfile()
   {
      if (password1 != null && !password1.isEmpty())
         user.setPassword(DigestPassword.digest(password1));
      user = service.merge(user);
      resetPasswords();
      admin = user.getRole().equals(UserRole.ADMIN);

      facesContext.addMessage(null,
               new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile has been updated for " + user.getName(),
                        null));

      return "/index";
   }

   private void resetPasswords()
   {
      password1 = null;
      password2 = null;
   }

   // ======================================
   // = Getters & setters =
   // ======================================

   public boolean isLoggedIn()
   {
      return loggedIn;
   }

   public void setLoggedIn(boolean loggedIn)
   {
      this.loggedIn = loggedIn;
   }

   public boolean isAdmin()
   {
      return admin;
   }

   public void setAdmin(boolean admin)
   {
      this.admin = admin;
   }

   public User getUser()
   {
      return user;
   }

   public void setUser(User user)
   {
      this.user = user;
   }

   public String getPassword1()
   {
      return password1;
   }

   public void setPassword1(String password1)
   {
      this.password1 = password1;
   }

   public String getPassword2()
   {
      return password2;
   }

   public void setPassword2(String password2)
   {
      this.password2 = password2;
   }

   public UserRole[] getRoles()
   {
      return UserRole.values();
   }
}
