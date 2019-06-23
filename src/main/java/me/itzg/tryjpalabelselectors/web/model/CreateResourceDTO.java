package me.itzg.tryjpalabelselectors.web.model;

import java.util.Map;
import lombok.Data;

/**
 * @author Geoff Bourne
 * @since Jun 2019
 */
@Data
public class CreateResourceDTO {
  String name;
  Map<String,String> labels;
}
