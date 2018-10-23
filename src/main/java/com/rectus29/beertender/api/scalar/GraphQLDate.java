package com.rectus29.beertender.api.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.GraphQLScalarType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Date;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 23/10/2018 09:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class GraphQLDate extends GraphQLScalarType {

	private static final Logger log = LogManager.getLogger(GraphQLDate.class);
	private static final String DEFAULT_NAME = "Date";

	public GraphQLDate() {
		this(DEFAULT_NAME);
	}

	private GraphQLDate(final String name) {
		super(name, "Date Time type", new Coercing<Date, String>() {

			@Override
			public String serialize(Object input) {
				if (input instanceof Date) {
					return ((Date) input).getTime()+"";
				}else{
					log.error("Unable to parse date in GraphQLDate scalar Type");
					return "Wrong Data Format";
				}
			}

			@Override
			public Date parseValue(Object input) {
				if(input instanceof String){
					return DateTime.parse((String)input).toDate();
				}else{
					log.error(new CoercingParseValueException("Invalid value '" + input + "' for LocalDateTime"));
					return null;
				}
			}

			@Override
			public Date parseLiteral(Object input) {
				if (!(input instanceof StringValue)) return null;
				String value = ((StringValue) input).getValue();
				return DateTime.parse(value).toDate();
			}
		});
	}

}
