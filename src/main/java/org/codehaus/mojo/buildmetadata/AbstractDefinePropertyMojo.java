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

import org.apache.maven.execution.MavenSession;
import org.apache.maven.execution.RuntimeInformation;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

public abstract class AbstractDefinePropertyMojo
    extends AbstractMojo
{
    /**
     * The maven project
     */
    @Component
    private MavenProject project;

    /**
     * The maven session
     */
    @Component
    private MavenSession session;

    /**
     * The RuntimeInforamtion for the current instance of Maven.
     */
    @Component
    private RuntimeInformation runtime;

    /**
     * The default value for undefined properties.
     */
    @Parameter( defaultValue = "" )
    private String defaultPropertyValue;

    @Component
    private BuildEnvironmentMetaData buildEnvironment;    
    /**
     * Get the current project instance.
     * 
     * @return the project
     */
    public MavenProject getProject()
    {
        return this.project;
    }

    public MavenSession getSession()
    {
        return session;
    }

    public RuntimeInformation getRuntime()
    {
        return runtime;
    }

    public String getDefaultPropertyValue()
    {
        return defaultPropertyValue;
    }
    
    public BuildEnvironmentMetaData getBuildEnvironment() {
        return buildEnvironment;
    }

}
