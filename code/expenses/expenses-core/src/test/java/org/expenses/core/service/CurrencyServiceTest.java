package org.expenses.core.service;

import org.expenses.core.model.Currency;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by antoine on 06/11/2015.
 */
@RunWith(Arquillian.class)
public class CurrencyServiceTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true,"org.expenses.core")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    CurrencyService currencyService;


    @Test
    public void testChangeRate() throws Exception {
        Assert.assertEquals(1.23,currencyService.changeRate(Currency.EURO),0);

    }
}
