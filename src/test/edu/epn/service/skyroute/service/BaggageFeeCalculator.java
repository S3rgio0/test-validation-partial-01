package ec.edu.epn.skyroute.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.beans.Transient;

//Test 1
@ExtendWith(MockitoExtension.class)
class BaggageFeeCalculatorTest {
    @Mock
    private PassengerService passengerService;
    @InjectMocks
    private BaggageFeeCalculator Calculator;
    @Test
    @DisplayName("Bebe calcular 30$ para el equipaje estándar")
    void shouldCalculateStandardBaggageFee() {
        double weight = 20.0;
        int bagCount = 1;
        Long passengerId = 1L;
        when(passengerService.isVip(passengerId)).thenReturn(false);

        double fee = Calculator.calculateFee(weight, bagCount, passengerId);

        assertEquals(30.0, fee);
        verify(passengerService).isVip(passengerId);
    }   

// Test 2

@Test
    @DisplayName("Debe calcular 80$ para equipaje con exceso de peso")
    void shouldCalculateOverweightBaggageFee() {
        double weight = 25.0;
        int bagCount = 1;
        Long passengerId = 2L;
        when(passengerService.isVip(passengerId)).thenReturn(false);

        double fee = Calculator.calculateFee(weight, bagCount, passengerId);

        assertEquals(80.0, fee);
        verify(passengerService).isVip(passengerId);
    }


//Test 3

@Test
@DisplayName("Debe Calcular $0 para pasajero Vip")
void shouldApplyVipBenefit(){
    duble weight = 15.0;
    int bagCount = 1;
    Long passengerId = 3L;
    when(passengerService.isVip(passengerId)).thenReturn(true);
    double fee = Calculator.calculateFee(weight, bagCount, passengerId);

    assertEquals(0.0, fee);
    verify(passengerService).isVip(passengerId);
}

//Test 4

@Test
@DisplayName("Debe calcular $30 para Vip con dos maletas")
void shouldApplyVipBenefitOnlyToFirstBag(){
    double weight = 15.0;
    int bagCount = 2;
    Long passengerId = 4L;
    when(passengerService.isVip(passengerId)).thenReturn(true);
    double fee = Calculator.calculateFee(weight, bagCount, passengerId);

    assertEquals(30.0, fee);
    verify(passengerService).isVip(passengerId);
}

//Test 5

@Test
@DisplayName("Debe lanzar IllegalArgumentException para peso negativo")
void shouldThrowExceptionForNegativeWeight(){
    double weight = -5.0;
    int bagCount = 1;
    Long passengerId = 5L;

    assertThrows(IllegalArgumentException.class, () -> {
        Calculator.calculateFee(weight, bagCount, passengerId);
    });
}
}

}







