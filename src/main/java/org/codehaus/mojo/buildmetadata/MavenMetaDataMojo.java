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

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Get maven-metadata which means the version of Maven the active profiles.
 * 
 * <pre>
 *   [propertyPrefix].version
 *   [propertyPrefix].profiles
 *   [propertyPrefix].goals
 * </pre>
 * 
 * @author pgier
 * @author <a href="codehaus@soebes.de">Karl-Heinz Marbaise</a>
 */
@Mojo( name = "maven", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true )
public class MavenMetaDataMojo
    extends AbstractDefinePropertyMojo
{

    /**
     * The prefix for the properties will be used to store the Maven 
     * information.
     */
    @Parameter( defaultValue = "maven" )
    private String propertyPrefix;

    /**
     * Main plugin execution
     */
    public void execute()
    {

        Properties buildEnvironmentProperties = new Properties();
        BuildEnvironmentMetaData buildEnvironment =
            new BuildEnvironmentMetaData( getLog(), getProject(), getSession(), getRuntime(), getDefaultPropertyValue() );

        buildEnvironment.getMavenProperties( buildEnvironmentProperties, propertyPrefix );
        // @TODO: Think about making the following call dependent on an option which is false by default.
        // getExecutionProperties( buildEnvironmentProperties, propertyPrefix );

        buildEnvironment.defineProjectProperty( buildEnvironmentProperties );

    }
}