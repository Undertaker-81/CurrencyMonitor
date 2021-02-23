package com.example.currencymonitor.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Map;

/**
 * @author Dmitriy Panfilov
 * 23.02.2021
 */
public class ExchangerUtil {

    public static String getYesterday(){
        return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toLocalDate().minus(Period.ofDays(1)).toString();
    }

    public static int comparator(Map<String, BigDecimal> mapLast, Map<String, BigDecimal> mapYesterday , String currencyBase, String currencyHit){
        BigDecimal currBaseLast = mapLast.get(currencyBase);
        BigDecimal currHitLast = mapLast.get(currencyHit);
        BigDecimal currBaseYesterday = mapYesterday.get(currencyBase);
        BigDecimal currHitYesterday = mapYesterday.get(currencyHit);
        BigDecimal differentLast = currBaseLast.divide(currHitLast, new MathContext(10));
        BigDecimal differentYesterday = currBaseYesterday.divide(currHitYesterday, new MathContext(10));
        return differentLast.compareTo(differentYesterday);
    }

    public static int comparator2(Map<String, BigDecimal> mapLast, Map<String, BigDecimal> mapYesterday , String currencyBase, String currencyHit){
        BigDecimal currBaseLast = mapLast.get(currencyBase);
        BigDecimal currHitLast = mapLast.get(currencyHit);
        BigDecimal currBaseYesterday = mapYesterday.get(currencyBase);
        BigDecimal currHitYesterday = mapYesterday.get(currencyHit);
        BigDecimal differentLast = currHitLast.divide(currBaseLast, new MathContext(10));
        BigDecimal differentYesterday = currHitYesterday.divide(currBaseYesterday, new MathContext(10));
        return differentYesterday.compareTo(differentLast);
    }
}
