package org.jarogoose.archigen.core.template;

import static org.jarogoose.archigen.core.util.StringUtils.splitByUpperCase;
import static org.springframework.util.StringUtils.capitalize;

import java.io.File;

import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.util.Paths;
import org.jarogoose.archigen.web.domain.Config;

public final class ControllerActionTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.control;

  import {{project-path}}.event.Event;
  import {{project-path}}.feature.{{root-name}}.api.{{feature-name}}ActionFacade;
  import {{project-path}}.feature.{{root-name}}.domain.model.request.Add{{feature-name}}Request;
  import {{project-path}}.rest.EnenbiController;
  import org.springframework.http.ResponseEntity;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestBody;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;

  /**
   * {@link {{feature-name}}ActionController} handles POST PUT DELETE REST events.
   * <p>
   * Action controller is responsible for:
   * <li> read requests handling </li>
   * <li> read response handling </li>
   * <li> all levels exceptions handling </li>
   * <li> event recording </li>
   * <li> passing request to appropriate facade </li>
   *
   * @author Generated by {{author-name}}.
   */
  @RestController()
  @RequestMapping("{{rest-api}}/{{domain-uri}}-api/action")
  public class {{feature-name}}ActionController implements EnenbiController {

    private final {{feature-name}}ActionFacade facade;

    public {{feature-name}}ActionController({{feature-name}}ActionFacade facade) {
      this.facade = facade;
    }

    @Event
    @PostMapping("add-{{uri}}")
    public ResponseEntity<Object> add{{feature-name}}(
      @RequestBody Add{{feature-name}}Request request
    ) {
      try {
        // TODO Auto-generated method stub
        return internalServerError();
      } catch (Exception e) {
        return internalServerError();
      }
    }
  }
  
  """;

  private final Config config;
  private final Domain domain;
  
  public ControllerActionTemplate(Config config, Domain domain) {
    this.config = config;
    this.domain = domain;
  }

  @Override
  public String content() {
    final String projectPath = String.format("%s.%s", config.artefact(), config.project());
    final String featureName = capitalize(domain.feature());
    final String uriName = String.join("-", splitByUpperCase(domain.feature()));

    String template = TEMPLATE;

    template = template.replace("{{author-name}}", config.author());
    template = template.replace("{{project-path}}", projectPath);
    template = template.replace("{{root-name}}", domain.root());
    template = template.replace("{{rest-api}}", domain.restApi());
    template = template.replace("{{domain-uri}}", domain.root());
    template = template.replace("{{uri}}", uriName);
    template = template.replace("{{feature-name}}", featureName);

    return template;
  }

  @Override
  public File file() {
    return new File(Paths.CONTROLLER_PATH
      .get(config, domain.root(), domain.feature(), "ActionController"));
  }
}
