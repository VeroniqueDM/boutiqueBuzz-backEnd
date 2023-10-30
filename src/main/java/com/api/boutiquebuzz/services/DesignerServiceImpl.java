//package com.api.boutiquebuzz.services;
//
//
//
//import com.api.boutiquebuzz.domain.dtos.CreateDesignerRequestDTO;
//import com.api.boutiquebuzz.domain.dtos.DesignerResponseDTO;
//import com.api.boutiquebuzz.domain.dtos.UpdateDesignerRequestDTO;
//import com.api.boutiquebuzz.domain.entities.Designer;
//import com.api.boutiquebuzz.repositories.DesignerRepository;
//import com.api.boutiquebuzz.utils.ErrorConstants;
//import com.api.boutiquebuzz.utils.ResourceNotFoundException;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class DesignerServiceImpl implements DesignerService {
//    private final DesignerRepository designerRepository;
//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public DesignerServiceImpl(DesignerRepository designerRepository, ModelMapper modelMapper) {
//        this.designerRepository = designerRepository;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public List<DesignerResponseDTO> getAllDesigners() {
//        List<Designer> designers = designerRepository.findAll();
//        return designers.stream()
//                .map(designer -> modelMapper.map(designer, DesignerResponseDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public DesignerResponseDTO getDesignerById(Long designerId) {
//        Optional<Designer> designerOptional = designerRepository.findById(designerId);
//        if (designerOptional.isPresent()) {
//            return modelMapper.map(designerOptional.get(), DesignerResponseDTO.class);
//        } else {
//            throw new ResourceNotFoundException(String.format(ErrorConstants.DESIGNER_NOT_FOUND_MESSAGE, designerId));
//        }
//    }
//
//    @Override
//    public DesignerResponseDTO createDesigner(CreateDesignerRequestDTO designerDTO) {
//        Designer designer = modelMapper.map(designerDTO, Designer.class);
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        designer.setCreatedAt(currentDateTime);
//        designer.setUpdatedAt(currentDateTime);
//        Designer savedDesigner = designerRepository.save(designer);
//        return modelMapper.map(savedDesigner, DesignerResponseDTO.class);
//    }
//
//    @Override
//    public DesignerResponseDTO updateDesigner(Long designerId, UpdateDesignerRequestDTO updateDesignerDTO) {
//        Optional<Designer> designerOptional = designerRepository.findById(designerId);
//        if (designerOptional.isPresent()) {
//            Designer existingDesigner = designerOptional.get();
//
//            if (hasUpdates(existingDesigner, updateDesignerDTO)) {
//                existingDesigner.setUpdatedAt(LocalDateTime.now());
//            }
//
//            modelMapper.map(updateDesignerDTO, existingDesigner);
//            Designer updatedDesigner = designerRepository.save(existingDesigner);
//
//            return modelMapper.map(updatedDesigner, DesignerResponseDTO.class);
//        } else {
//            throw new ResourceNotFoundException(String.format(ErrorConstants.DESIGNER_NOT_FOUND_MESSAGE, designerId));
//        }
//    }
//
//    @Override
//    public DesignerResponseDTO deleteDesigner(Long designerId) {
//        Designer designer = designerRepository.findById(designerId)
//                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.DESIGNER_NOT_FOUND_MESSAGE, designerId)));
//
//        designerRepository.delete(designer);
//
//        return modelMapper.map(designer, DesignerResponseDTO.class);
//    }
//
//    private boolean hasUpdates(Designer existingDesigner, UpdateDesignerRequestDTO updateDesignerDTO) {
//        boolean hasUpdates = !existingDesigner.getName().equals(updateDesignerDTO.getName()) ||
//                !existingDesigner.getEmail().equals(updateDesignerDTO.getEmail()) ||
//                !existingDesigner.getPhone().equals(updateDesignerDTO.getPhone());
//
//        return hasUpdates;
//    }
//}