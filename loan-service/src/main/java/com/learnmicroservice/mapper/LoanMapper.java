package com.learnmicroservice.mapper;


import com.learnmicroservice.dto.LoanDto;
import com.learnmicroservice.entity.Loan;

public class LoanMapper {

    public static LoanDto mapToLoansDto(Loan Loan, LoanDto LoanDto) {
        LoanDto.setLoanNumber(Loan.getLoanNumber());
        LoanDto.setLoanType(Loan.getLoanType());
        LoanDto.setMobileNumber(Loan.getMobileNumber());
        LoanDto.setTotalLoan(Loan.getTotalLoan());
        LoanDto.setAmountPaid(Loan.getAmountPaid());
        LoanDto.setOutstandingAmount(Loan.getOutstandingAmount());
        return LoanDto;
    }

    public static Loan mapToLoans(LoanDto LoanDto, Loan Loan) {
        Loan.setLoanNumber(LoanDto.getLoanNumber());
        Loan.setLoanType(LoanDto.getLoanType());
        Loan.setMobileNumber(LoanDto.getMobileNumber());
        Loan.setTotalLoan(LoanDto.getTotalLoan());
        Loan.setAmountPaid(LoanDto.getAmountPaid());
        Loan.setOutstandingAmount(LoanDto.getOutstandingAmount());
        return Loan;
    }

}
