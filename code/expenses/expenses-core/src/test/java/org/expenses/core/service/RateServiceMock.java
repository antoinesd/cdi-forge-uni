package org.expenses.core.service;

import javax.enterprise.inject.Alternative;

@Alternative
public class RateServiceMock implements Rateable {

    public double rate() {
        return 1.23;
    }
}
