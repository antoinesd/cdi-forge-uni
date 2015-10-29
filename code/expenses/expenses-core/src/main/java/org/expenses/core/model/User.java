package org.expenses.core.model;

import org.expenses.core.utils.DigestPassword;
import sun.misc.BASE64Encoder;

import java.io.Serializable;
import java.security.MessageDigest;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = User.FIND_BY_UUID, query = "SELECT u FROM User u WHERE u.uuid = :uuid"),
        @NamedQuery(name = User.FIND_BY_LOGIN, query = "SELECT u FROM User u WHERE u.login = :login"),
        @NamedQuery(name = User.FIND_BY_LOGIN_PASSWORD, query = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password"),
        @NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u")
})
public class User implements Serializable
{

   public static final String FIND_BY_EMAIL = "User.findByEmail";
   public static final String FIND_BY_LOGIN = "User.findByLogin";
   public static final String FIND_BY_UUID = "User.findByUUID";
   public static final String FIND_BY_LOGIN_PASSWORD = "User.findByLoginAndPassword";
   public static final String FIND_ALL = "User.findAll";

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column
   private String login;

   @Column
   private String password;
   private String uuid;

   @Column
   private String name;

   private String email;

   private UserRole role;

   @PrePersist
   private void digestPassword()
   {
      password = DigestPassword.digest(password);
   }

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   public String getLogin()
   {
      return login;
   }

   public void setLogin(String login)
   {
      this.login = login;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getUuid() {
      return uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public UserRole getRole() {
      return role;
   }

   public void setRole(UserRole role) {
      this.role = role;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof User))
      {
         return false;
      }
      User other = (User) obj;
      if (id != null)
      {
         if (!id.equals(other.id))
         {
            return false;
         }
      }
      return true;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      result += ", version: " + version;
      if (login != null && !login.trim().isEmpty())
         result += ", login: " + login;
      if (password != null && !password.trim().isEmpty())
         result += ", password: " + password;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      return result;
   }
}