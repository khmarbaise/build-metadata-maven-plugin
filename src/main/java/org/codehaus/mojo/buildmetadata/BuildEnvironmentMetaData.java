package org.codehaus.mojo.buildmetadata;

import java.util.List;
import java.util.Properties;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.execution.RuntimeInformation;
import org.apache.maven.model.Profile;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

public interface BuildEnvironmentMetaData
{
    
    String ROLE = BuildEnvironmentMetaData.class.getName();

    void defineProjectProperty( Properties properties );

    void definePropertyWithPrefix( Properties properties, String prefix, String name, String value );

    void getOperationSystemPropertiesWithoutPrefix( Properties properties );

    void getOperationSystemProperties( Properties properties, String propertyPrefix );

    void getJavaPropertiesWithoutPrefix( Properties properties );

    void getJavaProperties( Properties properties, String propertyPrefix );

    void getHostNamePropertyWithoutPrefix( Properties properties );

    void getHostNameProperty( Properties properties, String propertyPrefix );

    void getUserNamePropertyWithoutPrefix( Properties properties );

    void getUserNameProperty( Properties properties, String propertyPrefix );

    void getMavenVersionProperty( Properties properties, String propertyPrefix );

    void getMavenActiveProfiles( Properties properties, String propertyPrefix );

    void getExecutionProperties( Properties properties, String propertyPrefix );

    void getMavenGoals( Properties properties, String propertyPrefix );

    void getMavenOpts( Properties properties, String propertyPrefix );

    void getMavenCommandLine( Properties properties, String propertyPrefix );

    List<Profile> getActiveProfiles();

    void getJavaOptsPropertyWithoutPrefix( Properties properties );

    void getJavaOptsProperties( Properties properties, String propertyPrefix );

    void getMavenPropertiesWithoutPrefix( Properties buildEnvironmentProperties );

    void getMavenProperties( Properties buildEnvironmentProperties, String propertyPrefix );

    /**
     * Get the current project instance.
     * 
     * @return the project
     */
    MavenProject getProject();

    MavenSession getSession();

    RuntimeInformation getRuntime();

    Log getLog();

}