package com.example.prueba.Persistance.Mapper;


import com.example.prueba.Domain.DTO.Role;
import com.example.prueba.Persistance.Entity.Rol;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RolMapper {

   @Mapping(source = "nombre", target = "name")
    Role toRole(Rol rol);

    @InheritInverseConfiguration
    Rol toRol(Role role);
}
