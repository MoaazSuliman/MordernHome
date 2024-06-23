package com.moaaz.modernhome.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String name;

    private String email;
    private String phone1;
    private String phone2;
    private String address;
    private String password;

    private Role role;

    public static Person admin(){
        Person person= new Person();
        person.setName("Admin");
        person.setEmail("modernhomeinegypt@gmail.com");
        person.setRole(Role.ADMIN);
        return person;
    }
}
