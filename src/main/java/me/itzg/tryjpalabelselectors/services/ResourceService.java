package me.itzg.tryjpalabelselectors.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import me.itzg.tryjpalabelselectors.entities.Resource;
import me.itzg.tryjpalabelselectors.web.model.CreateResourceDTO;
import me.itzg.tryjpalabelselectors.web.model.ResourceDTO;
import me.itzg.tryjpalabelselectors.web.model.ResourceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Geoff Bourne
 * @since Jun 2019
 */
@Service
public class ResourceService {

  private final EntityManager entityManager;
  private final ObjectMapper objectMapper;

  @Autowired
  public ResourceService(EntityManager entityManager, ObjectMapper objectMapper) {
    this.entityManager = entityManager;
    this.objectMapper = objectMapper;
  }

  private static ResourceDTO toResourceDTO(Resource r) {
    return new ResourceDTO()
        .setId(r.getId())
        .setName(r.getName())
        .setLabels(fromLabelParis(r.getLabels()));
  }

  @Transactional
  public void batchCreate(List<CreateResourceDTO> batch) {

    batch.stream()
        .map(cr ->
            new Resource()
                .setName(cr.getName())
                .setLabels(toLabelPairs(cr.getLabels()))
        )
        .forEach(entityManager::persist);
  }

  private static List<String> toLabelPairs(Map<String, String> labels) {
    return labels.entrySet().stream()
        .map(entry -> entry.getKey() + "=" + entry.getValue())
        .collect(Collectors.toList());
  }

  public List<ResourceDTO> getAll() {
    return entityManager
        .createQuery("select r from Resource r", Resource.class)
        .getResultList()
        .stream()
        .map(ResourceService::toResourceDTO)
        .collect(Collectors.toList());
  }

  private static Map<String, String> fromLabelParis(List<String> labels) {

    return labels.stream()
        .map(s -> {
          final String[] parts = s.split("=", 2);
          return Map.entry(parts[0], parts[1]);
        })
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public List<ResourceDTO> query(ResourceQuery resourceQuery) {

    final List<String> selectors = toLabelPairs(resourceQuery.getLabelSelectors());

    final List<String> parts = new ArrayList<>(selectors.size());
    for (int i = 0; i < selectors.size(); i++) {
      parts.add(String.format("?%d member of r.labels", i+1));
    }

    final TypedQuery<Resource> query = entityManager
        .createQuery(
            "select r from Resource r where " +
                String.join(" and ", parts),
            Resource.class
        );

    for (int i = 0; i < selectors.size(); i++) {
      query.setParameter(i+1, selectors.get(i));
    }

    return query
        .getResultList()
        .stream()
        .map(ResourceService::toResourceDTO)
        .collect(Collectors.toList());
  }

  public void deleteAll() {
    entityManager.createQuery("delete from Resource");
  }
}
