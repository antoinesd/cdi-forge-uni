package org.expenses.web.utils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * @author Antonio Goncalves - http://www.antoniogoncalves.org --
 *         <p/>
 *         This class produces a few resources so they can be injected in CDI
 */
public class ResourceProducer {

    // ======================================
    // = Business methods =
    // ======================================

    @Produces
    @RequestScoped
    private FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}