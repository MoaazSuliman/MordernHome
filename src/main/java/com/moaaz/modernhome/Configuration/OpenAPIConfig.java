package com.moaaz.modernhome.Configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Modern Home",
                description = "Modern Home Created By Moaaz"
                , contact = @Contact(name = "Moaaz Suliman", email = "moaazsuliman1@gmail.com")
        )
)
public class OpenAPIConfig {

}
