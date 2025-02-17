package don.convenientsoftware.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    public int findNthMax(String filePath, int n) {
        List<Integer> numbers = readNumbersFromExcel(filePath);

        if (n > numbers.size() || n <= 0) {
            throw new IllegalArgumentException("N должно быть в диапазоне от 1 до количества чисел в файле");
        }

        return quickSelect(numbers, 0, numbers.size() - 1, numbers.size() - n);
    }

    private List<Integer> readNumbersFromExcel(String filePath) {
        List<Integer> numbers = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        numbers.add((int) cell.getNumericCellValue());
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла", e);
        }

        return numbers;
    }

    private int quickSelect(List<Integer> list, int left, int right, int k) {
        if (left == right) {
            return list.get(left);
        }

        int pivotIndex = partition(list, left, right);

        if (k == pivotIndex) {
            return list.get(k);
        } else if (k < pivotIndex) {
            return quickSelect(list, left, pivotIndex - 1, k);
        } else {
            return quickSelect(list, pivotIndex + 1, right, k);
        }
    }

    private int partition(List<Integer> list, int left, int right) {
        int pivotValue = list.get(right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (list.get(i) < pivotValue) {
                swap(list, i, storeIndex);
                storeIndex++;
            }
        }

        swap(list, storeIndex, right);
        return storeIndex;
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}