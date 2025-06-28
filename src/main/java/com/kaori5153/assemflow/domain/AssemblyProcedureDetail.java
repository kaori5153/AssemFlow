package com.kaori5153.assemflow.domain;

import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import jakarta.validation.Valid;
import java.util.List;
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
public class AssemblyProcedureDetail {

  @Valid
  private AssemblyProcedure assemblyProcedure;

  @Valid
  private Parts targetPart;

  @Valid
  private List<RequiredPartView> requiredPartViews;

}
