package com.example.prueba;

import com.example.prueba.Domain.DTO.User;
import com.example.prueba.Domain.Repository.UserRepository;
import com.example.prueba.Persistance.Crud.UsuarioCrudRepository;
import com.example.prueba.Persistance.Entity.Usuario;
import com.example.prueba.Persistance.Mapper.UsuarioMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository implements UserRepository {
    private final UsuarioCrudRepository crud;
    private final UsuarioMapper mapper;

    public UsuarioRepository(UsuarioCrudRepository crud, UsuarioMapper mapper){
        this.crud = crud;
        this.mapper = mapper;
    }

    @Override
    public User create(User user){
        if(user.getName() == null){
            throw new RuntimeException("El nombre debe de ser obligatorio");
        }
        if(user.getMail() == null){
            throw new RuntimeException("El correo debe de ser obligatorio");
        }
        if(!user.getMail().contains("@") && (!user.getMail().contains(".com") || !user.getMail().contains(".net"))){
            throw new RuntimeException("El correo no tiene un formato valido");
        }
        if(user.getPassword() == null){
            throw new RuntimeException("La contraseña debe de ser obligatoria");
        }
        if(user.getCellphone() == null){
            throw new RuntimeException("El telefono debe de ser obligatorio");
        }
        User u = new User(user.getId(),
                user.getName(),
                user.getMail(),
                user.getCellphone(),
                user.getPassword(),
                user.getRole());
        Usuario guardado = crud.save(mapper.toUsuario(u));
        return mapper.toUser(guardado);
    }
}
