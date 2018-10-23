package com.rectus29.beertender.api;


import com.coxautodev.graphql.tools.SchemaParser;
import com.rectus29.beertender.api.resolver.ProductResolver;
import com.rectus29.beertender.api.scalar.GraphQLBigDecimal;
import com.rectus29.beertender.api.scalar.GraphQLDate;
import graphql.servlet.AbstractGraphQLHttpServlet;
import graphql.servlet.GraphQLInvocationInputFactory;
import graphql.servlet.GraphQLObjectMapper;
import graphql.servlet.GraphQLQueryInvoker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;


@WebServlet(urlPatterns = "/api/graphql", loadOnStartup = 3)
public class GraphQLEndpoint extends AbstractGraphQLHttpServlet {

	private GraphQLInvocationInputFactory invocationInputFactory;

	public GraphQLEndpoint() {
	}

	@Override
	public void init() throws ServletException {
		super.init();
		this.invocationInputFactory = GraphQLInvocationInputFactory.newBuilder(
				SchemaParser.newParser()
						.file("schema.graphqls")
						.resolvers(
								new Query(),
								new Mutation(),
								new ProductResolver()/*,
						new SigninResolver()*/
						)
						.scalars(
								new GraphQLDate(),
								new GraphQLBigDecimal()
						)
						.build()
						.makeExecutableSchema()
		).build();
	}

	@Override
	protected GraphQLQueryInvoker getQueryInvoker() {
		return GraphQLQueryInvoker.newBuilder().build();
	}

	@Override
	protected GraphQLInvocationInputFactory getInvocationInputFactory() {
		return this.invocationInputFactory;
	}

	@Override
	protected GraphQLObjectMapper getGraphQLObjectMapper() {
		return GraphQLObjectMapper.newBuilder().build();
	}
}