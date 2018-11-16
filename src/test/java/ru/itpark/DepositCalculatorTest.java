package ru.itpark;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class DepositCalculatorTest {

    @Test
    void calculate() throws ParseException {
        {
            DepositCalculator calculator = new DepositCalculator();
            long calculate = calculator.calculate(100_000, 12, 10, "21.10.2018");

            assertEquals(110_000, calculate);
        }
        {
            DepositCalculator calculator = new DepositCalculator();
            long calculate = calculator.calculate(100_000, 24, 10, "21.10.2018");

            assertEquals(120_005, calculate);
        }

        {
            DepositCalculator calculator = new DepositCalculator();
            long calculate = calculator.calculate(700_000, 13, 10, "21.10.2018");

            assertEquals(775_945, calculate);
        }

        {
            DepositCalculator calculator = new DepositCalculator();
            long calculate = calculator.calculate(100_000, 36, 10, "15.11.2019");

            assertEquals(130000 ,calculate);
        }
    }
}