package com.myorg;

import com.sun.tools.javac.util.Assert;
import dev.stratospheric.cdk.SpringBootApplicationStack;
import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;

public class AwsCdkDemoApp {
    public static void main(final String[] args) {
        App app = new App();

        String accountId = (String)app
                .getNode()
                .tryGetContext("accountId");

        Assert.checkNonNull(accountId, "context variable 'accountId' must not be null");

        String region = (String)app
                .getNode()
                .tryGetContext("region");
        Assert.checkNonNull(region, "context variable 'region' must not be null");

        new SpringBootApplicationStack(
                app,
                "SpringBootApplication",
                makeEnv(accountId, region),
                "docker.io/stratospheric/todo-app-v1:latest"
        );

        app.synth();
    }

    private static Environment makeEnv(String accountId, String region) {
        return Environment.builder()
                          .account(accountId)
                          .region(region)
                          .build();
    }
}
