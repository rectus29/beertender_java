package com.rectus29.beertender.api.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.GraphQLScalarType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 23/10/2018 09:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class GraphQLBigDecimal extends GraphQLScalarType {

	private static final Logger log = LogManager.getLogger(GraphQLBigDecimal.class);
	private static final String DEFAULT_NAME = "BigDecimal";

	public GraphQLBigDecimal() {
		this(DEFAULT_NAME);
	}

	private GraphQLBigDecimal(final String name) {
		super(name, "BigDecimal type", new Coercing<BigDecimal, String>() {

			@Override
			public String serialize(Object input) {
				if (input instanceof BigDecimal) {
					return ((BigDecimal) input).intValue()+"";
				}else{
					log.error("Unable to parse BigDecimal in GraphQLBigDecimal scalar Type");
					return "nan";
				}
			}

			@Override
			public BigDecimal parseValue(Object input) {
				if(input instanceof String){
					try {
						// Create a DecimalFormat that fits your requirements
						DecimalFormatSymbols symbols = new DecimalFormatSymbols();
						symbols.setGroupingSeparator(',');
						symbols.setDecimalSeparator('.');
						String pattern = "#,##0.0#";
						DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
						decimalFormat.setParseBigDecimal(true);
						// parse the string
						BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse("10,692,467,440,017.120");
						return bigDecimal;
					} catch (ParseException e) {
						log.error("Error while parsing big decimal" , new CoercingParseValueException(e));
						throw  new CoercingParseValueException(e);
					}
				}else{
					log.error(new CoercingParseValueException("Invalid value '" + input + "' for LocalDateTime"));
					return null;
				}
			}

			@Override
			public BigDecimal parseLiteral(Object input) {
				if (!(input instanceof StringValue)) return null;
				String value = ((StringValue) input).getValue();
				return BigDecimal.valueOf(Integer.parseInt(value));
			}
		});
	}

}
