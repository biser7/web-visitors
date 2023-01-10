package com.webvisitors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorCount {
    private String email;
    private String phone;
    private long visitQuantity;
}
