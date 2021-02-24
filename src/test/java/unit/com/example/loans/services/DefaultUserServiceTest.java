package unit.com.example.loans.services;

import com.example.loans.domain.User;
import com.example.loans.exceptions.UserNotFoundException;
import com.example.loans.repository.UserRepository;
import com.example.loans.services.UserService;
import com.example.loans.services.impl.DefaultUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class DefaultUserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new DefaultUserService(userRepository);
    }

    @Test
    public void testGetUserByIdThenReturnUser() {
        User expectedUser = buildNewUser();

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedUser));

        User response = userService.getUser(1L);

        assertEquals(expectedUser, response);
    }

    @Test
    public void testGetUserByIdThenThrowsUserNotFoundException() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Throwable ex = assertThrows(UserNotFoundException.class, () -> {userService.getUser(1L);});
        assertEquals("The user with ID 1 does not exists", ex.getMessage());
    }

    @Test
    public void testSaveUserThenReturnSameUser() {
        User expectedUser = buildNewUser();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedUser);

        User response = userService.createUser(expectedUser);

        assertEquals(expectedUser, response);
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteUserByIdThenThrowsUserNotFoundException() {
        doThrow(EmptyResultDataAccessException.class).when(userRepository).deleteById(anyLong());

        assertThrows(EmptyResultDataAccessException.class, () -> {userService.deleteUser(1L);});
    }

    private User buildNewUser() {
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setFirstName("Emmanuel");
        expectedUser.setLastName("Carrizo");
        return expectedUser;
    }
}