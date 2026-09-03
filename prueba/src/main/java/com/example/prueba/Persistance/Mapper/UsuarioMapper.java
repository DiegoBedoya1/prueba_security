package com.example.prueba.Persistance.Mapper;

import com.example.prueba.Domain.DTO.User;
import com.example.prueba.Persistance.Entity.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface UsuarioMapper {

    @Mappings({
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "correo", target = "mail"),
            @Mapping(source = "telefono", target = "cellphone"),
            @Mapping(source = "rol", target = "role")
    })
    User toUser(Usuario usuario);

    @InheritInverseConfiguration
    Usuario toUsuario(User user);

}
