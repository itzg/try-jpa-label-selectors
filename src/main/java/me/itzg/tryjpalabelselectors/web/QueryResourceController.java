package me.itzg.tryjpalabelselectors.web;

import java.util.List;
import me.itzg.tryjpalabelselectors.services.ResourceService;
import me.itzg.tryjpalabelselectors.web.model.ResourceDTO;
import me.itzg.tryjpalabelselectors.web.model.ResourceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Geoff Bourne
 * @since Jun 2019
 */
@RestController
@RequestMapping("_query/resource")
public class QueryResourceController {

  private final ResourceService resourceService;

  @Autowired
  public QueryResourceController(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  @PostMapping
  public List<ResourceDTO> query(@RequestBody ResourceQuery query) {
    return resourceService.query(query);
  }
}
