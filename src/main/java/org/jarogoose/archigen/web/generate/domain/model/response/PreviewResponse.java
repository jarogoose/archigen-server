package org.jarogoose.archigen.web.generate.domain.model.response;

import java.util.List;

import lombok.Builder;

@Builder
public record PreviewResponse(
    List<String> templates
) {}
