package com.springboot.api.config.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
    @Parameter(
        name = "X-AUTH-TOKEN",
        description = "발급된 access_token",
        required = true,
        schema = @Schema(implementation = String.class),
        in = ParameterIn.HEADER
    )
})
public @interface XAuthTokenHeader {

}
