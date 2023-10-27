package com.api.boutiquebuzz.domain.dtos;

import lombok.Data;
import java.util.List;

@Data
public class AllDesignersResponseDTO {
    private List<DesignerResponseDTO> designers;
}
