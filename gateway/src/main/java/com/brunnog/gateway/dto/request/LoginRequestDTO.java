package com.brunnog.gateway.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginRequestDTO {

	@ApiModelProperty(position = 1)
	private String username;
	
	@ApiModelProperty(position = 2)
	private String password;
}
