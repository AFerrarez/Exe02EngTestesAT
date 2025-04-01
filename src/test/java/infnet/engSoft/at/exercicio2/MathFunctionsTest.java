package infnet.engSoft.at.exercicio2;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MathFunctionsTest {

    // Instancia MathFunctions utilizando dummy do MathLogger
    private MathFunctions mathFunctions = new MathFunctions(new DummyMathLogger());


    @Property
    void MultiplyByTwo(@ForAll int number) {
        int result = mathFunctions.multiplyByTwo(number);
        assertEquals(0, result % 2, "O resultado deve ser par.");
    }


    @Property
    void GenerateMultiplicationTable(
            @ForAll @IntRange(min = 1, max = 100) int number,
            @ForAll @IntRange(min = 1, max = 20) int limit) {
        int[] table = mathFunctions.generateMultiplicationTable(number, limit);
        for (int value : table) {
            assertEquals(0, value % number, "Cada elemento deve ser múltiplo do número informado.");
        }
    }


    @Property
    void IsPrime(@ForAll @IntRange(min = 2, max = 1000) int n) {
        if (mathFunctions.isPrime(n)) {
            for (int i = 2; i < n; i++) {
                assertNotEquals(0, n % i, "Um número primo não deve ser divisível por nenhum valor entre 2 e n-1.");
            }
        }
    }


    @Property
    void CalculateAverage(@ForAll List<@IntRange(min = -1000, max = 1000) Integer> numbersList) {
        Assume.that(!numbersList.isEmpty());
        int[] numbers = numbersList.stream().mapToInt(Integer::intValue).toArray();
        int min = numbersList.stream().min(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("Não foi possível determinar o valor mínimo."));
        int max = numbersList.stream().max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("Não foi possível determinar o valor máximo."));
        double average = mathFunctions.calculateAverage(numbers);
        assertTrue(average >= min && average <= max, "A média deve estar entre o valor mínimo e máximo do array.");
    }
}
