package com.emlakburada.emlakburada.model;

import com.emlakburada.emlakburada.model.enums.RealEstateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEstate {
    private RealEstateType realEstateType;
    private Address address;
    private String rooms;
    private int size;
    private int floor;
}