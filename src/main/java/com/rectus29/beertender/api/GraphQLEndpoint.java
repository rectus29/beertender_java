package com.rectus29.beertender.api;


import com.coxautodev.graphql.tools.SchemaParser;
import com.rectus29.beertender.api.resolver.CategoryResolver;
import com.rectus29.beertender.api.resolver.ProductResolver;
import com.rectus29.beertender.api.resolver.SecurityResolver;
import com.rectus29.beertender.api.resolver.UserResolver;
import com.rectus29.beertender.api.scalar.GraphQLBigDecimal;
import com.rectus29.beertender.api.scalar.GraphQLDate;
import com.rectus29.beertender.api.typeresolver.ProductTypeResolver;
import com.rectus29.beertender.api.typeresolver.SigninPayloadTypeResolver;
import graphql.servlet.AbstractGraphQLHttpServlet;
import graphql.servlet.GraphQLInvocationInputFactory;
import graphql.servlet.GraphQLObjectMapper;
import graphql.servlet.GraphQLQueryInvoker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 24/10/2018                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
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
								new ProductResolver(),
								new UserResolver(),
								new CategoryResolver(),
								new ProductTypeResolver(),
								new SigninPayloadTypeResolver(),
								new SecurityResolver()
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