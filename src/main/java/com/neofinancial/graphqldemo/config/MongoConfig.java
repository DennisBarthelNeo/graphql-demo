package com.neofinancial.graphqldemo.config;

import java.time.YearMonth;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions.MongoConverterConfigurationAdapter;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

  @Override
  protected String getDatabaseName() {
    return "test";
  }

  @Override
  protected void configureConverters(MongoConverterConfigurationAdapter adapter) {
    adapter.registerConverter(new YearMonthToStringConverter());
    adapter.registerConverter(new StringToYearMonthConverter());
  }

  @WritingConverter
  private static class YearMonthToStringConverter implements Converter<YearMonth, String> {

    @Override
    public String convert(YearMonth source) {
      return source.toString();
    }
  }

  @ReadingConverter
  private static class StringToYearMonthConverter implements Converter<String, YearMonth> {

    @Override
    public YearMonth convert(String source) {
      return YearMonth.parse(source);
    }
  }
}
