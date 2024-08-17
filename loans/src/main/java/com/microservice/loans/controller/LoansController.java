package com.microservice.loans.controller;

import com.microservice.loans.constant.LoansConstants;
import com.microservice.loans.dto.LoansDto;
import com.microservice.loans.dto.ResponseDto;
import com.microservice.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs Loans in bank",
        description = "CRUD REST APIs in bank to CREATE, READ, UPDATE and DELETE loans details"
)
@RestController
@RequestMapping( value = "/api/v1/loans", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LoansController {
    private final ILoansService iLoansService;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST APIs to create new Loan inside bank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(
            @RequestParam("mobile_number")
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber
    ){
        /* create loan */
        iLoansService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST APIs to fetch Loan details based on mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping
    public ResponseEntity<LoansDto> fetchLoanDetails(
            @RequestParam("mobile_number")
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iLoansService.fetchLoan(mobileNumber));
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST APIs to update Loan details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR"
            )
    })
    @PatchMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(
            @RequestBody LoansDto loansDto
    ){
        boolean isUpdated = iLoansService.updateLoan(loansDto);

        if(isUpdated)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200)
            );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseDto(LoansConstants.STATUS_500, LoansConstants.MESSAGE_500)
        );
    }

    @Operation(
            summary = "Delete Loan REST API",
            description = "REST APIs to delete Loan details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR"
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(
            @RequestParam("mobile_number")
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            String mobileNumber
    ){
        boolean isDeleted = iLoansService.deleteLoan(mobileNumber);
        if(isDeleted)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200)
            );
        return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).body(
                new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200)
        );
    }
}
