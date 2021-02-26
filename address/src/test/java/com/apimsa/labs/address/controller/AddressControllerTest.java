package com.apimsa.labs.address.controller;

import com.apimsa.labs.address.domain.model.Address;
import com.apimsa.labs.address.domain.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.validation.ConstraintViolationProblemModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebMvcTest(controllers = AddressController.class)
@ActiveProfiles("test")
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Address> addressList;

    private final static Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);
    
    @BeforeEach
    void setUp() {
        this.addressList = new ArrayList<>();
        this.addressList.add(new Address("331", "101", "kapad bazar", "mahim", "400016", "Mumbai", "MH", "IN"));
        this.addressList.add(new Address("332", "101", "saki road", "powai", "400072", "Mumbai", "MH", "IN"));
        this.addressList.add(new Address("333", "101", "mahakali road", "andheri", "400093", "Mumbai", "MH", "IN"));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }

    @Test
    void shouldFetchAllAddresss() throws Exception {

        given(addressService.findAllAddresses()).willReturn(addressList);

        this.mockMvc.perform(get("/api/address/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(addressList.size())));
        
        logger.info("Count of addresses in db: "+ addressList.size());
    }

    @Test
    void shouldFetchOneAddressByAddressId() throws Exception {
        final String addressId = "301";
        final Address address = new Address("301", "201", "line1", "line2", "400016", "Mumbai", "MH", "IN");;
        final Optional<Address> fetchedAddress = Optional.of(address);
        
        given(addressService.findByAddressId(addressId)).willReturn(fetchedAddress);

        this.mockMvc.perform(get("/api/address/{addressId}", addressId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zipCode", is(address.getZipCode())))
                .andExpect(jsonPath("$.city", is(address.getCity())));
    }

    @Test
    void shouldReturn404WhenFindByAddressId() throws Exception {
        final String addressId = "335";
        given(addressService.findByAddressId(addressId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/api/address/{addressId}", addressId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewAddress() throws Exception {
       
        Address address = new Address("304", "201", "saki road", "powai", "400072", "Mumbai", "MH", "IN");

        given(addressService.createAddress(any(Address.class))).willAnswer((invocation) -> invocation.getArgument(0));
        
        this.mockMvc.perform(post("/api/address/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city", is(address.getCity())))
                .andExpect(jsonPath("$.zipCode", is(address.getZipCode())))
        ;
    }

    @Test
    void shouldReturn400WhenCreateNewAddressWithoutZipCode() throws Exception {
        Address address = new Address("305", "201", "saki road", "powai", "", "Mumbai", "MH", "IN");

        given(addressService.createAddress(any(Address.class))).willAnswer((invocation) -> invocation.getArgument(0));
        
        this.mockMvc.perform(post("/api/address/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isBadRequest())
                //.andExpect(header().string("Content-Type", is("application/problem+json")))
                .andExpect(header().string("Content-Type", is(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                //.andExpect(jsonPath("$.type", is("https://zalando.github.io/problem/constraint-violation")))
                .andExpect(jsonPath("$.type", is("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.status", is(400)))
               // .andExpect(jsonPath("$.error[0].contains("zipCode")")
                .andReturn()
        ;
    }
}