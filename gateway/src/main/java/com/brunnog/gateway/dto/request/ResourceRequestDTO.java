package com.brunnog.gateway.dto.request;

import org.springframework.http.HttpMethod;

import com.brunnog.gateway.model.Resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sgs.architecture.rest.services.dto.request.IRequestDTO;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceRequestDTO implements IRequestDTO<Resource> {
	
	private Long serverId;
	private String path;
	private HttpMethod method;	
}
