package com.brunnog.gateway.config;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;

import com.brunnog.gateway.dto.request.LoginRequestDTO;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Multimap;

import springfox.documentation.builders.ApiListingBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiListing;
import springfox.documentation.service.Operation;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiListingScanningContext;
import springfox.documentation.spring.web.scanners.ApiModelReader;

public class LoginOperations extends ApiListingScanner {

	private static final String LOGIN = "Login";
	
	@Autowired
    private TypeResolver typeResolver;

	@Autowired
	public LoginOperations(ApiDescriptionReader apiDescriptionReader, ApiModelReader apiModelReader,
			DocumentationPluginsManager pluginsManager) {
		super(apiDescriptionReader, apiModelReader, pluginsManager);
	}

    @Override
    public Multimap<String, ApiListing> scan(ApiListingScanningContext context) {
        final Multimap<String, ApiListing> definition = super.scan(context);

        definition.put("login", new ApiListingBuilder(context.getDocumentationContext().getApiDescriptionOrdering())
            .apis(singletonList(getApiDescription()))
            .description(LOGIN)
            .tagNames(singleton(LOGIN))
            .build());

        return definition;
    }

	private ApiDescription getApiDescription() {
		return new ApiDescription("/login", "Login Authentication", singletonList(getOperations()), false);
	}

	private Operation getOperations() {
		return new OperationBuilder(new CachingOperationNameGenerator())
		            .method(POST)
		            .tags(singleton(LOGIN))
		            .uniqueId("login")
		            .parameters(singletonList(getParameter()))
		            .summary("Log in")
		            .responseMessages(singleton(getResponseMessage()))
		            .build();
	}

	private Parameter getParameter() {
		return new ParameterBuilder()
				    .name("request")
				    .description("request")
				    .parameterType("body")
				    .required(true)
				    .type(typeResolver.resolve(LoginRequestDTO.class))
				    .modelRef(new ModelRef("LoginRequestDTO"))
				    .build();
	}

	private ResponseMessage getResponseMessage() {
		return new ResponseMessageBuilder()
	        		.code(200)
	        		.message("Successfully logged in")
	        		.responseModel(new ModelRef("LoginResource"))
	        		.build();
	}
}
