package com.example.prueba.Persistance.Mapper;

import com.example.prueba.Domain.DTO.User;
import com.example.prueba.Persistance.Entity.Usuario;

//@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface UsuarioMapper {

    /*@Mappings({
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "correo", target = "mail"),
            @Mapping(source = "telefono", target = "cellphone"),
            @Mapping(source = "rol", target = "role")
    })*/
    User toUser(Usuario usuario);

    //@InheriteInverseConfiguration
    Usuario toUsuario(User user);

}
