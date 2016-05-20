package com.tanzakesho.azam;

/**
 * Created by othmankulindwa on 4/11/16.
 */
public class itemBScreenOne {

    public boolean returnTrip;
    public String currency;
    public String outboundFromTo;
    public String outDate;
    public String outTime;
    public static int outPassengers = 0;

    public String inboundFromTo;
    public String inDate;
    public String inTime;
    public static int inPassengers = 0;

    public itemBScreenOne(boolean returnTrip, String currency, String outboundFromTo, String outDate, String outTime, int outPassengers) {
        this.returnTrip = returnTrip;
        this.currency = currency;
        this.outboundFromTo = outboundFromTo;
        this.outDate = outDate;
        this.outTime = outTime;
        this.outPassengers = outPassengers;
    }

    public itemBScreenOne(boolean returnTrip, String currency, String outboundFromTo, String outDate, String outTime, int outPassengers,
                          String inboundFromTo, String inDate, String inTime, int inPassengers) {
        this.returnTrip = returnTrip;
        this.currency = currency;
        this.outboundFromTo = outboundFromTo;
        this.outDate = outDate;
        this.outTime = outTime;
        this.outPassengers = outPassengers;
        this.inboundFromTo = inboundFromTo;
        this.inDate = inDate;
        this.inTime = inTime;
        this.inPassengers = inPassengers;

    }


}
