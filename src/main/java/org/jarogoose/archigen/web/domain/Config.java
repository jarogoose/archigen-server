package org.jarogoose.archigen.web.domain;

import lombok.Builder;

@Builder
public record Config(
    String id,
    String artefact,
    String project,
    String baseDir
) {

}
