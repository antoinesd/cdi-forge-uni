package org.expenses.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.expenses.core.model.Conference;
import org.expenses.core.utils.Loggable;

/**
 * Transactional service for Conference entities.
 * <p/>
 * This class provides CRUD functionality for all Conference entities.
 */

@Transactional
@Loggable
public class ConferenceService extends AbstractService<Conference>
{
   public ConferenceService()
   {
      super(Conference.class);
   }

   @Override
   protected Predicate[] getSearchPredicates(Root<Conference> root, Conference example)
   {

      CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<>();

      String name = example.getName();
      if (name != null && !"".equals(name))
      {
         predicatesList.add(builder.like(
                  builder.lower(root.<String> get("name")),
                  '%' + name.toLowerCase() + '%'));
      }
      String country = example.getCountry();
      if (country != null && !"".equals(country))
      {
         predicatesList.add(builder.like(
                  builder.lower(root.<String> get("country")),
                  '%' + country.toLowerCase() + '%'));
      }
      String city = example.getCity();
      if (city != null && !"".equals(city))
      {
         predicatesList.add(builder.like(
                  builder.lower(root.<String> get("city")),
                  '%' + city.toLowerCase() + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }
}
