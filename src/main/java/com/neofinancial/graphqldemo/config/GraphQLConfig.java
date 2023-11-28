package com.neofinancial.graphqldemo.config;

import java.time.YearMonth;

import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring.Builder;

@Configuration
public class GraphQLConfig implements RuntimeWiringConfigurer {

  @Override
  public void configure(Builder builder) {
    builder.scalar(ExtendedScalars.GraphQLBigDecimal);
    builder.scalar(GraphQLScalarType.newScalar()
        .name("YearMonth")
        .description("Custom Scalar for Java's YearMonth object")
        .coercing(new YearMonthScalar())
        .build());
  }

  private static class YearMonthScalar implements Coercing<YearMonth, String> {

    @Override
    public YearMonth parseLiteral(Object input) throws CoercingParseLiteralException {
      if (input instanceof String) {
        return YearMonth.parse((String) input);
      }
      return null;
    }

    @Override
    public YearMonth parseValue(Object input) throws CoercingParseValueException {
      if (input instanceof String) {
        return YearMonth.parse((String) input);
      }
      return null;
    }

    @Override
    public String serialize(Object input) throws CoercingSerializeException {
      if (input instanceof YearMonth) {
        return ((YearMonth) input).toString();
      }

      return null;
    }
  }
}
