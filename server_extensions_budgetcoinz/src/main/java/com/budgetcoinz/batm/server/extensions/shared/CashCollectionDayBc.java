package com.budgetcoinz.batm.server.extensions.shared;

import com.generalbytes.batm.server.extensions.ICashCollectionDay;

public class CashCollectionDayBc implements ICashCollectionDay {

    private Integer dayOfMonth;

    public CashCollectionDayBc() {
    }

    @Override
    public Integer getDayOfMonth() {
        return dayOfMonth;
    }
}
