package mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

@ExtendWith(value = MockitoExtension.class)       //Чтобы аннотация Mockito автоматически начала работать без дополнительных насроек
public class UniversityTest {
    private final Student student = new Student("Евгений", true);
    @Mock
    private StudentValueGenerator studentValueGenerator;
    @InjectMocks                  // Аннотация заеняет блок @BeforeEach
    private University university;

//    @BeforeEach
//    public void setUp() {
//        university = new University(studentValueGenerator);
//    }

    @Test
    void getAllStudents() {
        assertNotNull(studentValueGenerator);
        Mockito.when(studentValueGenerator.generateAge()).thenReturn(50);

        university.addStudent(student);
        List<Student> expected = university.getAllStudents();
        assertEquals(expected.get(0).getAge(), 50);
    }

    @Test
    void getAllStudentsOver50Years() {
        Mockito.when(studentValueGenerator.generateAgeInRange(anyInt(), anyInt())).thenReturn(55);

        university.addStudentInRange(student, 50, 100);
        List<Student> expected = university.getAllStudents();
        assertEquals(expected.get(0).getAge(), 55);
    }

    @Test
    void getAllStudentsWithCountAgeGenerate() {  // Тест для проверки вызовов методов
        Mockito.when(studentValueGenerator.generateAge()).thenReturn(50);

        university.addStudent(student);
        List<Student> expected = university.getAllStudents();
        assertEquals(expected.get(0).getAge(), 50);

        Mockito.verify(studentValueGenerator).generateAge(); // Проверяем что метод generateAge вызывается один раз
//        Mockito.verify(studentValueGenerator, Mockito.times(2)).generateAge(); // Если нужно проверитьб что метод generateAge вызывается два раза

    }

    @Test
    void getAllStudentsInOrder() {  // Тест для проверки порядка вызовов методов
        Mockito.when(studentValueGenerator.generateAgeInRange(anyInt(), anyInt())).thenReturn(55);

        university.addStudentInRange(student, 50, 100);

        InOrder inOrder = Mockito.inOrder(studentValueGenerator);

        List<Student> expected = university.getAllStudents();

        inOrder.verify(studentValueGenerator, times(2)).generateAge();
        inOrder.verify(studentValueGenerator).generateAgeInRange(anyInt(), anyInt());
        assertEquals(expected.get(0).getAge(), 55);
    }

    @Test
    void addStudent() {
    }

    @Test
    void addStudentInRange() {
    }
}