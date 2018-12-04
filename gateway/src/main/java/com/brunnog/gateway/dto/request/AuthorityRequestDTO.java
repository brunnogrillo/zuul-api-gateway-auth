package com.brunnog.gateway.dto.request;

import com.brunnog.gateway.model.Authority;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sgs.architecture.rest.services.dto.request.IRequestDTO;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityRequestDTO implements IRequestDTO<Authority> {
	
	private Long serverId;
	private Long resourceId;
	private Long userId;
}
