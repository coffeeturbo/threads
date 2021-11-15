package threads.cache;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Builder(toBuilder = true)
public class Base {
    final int id;
    final int version;
    String name;
}
