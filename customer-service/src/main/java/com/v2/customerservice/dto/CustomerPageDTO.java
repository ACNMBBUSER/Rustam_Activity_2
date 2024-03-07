package com.v2.customerservice.dto;

import java.util.List;

public record CustomerPageDTO(
        int page,
        int size,
        int totalPage,
        List<CustomerDTO> customerDTOList) { }
