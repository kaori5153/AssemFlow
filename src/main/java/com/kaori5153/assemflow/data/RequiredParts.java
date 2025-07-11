package com.kaori5153.assemflow.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "組み立てに必要な部品")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class RequiredParts {

  @Schema(description = "必要部品ID", type = "int", example = "1", required = true)
  @Max(999)
  private Integer requiredPartId;

  @Schema(description = "組み立て手順ID", type = "int", example = "1", required = true)
  @NotNull
  @Max(999)
  private Integer procedureId;

  @Schema(description = "部品ID", type = "int", example = "1", required = true)
  @NotNull
  @Max(999)
  private Integer partId;

  @Schema(description = "必要数", type = "int", example = "1", required = true)
  @NotNull
  @Min(1)
  @Max(999)
  private Integer quantity;

}
