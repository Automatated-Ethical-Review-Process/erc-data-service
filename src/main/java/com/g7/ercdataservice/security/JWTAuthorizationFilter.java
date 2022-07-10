package com.g7.ercdataservice.security;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String authServiceURL;

    public JWTAuthorizationFilter(String authServiceURL) {
        this.authServiceURL = authServiceURL;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(parseJwt(request)!=null){
                JSONObject authResponse = callValidateAuthAPI(request);
                boolean isValid = Boolean.parseBoolean(authResponse.getAsString("valid"));
                if(isValid){
                    List<String> authorities = getAuthorities(authResponse);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(authResponse.getAsString("id"),
                                    null,authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else{
                    SecurityContextHolder.clearContext();
                }
            }else{
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            e.printStackTrace();
        }
    }
    private String parseJwt(HttpServletRequest request){
//        String authenticationHeader = request.getHeader(HEADER);
//
//        if(StringUtils.hasText(authenticationHeader) && authenticationHeader.startsWith(PREFIX)){
//            return authenticationHeader.substring(7);
//        }
//        return null;
        Cookie name = WebUtils.getCookie(request, "access");
        //System.out.println(name.getValue());
        return name.getValue();
    }

    private JSONObject callValidateAuthAPI(HttpServletRequest request) throws URISyntaxException {
        URI uri = new URI(authServiceURL);
        JSONObject json = new JSONObject();
        json.put("token", parseJwt(request));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, json, JSONObject.class);
        System.out.println(result.getStatusCode().value());
        return result.getBody();
    }

    private List<String> getAuthorities(JSONObject authResponse) {
        String authString[] = authResponse.getAsString("authorities").replace("[", "").replace("]", "").split(",");
        List<String> authorities = new ArrayList<>();
        for (String authStringTemp : authString) {
            authorities.add(authStringTemp.replace(" ",""));
        }
        return authorities;
    }



}
