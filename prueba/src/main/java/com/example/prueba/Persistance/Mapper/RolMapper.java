package com.example.prueba.Persistance.Mapper;


import com.example.prueba.Domain.DTO.Role;
import com.example.prueba.Persistance.Entity.Rol;

//@Mapper(componentModel = "spring")
public interface RolMapper {

   //@Mapping(source = "nombre", target = "name")
    Role toRole(Rol rol);

    //@InheriteInverseConfiguration
    Rol toRol(Role role);
}
