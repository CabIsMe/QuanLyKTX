package root.quanlyktx.model;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class QuickSort {
    public static List<Integer> sort(List<Integer> list) {
        if (list == null || list.size() <= 1) {
            return Collections.emptyList();
        }
        quickSort(list, 0, list.size() - 1);
        return list;
    }

    private static void quickSort(List<Integer> list, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = partition(list, left, right);
        quickSort(list, left, pivot - 1);
        quickSort(list, pivot + 1, right);
    }

    private static int partition(List<Integer> list, int left, int right) {
        int pivotIndex = (left + right) / 2;
        int pivotValue = list.get(pivotIndex);
        swap(list, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (list.get(i) > pivotValue) {
                swap(list, i, storeIndex);
                storeIndex++;
            }
        }
        swap(list, storeIndex, right);
        return storeIndex;
    }

    private static void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
