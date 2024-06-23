package com.moaaz.modernhome.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    protected String email;
    protected String phone1;
    protected String phone2;
    protected String address;
    protected String password;

    protected Role role;

    public static Person admin(){
        Person person= new Person();
        person.setName("Admin");
        person.setEmail("modernhomeinegypt@gmail.com");
        person.setRole(Role.ADMIN);
        return person;
    }
}
