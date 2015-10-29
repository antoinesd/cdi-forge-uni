package org.expenses.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.expenses.core.model.User;

/**
 * Transactional service for User entities.
 * <p/>
 * This class provides CRUD functionality for all User entities.
 */

@Transactional
public class UserService extends AbstractService<User>
{

   public UserService()
   {
      super(User.class);
   }

   @Override
   protected Predicate[] getSearchPredicates(Root<User> root, User example)
   {
      CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String login = example.getLogin();
      if (login != null && !"".equals(login))
      {
         predicatesList.add(builder.like(
                  builder.lower(root.<String> get("login")),
                  '%' + login.toLowerCase() + '%'));
      }
      String password = example.getPassword();
      if (password != null && !"".equals(password))
      {
         predicatesList.add(builder.like(
                  builder.lower(root.<String> get("password")),
                  '%' + password.toLowerCase() + '%'));
      }
      String name = example.getName();
      if (name != null && !"".equals(name))
      {
         predicatesList.add(builder.like(
                  builder.lower(root.<String> get("name")),
                  '%' + name.toLowerCase() + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }
}
