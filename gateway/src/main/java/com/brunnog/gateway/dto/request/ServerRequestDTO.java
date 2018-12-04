package com.brunnog.gateway.dto.request;

import com.brunnog.gateway.model.Server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sgs.architecture.rest.services.dto.request.IRequestDTO;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServerRequestDTO implements IRequestDTO<Server> {

	private String name;
	private String uri;
}
