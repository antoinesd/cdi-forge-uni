package org.expenses.web.view;

import java.io.Serializable;
import java.util.UUID;

import javax.annotation.PostConstruct;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

   @Inject
   private HttpServletResponse response;

   @Inject
   private HttpServletRequest request;

   // Logged user
   private User user = new User();

   // Checks if the user is logged in and if he/she is an administrator (UserRole.Admin)
   private boolean loggedIn;
   private boolean admin;

   private String password1;
   private String password2;
   // Remember me and cookie
   private static final String COOKIE_NAME = "JSFSampleCookie";
   private static final int COOKIE_AGE = 60; // Expires after 60 seconds or even 2_592_000 for one month

   private boolean rememberMe;

   @Inject
   private Conversation conversation;

   @Inject
   private UserService service;

   @Inject
   Instance<AccountBean> me;

   // ======================================
   // = Lifecycle methods =
   // ======================================

   @PostConstruct
   private void checkIfUserHasRememberMeCookie()
   {
      String coockieValue = getCookieValue();
      if (coockieValue == null)
         return;

      user = service.findByUUID(coockieValue);

      if (user != null)
      {
         // If the user is an administrator
         if (user.getRole().equals(UserRole.ADMIN))
            admin = true;
         // The user is now logged in
         loggedIn = true;
      }
      else
      {
         // The user maybe has an old coockie, let's get rid of it
         removeCookie();
      }
   }

   // ======================================
   // = Business methods =
   // ======================================

   public String doSignup()
   {
      // Does the login already exists ?
      if (service.findByLogin(user.getLogin()).size() > 0)
      {
         FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_WARN, "Login already exists " + user.getLogin(),
                           "You must choose a different login"));
         return null;
      }

      // Everything is ok, we can create the user
      user.setPassword(password1);
      user = service.persist(user);
      resetPasswords();
      FacesContext.getCurrentInstance().addMessage(null,
               new FacesMessage(FacesMessage.SEVERITY_INFO, "Hi " + user.getName(), "Welcome to this website"));
      // facesContext.addMessage(null,
      // new FacesMessage(FacesMessage.SEVERITY_INFO, "Hi " + user.getFirstName(), "Welcome to this website"));
      loggedIn = true;
      if (user.getRole().equals(UserRole.ADMIN))
         admin = true;
      return "index";
   }

   public String doSignin()
   {
      user = service.findByLoginPassword(user.getLogin(), user.getPassword());

      if (user != null)
      {
         // If the user is an administrator
         if (user.getRole().equals(UserRole.ADMIN))
         {
            admin = true;
         }
         // If the user has clicked on remember me
         if (rememberMe)
         {
            String uuid = UUID.randomUUID().toString();
            user.setUuid(uuid);
            addCookie(uuid);
         }
         else
         {
            user.setUuid(null);
            removeCookie();
         }
         // The user is now logged in
         loggedIn = true;
         return "index";
      }
      else
      {
         FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_WARN, "Wrong user/password",
                           "Check your inputs or ask for a new password"));
         return null;
      }

   }

   public String doLogout()
   {
      AlterableContext ctx = (AlterableContext) beanManager.getContext(SessionScoped.class);
      Bean<?> myBean = beanManager.getBeans(AccountBean.class).iterator().next();
      ctx.destroy(myBean);

      return "index";
   }

   public String doLogoutAndRemoveCookie()
   {
      removeCookie();
      user.setUuid(null);
      user = service.merge(user);
      AlterableContext ctx = (AlterableContext) beanManager.getContext(SessionScoped.class);
      Bean<?> myBean = beanManager.getBeans(AccountBean.class).iterator().next();
      ctx.destroy(myBean);
      return "index";
   }

   public String doForgotPassword()
   {
      user = service.findByEmail(user.getEmail());
      if (user != null)
      {
         String temporaryPassword = Lorem.getWords(1);
         user.setPassword(DigestPassword.digest(temporaryPassword));
         user = service.merge(user);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email sent",
                  "An email has been sent to " + user.getEmail() + " with temporary password :" + temporaryPassword));
         // send an email with the password "dummyPassword"
         return doLogout();
      }
      else
      {
         FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_WARN, "Unknown email",
                           "This email address is unknonw in our system"));
         return null;
      }
   }

   public String doUpdateProfile()
   {
      if (password1 != null && !password1.isEmpty())
         user.setPassword(DigestPassword.digest(password1));
      user = service.merge(user);
      resetPasswords();
      FacesContext.getCurrentInstance().addMessage(null,
               new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile has been updated for " + user.getName(),
                        null));
      return null;
   }

   public String doSearch()
   {
      return "search";
   }

   // Cookie
   private String getCookieValue()
   {
      Cookie[] cookies = request.getCookies();
      if (cookies != null)
      {
         for (Cookie cookie : cookies)
         {
            if (COOKIE_NAME.equals(cookie.getName()))
            {
               return cookie.getValue();
            }
         }
      }
      return null;
   }

   private void addCookie(String value)
   {
      Cookie cookie = new Cookie(COOKIE_NAME, value);
      cookie.setPath("/sampleJSFLogin");
      cookie.setMaxAge(COOKIE_AGE);
      response.addCookie(cookie);
   }

   private void removeCookie()
   {
      Cookie cookie = new Cookie(COOKIE_NAME, null);
      cookie.setMaxAge(0);
      response.addCookie(cookie);
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

   public boolean isRememberMe()
   {
      return rememberMe;
   }

   public void setRememberMe(boolean rememberMe)
   {
      this.rememberMe = rememberMe;
   }
}
