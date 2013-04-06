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

import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.reporting.AbstractMavenReportRenderer;
import org.codehaus.plexus.i18n.I18N;

public class MetaDataRenderer
    extends AbstractMavenReportRenderer
{

    /**
     * The locale we are rendering for.
     */
    private final Locale locale;

    private final I18N i18n;

    /**
     * The name of the bundle containing our I18n resources.
     */
    private final String bundleName;

    private final BuildEnvironmentMetaData buildEnvironmentMetaData;

    public MetaDataRenderer( Sink sink, String bundleName, I18N i18n, Locale locale,
                             BuildEnvironmentMetaData buildEnvironmentMetaData )
    {
        super( sink );
        this.i18n = i18n;
        this.bundleName = bundleName;
        this.locale = locale;

        this.buildEnvironmentMetaData = buildEnvironmentMetaData;

    }

    @Override
    public void render()
    {
        sink.head();

        sink.title();
        text( "Build metadata Report" );
        sink.title_();

        sink.head_();

        sink.body();
        renderBody();
        sink.body_();

        sink.flush();

        sink.close();
    }

    @Override
    public String getTitle()
    {
        return getText( "report.title" );
    }

    public String getText( String key )
    {
        return i18n.getString( getBundleName(), getLocale(), key );
    }

    @Override
    protected void renderBody()
    {
        sink.section1();

        sink.sectionTitle1();
        sink.text( "Build metadata Report" );
        sink.sectionTitle1_();

        sink.section1_();

        // TODO: Make a report section about build time (start of build!)

        Properties javaProperties = new Properties();
        getBuildEnvironmentMetaData().getJavaOptsPropertyWithoutPrefix( javaProperties );
        getBuildEnvironmentMetaData().getJavaPropertiesWithoutPrefix( javaProperties );

        renderJavaRuntime( javaProperties );

        Properties buildServerProperties = new Properties();
        getBuildEnvironmentMetaData().getHostNamePropertyWithoutPrefix( buildServerProperties );
        getBuildEnvironmentMetaData().getUserNamePropertyWithoutPrefix( buildServerProperties );

        sink.section2();
        sink.sectionTitle2();
        sink.text( "Build Server Information" );
        sink.sectionTitle2_();

        renderSection3( "Username / Host", buildServerProperties );
        
        Properties operationSystemProperties = new Properties();
        getBuildEnvironmentMetaData().getOperationSystemPropertiesWithoutPrefix( operationSystemProperties);

        renderSection3( "Operation System", operationSystemProperties );
        
//        renderBuildServer( buildServerProperties );

        Properties mavenProperties = new Properties();
        getBuildEnvironmentMetaData().getMavenPropertiesWithoutPrefix( mavenProperties );

        renderMavenInformation( mavenProperties );

    }

    public void renderSection2( String sectionTitle, Properties properties )
    {
        sink.section2();
        sink.sectionTitle2();
        sink.text( sectionTitle );
        sink.sectionTitle2_();

        sink.table();
        sink.tableRow();
        headerCell( sink, "Field" );
        headerCell( sink, "Value" );
        sink.tableRow_();

        @SuppressWarnings( { "unchecked", "rawtypes" } )
        SortedSet<Object> sortedSet = new TreeSet( properties.keySet() );

        for ( Iterator<Object> iterator = sortedSet.iterator(); iterator.hasNext(); )
        {
            String key = (String) iterator.next();

            sink.tableRow();
            cell( sink, (String) key );
            cell( sink, (String) properties.getProperty( key ) );
            sink.tableRow_();
        }

        sink.table_();

        sink.section2_();

    }

    public void renderSection3( String sectionTitle, Properties properties )
    {
        sink.section3();
        sink.sectionTitle3();
        sink.text( sectionTitle );
        sink.sectionTitle3_();

        sink.table();
        sink.tableRow();
        headerCell( sink, "Field" );
        headerCell( sink, "Value" );
        sink.tableRow_();

        @SuppressWarnings( { "unchecked", "rawtypes" } )
        SortedSet<Object> sortedSet = new TreeSet( properties.keySet() );

        for ( Iterator<Object> iterator = sortedSet.iterator(); iterator.hasNext(); )
        {
            String key = (String) iterator.next();

            sink.tableRow();
            cell( sink, (String) key );
            cell( sink, (String) properties.getProperty( key ) );
            sink.tableRow_();
        }

        sink.table_();

        sink.section3_();

    }
    
    public void renderJavaRuntime( Properties properties )
    {
        renderSection2( "Java Runtime Information", properties );
    }

    public void renderBuildServer( Properties properties )
    {
        renderSection2( "Build Server Information", properties );
    }

    public void renderMavenInformation( Properties properties )
    {
        renderSection2( "Maven Information", properties );
    }

    private void cell( Sink sink, String text )
    {
        sink.tableCell();
        sink.text( text );
        sink.tableCell_();
    }

    private void headerCell( Sink sink, String text )
    {
        sink.tableHeaderCell();
        sink.text( text );
        sink.tableHeaderCell_();
    }

    public I18N getI18n()
    {
        return i18n;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public String getBundleName()
    {
        return bundleName;
    }

    public BuildEnvironmentMetaData getBuildEnvironmentMetaData()
    {
        return buildEnvironmentMetaData;
    }

}
