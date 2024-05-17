package com.budgetcoinz.batm.server.extensions.shared;

import com.generalbytes.batm.server.extensions.IOpeningHours;

import java.util.Date;

public class OpeningHoursBc implements IOpeningHours {

    private OpeningDay day;
    private Date from;
    private Date to;
    private boolean cashCollectionDay;

    public OpeningHoursBc() {
    }

    @Override
    public OpeningDay getDay() {
        return day;
    }

    @Override
    public Date getFrom() {
        return from;
    }

    @Override
    public Date getTo() {
        return to;
    }

    @Override
    public boolean isCashCollectionDay() {
        return cashCollectionDay;
    }

}
