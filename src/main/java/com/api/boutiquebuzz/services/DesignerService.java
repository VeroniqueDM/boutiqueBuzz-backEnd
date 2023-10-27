package com.api.boutiquebuzz.services;


import com.api.boutiquebuzz.domain.dtos.CreateDesignerRequestDTO;
import com.api.boutiquebuzz.domain.dtos.DesignerResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateDesignerRequestDTO;

import java.util.List;

public interface DesignerService {
    List<DesignerResponseDTO> getAllDesigners();
    DesignerResponseDTO getDesignerById(Long designerId);
    DesignerResponseDTO createDesigner(CreateDesignerRequestDTO designerDTO);
    DesignerResponseDTO updateDesigner(Long id, UpdateDesignerRequestDTO updateDesignerDTO);
    DesignerResponseDTO deleteDesigner(Long designerId);
}