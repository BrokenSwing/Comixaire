package com.github.brokenswing.comixaire.extension;

import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationExtension;

public class TestExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver, TestInstancePostProcessor
{

    private final ApplicationExtension applicationExtension;
    private final MockitoExtension mockitoExtension;

    public TestExtension()
    {
        this.applicationExtension = new ApplicationExtension();
        this.mockitoExtension = new MockitoExtension();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception
    {
        this.mockitoExtension.afterEach(context);
        this.applicationExtension.afterEach(context);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        this.applicationExtension.beforeEach(context);
        this.mockitoExtension.beforeEach(context);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException
    {
        return this.applicationExtension.supportsParameter(parameterContext, extensionContext) ||
                this.mockitoExtension.supportsParameter(parameterContext, extensionContext);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException
    {
        if (this.applicationExtension.supportsParameter(parameterContext, extensionContext))
        {
            return this.applicationExtension.resolveParameter(parameterContext, extensionContext);
        }
        return this.mockitoExtension.resolveParameter(parameterContext, extensionContext);
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception
    {
        this.applicationExtension.postProcessTestInstance(testInstance, context);
    }

}
