package net.safri.micronaut


import groovy.transform.CompileStatic
import io.micronaut.validation.Validated

import javax.validation.constraints.NotBlank

@Validated
@CompileStatic
class TestCommand {
    @NotBlank
    String name
}
