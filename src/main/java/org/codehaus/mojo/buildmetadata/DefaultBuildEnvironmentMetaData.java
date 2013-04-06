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
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.execution.RuntimeInformation;
import org.apache.maven.model.Profile;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.Os;
import org.codehaus.plexus.util.StringUtils;

public class DefaultBuildEnvironmentMetaData
    implements BuildEnvironmentMetaData
{
    @Component
    private Log log;

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
    private String defaultPropertyValue;

//    public DefaultBuildEnvironmentMetaData()
//    {
//        // For plexus needed.
//    }

//    public DefaultBuildEnvironmentMetaData( Log log, MavenProject project, MavenSession session,
//                                            RuntimeInformation runtime, String defaultPropertyValue )
//    {
//        this.log = log;
//        this.project = project;
//        this.session = session;
//        this.runtime = runtime;
//        this.defaultPropertyValue = defaultPropertyValue;
//    }

    protected void defineProperty( String name, String value )
    {
        if ( getLog().isDebugEnabled() )
        {
            getLog().debug( "define property " + name + " = \"" + value + "\"" );
        }

        project.getProperties().put( name, value );
    }

    public void defineProjectProperty( Properties properties )
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
        String result = defaultPropertyValue;
        if ( StringUtils.isBlank( defaultPropertyValue ) )
        {
            result = "";
        }

        if ( StringUtils.isNotBlank( value ) )
        {
            result = value;
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#definePropertyWithPrefix(java.util.Properties,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    public void definePropertyWithPrefix( Properties properties, String prefix, String name, String value )
    {
        String propName = name;
        if ( !StringUtils.isBlank( prefix ) )
        {
            propName = prefix + "." + propName;
        }
        properties.put( propName, value );
    }

    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getOperationSystemPropertiesWithoutPrefix(java.util
     * .Properties)
     */
    public void getOperationSystemPropertiesWithoutPrefix( Properties properties )
    {
        getOperationSystemProperties( properties, null );
    }

    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getOperationSystemProperties(java.util.Properties,
     * java.lang.String)
     */
    public void getOperationSystemProperties( Properties properties, String propertyPrefix )
    {
        definePropertyWithPrefix( properties, propertyPrefix, "os.name", Os.OS_NAME );
        definePropertyWithPrefix( properties, propertyPrefix, "os.family", Os.OS_FAMILY );
        definePropertyWithPrefix( properties, propertyPrefix, "os.version", Os.OS_VERSION );
        definePropertyWithPrefix( properties, propertyPrefix, "os.arch", Os.OS_ARCH );
    }

    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getJavaPropertiesWithoutPrefix(java.util.Properties)
     */
    public void getJavaPropertiesWithoutPrefix( Properties properties )
    {
        getJavaProperties( properties, null );
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getJavaProperties(java.util.Properties,
     * java.lang.String)
     */
    public void getJavaProperties( Properties properties, String propertyPrefix )
    {
        final Properties executionProperties = getSession().getExecutionProperties();
        for ( JavaEnvironment item : JavaEnvironment.values() )
        {
            definePropertyWithPrefix( properties, propertyPrefix, item.getValue(),
                                      getPropertyIfExists( executionProperties, item.getValue() ) );
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getHostNamePropertyWithoutPrefix(java.util.Properties
     * )
     */
    public void getHostNamePropertyWithoutPrefix( Properties properties )
    {
        getHostNameProperty( properties, null );
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getHostNameProperty(java.util.Properties,
     * java.lang.String)
     */
    public void getHostNameProperty( Properties properties, String propertyPrefix )
    {

        try
        {
            definePropertyWithPrefix( properties, propertyPrefix, "hostname", InetAddress.getLocalHost().getHostName() );
        }
        catch ( UnknownHostException e )
        {
            getLog().error( "Unable to retrieve localhost hostname.", e );
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getUserNamePropertyWithoutPrefix(java.util.Properties
     * )
     */
    public void getUserNamePropertyWithoutPrefix( Properties properties )
    {
        getUserNameProperty( properties, null );
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getUserNameProperty(java.util.Properties,
     * java.lang.String)
     */
    public void getUserNameProperty( Properties properties, String propertyPrefix )
    {
        definePropertyWithPrefix( properties, propertyPrefix, "username", System.getProperty( "user.name" ) );
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getMavenVersionProperty(java.util.Properties,
     * java.lang.String)
     */
    public void getMavenVersionProperty( Properties properties, String propertyPrefix )
    {
        ArtifactVersion mavenVersion = getRuntime().getApplicationVersion();
        definePropertyWithPrefix( properties, propertyPrefix, "version", mavenVersion.toString() );

    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getMavenActiveProfiles(java.util.Properties,
     * java.lang.String)
     */
    public void getMavenActiveProfiles( Properties properties, String propertyPrefix )
    {

        final List<Profile> profiles = getActiveProfiles();

        if ( profiles == null || profiles.isEmpty() )
        {
            return;
        }

        List<String> profileIds = new ArrayList<String>();

        for ( Profile profile : profiles )
        {
            // TODO: Add the source of the profile!! like run-its:settings.xml !!
            String profileId = profile.getId();
            if ( !profileIds.contains( profileId ) )
            {
                profileIds.add( profileId );
            }
        }

        definePropertyWithPrefix( properties, propertyPrefix, "active.profiles",
                                  StringUtils.join( profileIds.iterator(), "," ) );
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getExecutionProperties(java.util.Properties,
     * java.lang.String)
     */
    public void getExecutionProperties( Properties properties, String propertyPrefix )
    {

        Properties executionProperties = getSession().getExecutionProperties();
        final Set<Object> sortedKeys = new TreeSet<Object>();
        sortedKeys.addAll( executionProperties.keySet() );
        for ( final Object originalKey : sortedKeys )
        {
            final String value = executionProperties.getProperty( (String) originalKey );
            definePropertyWithPrefix( properties, propertyPrefix, "execution.properties." + originalKey, value );
        }

    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getMavenGoals(java.util.Properties,
     * java.lang.String)
     */
    public void getMavenGoals( Properties properties, String propertyPrefix )
    {
        definePropertyWithPrefix( properties, propertyPrefix, "execution.goals",
                                  StringUtils.join( getSession().getGoals().iterator(), "," ) );

    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getMavenOpts(java.util.Properties,
     * java.lang.String)
     */
    public void getMavenOpts( Properties properties, String propertyPrefix )
    {
        final String value = getPropertyIfExists( getSession().getExecutionProperties(), "env.MAVEN_OPTS" );

        definePropertyWithPrefix( properties, propertyPrefix, "execution.opts", value );

    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getMavenCommandLine(java.util.Properties,
     * java.lang.String)
     */
    public void getMavenCommandLine( Properties properties, String propertyPrefix )
    {
        final String value = getPropertyIfExists( getSession().getExecutionProperties(), "env.MAVEN_CMD_LINE_ARGS" );

        definePropertyWithPrefix( properties, propertyPrefix, "execution.cmdline", value );
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getActiveProfiles()
     */
    @SuppressWarnings( "unchecked" )
    public List<Profile> getActiveProfiles()
    {
        return (List<Profile>) getProject().getActiveProfiles();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getJavaOptsPropertyWithoutPrefix(java.util.Properties
     * )
     */
    public void getJavaOptsPropertyWithoutPrefix( Properties properties )
    {
        getJavaOptsProperties( properties, null );
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getJavaOptsProperties(java.util.Properties,
     * java.lang.String)
     */
    public void getJavaOptsProperties( Properties properties, String propertyPrefix )
    {
        final String value = getPropertyIfExists( getSession().getExecutionProperties(), "env.JAVA_OPTS" );

        definePropertyWithPrefix( properties, propertyPrefix, "java.opts", value );

    }

    /*
     * (non-Javadoc)
     * @see
     * org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getMavenPropertiesWithoutPrefix(java.util.Properties
     * )
     */
    public void getMavenPropertiesWithoutPrefix( Properties buildEnvironmentProperties )
    {
        getMavenProperties( buildEnvironmentProperties, null );
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getMavenProperties(java.util.Properties,
     * java.lang.String)
     */
    public void getMavenProperties( Properties buildEnvironmentProperties, String propertyPrefix )
    {
        getMavenVersionProperty( buildEnvironmentProperties, propertyPrefix );
        getMavenActiveProfiles( buildEnvironmentProperties, propertyPrefix );
        getMavenGoals( buildEnvironmentProperties, propertyPrefix );
        getMavenOpts( buildEnvironmentProperties, propertyPrefix );
        getMavenCommandLine( buildEnvironmentProperties, propertyPrefix );
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getProject()
     */
    public MavenProject getProject()
    {
        return this.project;
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getSession()
     */
    public MavenSession getSession()
    {
        return session;
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getRuntime()
     */
    public RuntimeInformation getRuntime()
    {
        return runtime;
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.buildmetadata.BuildEnvironmentMetaDataImpl#getLog()
     */
    public Log getLog()
    {
        return log;
    }

}
