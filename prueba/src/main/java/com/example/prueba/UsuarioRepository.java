package com.example.prueba;

import com.example.prueba.Domain.DTO.User;
import com.example.prueba.Domain.Repository.UserRepository;
import com.example.prueba.Persistance.Crud.RolCrudRepository;
import com.example.prueba.Persistance.Crud.UsuarioCrudRepository;
import com.example.prueba.Persistance.Entity.Rol;
import com.example.prueba.Persistance.Entity.Usuario;
import com.example.prueba.Persistance.Mapper.UsuarioMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository implements UserRepository {
    private final UsuarioCrudRepository crud;
    private final UsuarioMapper mapper;
    private final RolCrudRepository rolCrud;
    private final PasswordEncoder passwordEncoder;

    public UsuarioRepository(UsuarioCrudRepository crud, UsuarioMapper mapper, RolCrudRepository rolCrud,PasswordEncoder passwordEncoder ){
        this.crud = crud;
        this.mapper = mapper;
        this.rolCrud = rolCrud;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public User create(User user){
        if(crud.findByCorreo(user.getMail()).isPresent()){
            throw new RuntimeException("El correo ya esta registrado");
        }
        Rol rol = rolCrud.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("El rol CLIENTE no existe"));
        User u = new User(user.getId(),
                user.getName(),
                user.getMail(),
                user.getCellphone(),
                passwordEncoder.encode(user.getPassword()),
                null);
        Usuario usuario = mapper.toUsuario(u);
        usuario.setRol(rol);
        Usuario guardado = crud.save(usuario);
        return mapper.toUser(guardado);
    }
}
