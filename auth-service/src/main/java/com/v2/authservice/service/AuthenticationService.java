package com.v2.authservice.service;

import com.v2.authservice.dto.AuthenticationRequest;
import com.v2.authservice.dto.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
