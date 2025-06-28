package com.kaori5153.assemflow.controller.converter;

import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import com.kaori5153.assemflow.domain.AssemblyProcedureDetail;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AssemblyProcedureConverter {

  public List<AssemblyProcedureDetail> toAssemblyProcedureDetails(
      List<AssemblyProcedure> procedureList,
      List<Parts> partsList,
      List<RequiredParts> requiredPartsList)
  {
    List<AssemblyProcedureDetail> details = new ArrayList<>();
    for (AssemblyProcedure procedure : procedureList) {
      AssemblyProcedureDetail detail = new AssemblyProcedureDetail();
      detail.setAssemblyProcedure(procedure);

      for (Parts parts : partsList) {
        if (procedure.getTargetPartId() == parts.getPartId()) {
          detail.setTargetPart(parts);
        }
      }

      List<RequiredParts> relatedRequiredParts = new ArrayList<>();
      for (RequiredParts requiredParts : requiredPartsList) {
        if (procedure.getProcedureId() == requiredParts.getProcedureId()) {
          relatedRequiredParts.add(requiredParts);
        }
      }

      detail.setRequiredParts(relatedRequiredParts);
      details.add(detail);
    }
    return details;
  }

}
