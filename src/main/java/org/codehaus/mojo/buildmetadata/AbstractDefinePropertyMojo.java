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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.Os;
import org.codehaus.plexus.util.StringUtils;

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
    protected MavenSession session;

    protected void defineProperty( String name, String value )
    {
        if ( getLog().isDebugEnabled() )
        {
            getLog().debug( "define property " + name + " = \"" + value + "\"" );
        }

        project.getProperties().put( name, value );
    }

    protected void defineProjectProperty( Properties properties )
    {

        for ( Entry<Object, Object> item : properties.entrySet() )
        {
            defineProperty( (String) item.getKey(), (String) item.getValue() );
        }

    }

    /**
     * Get the given property if it exists otherwise give back the default value.
     * 
     * @param properties The properties to get the particular property from.
     * @param propertyName The name of the property to be extracted.
     * @return The representation oft the property.
     */
    protected String getPropertyIfExists( final Properties properties, final String propertyName )
    {
        final String value = properties.getProperty( propertyName );
        // @TODO: Think about the value in case of an not defined property? Better suggestions?
        String result = "";
        if ( StringUtils.isNotBlank( value ) )
        {
            result = value;
        }

        return result;
    }

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

    public void definePropertyWithPrefix( Properties properties, String prefix, String name, String value )
    {
        String propName = name;
        if ( !StringUtils.isBlank( prefix ) )
        {
            propName = prefix + "." + propName;
        }
        properties.put( propName, value );
    }

    public void getOperationSystemProperties( Properties properties, String propertyPrefix )
    {
        definePropertyWithPrefix( properties, propertyPrefix, "os.name", Os.OS_NAME );
        definePropertyWithPrefix( properties, propertyPrefix, "os.family", Os.OS_FAMILY );
        definePropertyWithPrefix( properties, propertyPrefix, "os.version", Os.OS_VERSION );
        definePropertyWithPrefix( properties, propertyPrefix, "os.arch", Os.OS_ARCH );
    }

    public void getJavaProperties( Properties properties, String propertyPrefix )
    {
        final Properties executionProperties = getSession().getExecutionProperties();
        for ( JavaEnvironment item : JavaEnvironment.values() )
        {
            definePropertyWithPrefix( properties, propertyPrefix, item.getValue(),
                                      getPropertyIfExists( executionProperties, item.getValue() ) );
        }
    }

    public void getHostNameProperty( Properties properties, String propertyPrefix )
        throws MojoExecutionException
    {

        try
        {
            definePropertyWithPrefix( properties, propertyPrefix, "hostname", InetAddress.getLocalHost().getHostName() );
        }
        catch ( UnknownHostException e )
        {
            throw new MojoExecutionException( "Unable to retrieve localhost hostname.", e );
        }
    }

    public void getUserNameProperty( Properties properties, String propertyPrefix )
    {
        definePropertyWithPrefix( properties, propertyPrefix, "username", System.getProperty( "user.name" ) );
    }

}
