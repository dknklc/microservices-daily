package com.dekankilic.gatewayserver.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// This class is going to help me to extract the roles that I am getting from the keycloak into the format that Spring Security framework can understand.
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    // I need to write the logic to read the role information from the JWT access token that I'm going to receive and
    // convert the same into the collection of granted authority.
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access"); // The purpose of getClaim method is that it will give you access to the payload data available inside jwt access token.
        if(realmAccess == null || realmAccess.isEmpty()){
            return new ArrayList<>();
        }

        Collection<GrantedAuthority> returnValue = ((List<String>)realmAccess.get("roles"))
                .stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return returnValue;
    }
    // How to communicate about this RoleConverter to the configurations that we have done inside the SecurityConfig.java class.
    // For this, We will create a small method there named as grantedAuthoritiesExtractor.
}
