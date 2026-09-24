package com.example.sampleapp;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Deliberately trivial endpoint. Its only job is to prove the pipeline works
 * end-to-end: the version and log-level values come from the environment's
 * ConfigMap (see gke-manifests/base/configmap.yaml and the per-environment
 * overlays), so hitting this endpoint in dev vs. test vs. prod shows
 * different values without any code change.
 */
@RestController
public class InfoController {

    @Value("${backend-version:unknown}")
    private String backendVersion;

    @Value("${log-level:unknown}")
    private String logLevel;

    @GetMapping("/")
    public Map<String, String> info() {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("app", "sample-app");
        body.put("backendVersion", backendVersion);
        body.put("logLevel", logLevel);
        body.put("hostname", System.getenv().getOrDefault("HOSTNAME", "unknown"));
        return body;
    }
}
