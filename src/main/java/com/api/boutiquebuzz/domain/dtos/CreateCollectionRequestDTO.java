package com.api.boutiquebuzz.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateCollectionRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;
//    @JsonIgnore
////    private Long designerId;
@JsonIgnore
private Long ownerId;
    private List<String> imageUrls;

}