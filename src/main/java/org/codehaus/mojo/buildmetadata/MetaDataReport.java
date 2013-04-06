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
import java.util.Locale;

import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.execution.RuntimeInformation;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.i18n.I18N;

/**
 * Retrieve current username and place it under a configurable project property
 * 
 * @author <a href="kama@soebes.de">Karl-Heinz Marbaise</a>
 */
@Mojo( name = "metadatareport", requiresProject = true, requiresReports = true, defaultPhase = LifecyclePhase.SITE, threadSafe = true )
public class MetaDataReport
    extends AbstractMavenReport
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

    @Component
    private Renderer siteRenderer;

    @Component
    private I18N i18n;

    @Component
    private BuildEnvironmentMetaData buildEnvironment;    
    
    /**
     * The output directory for the report. Note that this parameter is only evaluated if the goal is run directly from
     * the command line. If the goal is run indirectly as part of a site generation, the output directory configured in
     * the Maven Site Plugin is used instead.
     * 
     * @parameter default-value="${project.reporting.outputDirectory}"
     * @required
     */
    private File outputDirectory;

    /**
     * {@inheritDoc}
     */
    public boolean isExternalReport()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean canGenerateReport()
    {
        return true;
    }

    public String getDescription( Locale arg0 )
    {
        return "Description";
    }

    public String getName( Locale arg0 )
    {
        return "Build Metadata Report";
    }

    public String getOutputName()
    {

        return "build-metadata-report";
    }

    @Override
    protected void executeReport( Locale locale )
        throws MavenReportException
    {
//        BuildEnvironmentMetaData buildEnvironment =
//            new DefaultBuildEnvironmentMetaData( getLog(), project, session, runtime, "" );
        MetaDataRenderer renderer =
            new MetaDataRenderer( getSink(), getOutputName(), getI18n(), locale, buildEnvironment );
        renderer.render();
    }

    @Override
    protected String getOutputDirectory()
    {
        if ( !outputDirectory.isAbsolute() )
        {
            outputDirectory = new File( project.getBasedir(), outputDirectory.getPath() );
        }

        return outputDirectory.getAbsolutePath();
    }

    @Override
    protected MavenProject getProject()
    {
        return project;
    }

    @Override
    protected Renderer getSiteRenderer()
    {
        return siteRenderer;
    }

    public I18N getI18n()
    {
        return i18n;
    }

}
