package com.example.currencymonitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Dmitriy Panfilov
 * 23.02.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangerData {
   private String disclaimer;
   private String license;
   private long timestamp;
   private String base;
   private Map<String, BigDecimal> rates;

}
