package dev.aco.back.DTO.Article.etc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class locationDTO {
    private Long locationId;
    private String locateName;
    private Long latitude;
    private Long longtitude;
}
