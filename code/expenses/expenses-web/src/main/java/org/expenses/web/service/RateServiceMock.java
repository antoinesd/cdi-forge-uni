package org.expenses.web.service;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;

@Alternative
@Priority(1)
public class RateServiceMock implements Rateable {

    public double rate() {
        return 1.23;
    }
}
