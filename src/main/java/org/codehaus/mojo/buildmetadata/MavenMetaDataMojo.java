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

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.execution.RuntimeInformation;
import org.apache.maven.model.Profile;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

/**
 * Get maven-metadata which means the version of Maven, the command line the goals which have been used or active
 * profiles etc.
 * 
 * @author XXX
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
    private String versionProperty;

    /**
     * Main plugin execution
     */
    public void execute()
    {
        Properties buildEnvironmentProperties = new Properties();

        getMavenVersionProperty( buildEnvironmentProperties, versionProperty );
        getActiveProfiles( buildEnvironmentProperties, versionProperty );
        
        defineProjectProperty( buildEnvironmentProperties );
        
    }

    public void getMavenVersionProperty(Properties properties, String propertyPrefix) {
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

        definePropertyWithPrefix( properties, propertyPrefix, "profiles", StringUtils.join( profileIds.iterator(), "," ) );
    }

    @SuppressWarnings( "unchecked" )
    public List<Profile> getActiveProfiles()
    {
        return (List<Profile>) getProject().getActiveProfiles();
    }
}