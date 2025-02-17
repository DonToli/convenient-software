package don.convenientsoftware.controller;

import don.convenientsoftware.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Excel Controller", description = "Контроллер для работы с Excel файлами")
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/nth-max")
    @Operation(summary = "Получить N-ное максимальное число из файла")
    public int getNthMax(
            @Parameter(description = "Путь к файлу") @RequestParam String filePath,
            @Parameter(description = "Позиция N") @RequestParam int n) {
        return excelService.findNthMax(filePath, n);
    }
}
