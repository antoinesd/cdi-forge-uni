package org.expenses.core.extension;


import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class BillingServiceExtension implements Extension {

    public void listen(@Observes AfterBeanDiscovery afterBeanDiscovery) {
        afterBeanDiscovery.addObserverMethod(new BillingServiceObserver());
    }
}
