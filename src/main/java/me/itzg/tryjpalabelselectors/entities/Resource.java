package me.itzg.tryjpalabelselectors.entities;

import java.util.List;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 * @author Geoff Bourne
 * @since Jun 2019
 */
@Entity
@Data
public class Resource {
  @Id @GeneratedValue
  long id;

  String name;

  /**
   * key=value pairs
   */
  @ElementCollection(fetch = FetchType.EAGER)
  List<String> labels;
}
