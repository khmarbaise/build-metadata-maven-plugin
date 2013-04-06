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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.execution.RuntimeInformation;
import org.apache.maven.model.Profile;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

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
     * The RuntimeInforamtion for the current instance of Maven.
     */
    @Component
    private RuntimeInformation runtime;

    /**
     * The name of the property in which to store the version of Maven.
     */
    @Parameter( defaultValue = "maven" )
    private String propertyPrefix;

    /**
     * Main plugin execution
     */
    public void execute()
    {
        Properties buildEnvironmentProperties = new Properties();

        getMavenVersionProperty( buildEnvironmentProperties, propertyPrefix );
        getActiveProfiles( buildEnvironmentProperties, propertyPrefix );
        getGoals( buildEnvironmentProperties, propertyPrefix );
        getMavenOpts( buildEnvironmentProperties, propertyPrefix );
        getMavenCommandLine( buildEnvironmentProperties, propertyPrefix );
        //@TODO: Think about making the following call dependent on an option which is false by default. 
        getExecutionProperties( buildEnvironmentProperties, propertyPrefix );

        defineProjectProperty( buildEnvironmentProperties );

    }

    public void getMavenVersionProperty( Properties properties, String propertyPrefix )
    {
        ArtifactVersion mavenVersion = runtime.getApplicationVersion();
        definePropertyWithPrefix( properties, propertyPrefix, "version", mavenVersion.toString() );

    }

    public void getActiveProfiles( Properties properties, String propertyPrefix )
    {

        final List<Profile> profiles = getActiveProfiles();

        if ( profiles == null || profiles.isEmpty() )
        {
            return;
        }

        List<String> profileIds = new ArrayList<String>();

        for ( Profile profile : profiles )
        {
            String profileId = profile.getId();
            if ( !profileIds.contains( profileId ) )
            {
                profileIds.add( profileId );
            }
        }

        definePropertyWithPrefix( properties, propertyPrefix, "execution.profiles.active",
                                  StringUtils.join( profileIds.iterator(), "," ) );
    }

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

    public void getGoals( Properties properties, String propertyPrefix )
    {
        definePropertyWithPrefix( properties, propertyPrefix, "execution.goals",
                                  StringUtils.join( getSession().getGoals().iterator(), "," ) );

    }

    public void getMavenOpts( Properties properties, String propertyPrefix )
    {
        final String value = getPropertyIfExists( getSession().getExecutionProperties(), "env.MAVEN_OPTS" );

        definePropertyWithPrefix( properties, propertyPrefix, "execution.opts", value );

    }

    public void getMavenCommandLine( Properties properties, String propertyPrefix )
    {
        final String value = getPropertyIfExists( getSession().getExecutionProperties(), "env.MAVEN_CMD_LINE_ARGS" );

        definePropertyWithPrefix( properties, propertyPrefix, "execution.cmdline", value );
    }

    @SuppressWarnings( "unchecked" )
    public List<Profile> getActiveProfiles()
    {
        return (List<Profile>) getProject().getActiveProfiles();
    }
}