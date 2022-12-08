package dev.aco.back.DTO.Visitor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorDTO {
    private Long visitorId;
    private String prevLink;
}
