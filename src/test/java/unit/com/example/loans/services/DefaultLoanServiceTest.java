package unit.com.example.loans.services;

import com.example.loans.domain.Loan;
import com.example.loans.domain.User;
import com.example.loans.exceptions.PageNotFoundException;
import com.example.loans.exceptions.UserNotFoundException;
import com.example.loans.repository.LoanRepository;
import com.example.loans.response.LoansResponse;
import com.example.loans.services.LoanService;
import com.example.loans.services.UserService;
import com.example.loans.services.impl.DefaultLoanService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultLoanServiceTest {

    private LoanRepository loanRepository;

    private UserService userService;

    private LoanService loanService;

    @Before
    public void setUp() {
        loanRepository = Mockito.mock(LoanRepository.class);
        userService = Mockito.mock(UserService.class);
        loanService = new DefaultLoanService(loanRepository, userService);
    }

    @Test
    public void testWhenGetAllLoansAndNotExistsLoansThenReturnsEmptyList() {
        List<Loan> emptyLoans = new ArrayList<>();
        Integer size = 5;
        Integer page = 1;

        PageRequest pageRequest = PageRequest.of(page, size).previous();
        Page<Loan> loansPaginated = new PageImpl<>(emptyLoans, pageRequest,0);

        Mockito.when(loanRepository.findAll(Mockito.any(Pageable.class))).thenReturn(loansPaginated);

        LoansResponse loansResponse = loanService.getAllLoans(size, page);

        assertThat(loansResponse).isNotNull();
        assertEquals( 0, loansResponse.getPaging().getTotal());
        assertEquals( emptyLoans.size(), loansResponse.getItems().size());
        assertEquals(emptyLoans, loansResponse.getItems());
    }

    @Test
    public void testWhenGetAllLoansInFirstPageWithFiveElementsThenReturnsFourLoansAndTotalLoansInFour() {
        List<Loan> expectedLoans = buildListWithLoans(4, false);
        Integer size = 5;
        Integer page = 1;

        PageRequest pageRequest = PageRequest.of(page, size).previous();
        Page<Loan> loansPaginated = new PageImpl<>(expectedLoans, pageRequest,4);

        Mockito.when(loanRepository.findAll(Mockito.any(Pageable.class))).thenReturn(loansPaginated);

        LoansResponse loansResponse = loanService.getAllLoans(size, page);

        assertThat(loansResponse).isNotNull();
        assertEquals( 4, loansResponse.getPaging().getTotal());
        assertEquals( expectedLoans.size(), loansResponse.getItems().size());
        assertEquals( expectedLoans, loansResponse.getItems());
    }

    @Test
    public void testWhenGetAllLoansInSecondPageWithFiveElementsThenReturnsLastTwoLoansAndTotalLoansInSeven() {
        List<Loan> expectedLoans = buildListWithLoans(2, false);
        Integer size = 5;
        Integer page = 2;

        PageRequest pageRequest = PageRequest.of(page, size).previous();
        Page<Loan> loansPaginated = new PageImpl<>(expectedLoans, pageRequest,7);

        Mockito.when(loanRepository.findAll(Mockito.any(Pageable.class))).thenReturn(loansPaginated);

        LoansResponse loansResponse = loanService.getAllLoans(size, page);

        assertThat(loansResponse).isNotNull();
        assertEquals( 7, loansResponse.getPaging().getTotal());
        assertEquals( expectedLoans.size(), loansResponse.getItems().size());
        assertEquals( expectedLoans, loansResponse.getItems());
    }

    @Test
    public void testWhenGetAllLoansThenThrowsPageNotFoundExceptionBecauseSecondPageNotExists() {
        List<Loan> emptyLoans = new ArrayList<>();
        Integer size = 5;
        Integer page = 2;

        PageRequest pageRequest = PageRequest.of(page, size).previous();
        Page<Loan> loansPaginated = new PageImpl<>(emptyLoans, pageRequest,3);

        Mockito.when(loanRepository.findAll(Mockito.any(Pageable.class))).thenReturn(loansPaginated);

        Throwable ex = assertThrows(PageNotFoundException.class, () -> {loanService.getAllLoans(size, page);});
        assertEquals("Number page not exists, max page is 1", ex.getMessage());
    }

    @Test
    public void testWhenGetAllLoansByUserAndNotExistsLoansInThisUserThenReturnsEmptyList() {
        List<Loan> emptyLoans = new ArrayList<>();
        User expectedUser = new User();
        Integer size = 5;
        Integer page = 1;

        PageRequest pageRequest = PageRequest.of(page, size).previous();
        Page<Loan> loansPaginated = new PageImpl<>(emptyLoans, pageRequest,0);

        Mockito.when(userService.getUser(Mockito.anyLong())).thenReturn(expectedUser);
        Mockito.when(loanRepository.findAllByUser(Mockito.any(User.class), Mockito.any(Pageable.class))).thenReturn(loansPaginated);

        LoansResponse loansResponse = loanService.getAllLoansByUser(10L, size, page);

        assertThat(loansResponse).isNotNull();
        assertEquals( 0, loansResponse.getPaging().getTotal());
        assertEquals( emptyLoans.size(), loansResponse.getItems().size());
        assertEquals( emptyLoans, loansResponse.getItems());
    }

    @Test
    public void testWhenGetAllLoansByUserInFirstPageWithFiveElementsThenReturnsFiveLoans() {
        List<Loan> expectedLoans = buildListWithLoans(5, true);
        User expectedUser = new User();
        Integer size = 5;
        Integer page = 1;

        PageRequest pageRequest = PageRequest.of(page, size).previous();
        Page<Loan> loansPaginated = new PageImpl<>(expectedLoans, pageRequest,5);

        Mockito.when(userService.getUser(Mockito.anyLong())).thenReturn(expectedUser);
        Mockito.when(loanRepository.findAllByUser(Mockito.any(User.class), Mockito.any(Pageable.class))).thenReturn(loansPaginated);

        LoansResponse loansResponse = loanService.getAllLoansByUser(10L, size, page);

        assertThat(loansResponse).isNotNull();
        assertEquals( 5, loansResponse.getPaging().getTotal());
        assertEquals( expectedLoans.size(), loansResponse.getItems().size());
        assertEquals( expectedLoans, loansResponse.getItems());
    }

    @Test
    public void testWhenGetAllLoansByUserInThirdPageWithSevenElementsThenReturnsLastSixLoansFromTotal20() {
        List<Loan> expectedLoans = buildListWithLoans(6, true);
        User expectedUser = new User();
        Integer size = 7;
        Integer page = 3;

        PageRequest pageRequest = PageRequest.of(page, size).previous();
        Page<Loan> loansPaginated = new PageImpl<>(expectedLoans, pageRequest,20);

        Mockito.when(userService.getUser(Mockito.anyLong())).thenReturn(expectedUser);
        Mockito.when(loanRepository.findAllByUser(Mockito.any(User.class), Mockito.any(Pageable.class))).thenReturn(loansPaginated);

        LoansResponse loansResponse = loanService.getAllLoansByUser(10L, size, page);

        assertThat(loansResponse).isNotNull();
        assertEquals( 20, loansResponse.getPaging().getTotal());
        assertEquals( expectedLoans.size(), loansResponse.getItems().size());
        assertEquals( expectedLoans, loansResponse.getItems());
    }

    @Test
    public void testWhenGetAllLoansByUserThenThrowsPageNotFoundExceptionBecauseThirdPageNotExists() {
        List<Loan> emptyLoans = new ArrayList<>();
        User expectedUser = new User();
        Integer size = 5;
        Integer page = 3;

        PageRequest pageRequest = PageRequest.of(page, size).previous();
        Page<Loan> loansPaginated = new PageImpl<>(emptyLoans, pageRequest,9);

        Mockito.when(userService.getUser(Mockito.anyLong())).thenReturn(expectedUser);
        Mockito.when(loanRepository.findAllByUser(Mockito.any(User.class), Mockito.any(Pageable.class))).thenReturn(loansPaginated);

        Throwable ex = assertThrows(PageNotFoundException.class, () -> {loanService.getAllLoansByUser(10L, size, page);});
        assertEquals("Number page not exists, max page is 2", ex.getMessage());
    }

    @Test
    public void testWhenGetAllLoansByUserThenThrowsUserNotFoundException() {
        UserNotFoundException expectedException = new UserNotFoundException("The user with ID 10 does not exists");
        Integer size = 5;
        Integer page = 3;

        Mockito.when(userService.getUser(Mockito.anyLong())).thenThrow(expectedException);

        Throwable ex = assertThrows(UserNotFoundException.class, () -> {loanService.getAllLoansByUser(10L, size, page);});
        assertEquals(expectedException.getMessage(), ex.getMessage());
    }

    private List<Loan> buildListWithLoans(int cantLoans, boolean isFilterByUser){
        List<Loan> loans = new ArrayList<>();

        IntStream.rangeClosed(1, cantLoans)
                .forEach(i -> {
                    User user = new User();
                    Loan loan;
                    if (isFilterByUser) {
                        user.setId((long) 10);
                    } else {
                        user.setId((long) i);
                    }
                    loan = new Loan((long) i, 100L * i, user);

                    loans.add(loan);
                });

        return loans;
    }
}