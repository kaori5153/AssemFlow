package com.kaori5153.assemflow.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequiredPartView {

    private int requiredPartId;

    private int partId;

    private String partName;

    private int quantity;

}
