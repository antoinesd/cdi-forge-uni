package org.expenses.web.utils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import com.thedeanda.lorem.Lorem;

/**
 * @author Antonio Goncalves - http://www.antoniogoncalves.org --
 *
 *         This class produces a few resources so they can be injected in CDI
 */
public class ResourceProducer
{

   // ======================================
   // = Business methods =
   // ======================================

   @Produces
   @RequestScoped
   private FacesContext produceFacesContext()
   {
      return FacesContext.getCurrentInstance();
   }

   // @Produces
   // @RequestScoped
   // private HttpServletRequest produceRequest()
   // {
   // return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
   // }

   @Produces
   @RequestScoped
   private HttpServletResponse produceResponse()
   {
      return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
   }

   @Produces
   @Named("lorem")
   private String produceLorem()
   {
      return Lorem.getParagraphs(1, 3);
   }
}