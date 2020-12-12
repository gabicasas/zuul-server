package com.gcc.app.zuul.server.oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	
	
	@Autowired
	Environment environment;
	
	//Se configura el token jwt
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		
		resources.tokenStore(tokenStore()); // El mismo que el del servicio oauth (copy paste) 
	}

	
	//Se configuran las rutas
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//http
		http
		.authorizeRequests()
		.antMatchers("/api/security/oauth/**").permitAll()
		.antMatchers(HttpMethod.GET,"/api/productos/listar",
				"/api/items/listar",
				"/api/usuarios/usuarios").permitAll()
		.antMatchers(HttpMethod.GET,"/api/productos/ver/{id}",
				"/api/items/ver/{id}/cantidad/{cantidad}",
				"/api/usuarios/usuarios/{id}")
		.hasAnyRole("ADMIN","USER")
		
		.antMatchers(HttpMethod.POST, 
				"/api/productos/crear", 
				 "/api/items/crear",
				 "/api/usuarios/usuarios/{id}")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, 
				"/api/productos/crear/{id}", 
				 "/api/items/crear/id{id}",
				 "/api/usuarios/usuarios/{id}")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, 
				"/api/productos/crear", 
				 "/api/items/crear",
				 "/api/usuarios/usuarios/{id}")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, 
				"/api/productos/crear/{id}", 
				 "/api/items/crear/id{id}",
				 "/api/usuarios/usuarios/{id}")
		.hasRole("ADMIN")
		.anyRequest().permitAll()
		/*.and().portMapper().http(8080).mapsTo(8443)*/
		
		.and().cors().configurationSource(configurationSource());
		
	}
	
	public CorsConfigurationSource configurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","TRACE","OPTIONS"));
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
		
		UrlBasedCorsConfigurationSource src= new UrlBasedCorsConfigurationSource();
				src.registerCorsConfiguration("/**", corsConfiguration);
		return src;
	}


	@Bean
	public JwtTokenStore tokenStore() {
		// TODO Auto-generated method stub
		return new JwtTokenStore(accessTokenConverter());
	}

	// Conversor de token que genera el token jwt
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter= new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(environment.getProperty("config.security.oauth.jwt.key"));
		return jwtAccessTokenConverter;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(configurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	
}
