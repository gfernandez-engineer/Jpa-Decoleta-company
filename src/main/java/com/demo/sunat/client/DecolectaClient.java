package com.demo.sunat.client;

import com.demo.sunat.config.FeignConfig;
import com.demo.sunat.dto.SunatRucResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "decolecta-client",
        url = "${decolecta.api.url}",
        configuration = FeignConfig.class
)
public interface DecolectaClient {

    @GetMapping("/v1/sunat/ruc")
    SunatRucResponse consultarRuc(@RequestParam("numero") String numero);

    //@PostMapping("/v1/sunat/ruc")
    //SunatRucResponse RegsitroRuc(@RequestParam("numero") String numero);
}
