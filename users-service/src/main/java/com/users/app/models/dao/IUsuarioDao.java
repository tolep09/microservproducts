package com.users.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.users.commons.app.models.entity.User;

//esta anotacion hace que spring genere automaticamente un controller para este servicio
//incluyendo sus endpoints
@RepositoryRestResource(path = "users") // users es el prefijo para los edpoints
public interface IUsuarioDao extends PagingAndSortingRepository<User, Long> {
	@RestResource(path="fbusername") //alias para invocar al metodo desde un request
	public User findByUsername(@Param("username") String username); //@Param es el nombre del parametro en el request
	
	// request:
	// .../search/fbusername?username=value
}
