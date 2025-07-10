package com.kaori5153.assemflow.controller;


import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import com.kaori5153.assemflow.domain.AssemblyProcedureDetail;
import com.kaori5153.assemflow.service.AssemblyProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@Controller
public class AssemblyProcedureController {

  private AssemblyProcedureService service;

  @Autowired
  public AssemblyProcedureController(AssemblyProcedureService service) {
    this.service = service;
  }

  @GetMapping("/assemProcedure/form")
  public String showSelectOperation() {
    return "selectOperation";
  }

  @Operation(summary = "部品情報一覧", description = "部品情報の一覧を検索します")
  @GetMapping("/parts")
  public String getPartsList(Model model) {
    model.addAttribute("parts", service.getAllParts());
    return "parts";
  }

  @GetMapping("/procedure")
  public String getAssemblyProcedureDetailsList(Model model) {
    model.addAttribute("procedure", service.getAllAssemblyProcedureList());
    return "procedure";
  }

  @GetMapping("/parts/{partId}")
  public String getPart(@PathVariable("partId") int partId, Model model) {
    model.addAttribute("part", service.getPartById(partId));
    return "part";
  }

  @GetMapping(value = "/parts/name", params = "partName")
  public String searchPart(@RequestParam String partName, Model model) {
    if (partName.isEmpty()) {
      return "redirect:/parts/name";
    } else {
      model.addAttribute("part", service.getPartByName(partName));
      return "part";
    }
  }

  @GetMapping("/procedure/search")
  public String showSearchProcedureForm() {
    return "searchProcedure";
  }

  @GetMapping(value = "/procedure/name", params = "targetPartName")
  public String searchProcedure(@RequestParam String targetPartName, Model model) {
    if (targetPartName.isEmpty()) {
      return "redirect:/procedure/name";
    } else {
      List<AssemblyProcedureDetail> procedureDetails = service.getAllAssemblyProcedureList();
      model.addAttribute("targetProcedure",
          service.getProcedureByTargetPartName(targetPartName, procedureDetails));
      return "targetProcedure";
    }
  }

  @GetMapping("/procedure/{id}")
  public String getAssemblyProcedure(@PathVariable("id") int id, Model model) {
    model.addAttribute("targetProcedure", service.getAssemblyProcedureById(id));
    return "targetProcedure";
  }

  @GetMapping("/parts/new")
  public String newPart(@ModelAttribute("part") Parts part, Model model) {
    model.addAttribute("part", part);
    return "registerPart";
  }

  @PostMapping("/parts")
  public String registerPart(@ModelAttribute Parts part, BindingResult result) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> System.out.println(error.toString()));
      return "registerPart";
    }
    service.resisterNewPart(part);
    return "redirect:/parts";
  }

  /**
   * 組み立てに必要な部品情報の新規登録画面を表示します。
   *
   * @param requiredPart 新規登録用の空の必要部品情報オブジェクト
   * @param model        登録フォームに必要なモデル情報
   * @return 必要部品情報の新規登録画面のビュー名
   */
  @GetMapping("/parts/required/new")
  public String newRequiredPart(@ModelAttribute("requiredPart") RequiredParts requiredPart,
      Model model) {
    model.addAttribute("requiredPart", requiredPart);
    return "registerRequiredPart";
  }

  /**
   * 組み立てに必要な部品部品情報の登録処理を行います。
   *
   * @param requiredPart 入力された必要部品情報データ
   * @param result       バリデーション結果
   * @param action       ユーザーのアクション
   * @return ユーザーが選択した操作へのリダイレクト
   */
  @PostMapping("/parts/required")
  public String registerRequiredPart(@ModelAttribute RequiredParts requiredPart,
      BindingResult result, @RequestParam("action") String action) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> System.out.println(error.toString()));
      return "registerRequiredPart";
    }
    service.resisterNewRequiredPart(requiredPart);
    if ("add".equals(action)) {
      return "redirect:/parts/required/new";
    } else if ("finish".equals(action)) {
      AssemblyProcedure procedure = service.getAssemblyProcedureByProcedureId(
          requiredPart.getProcedureId());
      int targetPartId = procedure.getTargetPartId();
      return "redirect:/procedure/" + targetPartId;
    }
    return "registerRequiredPart";
  }

  @GetMapping("/procedure/new")
  public String newProcedure(@ModelAttribute("assemProcedure") AssemblyProcedure assemProcedure,
      Model model) {
    model.addAttribute("assemProcedure", assemProcedure);
    return "registerAssemProcedure";
  }

  @PostMapping("/procedure")
  public String registerAssemProcedure(
      @ModelAttribute AssemblyProcedure assemProcedure, BindingResult result) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> System.out.println(error.toString()));
      return "registerAssemProcedure";
    }
    service.resisterNewAssemblyProcedure(assemProcedure);
    return "redirect:/parts/required/new";
  }

  @GetMapping("/parts/update/{id}")
  public String getPartByID(@PathVariable("id") int partId, Model model) {
    model.addAttribute("updatePart", service.getPartById(partId));
    model.addAttribute("partId", partId);
    model.addAttribute("message", "更新する情報を入力してください");
    return "updatePartById";
  }

  @PostMapping("/parts/update/{id}")
  public String updatePartById(@PathVariable("id") int partId,
      @ModelAttribute("updatePart") Parts part, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("updatePart", part);
      model.addAttribute("partId", partId);
      return "updatePartById";
    }
    service.updatePartById(part);
    return "redirect:/parts";
  }

  @GetMapping("/parts/required/update/{id}")
  public String getRequiredPartByID(@PathVariable("id") int requiredPartId, Model model) {
    model.addAttribute("updateRequiredPart",
        service.getRequiredPartByRequiredPartId(requiredPartId));
    model.addAttribute("requiredPartId", requiredPartId);
    model.addAttribute("message", "更新する情報を入力してください");
    return "updateRequiredPart";
  }

  @PostMapping("/parts/required/update/{id}")
  public String updateRequiredPart(@PathVariable("id") int requiredPartId,
      @ModelAttribute("updateRequiredPart") RequiredParts requiredPart, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("updateRequiredPart", requiredPart);
      model.addAttribute("procedureId", requiredPartId);
      return "updateRequiredPart";
    }
    service.updateRequiredPart(requiredPart);
    AssemblyProcedure procedure = service.getAssemblyProcedureByProcedureId(
        requiredPart.getProcedureId());
    int targetPartId = procedure.getTargetPartId();
    return "redirect:/procedure/" + targetPartId;
  }

  @GetMapping("/procedure/update/{id}")
  public String getAssemblyProcedureByID(@PathVariable("id") int procedureId, Model model) {
    model.addAttribute("updateProcedure", service.getAssemblyProcedureByProcedureId(procedureId));
    model.addAttribute("procedureId", procedureId);
    model.addAttribute("message", "更新する情報を入力してください");
    return "updateAssemblyProcedure";
  }

  @PostMapping("/procedure/update/{id}")
  public String updateAssemblyProcedure(@PathVariable("id") int procedureId,
      @ModelAttribute("updateProcedure") AssemblyProcedure assemblyProcedure, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("updateProcedure", assemblyProcedure);
      model.addAttribute("procedureId", procedureId);
      return "updateAssemblyProcedure";
    }
    service.updateAssemblyProcedure(assemblyProcedure);
    return "redirect:/procedure/" + assemblyProcedure.getTargetPartId();
  }

}
