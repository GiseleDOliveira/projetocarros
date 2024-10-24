package com.aps.projetocarros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome obrigatório!")
    private String nome;

    @Email(message = "Email deve ser válido!")
    private String email;

    @NotBlank(message = "Senha obrigatória!")
    @Size(min = 6, message = "A senha deve conter pelo menos 8 caracteres!")
    private String senha;

    @Transient
    private String senhaAntiga;

    @Transient
    private String novaSenha;

    public Usuario() {
    }

    public Usuario(Long id, @NotBlank(message = "Nome obrigatório!") String nome,
                   @Email(message = "Email deve ser válido!") String email,
                   @NotBlank(message = "Senha obrigatória!") @Size(min = 6, message = "A senha deve conter pelo menos 8 caracteres!") String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
