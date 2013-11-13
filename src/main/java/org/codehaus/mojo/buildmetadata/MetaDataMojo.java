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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Retrieve the metadata Java, Java OPTS, operation system, maven information and provide it as properties accessible
 * in the project.
 * 
 * @author <a href="codehaus@soebes.de">Karl-Heinz Marbaise</a>
 */
@Mojo( name = "metadata", defaultPhase = LifecyclePhase.INITIALIZE, threadSafe = true )
public class MetaDataMojo
    extends AbstractDefinePropertyMojo
{

    /**
     * The name of the property in which to store the local user name.
     */
    @Parameter( defaultValue = "build.metadata" )
    private String propertyPrefix;

    /**
     * Define the output file which contains the full list of build environment properties.
     */
    @Parameter( defaultValue = "${project.build.directory}/build-properties.xml" )
    private File outputXMLFile;

    /* (non-Javadoc)
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    public void execute()
        throws MojoExecutionException
    {

        Properties buildEnvironmentProperties = new Properties();
        BuildEnvironmentMetaData buildEnvironment =
            new BuildEnvironmentMetaData( getLog(), getProject(), getSession(), getRuntime(), getDefaultPropertyValue() );

        buildEnvironment.getAllProperties( buildEnvironmentProperties, propertyPrefix );

        buildEnvironment.defineProjectProperty( buildEnvironmentProperties );

        try
        {
            File path = outputXMLFile.getParentFile();

            if ( !path.exists() )
            {
                path.mkdirs();
            }

            if ( !outputXMLFile.exists() )
            {
                outputXMLFile.createNewFile();
            }
            writePropertiesToTextFile( buildEnvironmentProperties );
        }
        catch ( IOException e )
        {
            getLog().error( "Problem during writing of property file", e );
        }
    }

    public void writePropertiesToTextFile( Properties buildEnvironment )
        throws IOException
    {
        FileOutputStream fos = new FileOutputStream( outputXMLFile );
        buildEnvironment.store( fos, null );
    }

    public void writePropertiesToXMLFile( Properties buildEnvironment )
        throws IOException
    {
        FileOutputStream fos = new FileOutputStream( outputXMLFile );
        buildEnvironment.storeToXML( fos, null );
    }
}
