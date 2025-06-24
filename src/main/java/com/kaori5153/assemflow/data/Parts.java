package com.kaori5153.assemflow.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "部品")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Parts {

  @Schema(description = "部品ID", type = "int", example = "1", required = true)
  @NotNull
  @Max(999)
  private int partId;

  @Schema(description = "部品名", type = "String", example = "ねじ", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  private String partName;

  @Schema(description = "製造会社", type = "String", example = "ねじ会社", required = true)
  @Size(min = 1, max = 100)
  private String manufacturer;

  @Schema(description = "EOL期日", type = "LocalDate", example = "2023-12-31")
  private LocalDate eol;

  @Schema(description = "終売フラグ", type = "boolean")
  private boolean discontinued;

}
