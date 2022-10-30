package org.jarogoose.archigen.web.generate.domain.model.dto;

import java.util.List;

import org.jarogoose.archigen.core.TemplateKey;

import lombok.Builder;

@Builder
public record TemplateFilter(
        List<TemplateKey> templateKeys) {
}
