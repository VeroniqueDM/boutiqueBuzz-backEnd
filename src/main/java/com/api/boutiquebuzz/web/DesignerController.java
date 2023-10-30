//package com.api.boutiquebuzz.web;
//import org.springframework.context.support.DefaultMessageSourceResolvable;
//
//import com.api.boutiquebuzz.domain.dtos.CreateDesignerRequestDTO;
//import com.api.boutiquebuzz.domain.dtos.DesignerResponseDTO;
//import com.api.boutiquebuzz.domain.dtos.UpdateDesignerRequestDTO;
//import com.api.boutiquebuzz.services.DesignerService;
//import com.api.boutiquebuzz.utils.ErrorUtil;
//import com.api.boutiquebuzz.utils.ResourceNotFoundException;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//@CrossOrigin
//
//@RestController
//@RequestMapping("/designers")
//public class DesignerController {
//
//    private final DesignerService designerService;
//
//    @Autowired
//    public DesignerController(DesignerService designerService) {
//        this.designerService = designerService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<DesignerResponseDTO>> getAllDesigners() {
//        List<DesignerResponseDTO> designerResponseDTOs = designerService.getAllDesigners();
//        return ResponseEntity.ok(designerResponseDTOs);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getDesignerById(@PathVariable Long id) {
//        try {
//            DesignerResponseDTO designer = designerService.getDesignerById(id);
//            return ResponseEntity.ok(designer);
//        } catch (ResourceNotFoundException e) {
//
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createDesigner(@RequestBody @Valid CreateDesignerRequestDTO designerDTO, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            List<String> errorMessages = bindingResult.getAllErrors()
//                    .stream()
//                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                    .toList();
//
//            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
//        }
//
//        DesignerResponseDTO createdDesigner = this.designerService.createDesigner(designerDTO);
//        return new ResponseEntity<>(createdDesigner, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateDesigner(@PathVariable Long id, @RequestBody @Valid UpdateDesignerRequestDTO designerDTO, BindingResult bindingResult) {
//        ResponseEntity<?> error = ErrorUtil.getErrors(bindingResult);
//        if (error != null) return error;
//        try {
//            DesignerResponseDTO updatedDesigner = designerService.updateDesigner(id, designerDTO);
//            return ResponseEntity.ok(updatedDesigner);
//        } catch (ResourceNotFoundException e) {
//
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteDesigner(@PathVariable Long id) {
//        try {
//            DesignerResponseDTO deletedDesigner = designerService.deleteDesigner(id);
//            return ResponseEntity.ok(deletedDesigner);
//        } catch (ResourceNotFoundException e) {
//
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//}