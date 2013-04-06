package org.codehaus.mojo.buildmetadata;

/*
 * The MIT License
 *
 * Copyright (c) 2004, The Codehaus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Set the java properties which contain the information about java runtime, java specification etc. which has been used
 * during the build.
 * 
 * <pre>
 *   [propertyPrefix].java.runtime.name
 *   [propertyPrefix].java.runtime.version
 *   [propertyPrefix].java.specification.name
 *   [propertyPrefix].java.specification.vendor
 *   [propertyPrefix].java.specification.version
 *   [propertyPrefix].java.version
 *   [propertyPrefix].java.vendor
 *   [propertyPrefix].java.vm.name
 *   [propertyPrefix].java.vm.info
 *   [propertyPrefix].java.vm.version
 *   [propertyPrefix].java.vm.vendor
 *   [propertyPrefix].java.vm.specification.name
 *   [propertyPrefix].java.vm.specification.vendor
 *   [propertyPrefix].java.vm.specification.version
 *   [propertyPrefix].sun.management.compiler
 * </pre>
 * 
 * Where the propertyPrefix is the string set in the mojo parameter.
 * 
 * @author <a href="codehaus@soebes.de">Karl-Heinz Marbaise</a>
 */
@Mojo( name = "java", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true )
public class JavaInformationMojo
    extends AbstractDefinePropertyMojo
{

    /**
     * Prefix string to use for the set of the operation system properties.
     */
    @Parameter( defaultValue = "build.environment" )
    private String propertyPrefix;

    public void execute()
        throws MojoExecutionException
    {

        Properties buildEnvironmentProperties = new Properties();
        BuildEnvironmentMetaData buildEnvironment =
            new BuildEnvironmentMetaData( getLog(), getProject(), getSession(), getRuntime(), getDefaultPropertyValue() );

        buildEnvironment.getJavaProperties( buildEnvironmentProperties, propertyPrefix );
        buildEnvironment.getJavaOpts( buildEnvironmentProperties, propertyPrefix );

        buildEnvironment.defineProjectProperty( buildEnvironmentProperties );

    }

}
