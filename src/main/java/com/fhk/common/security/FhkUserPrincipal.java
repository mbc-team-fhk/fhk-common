package com.fhk.common.security;

import java.security.Principal;

public record FhkUserPrincipal(Long id, String role) implements Principal {
	@Override
	public String getName() {
		return String.valueOf(id);
	}
}
