package com.example.prueba.Domain.DTO;

public class User {
    private Long id;
    private String name;
    private String mail;
    private String cellphone;
    private String password;
    private Role role;

    public User(Long id, String name, String mail, String cellphone, String password, Role role) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.cellphone = cellphone;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
