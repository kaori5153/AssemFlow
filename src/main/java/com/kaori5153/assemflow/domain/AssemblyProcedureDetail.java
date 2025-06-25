package com.kaori5153.assemflow.domain;

import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import jakarta.validation.Valid;
import java.util.List;

public class AssemblyProcedureDetail {

  @Valid
  private AssemblyProcedure assemblyProcedure;

  @Valid
  private Parts targetPart;

  @Valid
  private List<RequiredParts> requiredParts;

}
